import java.io.*;
import java.util.ArrayList;
import java.util.List;

class ServerController {
    private static final String LOG_PATH = "src/log.txt"; // Файл для сохранения чата
    private List<ClientController> clientControllers;
    private boolean work;
    private ServerWindow serverWindow;

    public ServerController() {
        clientControllers = new ArrayList<>();
    }

    public void setServerWindow(ServerWindow serverWindow) {
        this.serverWindow = serverWindow;
        loadLog(); // Загружаем лог в GUI при старте
    }

    public void startServer() {
        work = true;
        appendLog("Сервер запущен!");
    }

    public void stopServer() {
        work = false;
        clientControllers.clear();
        appendLog("Сервер остановлен!");
    }

    public boolean connectUser(ClientController controller) {
        if (!work) return false;

        // Добавляем клиента в список
        clientControllers.add(controller);

        // Отправляем всю историю сообщений (лог) новому клиенту
        sendLogToClient(controller);

        appendLog("Клиент подключён: " + controller);
        return true;
    }

    private void sendLogToClient(ClientController clientController) {
        try (BufferedReader reader = new BufferedReader(new FileReader(LOG_PATH))) {
            String line;
            while ((line = reader.readLine()) != null) {
                clientController.appendLog(line); // Отправляем каждое сообщение клиенту
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void disconnectUser(ClientController controller) {
        clientControllers.remove(controller);
        appendLog("Клиент отключён: " + controller);
    }

    public void message(String text) {
        if (!work) return;
        appendLog(text);
        for (ClientController client : clientControllers) {
            client.appendLog(text);
        }
        saveInLog(text);
    }

    private void appendLog(String text) {
        if (serverWindow != null) {
            serverWindow.appendLog(text);
        }
    }

    private void saveInLog(String text) {
        try (FileWriter writer = new FileWriter(LOG_PATH, true)) {
            writer.write(text + "\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void loadLog() {
        try (BufferedReader reader = new BufferedReader(new FileReader(LOG_PATH))) {
            String line;
            while ((line = reader.readLine()) != null) {
                appendLog(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
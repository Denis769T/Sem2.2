public class ClientController {
    private ClientGUI clientGUI;
    private ServerController serverController;
    private boolean connected;
    private String name;

    public ClientController(ServerController serverController) { // Изменён параметр
        this.serverController = serverController;
        this.clientGUI = new ClientGUI(this);  // Передаём ссылку на контроллер в GUI
    }

    public void connectToServer(String ipAddress, String port, String login, String password) {
        if (serverController.connectUser(this)) { // Вызываем метод у ServerController
            connected = true;
            name = login;
            appendLog("Вы успешно подключились!\n");
        } else {
            appendLog("Подключение не удалось\n");
        }
    }

    public void disconnectFromServer() {
        if (connected) {
            connected = false;
            serverController.disconnectUser(this); // Вызываем метод у ServerController
            appendLog("Вы были отключены от сервера!\n");
        }
    }

    public void sendMessage(String message) {
        if (connected && !message.isEmpty()) {
            serverController.message(name + ": " + message); // Вызываем метод у ServerController
        } else {
            appendLog("Нет подключения к серверу\n");
        }
    }

    public void appendLog(String text) {
        clientGUI.appendLog(text);
    }
}
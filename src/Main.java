//На семинаре мы разделили класс отвечающий за клиентское приложение на класс, отвечающий
//за логику приложения и за графическую часть приложения. А также создали слабую связь между ними с помощью
//интерфейса.
//
//Аналогичным образом вам надо преобразовать серверную часть приложения. Схема, которую требуется реализовать,
//также есть в материалах к уроку.
//Вы можете работать со своим проектом из первой домашки, а можете работать с проектом
//с семинара (ссылка в материалах к уроку).
//
//Требуется разделить класс серверного приложения на контроллер, GUI и репозиторий.
//Если вы работаете со своим проектом, то клиентскую часть также надо разделить на контроллер и GUI.
//Связь между составляющими проекта реализовать с помощью интерфейсов





public class Main {
    public static void main(String[] args) {
        ServerController serverController = new ServerController();
        ServerWindow serverWindow = new ServerWindow(serverController);
        serverController.setServerWindow(serverWindow); // Добавляем связь контроллера и GUI

        ClientController client1 = new ClientController(serverController);
        ClientController client2 = new ClientController(serverController);
    }
}
// ClientGUI.java (отвечает только за интерфейс)
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class ClientGUI extends JFrame {
    public static final int WIDTH = 400;
    public static final int HEIGHT = 300;

    private JTextArea log;
    private JTextField tfIPAddress, tfPort, tfLogin, tfMessage;
    private JPasswordField password;
    private JButton btnLogin, btnSend;
    private JPanel headerPanel;
    private ClientController controller;

    public ClientGUI(ClientController controller) {
        this.controller = controller;

        setSize(WIDTH, HEIGHT);
        setResizable(false);
        setTitle("Chat client");
        setLocationRelativeTo(null);

        createPanel();
        setVisible(true);
    }

    public void appendLog(String text) {
        log.append(text + "\n");
    }

    public void toggleHeaderPanel(boolean visible) {
        headerPanel.setVisible(visible);
    }

    private void createPanel() {
        add(createHeaderPanel(), BorderLayout.NORTH);
        add(createLog());
        add(createFooter(), BorderLayout.SOUTH);
    }

    private Component createHeaderPanel() {
        headerPanel = new JPanel(new GridLayout(2, 3));
        tfIPAddress = new JTextField("127.0.0.1");
        tfPort = new JTextField("8189");
        tfLogin = new JTextField("Ivan Ivanovich");
        password = new JPasswordField("123456");
        btnLogin = new JButton("Login");
        btnLogin.addActionListener(e -> controller.connectToServer(tfIPAddress.getText(), tfPort.getText(), tfLogin.getText(), new String(password.getPassword())));

        headerPanel.add(tfIPAddress);
        headerPanel.add(tfPort);
        headerPanel.add(new JPanel());
        headerPanel.add(tfLogin);
        headerPanel.add(password);
        headerPanel.add(btnLogin);

        return headerPanel;
    }

    private Component createLog() {
        log = new JTextArea();
        log.setEditable(false);
        return new JScrollPane(log);
    }

    private Component createFooter() {
        JPanel panel = new JPanel(new BorderLayout());
        tfMessage = new JTextField();
        tfMessage.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                if (e.getKeyChar() == '\n') {
                    controller.sendMessage(tfMessage.getText());
                    tfMessage.setText("");
                }
            }
        });
        btnSend = new JButton("Send");
        btnSend.addActionListener(e -> {
            controller.sendMessage(tfMessage.getText());
            tfMessage.setText("");
        });
        panel.add(tfMessage);
        panel.add(btnSend, BorderLayout.EAST);
        return panel;
    }

    @Override
    protected void processWindowEvent(WindowEvent e) {
        if (e.getID() == WindowEvent.WINDOW_CLOSING) {
            controller.disconnectFromServer();
        }
        super.processWindowEvent(e);
    }
}

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class LogInGUI extends JComponent implements Runnable {
    JLabel usernameLabel;
    JLabel passwordLabel;
    JTextField usernameText;
    JTextField passwordText;
    JButton signInButton;
    JButton registerButton;
    JPanel panel;
    JFrame frame;
    Socket socket;
    public LogInGUI (Socket socket) {
        this.socket = socket;
    }



    ActionListener actionListener = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (e.getSource() == signInButton) {
                String userSignInAttempt = usernameText.getText() + " " + passwordText.getText();
                try {
                    PrintWriter writer = new PrintWriter(socket.getOutputStream());
                    BufferedReader bfr = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                } catch (IOException exception) {
                    JOptionPane.showMessageDialog(null, "Error connecting to server", "Tutor Service",
                            JOptionPane.ERROR_MESSAGE);
                }
            }
            if (e.getSource() == registerButton) {
                frame.dispose();
                RegisterGUI.runRegisterGUI(socket);
            }
        }
    };

    public static void runLogInGUI(Socket socket) {
            SwingUtilities.invokeLater(new LogInGUI(socket));
    }

    public void run() {
        panel = new JPanel();
        panel.setLayout(null);
        frame = new JFrame();
        frame.setTitle("Tutor Login");
        frame.setLocation(new Point(500, 300));
        frame.add(panel);
        frame.setSize(new Dimension(350, 200));
        usernameLabel = new JLabel("Username");
        usernameLabel.setBounds(25, 8, 70, 20);
        panel.add(usernameLabel);
        usernameText = new JTextField(20);
        usernameText.setBounds(25, 30, 200, 20);
        panel.add(usernameText);
        passwordLabel = new JLabel("Password");
        passwordLabel.setBounds(25, 55, 70, 20);
        panel.add(passwordLabel);
        passwordText = new JPasswordField(20);
        passwordText.setBounds(25, 80, 200, 20);
        panel.add(passwordText);
        signInButton = new JButton("Login");
        signInButton.setBounds(25, 110, 100, 30);
        signInButton.addActionListener(actionListener);
        panel.add(signInButton);
        registerButton = new JButton("Sign Up");
        registerButton.setBounds(125, 110, 100, 30);
        registerButton.addActionListener(actionListener);
        panel.add(registerButton);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setVisible(true);

    }
}
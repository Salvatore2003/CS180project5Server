import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
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
    User logInUser;
    PrintWriter writer;
    BufferedReader bfr;
    ObjectInputStream objectInputStream;
    ActionListener actionListener = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (e.getSource() == signInButton) {
                String userUsernameAttempt = usernameText.getText();
                String userPasswordAttempt = passwordText.getText();
                try {
                    writer.write("Logging in");
                    writer.println();
                    writer.flush();
                    writer.write(userUsernameAttempt);
                    writer.println();
                    writer.flush();
                    writer.write(userPasswordAttempt);
                    writer.println();
                    writer.flush();
                    if (bfr.readLine().contains("log in success")) {
                        JOptionPane.showMessageDialog(null, "Successful log in", "Tutor Service",
                                JOptionPane.INFORMATION_MESSAGE);
                        logInUser = (User) objectInputStream.readObject();
                        frame.dispose();
                        UserInterface.runUserInterface(logInUser);

                    } else {
                        System.out.println("no log in");
                        JOptionPane.showMessageDialog(null, "Invalid Username or password", "Tutor Service",
                                JOptionPane.ERROR_MESSAGE);
                    }

                } catch (IOException exception) {
                    JOptionPane.showMessageDialog(null, "Error connecting to server", "Tutor Service",
                            JOptionPane.ERROR_MESSAGE);
                } catch (ClassNotFoundException ex) {
                    JOptionPane.showMessageDialog(null, "Error logging in", "Tutor Service",
                            JOptionPane.ERROR_MESSAGE);
                }
            }
            if (e.getSource() == registerButton) {
                frame.dispose();
                RegisterGUI.runRegisterGUI(socket);
            }
        }
    };


    public LogInGUI(Socket socket) {
        this.socket = socket;
    }

    public static void runLogInGUI(Socket socket) {
        SwingUtilities.invokeLater(new LogInGUI(socket));
    }

    public void run() {
        try {
            writer = new PrintWriter(socket.getOutputStream());
            bfr = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            objectInputStream = new ObjectInputStream(socket.getInputStream());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
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
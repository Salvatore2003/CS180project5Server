import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.net.Socket;
/**
 * LogInGUI
 *
 * This GUI allows for a user to log in. If a user does not have an account they can make an account.
 *
 * @author Bryce LaMarca, Lab 25
 *
 * @version 4/30/2023
 *
 */

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
    ObjectOutputStream objectOutputStream;
    ActionListener actionListener = new ActionListener() {
        /**
         * waits for a button to be pressed and then executes apporiate steps
         * @param e the event to be processed
         */
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
                        JOptionPane.showMessageDialog(null, "Successful log in",
                                "Tutor Service", JOptionPane.INFORMATION_MESSAGE);
                        logInUser = (User) objectInputStream.readObject();
                        frame.dispose();
                        UserInterface.runUserInterface(logInUser, socket, writer, bfr, objectInputStream,
                                objectOutputStream);

                    } else {
                        JOptionPane.showMessageDialog(null, "Invalid Username or password",
                                "Tutor Service", JOptionPane.ERROR_MESSAGE);
                    }

                } catch (IOException exception) {
                    JOptionPane.showMessageDialog(null,
                            "Error connecting to server", "Tutor Service", JOptionPane.ERROR_MESSAGE);
                } catch (ClassNotFoundException ex) {
                    JOptionPane.showMessageDialog(null, "Error logging in", "Tutor Service",
                            JOptionPane.ERROR_MESSAGE);
                }
            }
            if (e.getSource() == registerButton) {
                frame.dispose();
                RegisterGUI.runRegisterGUI(socket, bfr, writer, objectInputStream, objectOutputStream);
            }
        }
    };
    public LogInGUI(Socket socket, BufferedReader bfr, PrintWriter writer, ObjectInputStream objectInputStream,
                    ObjectOutputStream objectOutputStream) {
        this.socket = socket;
        this.bfr = bfr;
        this.writer = writer;
        this.objectInputStream = objectInputStream;
        this.objectOutputStream = objectOutputStream;
    }


    /**
     * runs the Log in GUI
     * @param socket the server socket
     * @param bfr the reader
     * @param writer writer to files
     * @param objectInputStream the object input stream that receives objects
     * @param objectOutputStream the object output stream that outputs object
     */
    public static void runLogInGUI(Socket socket, BufferedReader bfr, PrintWriter writer,
                                   ObjectInputStream objectInputStream, ObjectOutputStream objectOutputStream) {
        SwingUtilities.invokeLater(new LogInGUI(socket, bfr, writer, objectInputStream, objectOutputStream));
    }

    /**
     * runs the LogInGUI
     */
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
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class RegisterGUI extends JComponent implements Runnable {
    JPanel panel;
    JFrame frame;
    JLabel usernameLabel;
    JTextField newUsernameText;
    JLabel passwordLabel;
    JTextField userPasswordText;
    JLabel confirmPasswordLabel;
    JTextField confirmPasswordText;
    JLabel emailLabel;
    JTextField userEmailText;
    JLabel buyerOrSellerLabel;
    ButtonGroup buyerOrSeller;
    JButton registerButton;
    JButton exitButton;
    JRadioButton buyerButton;
    JRadioButton sellerButton;
    Socket socket;
    ActionListener actionListener = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (e.getSource() == registerButton) {
                boolean validUser = true;
                String errors = "";
                String userName = newUsernameText.getText();
                String password = userPasswordText.getText();
                String userEmail = userEmailText.getText();
                String passwordCheck = confirmPasswordText.getText();
                String newUser = "";
                if (userName.length() < 5) {
                    errors += "Username must be 5 letters.\n";
                    validUser = false;
                }
                if (userName.contains(" ")) {
                    errors += "Username cannot contain spaces.\n";
                    validUser = false;
                }
                //same username server io
                if (password.length() < 5) {
                    errors += "Password must be 5 letters.\n";
                    validUser = false;
                }
                if (password.contains(" ")) {
                    errors += "Password cannot contain spaces.\n";
                    validUser = false;
                }
                if (!password.equals(passwordCheck)) {
                    errors += "Passwords must match\n";
                    validUser = false;
                }
                if (userEmail.contains(" ")) {
                    errors += "Email cannot contain spaces.\n";
                    validUser = false;
                }
                if (!userEmail.contains("@")) {
                    errors += "Email must contain @.\n";
                    validUser = false;
                }
                if (!buyerButton.isSelected() && !sellerButton.isSelected()) {
                    errors += "Select buyer or seller.\n";
                    validUser = false;
                }
                System.out.println(validUser);
                if (validUser) {
                    try {
                        String line;
                        PrintWriter writer = new PrintWriter(socket.getOutputStream());
                        BufferedReader bfr = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                        writer.write("New User");
                        writer.println();
                        writer.flush();
                        newUser = userName + " " + password + " " + userEmail + " " + buyerButton.isSelected() +
                                " " + sellerButton.isSelected();
                        System.out.println("wrote");
                        writer.write(newUser);
                        writer.println();
                        writer.flush();
                        line = bfr.readLine();
                        System.out.println(line);
                        if (line.equals("account created")) {
                            JOptionPane.showMessageDialog(null, "Account Successfully Created",
                                    "Account Creation", JOptionPane.INFORMATION_MESSAGE);
                            frame.dispose();
                            LogInGUI.runLogInGUI(socket);
                        } else if (line.equals("username exist error")) {
                            JOptionPane.showMessageDialog(null, "Username already exist",
                                    "Account Creation Error", JOptionPane.ERROR_MESSAGE);
                        } else {
                            JOptionPane.showMessageDialog(null, "Error occurred ",
                                            "while creating account.", JOptionPane.ERROR_MESSAGE);
                        }
                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    }

                } else {
                    JOptionPane.showMessageDialog(null, errors,
                            "Account Creation Error", JOptionPane.ERROR_MESSAGE);
                }

            }
            if (e.getSource() == exitButton) {
                frame.dispose();
                LogInGUI.runLogInGUI(socket);

            }
        }
    };

    public RegisterGUI(Socket socket) {
        this.socket = socket;
    }

    public static void runRegisterGUI(Socket socket) {
        SwingUtilities.invokeLater(new RegisterGUI(socket));
    }

    @Override
    public void run() {
        //sets up the gui
        panel = new JPanel();
        panel.setLayout(null);
        frame = new JFrame();
        frame.setTitle("Tutor Register");
        frame.setLocation(new Point(750, 250));
        frame.add(panel);
        frame.setSize(new Dimension(270, 330));

        //sets up username
        usernameLabel = new JLabel("Username");
        usernameLabel.setBounds(10, 5, 70, 20);
        panel.add(usernameLabel);
        newUsernameText = new JTextField(20);
        newUsernameText.setBounds(10, 30, 200, 20);
        panel.add(newUsernameText);

        //sets up password
        passwordLabel = new JLabel("Password");
        passwordLabel.setBounds(10, 55, 70, 20);
        panel.add(passwordLabel);
        userPasswordText = new JPasswordField(10);
        userPasswordText.setBounds(10, 80, 200, 20);
        panel.add(userPasswordText);

        //sets up confirm password
        confirmPasswordLabel = new JLabel("Confirm Password");
        confirmPasswordLabel.setBounds(10, 105, 200, 20);
        panel.add(confirmPasswordLabel);
        confirmPasswordText = new JPasswordField(200);
        confirmPasswordText.setBounds(10, 130, 200, 20);
        panel.add(confirmPasswordText);

        //sets up email
        emailLabel = new JLabel("Email");
        emailLabel.setBounds(10, 155, 200, 20);
        panel.add(emailLabel);
        userEmailText = new JTextField(10);
        userEmailText.setBounds(10, 180, 200, 20);
        panel.add(userEmailText);

        //sets up buyer or seller
        buyerOrSellerLabel = new JLabel("Enter Role");
        buyerOrSellerLabel.setBounds(10, 205, 200, 20);
        panel.add(buyerOrSellerLabel);
        buyerOrSeller = new ButtonGroup();
        buyerButton = new JRadioButton("Buyer");
        sellerButton = new JRadioButton("Seller");
        buyerOrSeller.add(buyerButton);
        buyerOrSeller.add(sellerButton);
        buyerButton.setBounds(10, 230, 63, 20);
        sellerButton.setBounds(70, 230, 110, 20);
        panel.add(buyerButton);
        panel.add(sellerButton);

        //sets up register button
        registerButton = new JButton("Sign Up");
        registerButton.setBounds(10, 255, 90, 20);
        registerButton.addActionListener(actionListener);
        exitButton = new JButton("Exit");
        exitButton.setBounds(105, 255, 90, 20);
        exitButton.addActionListener(actionListener);
        panel.add(registerButton);
        panel.add(exitButton);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setVisible(true);
    }
}
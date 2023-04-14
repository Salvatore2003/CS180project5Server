import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
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
    public

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
                    JOptionPane.showMessageDialog(null, "Account Successfully Created",
                            "Account Creation", JOptionPane.INFORMATION_MESSAGE);
                    newUser = userName + " " + password + " " + userEmail + " " + buyerButton.isSelected() +
                            " " + sellerButton.isSelected();
                    //PrintWriter printWriter = new PrintWriter(new InputStreamReader());
                    System.out.println(newUser);
                    LogInGUI.runLogInGUI();
                } else {
                    JOptionPane.showMessageDialog(null, errors,
                            "Account Creation Error", JOptionPane.ERROR_MESSAGE);
                }

            }
            if (e.getSource() == exitButton) {
                frame.dispose();
                LogInGUI.runLogInGUI();
            }
        }
    };

    public static void runRegisterGUI() {
        SwingUtilities.invokeLater(new RegisterGUI());
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

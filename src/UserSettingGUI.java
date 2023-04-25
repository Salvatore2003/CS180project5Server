import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.Socket;

public class UserSettingGUI extends JComponent implements Runnable{
    User user;
    JPanel panel;
    JFrame frame;
    JButton editUsername;
    JButton editPassword;
    JButton editEmail;
    JButton editRole;
    JButton exit;
    Socket socket;
    ActionListener actionListener = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (e.getSource() == editUsername){
                String newUsername;
                newUsername = JOptionPane.showInputDialog(null, "Enter new Username",
                        "Edit Username", JOptionPane.QUESTION_MESSAGE);
            }
            if (e.getSource() == editPassword) {
                String newPassword;
                String errors = "";
                boolean validPassword = true;
                newPassword = JOptionPane.showInputDialog(null, "Enter new Password",
                        "Edit Password", JOptionPane.QUESTION_MESSAGE);
                if (newPassword.contains(" ")) {
                    errors += "Password cannot contain spaces.\n";
                    validPassword = false;
                }
                if (newPassword.length() < 5) {
                    errors += "Password must be at least 5 characters.";
                    validPassword = false;
                }
                if (!validPassword) {
                    JOptionPane.showMessageDialog(null, errors, "New password error",
                            JOptionPane.ERROR_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(null, "Successfully changed password",
                            "New Password", JOptionPane.INFORMATION_MESSAGE);
                    user.setPassword(newPassword);
                    System.out.println(user.getPassword());
                }
            }
            if (e.getSource() == editEmail) {
                String newEmail;
                String errors = "";
                boolean validEmail = true;
                newEmail = JOptionPane.showInputDialog(null, "Enter new email",
                        "Edit Email", JOptionPane.QUESTION_MESSAGE);
                if (newEmail.contains(" ")) {
                    errors += "Password cannot contain space.\n";
                }
                if (newEmail.length() < 5) {
                    errors += "Password must be 5 characters long.";
                }
                if (!validEmail) {
                    JOptionPane.showMessageDialog(null, errors, "New email error",
                            JOptionPane.ERROR_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(null, "Successfully changed email", "New email",
                            JOptionPane.INFORMATION_MESSAGE);
                    user.setUserEmail(newEmail);
                }
            }
            if (e.getSource() == editRole) {
                int changeRole;
                changeRole = JOptionPane.showConfirmDialog(null, "Would you like to "
                + "change roles?", "Change role", JOptionPane.YES_NO_OPTION);
                if (changeRole == 0) {
                    if (user.isSeller()) {
                        user.setBuyer(true);
                        user.setSeller(false);
                    } else {
                        user.setBuyer(false);
                        user.setSeller(true);
                    }
                }
            }
            if (e.getSource() == exit) {
                frame.dispose();
                UserInterface.runUserInterface(user, socket);
            }
        }
    };
    public UserSettingGUI(User user, Socket socket) {
        this.user = user;
        this.socket = socket;
    }
    public static void runUserSettingGUI(User user, Socket socket) {

        SwingUtilities.invokeLater(new UserSettingGUI(user, socket));
    }
    @Override
    public void run() {
        panel = new JPanel();
        panel.setLayout(null);
        frame = new JFrame();
        frame.setTitle("Settings");
        frame.setLocation(new Point(750, 280));
        frame.add(panel);
        frame.setSize(new Dimension(270, 230));
        editUsername = new JButton("Edit Username");
        editUsername.setBounds(10, 5, 240, 30);
        editUsername.addActionListener(actionListener);
        editPassword = new JButton("Edit password");
        editPassword.setBounds(10, 40, 240, 30);
        editPassword.addActionListener(actionListener);
        editEmail = new JButton("Edit email");
        editEmail.setBounds(10, 75, 240, 30);
        editEmail.addActionListener(actionListener);
        editRole = new JButton("Edit role");
        editRole.setBounds(10, 110, 240, 30);
        editRole.addActionListener(actionListener);
        exit = new JButton("exit");
        exit.setBounds(10, 145, 240, 30);
        exit.addActionListener(actionListener);

        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        panel.add(editUsername);
        panel.add(editPassword);
        panel.add(editEmail);
        panel.add(editRole);
        panel.add(exit);
        frame.setVisible(true);
    }
}

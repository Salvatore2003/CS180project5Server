import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
public class UserSettingGUI extends JComponent implements Runnable{
    User user;
    JPanel panel;
    JFrame frame;
    JButton editUsername;
    JButton editPassword;
    JButton editEmail;
    JButton editRole;
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
                newPassword = JOptionPane.showInputDialog(null, "Enter new Password",
                        "Edit Password", JOptionPane.QUESTION_MESSAGE);
            }
            if (e.getSource() == editEmail) {
                String newEmail;
                newEmail = JOptionPane.showInputDialog(null, "Enter new email",
                        "Edit Email", JOptionPane.QUESTION_MESSAGE);
            }
            if (e.getSource() == editRole) {
                int changeRole;
                changeRole = JOptionPane.showConfirmDialog(null, "Would you like to "
                + "change roles?", "Change role", JOptionPane.YES_NO_OPTION);
                System.out.println(changeRole);
            }
        }
    };
    public UserSettingGUI(User user) {
        this.user = user;
    }
    public static void main(String[] args) {
        User user = new User("Bryce", "password", "e@", true, false);
        SwingUtilities.invokeLater(new UserSettingGUI(user));
    }
    @Override
    public void run() {
        panel = new JPanel();
        panel.setLayout(null);
        frame = new JFrame();
        frame.setTitle("Settings");
        frame.setLocation(new Point(750, 250));
        frame.add(panel);
        frame.setSize(new Dimension(270, 330));
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
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        panel.add(editUsername);
        panel.add(editPassword);
        panel.add(editEmail);
        panel.add(editRole);
        frame.setVisible(true);
    }
}

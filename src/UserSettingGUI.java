import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.net.Socket;

public class UserSettingGUI extends JComponent implements Runnable{
    User user;
    JPanel panel;
    JFrame frame;
    JButton editUsername;
    JButton editPassword;
    JButton editEmail;
    JButton editRole;
    JButton deleteUser;
    JButton exit;
    Socket socket;
    PrintWriter writer;
    BufferedReader bfr;
    ObjectInputStream objectInputStream;
    ObjectOutputStream objectOutputStream;
    ActionListener actionListener = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (e.getSource() == editUsername){
                String newUsername;
                String errors = "";
                String userNameExistResult = "";
                newUsername = JOptionPane.showInputDialog(null, "Enter new Username",
                        "Edit Username", JOptionPane.QUESTION_MESSAGE);
                if(newUsername.contains(" ")) {
                    errors += "Username cannot contain spaces.\n";
                }
                if (newUsername.length() < 5){
                    errors += "Username must be at least 5 characters long.";
                }
                if (newUsername.equals(user.getUserName())) {
                    errors += "Entered current username. Please enter a different username.";

                } else {
                    writer.write("check for existing usernames");
                    writer.println();
                    writer.flush();
                    writer.write(newUsername);
                    writer.println();
                    writer.flush();
                    try {
                        userNameExistResult = bfr.readLine();
                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    }
                    if (userNameExistResult.equals("username exist")) {
                        errors += "Username already exist. Please try another one.";
                    }
                }
                if (errors.equals("")) {

                    changeUserInfo("username", newUsername);
                    user.setUserName(newUsername);

                    JOptionPane.showMessageDialog(null, "Username has been changed.",
                            "Change Username", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(null, errors, "Change username errors",
                            JOptionPane.ERROR_MESSAGE);
                }


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
                    changeUserInfo("password", newPassword);
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
                    changeUserInfo("email", newEmail);
                    /*writer.write("changing user info");
                    writer.println();
                    writer.flush();
                    writer.write("email");
                    writer.println();
                    writer.flush();
                    writer.write(newEmail);
                    writer.println();
                    writer.flush();
                    try {
                        System.out.println(user.toString());
                        objectOutputStream.writeObject(user);
                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    }*/
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
                    changeUserInfo("role", "none");
                }
            }
            if (e.getSource() == deleteUser) {
                int userConfirmation;
                userConfirmation = JOptionPane.showConfirmDialog(null, "Are you sure you" +
                        " want to delete your account?", "Delete Account", JOptionPane.YES_NO_OPTION);
                if (userConfirmation == 0) {
                    changeUserInfo("delete account", "none");
                    frame.dispose();
                    LogInGUI.runLogInGUI(socket, bfr, writer, objectInputStream, objectOutputStream);
                }
            }
            if (e.getSource() == exit) {
                UserInterface.runUserInterface(user, socket, writer, bfr, objectInputStream, objectOutputStream);
                frame.dispose();
            }
        }
    };
    public UserSettingGUI(User user, Socket socket, PrintWriter writer, BufferedReader bfr,
                          ObjectInputStream objectInputStream, ObjectOutputStream objectOutputStream) {
        this.user = user;
        this.socket = socket;
        this.writer = writer;
        this.bfr = bfr;
        this.objectInputStream = objectInputStream;
        this.objectOutputStream = objectOutputStream;

    }
    public static void runUserSettingGUI(User user, Socket socket, PrintWriter writer, BufferedReader bfr,
                                         ObjectInputStream objectInputStream, ObjectOutputStream objectOutputStream) {

        SwingUtilities.invokeLater(new UserSettingGUI(user, socket, writer, bfr, objectInputStream, objectOutputStream));
    }
    @Override
    public void run() {
        panel = new JPanel();
        panel.setLayout(null);
        frame = new JFrame();
        frame.setTitle("Settings");
        frame.setLocation(new Point(750, 280));
        frame.add(panel);
        frame.setSize(new Dimension(265, 270));
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
        deleteUser = new JButton("Delete User");
        deleteUser.setBounds(10, 145, 240, 30);
        deleteUser.addActionListener(actionListener);
        exit = new JButton("exit");
        exit.setBounds(10, 180, 240, 30);
        exit.addActionListener(actionListener);

        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        panel.add(editUsername);
        panel.add(editPassword);
        panel.add(editEmail);
        panel.add(editRole);
        panel.add(deleteUser);
        panel.add(exit);
        frame.setVisible(true);
    }
    public void changeUserInfo(String infoChanging, String newInfo) {
        writer.write("changing user info");
        writer.println();
        writer.flush();
        writer.write(infoChanging);
        writer.println();
        writer.flush();
        writer.write(newInfo);
        writer.println();
        writer.flush();
        try {
            System.out.println("execute 1");
            System.out.println(user.getUserName());
            objectOutputStream.writeObject(user);
            objectOutputStream.reset();
            System.out.println("execute 2");
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }
}

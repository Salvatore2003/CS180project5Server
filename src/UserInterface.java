import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.net.Socket;

public class UserInterface extends JComponent implements Runnable{
    JPanel panel;
    JFrame frame;
    User user;
    JButton settingButton;
    JButton stores;
    JButton messages;
    JButton logOut;
    Socket socket;
    PrintWriter writer;
    BufferedReader bfr;
    ObjectInputStream objectInputStream;
    ObjectOutputStream objectOutputStream;
    ActionListener actionListener = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (e.getSource() == settingButton) {
                UserSettingGUI.runUserSettingGUI(user, socket, writer, bfr, objectInputStream, objectOutputStream);
                frame.dispose();
            }
            if (e.getSource() == stores) {
                if (user.isBuyer()) {
                    System.out.println("execute seller");

                    SellerGUI sellerGUI = new SellerGUI(user.getUserName());
                    frame.dispose();
                    sellerGUI.run();
                }
                if (user.isSeller()) {
                    System.out.println("execute buyer");
                    CustomerGUI customerGUI = new CustomerGUI();
                    frame.dispose();
                    customerGUI.runCustomerGUI();
                }
            }
            if (e.getSource() == messages) {
                //implement
            }
            if (e.getSource() == logOut) {
                LogInGUI.runLogInGUI(socket, bfr, writer, objectInputStream, objectOutputStream);
                frame.dispose();
            }
        }
    };
    public UserInterface(User user, Socket socket, PrintWriter writer, BufferedReader bfr,
                         ObjectInputStream objectInputStream, ObjectOutputStream objectOutputStream) {

        this.user = user;
        this.socket = socket;
        this.writer = writer;
        this.bfr = bfr;
        this.objectInputStream = objectInputStream;
        this.objectOutputStream = objectOutputStream;
    }

    public static void runUserInterface(User user, Socket socket, PrintWriter writer, BufferedReader bfr,
                                        ObjectInputStream objectInputStream, ObjectOutputStream objectOutputStream) {

        SwingUtilities.invokeLater(new UserInterface(user, socket, writer, bfr, objectInputStream, objectOutputStream));
    }
    public void run() {
        panel = new JPanel();
        panel.setLayout(null);
        frame = new JFrame();
        frame.setTitle("User Interface");
        frame.setLocation(new Point(750, 250));
        frame.add(panel);
        frame.setSize(new Dimension(270, 200));

        settingButton = new JButton("Settings");
        settingButton.setBounds(10, 0, 240, 30);
        panel.add(settingButton);
        settingButton.addActionListener(actionListener);

        stores = new JButton("Stores");
        stores.setBounds(10, 40, 240, 30);
        stores.addActionListener(actionListener);
        panel.add(stores);

        messages = new JButton("Messages");
        messages.setBounds(10, 80, 240, 30);
        messages.addActionListener(actionListener);
        panel.add(messages);

        logOut = new JButton("Log out");
        logOut.setBounds(10, 120, 240, 30);
        logOut.addActionListener(actionListener);
        panel.add(logOut);

        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        frame.setVisible(true);
    }
}

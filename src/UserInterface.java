import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.net.Socket;
/**
 * UserInterface
 * <p>
 * The interface that the user interacts with once logged in
 *
 * @author Bryce LaMarca, Lab 25
 * @version 4/20/2023
 */
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
                    CustomerGUI customerGUI = new CustomerGUI(user.getUserName());
                    customerGUI.run();

                }
                if (user.isSeller()) {
                    SellerGUI sellerGUI = new SellerGUI(user.getUserName());
                    sellerGUI.run();
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

    /**
     * runs the user interface
     * @param socket the server socket
     * @param bfr the reader
     * @param writer writer to files
     * @param objectInputStream the object input stream that receives objects
     * @param objectOutputStream the object output stream that outputs object
     */
    public static void runUserInterface(User user, Socket socket, PrintWriter writer, BufferedReader bfr,
                                        ObjectInputStream objectInputStream, ObjectOutputStream objectOutputStream) {

        SwingUtilities.invokeLater(new UserInterface(user, socket, writer, bfr, objectInputStream, objectOutputStream));
    }
    public void run() {
        panel = new JPanel();
        panel.setLayout(null);
        frame = new JFrame();
        frame.setTitle("User Interface " + user.getUserName());
        frame.setLocation(new Point(750, 250));
        frame.add(panel);
        frame.setSize(new Dimension(280, 170));

        settingButton = new JButton("Settings");
        settingButton.setBounds(10, 0, 240, 30);
        panel.add(settingButton);
        settingButton.addActionListener(actionListener);

        stores = new JButton("Stores");
        stores.setBounds(10, 40, 240, 30);
        stores.addActionListener(actionListener);
        panel.add(stores);


        logOut = new JButton("Log out");
        logOut.setBounds(10, 80, 240, 30);
        logOut.addActionListener(actionListener);
        panel.add(logOut);

        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        frame.setVisible(true);
    }
}

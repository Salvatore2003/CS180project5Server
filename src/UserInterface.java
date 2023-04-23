import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class UserInterface extends JComponent implements Runnable{
    JPanel panel;
    JFrame frame;
    User user;
    JButton settingButton;
    JButton stores;
    JButton messages;
    ActionListener actionListener = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (e.getSource() == settingButton) {
                System.out.println("settings");
            }
            if (e.getSource() == stores) {
                //implement
            }
            if (e.getSource() == messages) {
                //implement
            }
        }
    };
    public UserInterface(User user) {
        this.user = user;
    }

    public static void runUserInterface(User user) {
        SwingUtilities.invokeLater(new UserInterface(user));
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

        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        frame.setVisible(true);
    }
}

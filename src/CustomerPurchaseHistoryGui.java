import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

/**
 * CustomerPurchaseHistoryGUI
 *
 * Class displays the purchase history of the current customer in a JTextArea. This is done by
 * accessing the file of the customer.
 *
 * @author Dhruv Wadhwa, Lab 25
 *
 * @version 5/1/2023
 *
 */
public class CustomerPurchaseHistoryGui extends JComponent implements Runnable {
    JButton exit;
    private static String user;
    JFrame f;

    /**
     * waits for a button to be pressed and then executes apporiate steps
     * @param e1 the event to be processed
     */
    ActionListener actionListener = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e1) {
            if (e1.getSource() == exit) {
                f.dispose();
            }
        }
    };


    public CustomerPurchaseHistoryGui(String user) {
        this.user = user;
    }
    /**
     * runs the CustomerPurchaseHistoryGui
     *
     */
    public void run() {
        f = new JFrame();
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JTextArea area = new JTextArea();
        area.setText(getPurchaseHistory(user));
        f.setTitle("Purchase History");
        f.setSize(300, 300);
        JPanel panel = new JPanel();
        f.add(panel, BorderLayout.SOUTH);

        exit = new JButton("Exit");
        exit.addActionListener(actionListener);
        panel.add(exit);
        f.add(area);
        JScrollPane scrollPane = new JScrollPane(area);
        f.getContentPane().add(scrollPane);
        f.setVisible(true);
    }
    /**
     * reads the customer file
     * @param user string username
     */
    public synchronized String getPurchaseHistory(String user) {
        String agencyName;
        String tutorName;
        int hoursBooked;
        String[] split;
        int j = 0;

        String fileName = "Customer_" + user;
        int i = 0;
        String text = "";
        try {
            String directory = "./src/"; //Directory path must be here
            File dir = new File(directory);
            File input = new File(dir, fileName);
            FileReader fr = new FileReader(input);
            BufferedReader bfr = new BufferedReader(fr);
            String infoLine = bfr.readLine();
            while (infoLine != null) {
                split = infoLine.split(",", 5);
                agencyName = split[1];
                tutorName = split[2];
                hoursBooked = Integer.parseInt(split[3]);
                text = text + String.format("Booked Tutor: %s\nAgency: %s\nHours Booked: %d\n", tutorName,agencyName,hoursBooked);
                i++;
                infoLine = bfr.readLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return text;
    }
}
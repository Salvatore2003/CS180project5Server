import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class CustomerPurchaseHistoryGui extends JComponent implements Runnable {
    JButton exit;
    private static String user;
    JFrame f;

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

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new CustomerPurchaseHistoryGui(user));
    }
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
                System.out.println(text);
                infoLine = bfr.readLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return text;
    }
}
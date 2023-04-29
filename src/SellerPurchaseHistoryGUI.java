import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class SellerPurchaseHistoryGUI extends JComponent implements Runnable {
    JButton exit;
    private static String user;

    ActionListener actionListener = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e1) {
            if (e1.getSource() == exit) {

            }
        }
    };


    public SellerPurchaseHistoryGUI(String user) {
        this.user = user;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new SellerPurchaseHistoryGUI(user));
    }
    public void run() {
        JFrame f = new JFrame();
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JTextArea area = new JTextArea();
        String[] storeNames = getStoreNames(user);
        area.setText(getPurchaseHistory(storeNames));
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

    public String getPurchaseHistory(String[] storeNames) {
        String customerName;
        String agencyName;
        String tutorName;
        int hoursBooked;
        String[] split;
        String fileName;
        String directory = "./src/"; //Directory path must be here
        File dir = new File(directory);
        File[] files = dir.listFiles();
        String text = "";
        if (files != null) {
            for (int i = 0; i < files.length; i++) {
                if (files[i].getName().contains("Customer_")) {
                    fileName = files[i].getName();
                    for(int j = 0; j < storeNames.length; j++) {
                        try {
                            File input = new File(dir, fileName);
                            FileReader fr = new FileReader(input);
                            BufferedReader bfr = new BufferedReader(fr);
                            String infoLine = bfr.readLine();
                            while (infoLine != null) {
                                split = infoLine.split(",", 5);
                                if(split[1].equals(storeNames[j])){
                                    customerName = split[0];
                                    agencyName = split[1];
                                    tutorName = split[2];
                                    hoursBooked = Integer.parseInt(split[3]);
                                    text = text + String.format("Customer: %s\nBooked Tutor: %s\nAgency: %s\nHours Booked: %d\n",customerName, tutorName,agencyName,hoursBooked);
                                }
                                infoLine = bfr.readLine();
                            }

                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }

                }
            }
        }


        return text;
    }

    public String[] getStoreNames(String user) {
        String directory = "./src/"; //Directory path must be here
        File dir = new File(directory);
        File[] files = dir.listFiles();
        int count = 0;
        int j = 0;
        if (files != null) {
            for (int i = 0; i < files.length; i++) {
                if (files[i].getName().contains(user)) {
                    count++;
                }
            }
        }
        String[] storeNames = new String[count];
        if (files != null) {
            for (int i = 0; i < files.length; i++) {
                if (files[i].getName().contains(user)) {
                    storeNames[j] = files[i].getName();
                    storeNames[j] = storeNames[j].substring(storeNames[j].indexOf("_") + 1);
                    j++;
                }
            }
        } else {
            System.out.println("No such files found in directory.");
        }
        return storeNames;
    }
}
/**
 * SellerStatisticsGUI
 *
 * Class runs the interface to show the seller statistics to summarize the purchase history and show
 * the transactions done by the agency manager, each agency and each customer in that agency.
 *
 * @author Rakshit Pradhan, Lab 25
 *
 * @version 5/1/2023
 *
 */
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class SellerStatisticsGUI extends JComponent implements Runnable {
    JButton exit;
    String user;
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


    public SellerStatisticsGUI(String user) {
        this.user = user;
    }
    /**
     * runs the seller statistics gui
     */
   public void run() {
        f = new JFrame();
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JTextArea area = new JTextArea();
        String[] storeNames = getStoreNames(user);
        area.setText(getStatisitcs(storeNames));
        f.setTitle("Statistics");
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
     * gets the statistics of all the agencies
     * @param storeNames array of all the agency names
     */
    public String getStatisitcs(String[] storeNames) {
        String customerName = "";
        ArrayList<SellerStatistics> sellerStatisticsArrayList = new ArrayList<>();
        int[] count2Array = new int[storeNames.length];
        String[] split;
        String fileName;
        String directory = "./src/"; //Directory path must be here
        File dir = new File(directory);
        File[] files = dir.listFiles();
        String text = "";
        int count = 0;
        int count2 = 0;
        int count3 = 0;
        int l = 0;
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
                            count = 0;
                            while (infoLine != null) {
                                split = infoLine.split(",", 5);
                                if(split[1].equals(storeNames[j])){
                                    customerName = split[0];
                                    count++;
                                    count2++;
                                    count3++;
                                    System.out.println(count2);
                                }
                                infoLine = bfr.readLine();
                            }
                            if(count > 0) {
                                SellerStatistics sellerStatistics = new SellerStatistics(storeNames[j],customerName,count);
                                sellerStatisticsArrayList.add(sellerStatistics);
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        count2Array[j] = count2Array[j] + count;
                    }

                }

            }
        }
        text += "Total Transactions: " + count3 + "\n";
        for(int k = 0; k< storeNames.length; k++) {
            text += "         Store transactions " + storeNames[k] + ": " + count2Array[k] + "\n";
            for (int i = 0; i < sellerStatisticsArrayList.size(); i++) {
                if (sellerStatisticsArrayList.get(i).getAgencyName().equals(storeNames[k]))
                    text += "              Customer Transactions " + sellerStatisticsArrayList.get(i).getCustomerName() +
                            ": " + sellerStatisticsArrayList.get(i).getTransactionCount() + "\n";
            }
        }
        return text;
    }
    /**
     * gets the name of all the stores of the user
     * @param user username
     */
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
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Collections;
import java.util.HashSet;
import java.util.ArrayList;


public class CustomerStatistics extends JComponent implements Runnable {
    JButton exit;
    JButton increasingSort;
    JButton decreasingSort;
    JTextArea area;
    private static String user;

    ActionListener actionListener = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e1) {
            if (e1.getSource() == exit) {

            }
            if (e1.getSource() == increasingSort) {
                area.setText(getStatisticsIncreasing(user));
            }
            if (e1.getSource() == decreasingSort) {
                area.setText(getStatisticsDecreasing(user));
            }
        }
    };


    public CustomerStatistics(String user) {
        this.user = user;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new  CustomerStatistics(user));
    }
    public void run() {
        JFrame f = new JFrame();
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        area = new JTextArea();
        area.setText(getStatistics(user));
        f.setTitle("Customer Statistics");
        f.setSize(400, 300);
        JPanel panel = new JPanel();
        f.add(panel, BorderLayout.SOUTH);

        exit = new JButton("Exit");
        exit.addActionListener(actionListener);
        panel.add(exit);
        increasingSort = new JButton("Increasing Sort");
        increasingSort.addActionListener(actionListener);
        panel.add(increasingSort);
        decreasingSort = new JButton("Decreasing Sort");
        decreasingSort.addActionListener(actionListener);
        panel.add(decreasingSort);
        f.add(area);
        JScrollPane scrollPane = new JScrollPane(area);
        f.getContentPane().add(scrollPane);
        f.setVisible(true);
    }

    public String getStatistics(String user) {
        String agencyName;
        String tutorName;
        int hoursBooked;
        String[] split;

        String fileName = "Customer_" + user;
        int i = 0;
        String text = "";

        //HashSet<String> agencies = new HashSet<>();
        ArrayList<String> agencies = new ArrayList<>();
        ArrayList<String> agencies2 = new ArrayList<>();


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
                agencies2.add(agencyName);
                i++;
                if(!agencies.contains(agencyName)) {
                    agencies.add(agencyName);
                }

                infoLine = bfr.readLine();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        text = text + String.format("Number of Transactions by Customer: %d\n",i);
        int count;
        for (String agency : agencies) {
            count = Collections.frequency(agencies2, agency);
            text += String.format("Agency Name: %s Number of Transactions: %d\n", agency, count);
        }

        return text;
    }

    public String getStatisticsIncreasing(String user) {
        String agencyName;
        String tutorName;
        int hoursBooked;
        String[] split;

        String fileName = "Customer_" + user;
        int i = 0;
        String text = "";

        //HashSet<String> agencies = new HashSet<>();
        ArrayList<String> agencies = new ArrayList<>();
        ArrayList<String> agencies2 = new ArrayList<>();


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
                agencies2.add(agencyName);
                i++;
                if(!agencies.contains(agencyName)) {
                    agencies.add(agencyName);
                }

                infoLine = bfr.readLine();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        text = text + String.format("Number of Transactions by Customer: %d\n",i);
        int count;
        Collections.sort(agencies);
        for (String agency : agencies) {
            count = Collections.frequency(agencies2, agency);
            text += String.format("Agency Name: %s Number of Transactions: %d\n", agency, count);
        }
        return text;
    }

    public String getStatisticsDecreasing(String user) {
        String agencyName;
        String tutorName;
        int hoursBooked;
        String[] split;

        String fileName = "Customer_" + user;
        int i = 0;
        String text = "";

        //HashSet<String> agencies = new HashSet<>();
        ArrayList<String> agencies = new ArrayList<>();
        ArrayList<String> agencies2 = new ArrayList<>();


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
                agencies2.add(agencyName);
                i++;
                if(!agencies.contains(agencyName)) {
                    agencies.add(agencyName);
                }

                infoLine = bfr.readLine();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        text = text + String.format("Number of Transactions by Customer: %d\n",i);
        int count;
        Collections.sort(agencies);
        Collections.reverse(agencies);
        for (String agency : agencies) {
            count = Collections.frequency(agencies2, agency);
            text += String.format("Agency Name: %s Number of Transactions: %d\n", agency, count);
        }

        return text;
    }
}
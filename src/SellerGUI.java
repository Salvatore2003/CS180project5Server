import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.io.ObjectStreamException;
/**
 * SellerGUI
 *
 * Class runs the interface for the seller or agency manager. Allows adding, editing and deleting agencies.
 * You can also access other interfaces for the agency manager through this class.
 *
 * @author Dhruv Wadhwa, Lab 25
 *
 * @version 5/1/2023
 *
 */
public class SellerGUI extends JComponent implements Runnable {
    JButton createButton;
    JButton editButton;
    JButton deleteButton;
    JButton exitButton;
    JButton purchaseHistory;
    JButton statistics;
    JFrame frame;
    String user;

    /**
     * waits for a button to be pressed and then executes apporiate steps
     * @param e the event to be processed
     */
    ActionListener actionListener = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (e.getSource() == createButton) {
                String agencyName = JOptionPane.showInputDialog(null, "Please enter the name of the Tutoring Agency you want to create", "Create Form",
                        JOptionPane.QUESTION_MESSAGE);
                if (createFile(user, agencyName)) {
                    JOptionPane.showMessageDialog(null, "Agency has been created.", "Create Form", JOptionPane.PLAIN_MESSAGE);
                }

            }
            if (e.getSource() == editButton) {
                String agencyName = JOptionPane.showInputDialog(null, "Please enter the name of the Tutoring Agency you want to edit", "Create Form",
                        JOptionPane.QUESTION_MESSAGE);
                editAgencyGUI editAgencyGUI = new editAgencyGUI(user,agencyName);
                editAgencyGUI.run();


            }
            if (e.getSource() == deleteButton) {
                String agencyName = JOptionPane.showInputDialog(null, "Please enter the name of the Tutoring Agency you want to delete", "Delete Form",
                        JOptionPane.QUESTION_MESSAGE);
                if (deleteStore(user, agencyName)) {
                    JOptionPane.showMessageDialog(null, "Agency has been Deleted.", "Delete Form", JOptionPane.PLAIN_MESSAGE);
                }
            }
            if (e.getSource() == purchaseHistory) {
                SellerPurchaseHistoryGUI sellerPurchaseHistoryGUI = new SellerPurchaseHistoryGUI(user);
                sellerPurchaseHistoryGUI.run();
            }
            if (e.getSource() == statistics) {
                SellerStatisticsGUI sellerStatisticsGUI = new SellerStatisticsGUI(user);
                sellerStatisticsGUI.run();
            }
            if (e.getSource() == exitButton) {
                frame.dispose();
            }
        }
    };

    public SellerGUI(String user) {
        this.user = user;
    }
    /**
     * runs the seller gui
     */
    public void run() {
        JPanel panel = new JPanel();
        panel.setLayout(null);
        frame = new JFrame();
        frame.setTitle("Seller Interface");
        frame.setLocation(new Point(500, 200));
        frame.add(panel);
        frame.setSize(new Dimension(350, 450));
        createButton = new JButton("Create A New Tutor Agency");
        createButton.setBounds(20, 50, 300, 30);
        createButton.addActionListener(actionListener);
        panel.add(createButton);
        editButton = new JButton("Edit A Tutor Agency");
        editButton.setBounds(20, 110, 300, 30);
        editButton.addActionListener(actionListener);
        panel.add(editButton);
        deleteButton = new JButton("Delete A Tutor Agency");
        deleteButton.setBounds(20, 170, 300, 30);
        deleteButton.addActionListener(actionListener);
        panel.add(deleteButton);
        purchaseHistory = new JButton("Purchase History");
        purchaseHistory.setBounds(20, 230, 300, 30);
        purchaseHistory.addActionListener(actionListener);
        panel.add(purchaseHistory);
        statistics = new JButton("Statistics");
        statistics.setBounds(20, 290, 300, 30);
        statistics.addActionListener(actionListener);
        panel.add(statistics);
        exitButton = new JButton("Exit Interface");
        exitButton.setBounds(20, 350, 300, 30);
        exitButton.addActionListener(actionListener);
        panel.add(exitButton);
        frame.setVisible(true);
    }
    /**
     * creates an agency file
     * @param user which is username
     * @param agencyName which is the name of the agency
     */
    public boolean createFile(String user, String agencyName) {
        File dir = new File("./src/");
        String fileName = user + "_" + agencyName;
        File file = new File(dir, fileName);
        boolean flag = false;
        try {
            if (searchAgency(agencyName)) {
                throw new InvalidAgencyName("Name has already been used");
            }
            flag = file.createNewFile();
            if (flag) {
                JOptionPane.showMessageDialog(null, "File Created Successfully", "Create Form", JOptionPane.PLAIN_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(null, "File already exists", "Create Form", JOptionPane.ERROR_MESSAGE);
                flag = false;
            }
        } catch (InvalidAgencyName e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "Create Form", JOptionPane.ERROR_MESSAGE);
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Error while creating file", "Create Form", JOptionPane.ERROR_MESSAGE);
            flag = false;
        }
        return flag;
    }
    /**
     * searches the directory for the agency file
     * @param agencyName which is the name of the agency
     */
    public boolean searchAgency(String agencyName) {
        String directory = "./src/"; //Directory path must be here
        File dir = new File(directory);
        File[] files = dir.listFiles();
        boolean flag = false;
        if (files != null) {
            for (int i = 0; i < files.length; i++) {
                if (files[i].getName().contains(agencyName)) {
                    flag = true;
                }
            }
        } else {
            flag = false;
        }
        return flag;
    }
    /**
     * deletes an agency file
     * @param user which is username
     * @param agencyName which is the name of the agency
     */
    public synchronized boolean deleteStore(String user, String agencyName) {

        String fileName = user + "_" + agencyName;
        String directory = "./src/"; //Directory path must be here
        File dir = new File(directory);
        File file = new File(dir, fileName);
        boolean flag = false;
        try {
            if (!(searchAgency(agencyName))) {
                throw new InvalidAgencyName("No store found with this name");
            }
            flag = file.delete();
            if (flag) {
                JOptionPane.showMessageDialog(null, "File Deleted Successfully", "Delete Form", JOptionPane.PLAIN_MESSAGE);
            }
        } catch (InvalidAgencyName e){
            JOptionPane.showMessageDialog(null, e.getMessage(), "Delete Form", JOptionPane.ERROR_MESSAGE);
        }

        return flag;
    }
}
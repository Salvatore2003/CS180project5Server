import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;

public class SellerGUI extends JComponent implements Runnable {
    JButton createButton;
    JButton editButton;
    JButton deleteButton;
    JButton exitButton;
    private static String user;
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
            if (e.getSource() == exitButton) {

            }
        }
    };

    public SellerGUI(String user) {
        this.user = user;
    }

    public static void runSellerGUI(String[] args) {

        SwingUtilities.invokeLater(new SellerGUI(user));
    }

    public void run() {
        JPanel panel = new JPanel();
        panel.setLayout(null);
        JFrame frame = new JFrame();
        frame.setTitle("Seller Interface");
        frame.setLocation(new Point(500, 300));
        frame.add(panel);
        frame.setSize(new Dimension(350, 320));
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
        exitButton = new JButton("Exit Interface");
        exitButton.setBounds(20, 230, 300, 30);
        exitButton.addActionListener(actionListener);
        panel.add(exitButton);
        frame.setVisible(true);
    }

    public boolean createFile(String user, String agencyName) {
        File dir = new File("./src/");
        String fileName = user + "_" + agencyName;
        File file = new File(dir, fileName + ".csv");
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

    public boolean deleteStore(String user, String agencyName) {
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
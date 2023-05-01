import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;
import java.util.Vector;
/**
 * CustomerGUI
 *
 * This class runs the interface on the customer side. You can access any store, see
 * the data of the store, make a booking, search using a keyword,
 * and access other Customer interfaces as well.
 *
 * @author Dhruv Wadhwa, Lab 25
 *
 * @version 5/1/2023
 *
 */


public class CustomerGUI extends JComponent implements Runnable {
    JButton bookTutor;
    JButton purchaseHistory;
    JButton statistics;
    JButton search;
    JButton exit;
    String fileName;
    ArrayList<Tutor> tutors;
    DefaultTableModel m;
    String user;
    String agencyName;
    JFrame f;
    /**
     * waits for a button to be pressed and then executes apporiate steps
     * @param e1 the event to be processed
     */
    ActionListener actionListener = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e1) {
            if (e1.getSource() == bookTutor) {
                try {
                    String tutorName1 = JOptionPane.showInputDialog(null, "What is the name of the Tutor that you would like to book?", "Info Form",
                            JOptionPane.QUESTION_MESSAGE);
                    tutors = readFile();
                    boolean flag = true;
                    Tutor tutor = null;
                    for (int i = 0; i < tutors.size(); i++) {
                        if (tutors.get(i).getTutorName().equals(tutorName1)) {
                            flag = false;
                            tutor = tutors.get(i);
                        }
                    }
                    if (flag) {
                        throw new InvalidTutor("This tutor was not found in this store");
                    }

                    if(tutor.getHoursAvailable() == 0) {
                        throw new InvalidTutor("Tutor is not available and is completely booked");
                    }
                    String message = String.format("%s has %d hours available and %.1f hourly rate. How many hours would you like to book them for?",
                            tutor.getTutorName(), tutor.getHoursAvailable(), tutor.getHourlyRate());

                    int hoursBooked = Integer.parseInt(JOptionPane.showInputDialog(null, message, "Info Form",
                            JOptionPane.QUESTION_MESSAGE));
                    if(hoursBooked > tutor.getHoursAvailable()) {
                        throw new InvalidTutor("Hours booked exceed the hours available the tutor has");
                    }
                    Tutor tutorChange = new Tutor(tutor.getTutorName(), tutor.getAgencyName(),
                            tutor.getAgencyName(), tutor.getHoursAvailable() - hoursBooked,
                            tutor.getHourlyRate(), tutor.getHoursPromised() + hoursBooked);
                    for (int i = 0; i < tutors.size(); i++) {
                        if (tutors.get(i).getTutorName().equals(tutorName1)) {
                            tutors.set(i, tutorChange);
                        }
                    }
                    writeFile(tutors);
                    Customer customer = new Customer(user, agencyName, tutorName1, hoursBooked, tutor.getHourlyRate());
                    writeBooking(customer);
                    updateTable();
                    m.fireTableDataChanged();
                    f.repaint();
                    JOptionPane.showMessageDialog(null, "Booking has been Made", "Booking Form", JOptionPane.PLAIN_MESSAGE);


                } catch (InvalidTutor e) {
                    JOptionPane.showMessageDialog(null, e.getMessage(), "Error Form", JOptionPane.ERROR_MESSAGE);
                } catch (NumberFormatException e) {
                    JOptionPane.showMessageDialog(null, "Error. Please try again", "Error Form",
                            JOptionPane.ERROR_MESSAGE);
                }
            }
            if (e1.getSource() == purchaseHistory) {
                CustomerPurchaseHistoryGui customerPurchaseHistoryGui = new CustomerPurchaseHistoryGui(user);
                customerPurchaseHistoryGui.run();
            }
            if (e1.getSource() == statistics) {
                CustomerStatistics customerStatistics = new CustomerStatistics(user);
                customerStatistics.run();
            }
            if (e1.getSource() == exit) {
                f.dispose();
            }
            if (e1.getSource() == search) {
                String text = "";
                String searchWord = JOptionPane.showInputDialog(null, "Enter a keyword to search through tutor names or their about me", "Search Form",
                        JOptionPane.QUESTION_MESSAGE);
                tutors = readFile();
                ArrayList<Tutor> searchTutors = new ArrayList<>();
                for(int i = 0; i < tutors.size(); i++) {
                    if(tutors.get(i).getTutorName().contains(searchWord) || tutors.get(i).getAboutMe().contains(searchWord)){
                        searchTutors.add(tutors.get(i));
                    }
                }

                for(int i = 0; i < searchTutors.size(); i++) {
                    text += searchTutors.get(i).getTutorName() + "\n";
                }

                if(searchTutors.isEmpty()){
                    JOptionPane.showMessageDialog(null, "Did not find a search to match your keyword", "Search Form",
                            JOptionPane.ERROR_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(null, "Search found tutors:\n" + text, "Search Form",
                            JOptionPane.PLAIN_MESSAGE);
                }
            }
        }
    };

    public CustomerGUI(String user){
        this.user = user;
    }
    /**
     * runs the Customer GUI
     *
     */
    public void run() {
        try {
            createFile(user);
            agencyName = JOptionPane.showInputDialog(null, "What is the name of Store?", "Info Form",
                    JOptionPane.QUESTION_MESSAGE);
            String directory = "./src/"; //Directory path must be here
            File dir = new File(directory);
            File[] files = dir.listFiles();
            Vector<Object> headers = new Vector<Object>();
            headers.add(new Vector<Object>(Arrays.asList("Tutor Name")));
            headers.add(new Vector<Object>(Arrays.asList("Store Name")));
            headers.add(new Vector<Object>(Arrays.asList("About Me")));
            headers.add(new Vector<Object>(Arrays.asList("Hours Available")));
            headers.add(new Vector<Object>(Arrays.asList("Hourly Rate")));
            headers.add(new Vector<Object>(Arrays.asList("Hours Promised")));
            fileName = "";

            if (files != null) {
                for (int i = 0; i < files.length; i++) {
                    if (files[i].getName().contains(agencyName)) {
                        fileName = "./src/" + (files[i].getName());
                    }
                }
            }
            FileReader fin = new FileReader(fileName);
            m = createTableModel(fin, headers);


            f = new JFrame();
            f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            f.getContentPane().add(new JScrollPane(new JTable(m)));
            f.setTitle("Customer Interface");
            f.setSize(600, 300);
            JPanel panel = new JPanel();
            f.add(panel, BorderLayout.SOUTH);
            bookTutor = new JButton("Book Tutor");
            bookTutor.addActionListener(actionListener);
            panel.add(bookTutor);
            purchaseHistory = new JButton("Purchase History");
            purchaseHistory.addActionListener(actionListener);
            panel.add(purchaseHistory);
            statistics = new JButton("View Statistics Dashboard");
            statistics.addActionListener(actionListener);
            panel.add(statistics);
            exit = new JButton("Exit");
            exit.addActionListener(actionListener);
            panel.add(exit);
            search = new JButton("Search");
            search.addActionListener(actionListener);
            panel.add(search);

            f.setVisible(true);

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error. Please try again", "Error Form",
                    JOptionPane.ERROR_MESSAGE);
        }
    }
    /**
     * Adds data to the tables
     * @param in
     * @param headers
     */
    public synchronized static DefaultTableModel createTableModel(Reader in,
                                                     Vector<Object> headers) {
        DefaultTableModel model = null;
        Scanner s = null;

        try {
            Vector<Vector<Object>> rows = new Vector<Vector<Object>>();
            s = new Scanner(in);

            while (s.hasNextLine()) {
                rows.add(new Vector<Object>(Arrays.asList(s.nextLine()
                        .split("\\s*,\\s*",
                                -1))));
            }

            if (headers == null) {
                headers = rows.remove(0);
                model = new DefaultTableModel(rows, headers);
            } else {
                model = new DefaultTableModel(rows, headers);
            }

            return model;
        } finally {
            s.close();
        }

    }
    /**
     * reads files
     *
     */
    public synchronized ArrayList<Tutor> readFile() {
        ArrayList<Tutor> tutors = new ArrayList<>();
        String tutorName;
        String aboutMe;
        String agencyName;
        int hoursAvailable;
        double hourlyRate;
        int hoursPromised;
        String[] split;

        try {
            File input = new File(fileName);
            FileReader fr = new FileReader(input);
            BufferedReader bfr = new BufferedReader(fr);
            String infoLine = bfr.readLine();
            while (infoLine != null) {
                split = infoLine.split(",", 6);
                tutorName = split[0];
                agencyName = split[1];
                aboutMe = split[2];
                hoursAvailable = Integer.parseInt(split[3]);
                hourlyRate = Double.parseDouble(split[4]);
                hoursPromised = Integer.parseInt(split[5]);
                Tutor tutor = new Tutor(tutorName, agencyName, aboutMe, hoursAvailable, hourlyRate, hoursPromised);
                tutors.add(tutor);
                infoLine = bfr.readLine();

            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Error. Please try again", "Error Form",
                    JOptionPane.ERROR_MESSAGE);
        }
        return tutors;
    }

    /**
     * writes to the files
     * @param tutors arraylist of tutor objects
     */
    public synchronized void writeFile(ArrayList<Tutor> tutors) {
        File file = new File(fileName);
        String infoLine;
        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter(file, false));
            for (int i = 0; i < tutors.size(); i++) {
                infoLine = String.format("%s,%s,%s,%d,%.1f,%d", tutors.get(i).getTutorName(),
                        tutors.get(i).getAgencyName(), tutors.get(i).getAboutMe(),
                        tutors.get(i).getHoursAvailable(), tutors.get(i).getHourlyRate(),
                        tutors.get(i).getHoursPromised());
                bw.write(infoLine);
                bw.newLine();
            }
            bw.close();
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Error. Please try again", "Error Form",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * updates data in table
     */
    public synchronized void updateTable() {
        try {
            Vector<Object> headers = new Vector<Object>();
            headers.add(new Vector<Object>(Arrays.asList("Tutor Name")));
            headers.add(new Vector<Object>(Arrays.asList("Store Name")));
            headers.add(new Vector<Object>(Arrays.asList("About Me")));
            headers.add(new Vector<Object>(Arrays.asList("Hours Available")));
            headers.add(new Vector<Object>(Arrays.asList("Hourly Rate")));
            headers.add(new Vector<Object>(Arrays.asList("Hours Promised")));
            FileReader fin = new FileReader(fileName);
            m = createTableModel(fin, headers);
            m.fireTableDataChanged();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error. Please try again", "Error Form",
                    JOptionPane.ERROR_MESSAGE);
        }

    }

    /**
     * creates file for customer if already not existing
     * @param user username string
     */
    public void createFile(String user) {
        File dir = new File("./src/");
        String fileName = "Customer_" + user;
        File file = new File(dir, fileName);
        boolean flag = false;
        try {
            flag = file.createNewFile();
            if (flag) {
                JOptionPane.showMessageDialog(null, "Welcome Customer", "Create Form", JOptionPane.PLAIN_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(null, "Welcome Back", "Create Form", JOptionPane.PLAIN_MESSAGE);
                flag = false;
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Error while creating file", "Create Form", JOptionPane.ERROR_MESSAGE);
            flag = false;
        }
    }
    /**
     * writes the transaction or booking into the customer file
     * @param customer which is being added to the file
     */
    public synchronized void writeBooking(Customer customer){
        String fileName = "Customer_" + user;
        String directory = "./src/"; //Directory path must be here
        File dir = new File(directory);
        File file = new File(dir, fileName);
        String infoLine = String.format("%s,%s,%s,%d, %.1f", customer.getCustomerName(), customer.getAgencyName(),
                customer.getTutorName(), customer.getHoursBooked(), customer.getHourlyRate());
        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter(file, true));

            bw.write(infoLine);
            bw.newLine();

            bw.close();
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Error. Please try again", "Error Form",
                    JOptionPane.ERROR_MESSAGE);
        }
    }
}
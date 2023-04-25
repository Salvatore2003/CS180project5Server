import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.ArrayList;

public class editAgencyGUI extends JComponent implements Runnable {
    JButton addButton;
    JButton editButton;
    JButton deleteButton;
    JButton exitButton;
    private static String user;
    private static String agencyName;
    JPanel panel;
    JFrame frame;

    ActionListener actionListener = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e1) {
            if (e1.getSource() == addButton) {
                ArrayList<Tutor> tutors = new ArrayList<>();
                ArrayList<Tutor> tutorsAdd = new ArrayList<>();
                tutors = readFile();
                tutorsAdd = takeInput2();
                tutors.addAll(tutorsAdd);
                writeFile(tutors);
            }
            if (e1.getSource() == editButton) {
                try {
                    ArrayList<Tutor> tutors = new ArrayList<>();
                    tutors = readFile();
                    boolean flag = true;
                    String tutorName1 = JOptionPane.showInputDialog(null, "What is the name of the Tutor who's information you would like to edit?", "Info Form",
                            JOptionPane.QUESTION_MESSAGE);
                    for (int i = 0; i < tutors.size(); i++) {
                        if (tutors.get(i).getTutorName().equals(tutorName1)) {
                            flag = false;
                        }
                    }
                    if(flag) {
                        throw new InvalidTutor("This tutor was not found in this store");
                    }

                    Tutor tutor = takeInput1();
                    for (int i = 0; i < tutors.size(); i++) {
                        if (tutors.get(i).getTutorName().equals(tutorName1)) {
                            tutors.set(i, tutor);
                            flag = false;
                        }
                    }

                    writeFile(tutors);
                } catch (InvalidTutor e) {
                    JOptionPane.showMessageDialog(null, e.getMessage(), "Error Form", JOptionPane.ERROR_MESSAGE);

                }

            }
            if (e1.getSource() == deleteButton) {
                ArrayList<Tutor> tutors = new ArrayList<>();
                try {
                    boolean flag = true;
                    String tutorName1 = JOptionPane.showInputDialog(null, "What is the name of the Tutor who's information you would like to delete?", "Info Form",
                            JOptionPane.QUESTION_MESSAGE);
                    tutors = readFile();
                    for (int i = 0; i < tutors.size(); i++) {
                        if (tutors.get(i).getTutorName().equals(tutorName1)) {
                            flag = false;
                        }
                    }
                    if (flag) {
                        throw new InvalidTutor("This tutor was not found in this store");
                    }

                    for (int i = 0; i < tutors.size(); i++) {
                        if (tutors.get(i).getTutorName().equals(tutorName1)) {
                            tutors.remove(tutors.get(i));
                        }
                    }
                    writeFile(tutors);
                } catch (InvalidTutor e) {
                        JOptionPane.showMessageDialog(null, e.getMessage(), "Error Form", JOptionPane.ERROR_MESSAGE);

                }
            }
            if (e1.getSource() == exitButton) {
                frame.dispose();
            }
        }
    };

    public editAgencyGUI(String user, String agencyName) {
        this.user = user;
        this.agencyName = agencyName;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new editAgencyGUI(user, agencyName));
    }

    public void run() {
        panel = new JPanel();
        panel.setLayout(null);
        frame = new JFrame();
        frame.setTitle("Edit Agency Interface");
        frame.setLocation(new Point(500, 300));
        frame.add(panel);
        frame.setSize(new Dimension(350, 320));
        addButton = new JButton("Add new Tutors to Agency");
        addButton.setBounds(20, 50, 300, 30);
        addButton.addActionListener(actionListener);
        panel.add(addButton);
        editButton = new JButton("Edit Information about a Tutor");
        editButton.setBounds(20, 110, 300, 30);
        editButton.addActionListener(actionListener);
        panel.add(editButton);
        deleteButton = new JButton("Delete information of a Tutor");
        deleteButton.setBounds(20, 170, 300, 30);
        deleteButton.addActionListener(actionListener);
        panel.add(deleteButton);
        exitButton = new JButton("Exit Interface");
        exitButton.setBounds(20, 230, 300, 30);
        exitButton.addActionListener(actionListener);
        panel.add(exitButton);
        frame.setVisible(true);
    }

    public ArrayList<Tutor> readFile() {
        String fileName = user + "_" + agencyName;
        ArrayList<Tutor> tutors = new ArrayList<>();
        String tutorName;
        String aboutMe;
        int hoursAvailable;
        double hourlyRate;
        int hoursPromised;
        String[] split;

        try {
            String directory = "./src/"; //Directory path must be here
            File dir = new File(directory);
            File input = new File(dir, fileName);
            FileReader fr = new FileReader(input);
            BufferedReader bfr = new BufferedReader(fr);
            String infoLine = bfr.readLine();
            while (infoLine != null) {
                split = infoLine.split(",", 6);
                tutorName = split[0];
                aboutMe = split[2];
                hoursAvailable = Integer.parseInt(split[3]);
                hourlyRate = Double.parseDouble(split[4]);
                hoursPromised = Integer.parseInt(split[5]);
                Tutor tutor = new Tutor(tutorName, agencyName, aboutMe, hoursAvailable, hourlyRate, hoursPromised);
                tutors.add(tutor);
                infoLine = bfr.readLine();

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return tutors;
    }

    public void writeFile(ArrayList<Tutor> tutors) {
        String fileName = user + "_" + agencyName;
        String directory = "./src/"; //Directory path must be here
        File dir = new File(directory);
        File file = new File(dir, fileName);
        String infoLine;
        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter(file, false));
            for (int i = 0; i < tutors.size(); i++) {
                infoLine = String.format("%s,%s,%s,%d,%f,%d", tutors.get(i).getTutorName(),
                        tutors.get(i).getAgencyName(), tutors.get(i).getAboutMe(),
                        tutors.get(i).getHoursAvailable(), tutors.get(i).getHourlyRate(),
                        tutors.get(i).getHoursPromised());
                bw.write(infoLine);
                bw.newLine();
            }
            bw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public Tutor takeInput1() {
        String tutorName;
        String aboutMe;
        int hoursAvailable;
        double hourlyRate;
        int hoursPromised;

        tutorName = JOptionPane.showInputDialog(null, "What is the name of the Tutor?", "Info Form",
                JOptionPane.QUESTION_MESSAGE);
        aboutMe = JOptionPane.showInputDialog(null, "Write the aboutMe for the Tutor", "Info Form",
                JOptionPane.QUESTION_MESSAGE);
        hoursAvailable = Integer.parseInt(JOptionPane.showInputDialog(null, "What are the hours available of the Tutor?", "Info Form",
                JOptionPane.QUESTION_MESSAGE));
        hourlyRate = Double.parseDouble(JOptionPane.showInputDialog(null, "What is the hourly rate of the Tutor?", "Info Form",
                JOptionPane.QUESTION_MESSAGE));
        hoursPromised = Integer.parseInt(JOptionPane.showInputDialog(null, "What are the hours promised of the Tutor?", "Info Form",
                JOptionPane.QUESTION_MESSAGE));
        Tutor tutor = new Tutor(tutorName, agencyName, aboutMe, hoursAvailable, hourlyRate, hoursPromised);
        return tutor;
    }

    public ArrayList<Tutor> takeInput2() {
        ArrayList<Tutor> tutors = new ArrayList<>();
        Tutor tutor;
        int userInput = 0;
        String tutorName;
        String aboutMe;
        int hoursAvailable;
        double hourlyRate;
        int hoursPromised;
        String fileName;
        String[] split;
        String[] options = new String[2];
        options[0] = "Manually for one product";
        options[1] = "Input file for multiple Tutors";

        String method = (String) JOptionPane.showInputDialog(null, "How would you like to enter the Tutor information", "Info Form",
                JOptionPane.PLAIN_MESSAGE, null, options, null);

        if(method.equals(options[0])) {
            tutor = takeInput1();
            tutors.add(tutor);
        }
        if(method.equals(options[1])) {
            fileName = JOptionPane.showInputDialog(null, "What is the name of the File?", "Info Form",
                    JOptionPane.QUESTION_MESSAGE);
            try {
                File dir = new File("./src/");
                File input = new File(dir, fileName);
                FileReader fr = new FileReader(input);
                BufferedReader bfr = new BufferedReader(fr);
                String infoLine = bfr.readLine();
                while (infoLine != null) {
                    split = infoLine.split(",", 6);
                    tutorName = split[0];
                    aboutMe = split[2];
                    hoursAvailable = Integer.parseInt(split[3]);
                    hourlyRate = Double.parseDouble(split[4]);
                    hoursPromised = Integer.parseInt(split[5]);
                    tutor = new Tutor(tutorName, agencyName, aboutMe, hoursAvailable, hourlyRate, hoursPromised);
                    tutors.add(tutor);
                    infoLine = bfr.readLine();
                }
            } catch(FileNotFoundException e) {
                System.out.println("lol");
            }
            catch (IOException e) {
                e.printStackTrace();
            }
        }
        return tutors;
    }
}

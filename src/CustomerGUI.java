import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.io.File;
import java.io.FileReader;
import java.io.Reader;
import java.util.Arrays;
import java.util.Scanner;
import java.util.Vector;


public class CustomerGUI {

    public static void main(String[] args) {
        try {
            String storeName = JOptionPane.showInputDialog(null, "What is the name of Store?", "Info Form",
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
            String fileName = "";

            if (files != null) {
                for (int i = 0; i < files.length; i++) {
                    if (files[i].getName().contains(storeName)) {
                        fileName = "./src/" + (files[i].getName());
                    }
                }
            }
            FileReader fin = new FileReader(fileName);
            DefaultTableModel m = createTableModel(fin, headers);


            JFrame f = new JFrame();
            f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            f.getContentPane().add(new JScrollPane(new JTable(m)));
            f.setSize(600, 300);
            f.setVisible(true);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static DefaultTableModel createTableModel(Reader in,
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
}
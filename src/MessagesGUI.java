/*import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class UserMessagesGUI {
    private JFrame frame;
    private JTextField subjectTextField;
    private JTextArea messageTextArea;
    private JComboBox<String> recipientComboBox;
    private Messages messages;
    private User currentUser;
    private ArrayList<User> users;

    public UserMessagesGUI(User currentUser, ArrayList<User> users) {
        this.currentUser = currentUser;
        this.users = users;
        messages = new Messages();
        initialize();
    }

    public static void main(String[] args) {
        //Create some users for testing

        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    UserMessagesGUI window = new UserMessagesGUI(user1, users);
                    window.frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void initialize() {
        frame = new JFrame();
        frame.setBounds(100, 100, 600, 400);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setLayout(new BorderLayout(0, 0));

        JPanel topPanel = new JPanel();
        frame.getContentPane().add(topPanel, BorderLayout.NORTH);

        JLabel subjectLabel = new JLabel("Subject:");
        topPanel.add(subjectLabel);

        subjectTextField = new JTextField();
        topPanel.add(subjectTextField);
        subjectTextField.setColumns(10);

        JLabel recipientLabel = new JLabel("Recipient:");
        topPanel.add(recipientLabel);

        recipientComboBox = new JComboBox<>();
        for (User user : users) {
            if (!user.equals(currentUser)) {
                recipientComboBox.addItem(user.getUserName());
            }
        }
        topPanel.add(recipientComboBox);

        messageTextArea = new JTextArea();
        JScrollPane scrollPane = new JScrollPane(messageTextArea);
        frame.getContentPane().add(scrollPane, BorderLayout.CENTER);

        JPanel bottomPanel = new JPanel();
        frame.getContentPane().add(bottomPanel, BorderLayout.SOUTH);

        JButton sendMessageButton = new JButton("Send Message");
        sendMessageButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String subject = subjectTextField.getText();
                String message = messageTextArea.getText();
                User recipient = null;
                for (User user : users) {
                    if (user.getUserName().equals(recipientComboBox.getSelectedItem())) {
                        recipient = user;
                        break;
                    }
                }
                if (recipient != null) {
                    messages.sendMessage(subject, message, currentUser, recipient);
                }
            }
        });
        bottomPanel.add(sendMessageButton);

        JButton viewInboxButton = new JButton("View Inbox");
        viewInboxButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                messages.printInbox(currentUser);
            }
        });
        bottomPanel.add(viewInboxButton);

        JButton viewOutboxButton = new JButton("View Outbox");
        viewOutboxButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                messages.printOutbox(currentUser);
            }
        });
        bottomPanel.add(viewOutboxButton);
    }
}*/



import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;

public class MessagesGUI extends JComponent implements Runnable {
    private JTextArea messagesArea;
    private JTextField inputField;
    private JButton sendButton;
    private JScrollPane scrollPane;
    private static ObjectOutputStream objectOutputStream;
    private static ObjectInputStream objectInputStream;
    private static PrintWriter writer;

    public MessagesGUI(ObjectOutputStream objectOutputStream, ObjectInputStream objectInputStream, PrintWriter writer) {
        this.objectOutputStream = objectOutputStream;
        this.objectInputStream = objectInputStream;
        this.writer = writer;
    }

    public static void main(String[] args) {

        SwingUtilities.invokeLater(new MessagesGUI(objectOutputStream,  objectInputStream, writer));
    }

    public void run() {
        JFrame frame = new JFrame();
        frame.setTitle("Messages");
        frame.setSize(600, 400);

        frame.setLayout(new BorderLayout());

        messagesArea = new JTextArea();
        messagesArea.setEditable(false);
        scrollPane = new JScrollPane(messagesArea);

        inputField = new JTextField();
        inputField.setPreferredSize(new Dimension(450, 30));

        sendButton = new JButton("Send");
        sendButton.setPreferredSize(new Dimension(120, 30));
        sendButton.addActionListener(new SendButtonListener());

        JPanel bottomPanel = new JPanel(new BorderLayout());
        bottomPanel.add(inputField, BorderLayout.CENTER);
        bottomPanel.add(sendButton, BorderLayout.EAST);

        add(scrollPane, BorderLayout.CENTER);
        add(bottomPanel, BorderLayout.SOUTH);

        frame.setVisible(true);

    }

    private class SendButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String message = inputField.getText().trim();

            if (!message.isEmpty()) {
                try {
                    // Sending message to the server
                    writer.println("MESSAGE:" + message);
                    writer.flush();

                    // Append sent message to the messages area
                    messagesArea.append("You: " + message + "\n");

                    inputField.setText("");
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(MessagesGUI.this,
                            "Failed to send the message.",
                            "Error",
                            JOptionPane.ERROR_MESSAGE);
                }
            }
        }
    }

    public void displayMessage(String message) {
        messagesArea.append(message + "\n");
    }
}

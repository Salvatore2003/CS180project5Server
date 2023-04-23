import javax.swing.*;
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
        // Create some users for testing
        ArrayList<User> users = new ArrayList<>();
        User user1 = new User("user1");
        User user2 = new User("user2");
        users.add(user1);
        users.add(user2);

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
}

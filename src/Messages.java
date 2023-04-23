import java.util.*;
import java.io.*;
/**
 * Utility class containing all the methods to add, remove, and edit messages as well as a
 * method that prints the inbox and outbox of the user this class is assigned to.
 */
public class Messages{
    public Messages() {
    }

    /**
     * Prints the subjects of the emails in a user's inbox, or prints
     * "Empty Inbox!" if the user's inbox contains no mail
     */
    public void printInbox(User user) {
        ArrayList<Message> inbox = user.getInbox();
        if(inbox.size() == 0) {
            System.out.println("Empty Inbox!");
            return;
        }
        System.out.println("You have " + inbox.size() + " messages.");
        for(int i = 0;i < inbox.size();i++) {
            System.out.println(inbox.get(i).getSubject());
        }
    }

    public void printOutbox(User user) {
        ArrayList<Message> outbox = user.getOutbox();
        if(outbox.size() == 0) {
            System.out.println("Empty Inbox!");
            return;
        }
        System.out.println("You have " + outbox.size() + " messages.");
        for(int i = 0;i < outbox.size();i++) {
            System.out.println(outbox.get(i).getSubject());
        }
    }

    /**
     * Sends a message to a recipient's outbox containing a subject and a message,
     * then adds the same message to the sender's outbox. Does not allow buyers
     * to message buyers or sellers to message sellers.
     */
    public void sendMessage(String subject, String message, User sender, User recipient) {
        if(sender.isBuyer() && recipient.isBuyer()) {
            System.out.println("You cannot message another buyer!");
            return;
        }
        if(sender.isSeller() && recipient.isSeller()) {
            System.out.println("You cannot message another seller!");
            return;
        }

        try {
            ArrayList<Message> recInbox = recipient.getInbox();
            recInbox.add(new Message(subject, message, sender, recipient));
            ArrayList<Message> senderOutbox = sender.getOutbox();
            senderOutbox.add(new Message(subject, message, sender, recipient));
            sender.setOutbox(senderOutbox);
            System.out.println("Message sent successfully.");
        } catch (Exception e) {
            System.out.println("Error sending message.");
        }
    }

    /**
     * Edits a message in both the sender's outbox and the recipient's inbox
     */
    public void editMessage(Message oldMessage, String newSubject, String newMessage, User sender, User recipient) {
        try {
            ArrayList<Message> si = sender.getOutbox();
            ArrayList<Message> ri = recipient.getInbox();
            int userIndex = -1;
            for(int i = 0;i < si.size();i++) {
                if(si.get(i).equals(oldMessage)) {
                    userIndex = i;
                }
            }
            int recipientIndex = -1;
            for(int i = 0;i < ri.size();i++) {
                if(si.get(i).equals(oldMessage)) {
                    recipientIndex = i;
                }
            }
            si.get(userIndex).setSubject(newSubject);
            si.get(userIndex).setMessage(newMessage);
            ri.get(recipientIndex).setSubject(newSubject);
            ri.get(recipientIndex).setMessage(newMessage);
            sender.setOutbox(si);
            recipient.setInbox(ri);
            System.out.println("Message edited successfully.");
        } catch (Exception e) {
            System.out.println("Error editing message.");
            return;
        }
    }

    /**
     * Deletes a message from the sender's outbox while not deleting it from
     * the recipient's inbox
     */
    public void deleteMessage(Message delMessage, User sender) {
        ArrayList<Message> si = sender.getOutbox();
        for(int i = 0;i < si.size();i++) {
            if(si.get(i).equals(delMessage)) {
                si.remove(i);
                sender.setOutbox(si);
                System.out.println("Message deleted.");
                return;
            }
        }
        System.out.println("Message not found.");
    }

    public void updateMessageFile(User sender, User recipient){
        try {
            PrintWriter pw = new PrintWriter(new FileOutputStream(sender.getUserName() + "messages.txt", false));
            PrintWriter pw2 = new PrintWriter(new FileOutputStream(recipient.getUserName() + "messages.txt", false));
            ArrayList<Message> senderInbox = sender.getInbox();
            ArrayList<Message> senderOutbox = sender.getOutbox();
            ArrayList<Message> recipientInbox = recipient.getInbox();
            ArrayList<Message> recipientOutbox = recipient.getOutbox();
            pw.println("INBOX");
            pw.println();
            for(int i = 0;i < senderInbox.size();i++) {
                pw.println("Subject: " + senderInbox.get(i).getSubject());
                pw.println("Message: " + senderInbox.get(i).getMessage());
                pw.println("Sender: " + senderInbox.get(i).getSender().getUserName());
            }
            pw.println();
            pw.println();
            pw.println();
            pw.println("OUTBOX");
            pw.println();
            for(int i = 0;i < senderOutbox.size();i++) {
                pw.println("Subject: " + senderOutbox.get(i).getSubject());
                pw.println("Message: " + senderOutbox.get(i).getMessage());
                pw.println("Recipient: " + senderOutbox.get(i).getRecipient().getUserName());
            }
            pw2.println("INBOX");
            pw2.println();
            for(int i = 0;i < recipientInbox.size();i++) {
                pw2.println("Subject: " + recipientInbox.get(i).getSubject());
                pw2.println("Message: " + recipientInbox.get(i).getMessage());
                pw2.println("Sender: " + recipientInbox.get(i).getSender().getUserName());
            }
            pw2.println();
            pw2.println();
            pw2.println();
            pw2.println("OUTBOX");
            pw2.println();
            for(int i = 0;i < recipientOutbox.size();i++) {
                pw2.println("Subject: " + recipientOutbox.get(i).getSubject());
                pw2.println("Message: " + recipientOutbox.get(i).getMessage());
                pw2.println("Recipient: " + recipientOutbox.get(i).getRecipient().getUserName());
            }
            pw.close();
            pw2.close();
        } catch (Exception e) {
            System.out.println("Error writing to file.");
        }
    }
    public void readMessageFile(User user, ArrayList<User> users) {
        BufferedReader br;
        try {
            br = new BufferedReader(new FileReader(user.getUserName() + "messages.csv"));
            ArrayList<Message> userInbox = new ArrayList<Message>();
            ArrayList<Message> userOutbox = new ArrayList<Message>();
            br.readLine();
            br.readLine();
            for(String line = br.readLine();!line.equals("OUTBOX") && line != null;line = br.readLine()) {
                String subject = br.readLine().substring(9);
                String message = br.readLine().substring(9);
                String sender = br.readLine().substring(8);
                User sender2 = null;
                for(int i = 0;i < users.size();i++) {
                    if(users.get(i).getUserName().equals(sender)) {
                        sender2 = users.get(i);
                    }
                }
                userInbox.add(new Message(subject, message, sender2, user));
            }
        } catch (Exception e) {
            System.out.println("Error reading message file!");
        }
    }
}

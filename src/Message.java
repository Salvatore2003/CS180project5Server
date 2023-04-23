import java.util.*;
/**
 * Stores a message with a subject and message
 *
 * @author Nick Clarkson
 * @version 1
 */
public class Message
{
    private String subject;
    private String message;
    private User sender;
    private User recipient;
    public Message(String subject, String message, User sender, User recipient) {
        this.subject = subject;
        this.message = message;
        this.sender = sender;
        this.recipient = recipient;
    }

    public String getSubject() { return subject; }

    public void setSubject(String subject) { this.subject = subject; }

    public String getMessage() { return message; }

    public void setMessage(String message) { this.message = message; }

    public User getSender() { return sender; }

    public User getRecipient() { return recipient; }

    public boolean equals(Message message) {
        return this.subject.equals(message.getSubject()) && this.message.equals(message.getMessage()) && this.sender == message.getSender() && this.recipient == message.getRecipient();
    }
}
package yaruliy.model.messaging;
import javax.persistence.*;

@Table(name = "history")
@Entity
public class ChatMessage {
    private static final long serialVersionUID = -3009157732242241606L;
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Id @Column(name = "id")
    private long id;
    @Column(name = "message") private String message;
    @Column(name = "sender") private String sender;
    @Column(name = "recipient") private String recipient;

    public ChatMessage(){}

    public ChatMessage(String message, String sender, String recipient) {
        this.message = message;
        this.sender = sender;
        this.recipient = recipient;
    }

    public long getId() { return id; }
    public String getMessage() { return message; }
    public String getSender() { return sender; }
    public String getRecipient() { return recipient; }

    public void setId(long id) {
        this.id = id;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public void setRecipient(String recipient) {
        this.recipient = recipient;
    }

    public String toString(){
        return " [message: " + this.message + "; " + this.sender + " -> " + this.recipient + "]";
    }
}
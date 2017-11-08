package yaruliy.mqtt.event;
import org.springframework.context.ApplicationEvent;

public class MessageReceivedEvent extends ApplicationEvent{
    private String message;
    private String sender;
    private String recipient;

    public MessageReceivedEvent(Object source, String message, String sender, String recipient) {
        super(source);
        this.message = message;
        this.sender = sender;
        this.recipient = recipient;
    }

    public String getMessage() { return message; }
    public String getSender() { return sender; }
    public String getRecipient() { return recipient; }
}

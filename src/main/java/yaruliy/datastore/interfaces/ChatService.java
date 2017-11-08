package yaruliy.datastore.interfaces;
import yaruliy.model.messaging.ChatMessage;
import java.util.List;

public interface ChatService {
    List<ChatMessage> getMessagesBySenderAndRecipient(String sender, String recipient);
    void saveMessage(ChatMessage message);
    void deleteAllRecords();
}
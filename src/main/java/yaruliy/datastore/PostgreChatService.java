package yaruliy.datastore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import yaruliy.datastore.interfaces.ChatService;
import yaruliy.datastore.postgrestaff.HistoryRepository;
import yaruliy.model.messaging.ChatMessage;
import java.util.List;

@Service
public class PostgreChatService implements ChatService{
    HistoryRepository repository;
    @Autowired public void setUserRepository(HistoryRepository repository){ this.repository = repository; }

    @Override
    public List<ChatMessage> getMessagesBySenderAndRecipient(String sender, String recipient) {
        return this.repository.findBySenderAndRecipient(sender, recipient);
    }

    @Override
    public void saveMessage(ChatMessage message) {
        System.out.println("Save message:" + message);
        this.repository.save(message);
    }

    @Override
    public void deleteAllRecords() {
        this.repository.deleteAll();
    }
}
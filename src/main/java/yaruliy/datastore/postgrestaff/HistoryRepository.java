package yaruliy.datastore.postgrestaff;
import org.springframework.data.repository.CrudRepository;
import yaruliy.model.messaging.ChatMessage;
import java.util.List;

public interface HistoryRepository extends CrudRepository<ChatMessage, Long> {
    List<ChatMessage> findBySenderAndRecipient(String sender, String recipient);
}
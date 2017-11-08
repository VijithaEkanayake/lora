package yaruliy.datastore.postgrestaff;
import org.springframework.data.repository.CrudRepository;
import yaruliy.model.json.JSONLoraMessage;
import java.util.List;

public interface MessageRepository extends CrudRepository<JSONLoraMessage, Long> {
    List<JSONLoraMessage> findByuserID(String login);
}
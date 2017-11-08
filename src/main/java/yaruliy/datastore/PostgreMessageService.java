package yaruliy.datastore;
import org.springframework.beans.factory.annotation.Autowired;
import yaruliy.datastore.interfaces.MessageService;
import yaruliy.datastore.postgrestaff.MessageRepository;
import yaruliy.model.json.JSONLoraMessage;
import java.util.List;

public class PostgreMessageService implements MessageService {
    MessageRepository repository;
    @Autowired
    public void setUserRepository(MessageRepository repository){ this.repository = repository; }

    @Override
    public void writeMessage(JSONLoraMessage message) {
        repository.save(message);
    }

    @Override
    public List<JSONLoraMessage> getMessagesByUserLogin(String login) {
        return repository.findByuserID(login);
    }

    @Override
    public JSONLoraMessage getMessageByID(String id) {
        return repository.findOne(Long.valueOf(id));
    }

    @Override
    public void deleteMessage(int id) {

    }

    @Override
    public void openSession() {

    }

    @Override
    public void closeSession() {

    }
}
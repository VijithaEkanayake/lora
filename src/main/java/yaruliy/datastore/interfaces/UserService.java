package yaruliy.datastore.interfaces;
import yaruliy.model.User;
import java.util.List;

public interface UserService {
    User findByLogin(String login);
    String saveUser(User user);
    List<User> getAll();
    void openSession();
    void closeSession();
}
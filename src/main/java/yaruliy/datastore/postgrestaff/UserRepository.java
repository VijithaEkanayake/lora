package yaruliy.datastore.postgrestaff;
import org.springframework.data.repository.CrudRepository;
import yaruliy.model.User;
import java.util.List;

public interface UserRepository extends CrudRepository<User, Long> {
    List<User> findByLogin(String login);
}
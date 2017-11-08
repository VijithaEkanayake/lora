package yaruliy.datastore;
import org.springframework.beans.factory.annotation.Autowired;
import yaruliy.datastore.interfaces.UserService;
import yaruliy.datastore.postgrestaff.UserRepository;
import yaruliy.model.User;

import java.util.List;

public class PostgreUserService implements UserService{
    UserRepository repository;
    @Autowired public void setUserRepository(UserRepository repository){ this.repository = repository; }

    public PostgreUserService(){}

    @Override
    public User findByLogin(String login) { return repository.findByLogin(login).get(0); }

    @Override
    public String saveUser(User user) {
        repository.save(user);
        return "User successfully recorded";
    }

    @Override
    public List<User> getAll() {
        return (List<User>)repository.findAll();
    }

    @Override
    public void openSession() {

    }

    @Override
    public void closeSession() {

    }
}
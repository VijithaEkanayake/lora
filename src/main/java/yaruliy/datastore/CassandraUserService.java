package yaruliy.datastore;
import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.ResultSet;
import com.datastax.driver.core.Session;
import com.datastax.driver.mapping.Mapper;
import com.datastax.driver.mapping.MappingManager;
import org.springframework.beans.factory.annotation.Value;
import yaruliy.datastore.interfaces.UserService;
import yaruliy.model.User;
import javax.annotation.PostConstruct;
import java.util.List;

public class CassandraUserService implements UserService {
    private Cluster cluster = null;
    private Session session = null;
    @Value("${cassandra.address}")
    private String address;
    public CassandraUserService(){}

    @Override
    public User findByLogin(String login) {
        openSession();
        String query = "SELECT * FROM Lora.users WHERE login = " + "'" + login + "'" + " ALLOW FILTERING ;";
        MappingManager manager = new MappingManager(session);
        Mapper<User> mapper = manager.mapper(User.class);
        ResultSet results = session.execute(query);
        if(mapper.map(results).iterator().hasNext())
            return mapper.map(results).iterator().next();
        else return new User();
    }

    @Override
    public String saveUser(User user){
        openSession();
        MappingManager manager = new MappingManager(session);
        Mapper<User> mapper = manager.mapper(User.class);
        mapper.save(user);
        return "User successfully recorded";
    }

    @Override
    public List<User> getAll() {
        return null;
    }

    private void prepareDataBase() {
        final String createKeySpace =
                "CREATE KEYSPACE IF NOT EXISTS Lora WITH replication = {'class':'SimpleStrategy', 'replication_factor':3};";

        final String createTable =
                "CREATE TABLE IF NOT EXISTS Lora.users ("
                        + "uuid uuid PRIMARY KEY,"
                        + "login text,"
                        + "password text,"
                        + "name text,"
                        + "surname text,"
                        + "emails list<text>"
                        + ");";

        Cluster.builder().addContactPoint(address).build().connect().execute(createKeySpace);
        Cluster.builder().addContactPoint(address).build().connect().execute(createTable);
    }

    @Override
    public void openSession() {
        cluster = Cluster.builder().addContactPoint(address).build();
        session = cluster.connect("Lora");
    }

    @Override
    public void closeSession() {
        if (session != null) session.close();
        if (cluster != null) cluster.close();
    }

    @PostConstruct
    public void postConstruct(){ prepareDataBase(); }
}
package yaruliy.datastore.postgrestaff;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import yaruliy.model.Gateway;

@Repository
public interface GatewayRepository extends CrudRepository<Gateway, Long>{
    Gateway findByMac(String mac);
}
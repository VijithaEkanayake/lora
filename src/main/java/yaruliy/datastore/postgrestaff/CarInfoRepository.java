package yaruliy.datastore.postgrestaff;
import org.springframework.data.repository.CrudRepository;
import yaruliy.model.parking.Info;

public interface CarInfoRepository extends CrudRepository<Info, Long> {

}
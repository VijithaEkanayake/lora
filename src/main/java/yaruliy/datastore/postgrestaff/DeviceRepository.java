package yaruliy.datastore.postgrestaff;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import yaruliy.model.Device;

@Repository
public interface DeviceRepository extends CrudRepository<Device, Long> {
    Device findByLoraid(String loraid);
}
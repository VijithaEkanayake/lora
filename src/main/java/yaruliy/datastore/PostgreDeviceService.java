package yaruliy.datastore;
import org.springframework.beans.factory.annotation.Autowired;
import yaruliy.datastore.interfaces.DeviceService;
import yaruliy.datastore.postgrestaff.DeviceRepository;
import yaruliy.model.Device;
import java.util.List;

public class PostgreDeviceService implements DeviceService{
    DeviceRepository repository;
    @Autowired
    public void setUserRepository(DeviceRepository repository){ this.repository = repository; }

    @Override
    public List<Device> getDevices() {
        return (List<Device>)repository.findAll();
    }

    @Override
    public Device getDeviceByID(String id) {
        return repository.findOne(Long.valueOf(id));
    }

    @Override
    public Device getDeviceByLoraID(String id) {
        Device device = repository.findByLoraid(id);
        if(device != null) return device;
        else {
            Device newDevice = new Device();
            newDevice.setLoraid("lora-" + this.deviceCount());
            return newDevice;
        }
    }

    @Override
    public String saveDevice(Device device) {
        repository.save(device);
        return "Success";
    }

    @Override
    public boolean containsDevice(String loraid) {
        return repository.exists(this.getDeviceByLoraID(loraid).getId());
    }

    @Override
    public long deviceCount() {
        return repository.count();
    }

    @Override
    public void openSession() {

    }

    @Override
    public void closeSession() {

    }
}
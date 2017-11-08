package yaruliy.datastore;
import org.springframework.beans.factory.annotation.Autowired;
import yaruliy.datastore.interfaces.GatewayService;
import yaruliy.datastore.postgrestaff.GatewayRepository;
import yaruliy.model.Gateway;

import java.util.List;

public class PostgreGatewayService implements GatewayService{
    private GatewayRepository repository;
    @Autowired public void setRepository(GatewayRepository repository){ this.repository = repository; }

    @Override
    public void saveGateway(Gateway gateway) {
        repository.save(gateway);
    }

    @Override
    public List<Gateway> getDevices() {
        return (List<Gateway>) repository.findAll();
    }

    @Override
    public Gateway getGatewayByMac(String mac) {
        Gateway gateway = repository.findByMac(mac);
        if(gateway != null) return gateway;
        else{
            Gateway newGateway = new Gateway();
            newGateway.setMac("06-00-00-00-00-00");
            return newGateway;
        }
    }
}
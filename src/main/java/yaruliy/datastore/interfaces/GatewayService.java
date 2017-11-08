package yaruliy.datastore.interfaces;
import yaruliy.model.Gateway;
import java.util.List;

public interface GatewayService {
    void saveGateway(Gateway gateway);
    List<Gateway> getDevices();
    Gateway getGatewayByMac(String mac);
}
package yaruliy.datastore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import yaruliy.datastore.postgrestaff.CarInfoRepository;
import yaruliy.model.parking.Info;
import java.util.List;

@Service
public class PostgreCarInfoService {
    CarInfoRepository repository;
    @Autowired public void setUserRepository(CarInfoRepository repository){ this.repository = repository; }

    public List<Info> getInfo(){
        return (List<Info>)this.repository.findAll();
    }

    public void save(Info info){
        this.repository.save(info);
    }
}
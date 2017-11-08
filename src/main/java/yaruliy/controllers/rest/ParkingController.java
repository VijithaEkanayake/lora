package yaruliy.controllers.rest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import yaruliy.datastore.PostgreCarInfoService;
import yaruliy.model.parking.Info;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(value = "/car")
public class ParkingController {
    private final PostgreCarInfoService infoService;

    @Autowired
    public ParkingController(PostgreCarInfoService infoService) {
        this.infoService = infoService;
    }

    @RequestMapping(value = "/carinfo")
    public List<Info> infos(){
        return this.infoService.getInfo();
    }

    @RequestMapping(value = "/carinfo/{count}")
    public List<Info> infosN(@PathVariable(value = "count") int count){
        ArrayList<Info> list = new ArrayList<>();
        for (Info info: this.infoService.getInfo()){
            if(count > 0){
                list.add(info);
                count--;
            }
        }
        return list;
    }
}
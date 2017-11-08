package yaruliy.model.parking;
import javax.persistence.*;

@Table(name = "carinfo")
@Entity
public class Info {
    @GeneratedValue(strategy = GenerationType.AUTO) @Id @Column(name = "id") private long id;
    @Column private String free;
    @Column private String timestamp;
    @Column private String occupied;

    public Info(String free, String timestamp, String occupied) {
        this.free = free;
        this.timestamp = timestamp;
        this.occupied = occupied;
    }

    public Info(){}

    public String getFree() {
        return free;
    }

    public void setFree(String free) {
        this.free = free;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getOccupied() {
        return occupied;
    }

    public void setOccupied(String occupied) {
        this.occupied = occupied;
    }
}
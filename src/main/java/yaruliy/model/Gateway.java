package yaruliy.model;
import javax.persistence.*;

@Table(name = "gateways")
@Entity
public class Gateway {
    @GeneratedValue(strategy = GenerationType.AUTO) @Id @Column(name = "id") private long id;
    @Column private String mac;

    public String getMac() {
        return mac;
    }

    public void setMac(String mac) {
        this.mac = mac;

    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
}
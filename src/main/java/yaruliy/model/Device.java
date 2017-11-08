package yaruliy.model;
import javax.persistence.*;
import java.util.Date;

@Table(name = "devices")
@Entity
public class Device {
    @GeneratedValue(strategy = GenerationType.AUTO) @Id @Column(name = "id") private long id;
    @Column private String loraid;
    @Column private double longitude;
    @Column private double latitude;
    @Column private String lastUsedGateway;
    @Column private Date lastUsedGatewayDate;

    public Date getLastUsedGatewayDate() {
        return lastUsedGatewayDate;
    }
    public void setLastUsedGatewayDate(Date lastUsedGatewayDate) {
        this.lastUsedGatewayDate = lastUsedGatewayDate;
    }
    public String getLastUsedGateway() { return lastUsedGateway; }
    public void setLastUsedGateway(String lastUsedGateway) { this.lastUsedGateway = lastUsedGateway; }
    public long getId() { return id; }
    public void setId(long id) { this.id = id; }
    public String getLoraid() { return loraid; }
    public void setLoraid(String loraid) { this.loraid = loraid; }
    public double getLongitude() { return longitude; }
    public void setLongitude(double longitude) { this.longitude = longitude; }
    public double getLatitude() { return latitude; }
    public void setLatitude(double latitude) { this.latitude = latitude; }

    public Device(){}

    public String toString(){
        return "id: " + this.id + "\nloraid: " + this.loraid +
                "\nlongitude: " + this.longitude + "\nlatitude: " + this.latitude + "\n";
    }
}
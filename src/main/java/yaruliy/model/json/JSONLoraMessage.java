package yaruliy.model.json;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import yaruliy.util.crypto.Crypto;
import javax.persistence.*;

@Table(name = "messages")
@JsonIgnoreProperties(ignoreUnknown = true)
@Entity
public class JSONLoraMessage{
    @Column @Id @GeneratedValue(strategy = GenerationType.AUTO) private long id;
    @Column private String data;
    @Column private String tmst;
    @Column private String freq;
    @Column private String size;
    @Column private String userID;
    @Column private String source;
    @Column private String topic;

    public String getTopic() { return topic; }
    public void setTopic(String topic) { this.topic = topic; }
    public String getSource() { return source; }
    public void setSource(String source) { this.source = source; }
    public String getUserID() { return userID; }
    public void setUserID(String userID) { this.userID = userID; }
    public String getData() { return data; }
    public void setData(String data){ this.data = data; }
    public String getTmst() { return tmst; }
    public void setTmst(String tmst){ this.tmst = tmst; }
    public String getFreq() { return freq; }
    public void setFreq(String freq) { this.freq = freq; }
    public String getSize() { return size; }
    public void setSize(String size) { this.size = size; }
    public long getId() { return id; }
    public void setId(long id) { this.id = id; }

    public JSONLoraMessage(@JsonProperty("data") String data){
        try {
            String d = Crypto.toText(Crypto.decrypt(data));
            System.out.println("data from constructor: " + d);
            this.data = d;
        }
        catch (Exception e) { e.printStackTrace(); }
        this.userID = "login";
    }
    public JSONLoraMessage(){
        this.userID = "login";
    }

    public String toString(){
        return "{" +
                "\n\t" + "data: " + "\"" + this.data + "\"" +"," +
                "\n\t" + "tmst: " + this.tmst + "," +
                "\n\t" + "freq: " + this.freq + "," +
                "\n\t" + "size: " + this.size + "," +
                "\n\t" + "id: " + this.id + "," +
                "\n\t" + "userID: " + "\"" + this.userID + "\"" +
                "\n" +
                "}";
    }
}
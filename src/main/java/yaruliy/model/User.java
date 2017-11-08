package yaruliy.model;
import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Table(name = "users")
@javax.persistence.Entity
public class User implements Serializable {
    private static final long serialVersionUID = -3009157732242241606L;

    @GeneratedValue(strategy = GenerationType.AUTO) @Id @Column(name = "id") private long id;
    @Column(name = "login") private String login;
    @Column(name = "password") private String password;
    @Column(name = "name") private String name;
    @Column(name = "surname") private String surname;
    //@Column(name = "emails") private List<String> emails;

    public List<String> getEmails() {
        ArrayList<String> emails = new ArrayList<>();
        emails.add("l.yaruk1993@gmail.com");
        return emails;
    }
    //public void setEmails(List<String> emails) { this.emails = emails; }
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
    public String getLogin() { return login; }
    public void setLogin(String login) { this.login = login; }
    public long getID() { return id; }
    public void setID(long uuid) { this.id = uuid; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getSurname() { return surname; }
    public void setSurname(String surname) { this.surname = surname; }

    public User(){}
    public User(String login, String password, long uuid){
        this.login = login;
        this.password = password;
        this.id = uuid;
    }

    public User(String login, String password, String name, String surname, String email){
        //this.emails = new ArrayList<>();
        this.login = login;
        this.password = password;
        this.name = name;
        this.surname = surname;
        //this.emails.add(email);
    }

    public String toString(){ return "[" + this.login + "; " + this.password + "]"; }
}
package yaruliy.app;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import yaruliy.mqtt.MainMqttService;
import yaruliy.secutiry.UDService;
import yaruliy.sockets.SocketServer;

@SpringBootApplication(scanBasePackages = "yaruliy")
@EnableJpaRepositories("yaruliy.datastore")
@EntityScan("yaruliy.model")
public class LoraApplication {
   /* private static SocketServer socketServer;
    @Autowired public void setSocketServer(SocketServer server){ this.socketServer = server; }*/
    private static MainMqttService mainMqttService;
    @Autowired public void setMqttService(MainMqttService mainMqttService){this.mainMqttService = mainMqttService; }

	public static void main(String[] args) {
	    SpringApplication.run(LoraApplication.class, args);
	    mainMqttService.consumer().run();
	    /*socketServer.init();
	    socketServer.start();*/
	}

    @Autowired
    public void authenticationManager(AuthenticationManagerBuilder builder,
                                      UDService service) throws Exception {
        builder.userDetailsService(service).passwordEncoder(new BCryptPasswordEncoder());
    }
}
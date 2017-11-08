package yaruliy.config;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;
import yaruliy.datastore.*;
import yaruliy.datastore.interfaces.DeviceService;
import yaruliy.datastore.interfaces.GatewayService;
import yaruliy.datastore.interfaces.MessageService;
import yaruliy.datastore.interfaces.UserService;
import yaruliy.mqtt.util.MqttServiceBuilder;

@Configuration
public class BeansConfig {
    @Value("${database.type}") private String database_type;
    @Value("${BROKER_URL}") private String BROKER_URL;

    @Bean public ObjectMapper getObjectMapper() { return new ObjectMapper(); }
    @Bean public SseEmitter getSseEmitter() { return new SseEmitter(Long.MAX_VALUE); }

    @Bean public MqttClient getMqttClient() {
        try { return MqttServiceBuilder.connect(BROKER_URL, "UserMessagePublisher"); }
        catch (MqttException e) { e.printStackTrace(); }
        return null;
    }

    @Bean public UserService getUserService() {
        switch (database_type){
            case "postgre" : return new PostgreUserService();
            case "cassandra" : return new CassandraUserService();
            default : return new PostgreUserService();
        }
    }

    @Bean public MessageService getMessageService() {
        switch (database_type){
            case "postgre" : return new PostgreMessageService();
            case "cassandra" : return new CassandraMessageService();
            default : return new PostgreMessageService();
        }
    }

    @Bean public DeviceService getDeviceService() {
        switch (database_type) {
            case "postgre": return new PostgreDeviceService();
            case "cassandra": return new CassandraDeviceService();
            default: return new PostgreDeviceService();
        }
    }

    @Bean public GatewayService gatewayService() {
        switch (database_type) {
            case "postgre": return new PostgreGatewayService();
            case "cassandra": return new CassandraGatewayService();
            default: return new PostgreGatewayService();
        }
    }
}
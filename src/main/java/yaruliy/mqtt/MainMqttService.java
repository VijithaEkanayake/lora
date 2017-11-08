package yaruliy.mqtt;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;
import yaruliy.datastore.PostgreCarInfoService;
import yaruliy.datastore.interfaces.MessageService;
import yaruliy.mqtt.calls.BasicCallback;
import yaruliy.mqtt.util.MqttServiceBuilder;
import java.util.LinkedList;

@Component
public class MainMqttService {
    @Value("${BROKER_URL}")
    private String BROKER_URL;
    private final String[] topics = {"lora/messages","lora/coordinates", "lora/test", "lora/parking"};
    LinkedList<BasicCallback> callbacks;
    private int startCount = 0;

    @Autowired
    public MainMqttService(ApplicationEventPublisher eventPublisher,
                           MessageService messageService, ObjectMapper mapper, PostgreCarInfoService carInfoService){
        this.callbacks = new LinkedList<>();
        for (int i = 0; i < 45; i++){
            callbacks.add(new BasicCallback(eventPublisher, messageService, mapper, this, carInfoService));
        }
    }

    public void startListening(){
        try {
            MqttClient client = MqttServiceBuilder.connect(BROKER_URL, "Lora_SUB[" + startCount + "]");
            BasicCallback callback = callbacks.getFirst();
            client.setCallback(callback);
            callback.setDefaultPubClient(MqttServiceBuilder.connect(BROKER_URL, "Lora_PUB[" + startCount + "]"));
            client.subscribe(topics);
            System.out.println("-> Size of MQTTClientPool = " + callbacks.size());
            startCount++;
        }
        catch (MqttException e) {
            e.printStackTrace();
        }
    }

    public Runnable consumer() { return this::startListening; }
    public void removeCallback(BasicCallback basicCallback) { this.callbacks.remove(basicCallback); }
}
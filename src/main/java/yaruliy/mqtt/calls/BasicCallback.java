package yaruliy.mqtt.calls;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.springframework.context.ApplicationEventPublisher;
import yaruliy.datastore.PostgreCarInfoService;
import yaruliy.datastore.interfaces.DeviceService;
import yaruliy.datastore.interfaces.GatewayService;
import yaruliy.datastore.interfaces.MessageService;
import yaruliy.email.Sender;
import yaruliy.model.Device;
import yaruliy.model.json.JSONData;
import yaruliy.model.json.JSONLoraMessage;
import yaruliy.model.json.JSONRxpk;
import yaruliy.model.parking.Info;
import yaruliy.mqtt.MainMqttService;
import yaruliy.mqtt.event.MessageReceivedEvent;
import yaruliy.util.parse.JSONParser;
import java.text.SimpleDateFormat;
import java.util.*;

public class BasicCallback implements MqttCallback {
    private int count = 0;
    private SimpleDateFormat dateFormat;
    private Calendar cal;
    private MqttClient pubClient;
    private MqttClient originClient;
    private final ApplicationEventPublisher eventPublisher;
    private final MessageService messageService;
    private final MainMqttService service;
    private final ObjectMapper mapper;
    private final PostgreCarInfoService carInfoService;
    private GatewayService gatewayService;
    private DeviceService deviceService;
    private Sender sender;

    public BasicCallback(ApplicationEventPublisher eventPublisher,
                         MessageService messageService, ObjectMapper mapper,
                         MainMqttService service, PostgreCarInfoService carInfoService){
        TimeZone.setDefault(TimeZone.getTimeZone("Europe/Kyiv"));
        this.dateFormat = new SimpleDateFormat("yyyy.MM.dd HH:mm:ss");
        this.cal = Calendar.getInstance();
        this.eventPublisher = eventPublisher;
        this.messageService = messageService;
        this.service = service;
        this.mapper = mapper;
        this.carInfoService = carInfoService;
    }

    public void connectionLost(Throwable arg0) {
        System.out.println("\n!!!!!!!!!!!--Connection-Lost--!!!!!!!!!!!!!\n-> "
                + arg0.toString() + "\n!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!\n");
        arg0.printStackTrace();
        service.removeCallback(this);
        service.startListening();
    }

    public void messageArrived(String topic, MqttMessage arrivedMessage) throws Exception {
        String data = new String(arrivedMessage.getPayload());
        System.out.println("\n\n-------------------------------------------------");
        System.out.println("| Topic: " + topic);
        System.out.println("| Message: " + data);
        System.out.println("-------------------------------------------------");

        if(data.indexOf("{") > 0 && data.lastIndexOf("}") > 0){
            data = data.substring(data.indexOf("{"), data.lastIndexOf("}"));
        }

        switch(topic){
            case "lora/coordinates": {
                data = data.substring(data.indexOf('{'), data.lastIndexOf('}') + 1);
                System.out.println("parser data:" + data + "\n");

                mapper.configure(JsonParser.Feature.ALLOW_UNQUOTED_FIELD_NAMES, true);
                JSONLoraMessage message = mapper.readValue(data, JSONRxpk.class).getLoraMessages()[0];
                System.out.println("loramessage: \n" + message.toString());

                message.setId(12);
                message.setTmst(getCurrentTime());
                message.setSource("MQTT");
                message.setTopic(topic);
                messageService.writeMessage(message);

                JSONData jsonData = mapper.readValue(message.getData(), JSONData.class);

                Device device = deviceService.getDeviceByLoraID(jsonData.getId());
                device.setLatitude(jsonData.getLat());
                device.setLongitude(jsonData.getLon());
                System.out.println("\n" + device);
                deviceService.saveDevice(device);

                sender.sendHTMLMessage("New Message [" + count + "]", "" +
                        "Message: " + message.getData() +
                        "<br>Source: MQTT" +
                        "<br>Time: " + message.getTmst() +
                        "<br>Device: " +
                        "<a href=\"http://ec2-34-210-69-19.us-west-2.compute.amazonaws.com:7001/" +
                        "device/" + device.getId() + "\">link</a>"
                );
                count++;
                break;
            }
            case "lora/messages": {
                JSONLoraMessage message = JSONParser.parse(data).buildObject();
                message.setTopic("lora/messages");
                System.out.println(message);
                messageService.writeMessage(message);

                /*String lastmessage = data.split(",")[1];
                String clientID = data.split(",")[0];
                System.out.println("cliend ID: " + clientID);

                Device device = deviceService.getDeviceByLoraID(clientID);
                device.setLastUsedGatewayDate(new Timestamp(System.currentTimeMillis()));
                device.setLastUsedGateway("06-00-00-00-00-00");
                deviceService.saveDevice(device);

                System.out.println("device.getLastUsedGateway(): " + device.getLastUsedGateway());
                Gateway gateway = gatewayService.getGatewayByMac(device.getLastUsedGateway());
                System.out.println("gateway MAC: " + gateway.getMac());

                System.out.println("messages [" + lastmessage.trim() + "] will be sended to device/" + gateway.getMac());
                pubClient.publish("device/"+ gateway.getMac() , new MqttMessage(lastmessage.getBytes()));*/
                break;
            }
            case "lora/test": {
                JSONLoraMessage message = new JSONLoraMessage();
                message.setTopic("lora/test");
                message.setData(data);
                message.setTmst(getCurrentTime());
                message.setSize(String.valueOf(1));

                //System.out.println(message);
                String[] arr = message.getData().split(",");
                //System.out.println(Arrays.toString(arr));
                pubEvent(arr[0], arr[1], arr[2]);
                //messageService.writeMessage(message);
                /*mapper.configure(JsonParser.Feature.ALLOW_UNQUOTED_FIELD_NAMES, true);
                JSONRxpk rxpk = mapper.readValue(data, JSONRxpk.class);

                JSONLoraMessage message = mapper.readValue(data, JSONRxpk.class).getLoraMessages()[0];
                message.setID(UUID.randomUUID());
                message.setTmst(currentTime);
                message.setSource("MQTT");
                message.setTopic(topic);
                System.out.println("message mapped by jackson:\n");
                System.out.println(message);
                System.out.println();*/
                //messageService.writeMessage(message);

                //System.out.println(message.getData());
                //JSONData jsonData = mapper.readValue(message.getData(), JSONData.class);
                break;
            }
            case "lora/parking": {
                JSONLoraMessage message = JSONParser.parse(data).buildObject();
                message.setTopic("lora/parking");
                System.out.println(message);

                Info info = new Info();
                for (Map.Entry<String, String> entry : JSONParser.parse(message.getData()).getAsHash().entrySet()) {
                    switch (entry.getKey()){
                        case "free" : {
                            info.setFree(entry.getValue());
                            break;
                        }
                        case "timestamp" : {
                            info.setTimestamp(entry.getValue());
                            break;
                        }
                        case "occupied" : {
                            info.setOccupied(entry.getValue());
                            break;
                        }
                    }
                }
                this.carInfoService.save(info);
                this.messageService.writeMessage(message);
            }
        }
    }

    public void deliveryComplete(IMqttDeliveryToken token) {
        System.out.println("-----------------\n" + token.toString() + "---------------------\n");
        System.out.println("Delivery is Complete");
    }

    private void pubEvent(String message, String sender, String recipient){
        MessageReceivedEvent event = new MessageReceivedEvent(this, message, sender, recipient);
        eventPublisher.publishEvent(event);
    }

    private String getCurrentTime(){
        cal.setTime(new Date());
        cal.add(Calendar.HOUR_OF_DAY, 3);
        return dateFormat.format(cal.getTime());
    }

    public void setDefaultPubClient(MqttClient client){ this.pubClient = client; }
}
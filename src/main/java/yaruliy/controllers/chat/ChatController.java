package yaruliy.controllers.chat;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import yaruliy.datastore.interfaces.ChatService;
import yaruliy.model.messaging.ChatMessage;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(value = "/chat")
public class ChatController {
    private MqttClient publisher;
    private ChatService chatService;
    @Autowired public void setPublisher(MqttClient publisher){ this.publisher = publisher; }
    @Autowired public void setChatService(ChatService chatService){ this.chatService = chatService; }

    @RequestMapping(value = "/sendmessage", method = RequestMethod.POST)
    public String sendMessageFromBrowser(@RequestParam(name = "message") String message,
                                         @RequestParam(name = "recipient") String recipient,
                                         HttpServletRequest request){
        try {
            System.out.println("\n");
            System.out.println("-------------------------------------");
            System.out.println("----------PUB-Controller-1----------");
            String res = message + "," + request.getUserPrincipal().getName() + "," + recipient;
            System.out.println("Pub: " + res);
            publisher.publish("lora/test", new MqttMessage(res.getBytes()));
            System.out.println("----------PUB-Controller-2----------");
            System.out.println("-------------------------------------");
            System.out.println("\n");
        }
        catch (MqttException e) { e.printStackTrace(); }
        return "success";
    }

    @RequestMapping(value = "/history/{login}", method = RequestMethod.POST)
    public List<ChatMessage> getHistory(@PathVariable(value = "login") String login, HttpServletRequest request){
        List<ChatMessage> result = new ArrayList<>();
        result.addAll(chatService.getMessagesBySenderAndRecipient(login, request.getUserPrincipal().getName()));
        result.addAll(chatService.getMessagesBySenderAndRecipient(request.getUserPrincipal().getName(), login));
        return result;
    }

    @RequestMapping(value = "/clear", method = RequestMethod.GET)
    public String clear(){
        this.chatService.deleteAllRecords();
        return "all records was droped";
    }
}
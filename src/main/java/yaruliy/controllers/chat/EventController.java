package yaruliy.controllers.chat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;
import yaruliy.datastore.interfaces.ChatService;
import yaruliy.model.messaging.ChatMessage;
import yaruliy.mqtt.event.MessageReceivedEvent;
import yaruliy.sse.SseEmitterService;

import java.util.concurrent.Executors;

@Controller
@Component
public class EventController{
    private final SseEmitterService emitterService;
    private final ChatService chatService;
    @Autowired public EventController(SseEmitterService emitterService, ChatService chatService) {
        this.emitterService = emitterService;
        this.chatService = chatService;
    }

    @RequestMapping(value = "/messaging/{login}", method = RequestMethod.GET)
    public SseEmitter events(@PathVariable(value = "login") String login){
        if(this.emitterService.contains(login)){
            return this.emitterService.getEmitterByLogin(login);
        }
        else {
            this.emitterService.addEmitter(login, new SseEmitter(Long.MAX_VALUE));
            return this.emitterService.getEmitterByLogin(login);
        }
    }

    @EventListener
    public void handleEvent(MessageReceivedEvent event) {
        Executors.newSingleThreadExecutor().execute(() -> {
            try {
                System.out.println("\n");
                System.out.println("-------------------------------------");
                System.out.println("---------EventController-1-----------");

                String eventType = "from" + event.getSender().trim() + "to" + event.getRecipient().trim();
                System.out.println("Message: " + event.getMessage() + " to  [" + eventType + "]");

                chatService.saveMessage(new ChatMessage(event.getMessage(), event.getSender(), event.getRecipient()));
                this.emitterService.getEmitterByLogin(event.getRecipient())
                        .send(SseEmitter.event().name(eventType).data(event.getMessage()));

                System.out.println("---------EventController-2-----------");
                System.out.println("-------------------------------------");
                System.out.println("\n");
            }
            catch (Exception e) { this.emitterService.getEmitterByLogin(event.getRecipient()).completeWithError(e); }
        });
    }
}
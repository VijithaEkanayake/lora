package yaruliy.sse;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;
import java.util.HashMap;

@Service
public class SseEmitterService {
    private HashMap<String, SseEmitter> emitters;

    public SseEmitterService(){
        this.emitters = new HashMap<>();
    }

    public void addEmitter(String login, SseEmitter emitter){
        this.emitters.put(login, emitter);
    }

    public SseEmitter getEmitterByLogin(String login){
        return this.emitters.get(login);
    }

    public boolean contains(String login){
        return this.emitters.containsKey(login);
    }
}
package yaruliy.util.parse;
import yaruliy.model.json.JSONLoraMessage;

import java.util.HashMap;
import java.util.Map;

public class JSONParseResult {
    HashMap<String, String> fields;

    public JSONParseResult(){ this.fields = new HashMap<>(); }
    public JSONParseResult(HashMap<String, String> map) { this.fields = map; }
    public void setFields(HashMap<String, String> fields) { this.fields = fields; }

    public void printObject(){
        System.out.println("{");
        for (Map.Entry<String, String> entry : fields.entrySet()) {
            System.out.println("\t" + entry.getKey()+" : " + entry.getValue());
        }
        System.out.println("}");
    }

    public JSONLoraMessage buildObject(){
        JSONLoraMessage message = new JSONLoraMessage();

        for (Map.Entry<String, String> entry : fields.entrySet()) {
            switch (entry.getKey()){
                case "data" : {
                    message.setData(entry.getValue());
                    break;
                }
                case "tmst" : {
                    message.setTmst(entry.getValue());
                    break;
                }
                case "freq" : {
                    message.setFreq(entry.getValue());
                    break;
                }
                case "size" : {
                    message.setSize(entry.getValue());
                }
            }
        }

        return message;
    }

    public HashMap<String, String> getAsHash(){
        return this.fields;
    }
}
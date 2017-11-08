package yaruliy.model.json;

import com.fasterxml.jackson.annotation.JsonProperty;

public class JSONRxpk {
    private JSONLoraMessage[] rxpk;
    public JSONLoraMessage[] getLoraMessages() { return rxpk; }
    public void setRxpk(JSONLoraMessage[] rxpk) { this.rxpk = rxpk; }

    public JSONRxpk(){}

    public JSONRxpk(@JsonProperty("rxpk") JSONLoraMessage[] rxpk){ this.rxpk = rxpk; }

    public String toString(){
        return "size: " + rxpk.length + "\n" + rxpk[0].toString();
    }
}
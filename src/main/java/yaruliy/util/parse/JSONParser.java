package yaruliy.util.parse;
import yaruliy.util.crypto.Crypto;
import java.util.HashMap;

public class JSONParser {
    public static JSONParseResult parse(String horror){
        System.out.println("Origin data for parsing: " + horror);
        String data = horror;
        if(horror.indexOf("[") > 0 && horror.lastIndexOf("]") > 0) {
            data = horror.substring(horror.indexOf("[") + 1, horror.lastIndexOf("]"));
        }
        data = data.substring(data.indexOf("{") + 1, data.lastIndexOf("}"));
        String[] fields = data.split(",");
        System.out.println();

        HashMap<String, String> fieldMap = new HashMap<>();
        for (String s: fields) {
            String[] field = s.split(":");
            if(field.length > 1){
                if(field[0].substring(field[0].indexOf("\"") + 1, field[0].lastIndexOf("\"")).equals("data")){
                    fieldMap.put(
                            field[0].substring(field[0].indexOf("\"") + 1, field[0].lastIndexOf("\"")),
                            decode(field[1].replace("\"",""))
                    );
                }
                else {
                    fieldMap.put(
                            field[0].substring(field[0].indexOf("\"") + 1, field[0].lastIndexOf("\"")),
                            field[1].replace("\"","")
                    );
                }
            }
        }

        JSONParseResult result = new JSONParseResult(fieldMap);
        result.printObject();

        return result;
    }

    private static String decode(String s){
        try { return Crypto.toText(Crypto.decrypt(s)); }
        catch (Exception e) { e.printStackTrace(); }
        return "";
    }
}
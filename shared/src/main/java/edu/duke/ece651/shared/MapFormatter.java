package edu.duke.ece651.shared;
import java.utils.*;
import org.json;

public class MapFormatter implements Formatter{
  void compose(){
  }
  HashMap<Integer, ArrayList<Territory>> parse(InputStream MapJson){
    BufferedReader br = new BufferedReader(new InputStreamReader(MapJson));
    String str = "";
    String line = br.readLine();
    while(line != null){
      str += line;
      line = br.readLine();
    }
    JsonObject InputMap = new JSONObject(str);
    
  }
}

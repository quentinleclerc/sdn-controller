package net.sdci.sdn;

import java.awt.font.GlyphJustificationInfo;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import net.sdci.sdn.beans.Host;
import net.sdci.sdn.beans.Switch;

public class StatisticsAdapter {
   private final static String CONTROLLER_URL = "http://10.0.2.15:8080";
   private TopologyAssociator topologyAssociator;
   public StatisticsAdapter() {
	   this.topologyAssociator = new TopologyAssociator();
   }
   
   public String enableStatistics(){

	   RestClient client = new RestClient(CONTROLLER_URL);
	   JSONObject input = new JSONObject();
	   input.put("", "");
	   String output = client.post("/wm/statistics/config/enable/json", input);
	   return output;
   }
   
   public String getBwConsumption(String switchName,int portId){

	   RestClient client = new RestClient(CONTROLLER_URL);

       Switch sw = topologyAssociator.getSwitch(switchName);
       String switchId = sw.getDpid();
	   String url = "/wm/statistics/bandwidth/" + switchId + "/" + portId + "/json";
	   String response = client.get(url);	   
	   
	   return response;
   }

}

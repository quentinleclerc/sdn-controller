package net.sdci.sdn;

import java.util.Collection;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import net.sdci.sdn.beans.AttachmentPoint;
import net.sdci.sdn.beans.Host;
import net.sdci.sdn.beans.Switch;

public class ControllerAdapter {
   private final static String CONTROLLER_URL = "http://10.0.2.15:8080";
   private TopologyAssociator topologyAssociator;
   public ControllerAdapter(){
       this.topologyAssociator = new TopologyAssociator();
   }
   public int changeIPdest(String flowName, String from, String to, String newTo, int port){
       RestClient client = new RestClient(CONTROLLER_URL);
       Host hostFrom = topologyAssociator.getHost(from);
       Host hostTo = topologyAssociator.getHost(to);
       Host hostNewTo = topologyAssociator.getHost(newTo);
       
       AttachmentPoint attachmentPoint = hostFrom.getAttachmentPoint();
       Switch sw = topologyAssociator.getSwitchByDPID(attachmentPoint.getSwitchId());
       
       JSONObject rule = new JSONObject();
       String dpid = sw.getDpid();
       
       rule.put("switch", dpid);
       rule.put("name", flowName);
       rule.put("priority", "36000");
       rule.put("in_port", attachmentPoint.getPort());
       rule.put("eth_type", "0x0800");
       //rule.put("ip_proto", "0x0006");
       rule.put("ipv4_src", hostFrom.getIp());
       //rule.put("eth_src", hostFrom.getMac());
       rule.put("ipv4_dst", hostTo.getIp());
       rule.put("active", "true");
       //rule.put("eth_dst", hostTo.getMac());
       //rule.put("actions", "set_field=ipv4_dst->"+hostNewTo.getIp()+",set_field=tcp_dst->"+port+",output=normal");
       //rule.put("actions", "set_field=ipv4_dst->"+hostNewTo.getIp());
       rule.put("actions", "set_field=ipv4_dst->"+hostNewTo.getIp()+",output=normal");
       client.post("/wm/staticflowpusher/json", rule);
       return 0;
   }
   
   public int changeIPsource(String flowName, String to, String from, String newFrom){
       RestClient client = new RestClient(CONTROLLER_URL);
       Host hostFrom = topologyAssociator.getHost(from);
       Host hostTo = topologyAssociator.getHost(to);
       Host hostNewFrom = topologyAssociator.getHost(newFrom);
       
       AttachmentPoint attachmentPoint = hostFrom.getAttachmentPoint();
       Switch sw = topologyAssociator.getSwitchByDPID(attachmentPoint.getSwitchId());
       
       JSONObject rule = new JSONObject();
       String dpid = sw.getDpid();
       rule.put("switch", dpid);
       rule.put("name", flowName);
       rule.put("priority", "35900");
       rule.put("in_port", "1");
       rule.put("eth_type", "0x0800");
       rule.put("ipv4_src",hostTo.getIp());
       // rule.put("eth_src", hostTo.getMac());
       rule.put("ipv4_dst", hostFrom.getIp());
       //rule.put("eth_dst", hostFrom.getMac());
       rule.put("actions", "set_field=eth_src->"+hostNewFrom.getMac()+",set_field=ipv4_src->"+hostNewFrom.getIp()+",output=normal");
       client.post("/wm/staticflowpusher/json", rule);
       return 0;
   }
   
   public int deleteFlow(String flowName) {
	   RestClient client = new RestClient(CONTROLLER_URL);
	   JSONObject rule = new JSONObject();
	   rule.put("name", flowName);
	   client.delete("/wm/staticflowpusher/json", rule);
	   return 0;
   }

   public void listFlows() {
	   RestClient client = new RestClient(CONTROLLER_URL);
	   JSONParser parser = new JSONParser();
	   try {
		JSONObject rules = (JSONObject) parser.parse(client.get("/wm/staticentrypusher/list/all/json"));
		System.out.println(rules.toString());
	} catch (ParseException e) {
		e.printStackTrace();
	}
	   
	   
   }
   
   public void displayTopology() {
	   Collection<Switch> switches = this.topologyAssociator.getSwitchs();
	   Collection<Host> hosts = this.topologyAssociator.getHosts();
	   
	   for (Switch sw : switches) {
		   System.out.println(sw);
	   }
	   
	   for (Host host : hosts) {
		   System.out.println(host);
	   }

	  
   }
}


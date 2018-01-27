package net.sdci.sdn;

import net.sdci.sdn.beans.AttachmentPoint;
import net.sdci.sdn.beans.Host;
import net.sdci.sdn.beans.Switch;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class TopologyAssociator {
   private HashMap<String, Host> hosts;
   private HashMap<String, Switch> switches;
   
   public TopologyAssociator(){
       initHosts();
       initSwitches();
   }
   
   public Collection<Switch> getSwitchs(){
	   return switches.values();
   }
   
   public Collection<Host> getHosts(){
	   return hosts.values();
   }
   
   private void initHosts() {
       Host device1 = new Host("172.17.0.4", "00:00:00:00:00:03", new AttachmentPoint("00:00:00:00:00:02", 2));
       Host device2 = new Host("172.17.0.5", "00:00:00:00:00:04", new AttachmentPoint("00:00:00:00:00:02", 3));
       Host device3 = new Host("172.17.0.6", "00:00:00:00:00:05", new AttachmentPoint("00:00:00:00:00:02", 4));
       Host gi = new Host("172.17.0.2", "00:00:00:00:00:01", new AttachmentPoint("00:00:00:00:00:01", 2));
       Host server = new Host("172.17.0.3", "00:00:00:00:00:02", new AttachmentPoint("00:00:00:00:00:01", 1));
       Host gi2 = new Host("172.17.0.7", "00:00:00:00:00:02", new AttachmentPoint("00:00:00:00:00:01", 3));

       hosts = new HashMap<>();
       hosts.put("Zone1", device1);
       hosts.put("Zone2", device2);
       hosts.put("Zone3", device3);
       hosts.put("Server", server);
       hosts.put("GI", gi);
       hosts.put("GI2", gi2);
   }
   private void initSwitches() {
       Switch switch1 = new Switch("/10.0.2.15:48512", "00:00:00:00:00:01");
       Switch switch2 = new Switch("/10.0.2.15:48514", "00:00:00:00:00:02");
       switches = new HashMap<>();
       switches.put("Switch1", switch1);
       switches.put("Switch2", switch2);
   }
   
   public Host getHost(String nodeName){
       return hosts.get(nodeName);
   }
   
   public Switch getSwitch(String switchName){
       return switches.get(switchName);
   }
   
   public Switch getSwitchByDPID(String dpid){
       Switch found = null;
       
       for (Map.Entry<String, Switch> entry : switches.entrySet()){
           Switch sw = entry.getValue();
           if (sw.getDpid().equals(dpid)) {
               found = sw;
           }
       }
       return found;
   }
   public String getSwitchName(Switch sw){
       String found = "";
       for (Map.Entry<String, Switch> entry : switches.entrySet()){
           Switch switchVal = entry.getValue();
           if (switchVal.equals(sw)) {
               found = entry.getKey();
           }
       }
       return found;
   }
}

package net.sdci.core;

import com.github.dockerjava.api.model.Statistics;

import net.sdci.sdn.ControllerAdapter;
import net.sdci.sdn.StatisticsAdapter;
import net.sdci.sdn.TopologyAssociator;

public class Core {

	private ControllerAdapter controllerAdapter;
	private StatisticsAdapter statisticsAdapter;
	
	public Core(ControllerAdapter controllerAdapter, StatisticsAdapter statisticsAdapter) {
		this.controllerAdapter = controllerAdapter;
		this.statisticsAdapter = statisticsAdapter;
	}
	
	public int redirectTraffic(String redirectionName, String from, String to, String newTo, int port) {
		this.controllerAdapter.changeIPdest(redirectionName+"_changeIpDest", from, to, newTo, port);
		this.controllerAdapter.changeIPsource(redirectionName+"_changeIpSource", newTo, from, to);
	
		return 0;
	}
	
	public int deleteRedirection(String redirectionName) {
		controllerAdapter.deleteFlow(redirectionName+"_changeIpSource");
		controllerAdapter.deleteFlow(redirectionName+"_changeIpDest");
		
		return 0;
	}
	
	public String monitorSwitch(String switchId, int portId){
		
		//statisticsAdapter.enableStatistics();
		String stats = statisticsAdapter.getBwConsumption(switchId, portId);
		
		return stats;
	}

	public void listRedirections() {
		this.controllerAdapter.listFlows();
		
	}

	public void displayTopology() {
		this.controllerAdapter.displayTopology();
	}
	
	
}

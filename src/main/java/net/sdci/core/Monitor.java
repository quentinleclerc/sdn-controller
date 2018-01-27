package net.sdci.core;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import com.github.dockerjava.api.model.Container;

import net.sdci.orch.VNFManager;
import net.sdci.sdn.ControllerAdapter;
import net.sdci.sdn.StatisticsAdapter;
import net.sdci.sdn.beans.Switch;

public class Monitor extends Thread{


	private String ctrlUrl = "172.17.0.7:2375";
	private int threshold = 100;
	private int bindPort = 5050;
	private int counter = 0;

	private ControllerAdapter controllerAdapter = new ControllerAdapter();
	private StatisticsAdapter statisticsAdapter = new StatisticsAdapter();
	private Core core = new Core(controllerAdapter, statisticsAdapter);
	private VNFManager manager = new VNFManager(ctrlUrl);
	private String name = "amassuoTheKing";
	private JSONParser parser = new JSONParser();


	boolean exit = false;

	Thread t;
	Scanner scanner;
	
	@Override
	public void run() {

		t = new Thread() {
			public void run() {
				scanner = new Scanner(System.in);
				while(true) {
					String read = scanner.nextLine();
					if(read.equals("exit")) {
						exit();
						break;
					}
				}
			}
		};
		t.start();
		while(!exit) {
			try {
				String stats = core.monitorSwitch("Switch1", 2);
				JSONArray json = (JSONArray) parser.parse(stats);

				JSONObject object = (JSONObject) json.get(0);
				//			System.out.println(object.toJSONString());

				int Rx = Integer.parseInt((String)object.get("bits-per-second-tx"));
				System.out.println("bits tx "+ Rx);
				System.out.println("counter "+ counter);
				if (Rx > threshold && counter > 9){
					List<Container> list = manager.getContainers(true);
					if (list.isEmpty()){
						manager.addGateway("advisehub/gi2finaleone",name+0, 8282, bindPort);
						manager.startGateway(name+0);
						list.add(manager.getGateway(name+0));
						System.out.println("new gateway created");
						//					List<Switch> listDevices = getDevicesToRedirect();
						//					for (int i =1 ;i<=3;i++) {
						//						 core.redirectTraffic("fromZone1toGItoGI"+i, "Zone"+i, "GI", name+list.size(), bindPort+list.size());
						//					}
					}
					else{
						int nbContainers = list.size();
						String statsDC = core.monitorSwitch("Switch1", 2);


						json = (JSONArray) parser.parse(statsDC);

						object = (JSONObject) json.get(0);
						//					System.out.println(object.toJSONString());

						int RxDC = Integer.parseInt((String)object.get("bits-per-second-tx"));

						if (RxDC > threshold*nbContainers){
							manager.addGateway("advisehub/gi2finaleone",name+nbContainers, 8282, bindPort+nbContainers);
							manager.startGateway(name+nbContainers);
							System.out.println("gateway created");
							//						List<Switch> listDevices = getDevicesToRedirect();
							//						for (int i =1 ;i<=3;i++) {
							//							 core.redirectTraffic("fromZone1toGItoGI"+i, "Zone"+i, "GI", name+list.size(), bindPort+list.size());
							//						}
						}
					} 
					counter = 0;
				}
				else if (counter < 10 && Rx < threshold){
					counter = 0;
				}
				else if (Rx > threshold && counter < 10){
					counter += 1;
				}
				else{
					counter = 0;
				}
				Thread.sleep(500);
			} catch (ParseException | IOException | InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	public void exit() {
		exit = true;
		//scanner.close();
		//t.yield();
	}

	public List<Switch> getDevicesToRedirect(){
		List<Switch> list = new ArrayList<>();

		List<Integer> trafficDevices = new ArrayList<>();


		return list;
	}

}

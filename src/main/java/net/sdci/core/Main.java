package net.sdci.core;

import java.io.IOException;
import java.util.Scanner;

import net.sdci.orch.VNFManager;
import net.sdci.sdn.ControllerAdapter;
import net.sdci.sdn.StatisticsAdapter;
import net.sdci.core.Core;

public class Main {
	
	
	public static void main(String[] args) throws IOException, InterruptedException {
		Scanner scanner = new Scanner(System.in);
		System.out.println("Welcome !");
		//System.out.print("Enter the Ctrlurl of the DC (@:port) : ");
		boolean urlOk = false;
		String ctrlUrl = "172.17.0.7:2375";
//		while(!urlOk) {
//			ctrlUrl = scanner.nextLine();
//			ctrlUrl.replaceAll("\\n",""); ctrlUrl.replaceAll("\\r","");
//			if(!ctrlUrl.contains(":") || ctrlUrl.split("\\.").length != 4) {
//				System.out.println("Error ! expected format => @:port");
//				System.out.print("Enter the Ctrlurl of the DC (@:port) : ");
//			} else {
//				urlOk = true;
//			}
//		}
		
		VNFManager vnfManager = new VNFManager(ctrlUrl);
		ControllerAdapter controllerAdapter = new ControllerAdapter();
		StatisticsAdapter statisticsAdapter = new StatisticsAdapter();
		Core core = new Core(controllerAdapter,statisticsAdapter);
		boolean exit = false;
		while(!exit) {
			System.out.println("*********   Menu   *********");
			System.out.println("1. Display the network topology");
			System.out.println("2. Redirect traffic");
			System.out.println("3. Delete redirection");
			System.out.println("4. Deploy gateway");
			System.out.println("5. Monitor the system");
			System.out.println("6. Print rules");
			System.out.println("7. Exit :'( ");
			System.out.print("\n \n Your selection ? : ");
			int selection = scanner.nextInt();
			scanner.nextLine();
			String redName;
			int bindport;
			switch(selection) {
				case 1:
					core.displayTopology();
					break;
				case 2:
					System.out.print("Enter redirection name : "); redName = scanner.nextLine();
					System.out.print("Enter zone name : "); String zoneName = scanner.nextLine();
					System.out.print("Enter first gateway name : "); String gwName1 = scanner.nextLine();
					System.out.print("Enter second gateway name : "); String gwName2 = scanner.nextLine();
					System.out.print("Enter container bindport : "); bindport = scanner.nextInt(); scanner.nextLine();
					core.redirectTraffic(redName, zoneName, gwName1, gwName2, bindport);
					break;
				case 3:
					System.out.print("Enter redirection name : "); redName = scanner.nextLine();	
					core.deleteRedirection(redName);
					break;
				case 4:
					//System.out.print("Enter image Name : "); String img = scanner.nextLine();
					System.out.print("Enter container name : "); String ctId = scanner.nextLine();
					//System.out.print("Enter container port : "); int port = scanner.nextInt(); scanner.nextLine();
					System.out.print("Enter container bindport : "); bindport = scanner.nextInt(); scanner.nextLine();
					String img = "advisehub/gi2finaleone";
					int port = 8282;
					vnfManager.addGateway(img, ctId, port, bindport);
					vnfManager.startGateway(ctId); 
					break;
				case 5: 
					Thread monitor = new Monitor();
					monitor.start();
					monitor.join();
					break;
				case 6:
					core.listRedirections();
					break;
				case 7:
					exit = true;
					break;
			}
		}
		scanner.close();

//		vnfManager.addGateway("advisehub/gi2finaleone", "GI2_id", 8282, 9090);
//		vnfManager.startGateway("GI2_id");
		
		// ControllerAdapter controllerAdapter = new ControllerAdapter();

		// Core core = new Core(controllerAdapter);
		// core.deleteRedirection("fromZone1toGItoGI2");
		// core.redirectTraffic("fromZone1toGItoGI2", "Zone1", "GI", "GI2", 9090);
	}
	
}
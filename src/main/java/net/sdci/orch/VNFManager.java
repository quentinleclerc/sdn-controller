package net.sdci.orch;

import java.util.List;
import java.io.IOException;

import com.github.dockerjava.api.exception.ConflictException;
import com.github.dockerjava.api.model.Container;

public class VNFManager {

	DockerCli dockerClient;
	
	public VNFManager(String ctrldockerurl) {
		
		dockerClient = new DockerCli(ctrldockerurl);
	}
	
	public int addGateway(String img, String name, int port, int bindport) throws IOException {
		try {
			dockerClient.createContainer(img, name, port, bindport);
			return 0;
		} catch(ConflictException e) {
			dockerClient.stopContainer(name);
			dockerClient.removeContainer(name);
			return addGateway(img,name,port,bindport);
		} catch(Exception e) {
			e.printStackTrace();
			return -1;
		}
	}
	
	public int startGateway(String name) {
		try {
			dockerClient.startContainer(name);
			return 0;
		} catch(Exception e) {
			e.printStackTrace();
			return -1;
		}
	}
	
	public int stopGateway(String name) {
		try {
			dockerClient.stopContainer(name);
			return 0;
		} catch(Exception e) {
			e.printStackTrace();
			return -1;
		}
	}
	
	public int deleteGateway(String name) {
		try {
			dockerClient.removeContainer(name);
			return 0;
		} catch(Exception e) {
			e.printStackTrace();
			return -1;
		}
	}
	
	public Container getGateway(String name) throws IOException {
		for(Container c : dockerClient.listContainers(true)) {
			if(c.getId().equals(name))
				return c;
		}
		return null;
	}
	
	public List<Container> getContainers(Boolean containsAll) throws IOException {
		return dockerClient.listContainers(containsAll);
	}
	
}

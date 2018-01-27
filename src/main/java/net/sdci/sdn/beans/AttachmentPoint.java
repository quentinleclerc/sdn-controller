package net.sdci.sdn.beans;

public class AttachmentPoint {

    private String switchId;
    private int port;

    public AttachmentPoint(String switchId, int port) {
        this.switchId = switchId;
        this.port = port;
    }
    
    public int getPort() {
    	return this.port;
    }
    
    public String getSwitchId() {
    	return this.switchId;
    }

	@Override
	public String toString() {
		return "AttachmentPoint [switchId=" + switchId + ", port=" + port + "]";
	}
    
    
}

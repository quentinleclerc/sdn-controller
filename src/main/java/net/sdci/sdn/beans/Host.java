package net.sdci.sdn.beans;

public class Host extends Node {

    private AttachmentPoint attachmentPoint;

    public Host(String ip, String mac, AttachmentPoint point){
        super(ip, mac);

        this.attachmentPoint = point;
    }

	public AttachmentPoint getAttachmentPoint() {
		return attachmentPoint;
	}

	@Override
	public String toString() {
		return "Host " + super.toString() + "[attachmentPoint=" + attachmentPoint + "]";
	}
	
	
    
}

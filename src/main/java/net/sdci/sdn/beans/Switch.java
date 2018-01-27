package net.sdci.sdn.beans;

public class Switch {

    private final String dpid;
    private final String mac;

    public Switch(String mac, String dpid){
        this.dpid = dpid;
        this.mac = mac;
    }

	public String getDpid() {
		return dpid;
	}

	public String getMac() {
		return mac;
	}

	@Override
	public String toString() {
		return "Switch [dpid=" + dpid + "]";
	}
	
	
}



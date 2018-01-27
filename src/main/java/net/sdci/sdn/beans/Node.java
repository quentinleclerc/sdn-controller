package net.sdci.sdn.beans;

public class Node {

    private String ip;
    private String mac;

    public Node() {

    }

    public Node(String ip, String mac) {
        this.ip = ip;
        this.mac = mac;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Node node = (Node) o;

        if (!ip.equals(node.ip)) return false;
        return mac.equals(node.mac);
    }

    @Override
    public int hashCode() {
        int result = ip.hashCode();
        result = 31 * result + mac.hashCode();
        return result;
    }
    
    public String getIp() {
    	return this.ip;
    }
    
    public String getMac() {
    	return this.mac;
    }

	@Override
	public String toString() {
		return "Node [ip=" + ip + ", mac=" + mac + "]";
	}
    
    
    
}

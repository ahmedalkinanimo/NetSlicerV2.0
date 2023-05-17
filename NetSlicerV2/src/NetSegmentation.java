import java.util.ArrayList;

public class NetSegmentation {
	private SubnettingStrategy sns;
	public NetSegmentation(SubnettingStrategy sns) {
		this.sns=sns;
	}
	public ArrayList<String> applySubnet(networkIPAddress netIp){
		return sns.Subnet(netIp);
	}
}

import java.util.ArrayList;

public class FixedHostSubnettingStrategy implements SubnettingStrategy{
	private int numOfHosts;
	
	public FixedHostSubnettingStrategy(int numOfHosts) {
		this.setNumOfHosts(numOfHosts);
	}
	
	  public ArrayList<String> Subnet(networkIPAddress netIp) {
			ArrayList<String> subnets=new ArrayList<String>();
			int hostPortion=32-netIp.getPrefix();
			int keptBits=(int) Math.ceil(Math.log(numOfHosts+2)/Math.log(2));
					
			if(keptBits>=hostPortion) {
				return subnets;
			}
			int borrowedBits=hostPortion-keptBits;
			int newPrefix=netIp.getPrefix()+borrowedBits;	
			int usableIP=(int)Math.pow(2,32-newPrefix);
			int numOfSubNets=(int)Math.pow(2, borrowedBits);
			String subnet;
			for(int i=0;i<numOfSubNets;i++) {
				subnet="";
				int accumulativeNumberOfHosts=i*usableIP;
				int[] maskMod=new int[4];
				for(int j=0;j<4;j++) {
					maskMod[j]=(accumulativeNumberOfHosts>>j*8) & 0xFF;	
				}
				subnet=(netIp.getNetIpOctet()[0]+maskMod[3])
						+"."+(netIp.getNetIpOctet()[1]+maskMod[2])
						+"."+(netIp.getNetIpOctet()[2]+maskMod[1])
						+"."+(netIp.getNetIpOctet()[3]+maskMod[0])
						+"/"+Integer.toString(newPrefix);
				subnets.add(subnet);
			}
			return subnets;
		}

	public int getNumOfHosts() {
		return numOfHosts;
	}

	public void setNumOfHosts(int numOfHosts) {
		this.numOfHosts = numOfHosts;
	}
	
	
}


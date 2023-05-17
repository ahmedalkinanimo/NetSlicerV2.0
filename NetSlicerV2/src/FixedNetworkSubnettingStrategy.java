import java.util.ArrayList;

public class FixedNetworkSubnettingStrategy implements SubnettingStrategy{
	private int numOfSubNets;
	
	public FixedNetworkSubnettingStrategy(int numOfSubNets) {
		this.setNumOfSubNets(numOfSubNets);
	}
	
	public ArrayList<String> Subnet(networkIPAddress netIp) {
		ArrayList<String> subnets=new ArrayList<String>();
		int borrowedBits=(int) Math.ceil(Math.log(numOfSubNets)/Math.log(2));
		if(borrowedBits>=(32-netIp.getPrefix())-1) {
			return subnets;
		}		
		int newPrefix=netIp.getPrefix()+borrowedBits;	
		int usableIP=(int)Math.pow(2,32-newPrefix);
		
		String subnet;
		for(int i=0;i<numOfSubNets;i++) {
			subnet="";
			int accumulativeNumberOfHosts=i*usableIP;
			int[] maskMod=new int[4];
			for(int j=0;j<4;j++) {
				maskMod[j]=(accumulativeNumberOfHosts>>j*8) & 0xFF;
			}
			System.out.println();
			subnet=(netIp.getNetIpOctet()[0]+maskMod[3])
					+"."+(netIp.getNetIpOctet()[1]+maskMod[2])
					+"."+(netIp.getNetIpOctet()[2]+maskMod[1])
					+"."+(netIp.getNetIpOctet()[3]+maskMod[0])
					+"/"+Integer.toString(newPrefix);
			subnets.add(subnet);
		}
		return subnets;
	}
	
	public int getNumOfSubNets() {
		return numOfSubNets;
	}

	public void setNumOfSubNets(int numOfSubNets) {
		this.numOfSubNets = numOfSubNets;
	}
	
}

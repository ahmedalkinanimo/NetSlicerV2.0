import java.util.ArrayList;
import java.util.Arrays;

public class VLSMSubnettingStrategy implements SubnettingStrategy {
	private int[] numOhHostsPerSubNet;
	
	private static int possibleNumOfNetworks(int[] numOfNet,networkIPAddress netIp) {
		int count=0;
		int sum=0;
		for (int i = 0; i < numOfNet.length; i++) {           
            int inputValue=numOfNet[i];
            int mappedValue=0;
            while (inputValue >= 2) {
            	inputValue = inputValue >> 1;
                mappedValue = (mappedValue == 0) ? 4 : mappedValue << 1;
            }           
            sum+=mappedValue;
            if(sum<=(netIp.getNumberOfHosts()+2)) {
            	count++;
            }else {
            	break;
            }
        }
		return count;
	}
	
	public VLSMSubnettingStrategy(int[] numOhHostsPerSubNet) {
		Arrays.sort(numOhHostsPerSubNet);
        for (int i = 0; i < numOhHostsPerSubNet.length / 2; i++) {
            int temp = numOhHostsPerSubNet[i];
            numOhHostsPerSubNet[i] = numOhHostsPerSubNet[numOhHostsPerSubNet.length - 1 - i];
            numOhHostsPerSubNet[numOhHostsPerSubNet.length - 1 - i] = temp;
        }
		this.setNumOhHostsPerSubNet(numOhHostsPerSubNet);
	}
	
	  public ArrayList<String> Subnet(networkIPAddress netIp) {
		  ArrayList<String> subnets=new ArrayList<String>();
		  int hostInd=0;	  
		  int hostPortion=32-netIp.getPrefix();
		  int subnetHosts = numOhHostsPerSubNet[hostInd];	
		  int keptBits=(int) Math.ceil(Math.log(subnetHosts+2)/Math.log(2));	  		   
		  		  
		  int numOfNetworks=possibleNumOfNetworks(numOhHostsPerSubNet,netIp);
		  int accumulativeNumberOfHosts=0;
		  if(keptBits<hostPortion) {
			  subnets=Subnet(hostInd,subnets, netIp, numOfNetworks,accumulativeNumberOfHosts); 
		  }
		  return subnets;
	  }
	  
	  public ArrayList<String> Subnet(int hostInd,ArrayList<String> subnets,networkIPAddress netIp, int numOfNetworks,int accumulativeNumberOfHosts) {
		  if (hostInd == numOfNetworks) { //numOfNetworks
	          // Base case: All hosts have been allocated to subnets
	          return subnets;
	      }
		  
		  int hostPortion=32-netIp.getPrefix();
		  int subnetHosts = numOhHostsPerSubNet[hostInd];
		  int keptBits=(int) Math.ceil(Math.log(subnetHosts+2)/Math.log(2));
		  
		  int borrowedBits=hostPortion-keptBits;
		  int newPrefix=netIp.getPrefix()+borrowedBits;	
		  int usableIP=(int)Math.pow(2,32-newPrefix);
		  
		  String subnet="";
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
		  accumulativeNumberOfHosts+=usableIP;
		  hostInd++;
		  
		  return Subnet(hostInd,subnets,netIp, numOfNetworks,accumulativeNumberOfHosts); 
	  }
	
	public int[] getNumOhHostsPerSubNet() {
		return numOhHostsPerSubNet;
	}

	public void setNumOhHostsPerSubNet(int[] numOhHostsPerSubNet) {
		this.numOhHostsPerSubNet = numOhHostsPerSubNet;
	}
	
}


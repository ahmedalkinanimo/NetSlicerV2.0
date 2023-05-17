
public class networkIPAddress{
	private IPAddress enteredIp;					// instance of the Entered IP address
	private String netIp;							// Network IP address as String						// IP prefix
	private int[] netIpOctet=new int[4]; 			// NetWork Ip address octets
	private String firstIpAddress;					// 1st available Ip address
	private String lastIpAddress;					// last available Ip address
	private String broadCastIpAddress;				// BoradCast Ip Address
	private int numberOfHosts;						// # of usable Hosts
	
	public networkIPAddress(IPAddress enteredIp) {
		this.setEnteredIp(enteredIp);
		this.setNetIp();				// using the subnet mask octets, this method will determine the network IP address in form of string and numerical value for the octets
		this.setFirstIpAddress();		// this method will find the 1st available IP address
		this.setLastIpAddress();		// this method will find the Last available IP address
		this.setbroadCastIpAddress();	// this method will find the broad cast IP address
		this.setNumberOfHosts();		/// this method will find the # of available usable IP address in the network
	}
	
	public void setNetIp() { 
		// this method will find the network IP address: in a String form and numerical form of 4 octets
		//  Network IP address = Entered IP address & (bitwise AND) subnet mask
		this.netIp="";
		String temp;
		for(int i=0;i<4;i++) {
			this.netIpOctet[i]=this.enteredIp.getIpOctet()[i]&this.enteredIp.getSubnetMask()[i];
			temp=String.valueOf(this.netIpOctet[i]);
			this.netIp+=(i!=3?temp+".":temp);
		}
	}
	
	public int[] getNetIpOctet() {
		return netIpOctet;
	}
	
	public IPAddress getEnteredIp() {
		return enteredIp;
	}

	public void setEnteredIp(IPAddress enteredIp) {
		this.enteredIp = enteredIp;
	}

	public void setFirstIpAddress() {
		// this method will find the 1st available IP address: 
		//  1st available IP address = netIP+1
		this.firstIpAddress="";
		for(int i=0;i<4;i++) {
			firstIpAddress+=(i!=3?String.valueOf(this.netIpOctet[i])+".":String.valueOf(this.netIpOctet[i]+1));
		}
	}

	public void setLastIpAddress() {
		// this method will find the last IP address:
		// last IP address= [netIP ~ (bitwise negation) of subnet mask] -1
		// integers are represented using two's complement notation in Java. 
		// To convert the bitwise negation of 255 back to its unsigned binary representation, 
		// we added 256 to the result
		this.lastIpAddress="";
		for(int i=0;i<4;i++) {
			lastIpAddress+=(i!=3?String.valueOf(netIpOctet[i]|(~enteredIp.getSubnetMask()[i]+256))+".":String.valueOf(netIpOctet[i]|(~enteredIp.getSubnetMask()[i]+256)-1));
		}
	}

	public void setbroadCastIpAddress() {
		// this method will find the broad cast IP address:
		// broad cast IP address= netIP ~ (bitwise negation) of subnet mask
		// integers are represented using two's complement notation in Java. 
		// To convert the bitwise negation of 255 back to its unsigned binary representation, 
		// we added 256 to the result
		this.broadCastIpAddress="";
		for(int i=0;i<4;i++) {
			broadCastIpAddress+=(i!=3?String.valueOf(netIpOctet[i]|(~enteredIp.getSubnetMask()[i]+256))+".":String.valueOf(netIpOctet[i]|(~enteredIp.getSubnetMask()[i]+256)));
		}
	}

	public void setNumberOfHosts() {
		// the size of host portion of the ip address = 32- prefix
		// # of usable hosts = 2 to the power of (32- prefix) -2
		this.numberOfHosts=(int)Math.pow(2,(32-this.enteredIp.getPrefix()))-2;
	}
	
	public int getPrefix() {
		return enteredIp.getPrefix();
	}
	
	public String toString() {
		// returns a string representation of an object (Network IP address) 
		String str="Entered IP address: "+this.getEnteredIp();
		str+="\nNetwork Ip address :"+this.netIp+"/"+this.getPrefix();
		str+="\nSubnet mask: "+this.enteredIp.getSubnetMaskStr();
		str+="\nNumber of Hosts :"+this.numberOfHosts;
		str+="\nBroad Cast Ip Address :"+this.broadCastIpAddress;
		str+="\nFirst Ip Address :"+this.firstIpAddress;
		str+="\nLast Ip Address :"+this.lastIpAddress;
		return str;
	}
	
	public void setNetIpOctet(int netIpOctet, int ind) {
		this.netIpOctet[ind] = netIpOctet;
	}
	
	public String getNetIp() {
		return netIp;
	}

	public int getNumberOfHosts() {
		return numberOfHosts;
	}
	
}




public class IPAddress {
	private String Ip;								// Entered IP address as string
	private int[] IpOctet=new int[4];				// IP 4 octet
	private int prefix;								// IP prefix
	private int[] subnetMask=new int[4];			// subnet  address octets 
	private String subnetMaskStr;					// subnet  address mask
	
	
	public IPAddress(String ipInput){ // ipInput is validated
		// Argument Constructor 
		
		this.setIp(ipInput); 			// set the Entered (validated) IP address
		this.setOctetsAndPrefix();		// prefix and octets of the entered IP address extraction
		this.setSubnetMask();			// determining the subnet mask in a form of string and numerical value for the octets		
	}
	
	public void setIp(String ip) {
		Ip = ip;
	}
	
	public void setOctetsAndPrefix() {
		// extract the Octets and prefix from the IP address
		String[] IpPrefix=Ip.split("/");
		String[] Oct=IpPrefix[0].split("\\.");
		this.prefix=Integer.parseInt(IpPrefix[1]); 
		for(int i=0;i<4;i++) {
			this.IpOctet[i]=Integer.parseInt(Oct[i]);	
		}	
	}
	
	public void setSubnetMask() {
		// determining the subnet mask in a form of string and numerical value for the octets		
		this.subnetMaskStr="";
		// generating a sequence of 32 ones and shifting them to the left by (32-prefix)
		// example: if prefix is 24, the 32 of the ones will be shifted to the left by 8 and we will end up with 24 ones
		// whic will be assigned to mask
		int mask = 0xFFFFFFFF << (32 - prefix);
		// the following procedure is to break down the mask into 4 octets of the subnet mask and to generat the string form of the subnet mask
		int stepSize=24;
		for(int i=0;i<4;i++) {
			// for each iteration, the mask will be shifted to the right by a step size (adapted) and a
			// bitwise AND operation will be performed with 0xFF (11111111) to find the octets 
			this.subnetMask[i]=mask>>>stepSize & 0xFF;
			stepSize-=8;
			this.subnetMaskStr+=(i!=3?Integer.toString(subnetMask[i])+".":Integer.toString(subnetMask[i]));
		}
	}

	public void setSubnetMaskStr(String subnetMaskStr) {
		this.subnetMaskStr = subnetMaskStr;
	}
	
	public String getIp() {
		return Ip;
	}
	public int[] getIpOctet() {
		return IpOctet;
	}
	public int getPrefix() {
		return prefix;
	}
	public String getSubnetMaskStr() {
		return subnetMaskStr;
	}
	public int[] getSubnetMask() {
		return subnetMask;
	}
	
	public static boolean validateIp(String ipInput) {
		// check for the format of the Entered IP address: aa.bb.cc.dd/ee
		
		try {
			String[] IpPrefix=ipInput.split("/");  // using'/' to split IP address
			// 2 elements should be produced if the format was correct
			//aa.bb.cc.dd & ee
			
			if(IpPrefix.length!=2)
				return false;
			// checking if the prefix is acceptable
			int pfx=Integer.parseInt(IpPrefix[1]); // if the entered prefix is nut numerical, NumberFormatException exception will be thrown 
			if(pfx <0 || pfx >32) // checking of invalid numerical value
				return false;
			
			
			String[] Oct=IpPrefix[0].split("\\."); // using'.' to split IP address octets
			// 4 elements should be produced if the format was correct 
			// aa, bb, cc, & dd
			
			if(Oct.length!=4)
				return false;
			int ipOct;
			for(int i=0;i<4;i++) {
				ipOct=Integer.parseInt(Oct[i]);
				if(ipOct <0 || ipOct >255)
					return false;
			}	
			// if no exceptions are thrown, valid IP address
			return true;	
			
		}catch(NumberFormatException e) {
			return false;
		}	
	}
	
	public String toString() {
		// returns a string representation of an object (IP address) 
		String str=""+this.Ip;
		return str;
	}
	
}

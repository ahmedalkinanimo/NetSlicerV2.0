import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;

import javax.swing.JOptionPane;

public abstract class NetCal {
	
	public static ArrayList<String> hostSubReq(String IP){
		ArrayList<String> subnets = null;
		String userInput="";		
		try {
			if(!(IPAddress.validateIp(IP))) {
				JOptionPane.showMessageDialog(null,"Invalid IP address","Error", JOptionPane.ERROR_MESSAGE);
				throw new Exception("Invalid IP address");
			}else {
				while(true) {			
					networkIPAddress netIP=new networkIPAddress(new IPAddress(IP));
					userInput = JOptionPane.showInputDialog(null, "How Many hosts do You Need per network?");					
					int numberofSubNets=Integer.parseInt(userInput);
					if(numberofSubNets<=0) {
						JOptionPane.showMessageDialog(null,"It's impossible to complete the task with the given information","No Subnets", JOptionPane.ERROR_MESSAGE);
					}else {
						subnets=new NetSegmentation(new FixedHostSubnettingStrategy(numberofSubNets)).applySubnet(netIP);		
						if(subnets.size()==0) {
							JOptionPane.showMessageDialog(null,"It's impossible to complete the task with the given information","No Subnets", JOptionPane.ERROR_MESSAGE);
						}else{
							subnets.add(0,userInput);
							return subnets;
						}
					}
				}	
			}
		}catch(NumberFormatException e2) {
        	JOptionPane.showMessageDialog(null,"Invalid Entry","Error", JOptionPane.ERROR_MESSAGE);
		}catch (Exception e) {
	        System.out.println(e.getMessage());
	    }
		return subnets;
	}

	
	public static ArrayList<String> netSubReq(String IP){
		ArrayList<String> subnets = null;
		String userInput="";
		
			try {
				if(!(IPAddress.validateIp(IP))) {
					JOptionPane.showMessageDialog(null,"Invalid IP address","Error", JOptionPane.ERROR_MESSAGE);
					throw new Exception("Invalid IP address");
				}else {
					while(true) {
					networkIPAddress netIP=new networkIPAddress(new IPAddress(IP));
					userInput = JOptionPane.showInputDialog(null, "How Many Networks do You Need?");
	
					int numberofSubNets=Integer.parseInt(userInput);
					subnets=new NetSegmentation(new FixedNetworkSubnettingStrategy(numberofSubNets)).applySubnet(netIP);	
					if(subnets.size()==0) {
						JOptionPane.showMessageDialog(null,"It's impossible to complete the task with the given information","No Subnets", JOptionPane.ERROR_MESSAGE);
					}
					else{
						subnets.add(0,userInput);
						return subnets;
					}
				}
			}
			}catch(NumberFormatException e2) {
		        JOptionPane.showMessageDialog(null,"Invalid Entry","Error", JOptionPane.ERROR_MESSAGE);
			}catch (Exception e) {
			       System.out.println(e.getMessage());
				}
			return subnets;
			}
					

	public static ArrayList<String> VLSMSubReq(String IP){
		ArrayList<String> subnets = null;		
		try {
			if(!(IPAddress.validateIp(IP))) {
				JOptionPane.showMessageDialog(null,"Invalid IP address","Error", JOptionPane.ERROR_MESSAGE);
				throw new Exception("Invalid IP address");
			}else {
				while(true) {
					networkIPAddress netIP=new networkIPAddress(new IPAddress(IP));
					String userInput = JOptionPane.showInputDialog(null, "Enter the # of hosts of each subnet, use space between numbers");
					String[] strArray = userInput.split(" ");
					Integer[] intArray = new Integer[strArray.length];
					boolean flagNeg=false;
					for (int i = 0; i < strArray.length; i++) {
					    intArray[i] = Integer.parseInt(strArray[i]);
					    if(intArray[i]<=0)
					    	flagNeg=true;
					}
					if(flagNeg) {
						JOptionPane.showMessageDialog(null,"It's impossible to complete the task with the given information","No Subnets", JOptionPane.ERROR_MESSAGE);
						
					}else {
						Arrays.sort(intArray, Comparator.reverseOrder());
						int[] sortedArr = Arrays.stream(intArray).mapToInt(Integer::intValue).toArray();
						subnets=new NetSegmentation(new VLSMSubnettingStrategy(sortedArr)).applySubnet(netIP);	
						if(subnets.size()==0) {
							JOptionPane.showMessageDialog(null,"It's impossible to complete the task with the given information","No Subnets", JOptionPane.ERROR_MESSAGE);
						}else{
							subnets.add(0,Arrays.toString(sortedArr));
							return subnets;
						}
					}
				}
			}
		}catch(NumberFormatException e2) {
		       JOptionPane.showMessageDialog(null,"Invalid Entry","Error", JOptionPane.ERROR_MESSAGE);
		}catch (Exception e) {
			    System.out.println(e.getMessage());
		}
		return subnets;
	}
	
	public static String networkInfo(String IP){
		networkIPAddress netIP = null;
	    try {
	        if (!(IPAddress.validateIp(IP))) {
	            JOptionPane.showMessageDialog(null, "Invalid IP address", "Error", JOptionPane.ERROR_MESSAGE);
	            throw new Exception("Invalid IP address");
	        } else {
	            netIP = new networkIPAddress(new IPAddress(IP));
	            return netIP.toString();
	        }
	    } catch (Exception e) {
	        System.out.println(e.getMessage());
	    }
	    return "Unable to calculate network information";		
	}
}


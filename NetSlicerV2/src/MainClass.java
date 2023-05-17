import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.awt.event.*;


import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

/***
 * @author aaljanabi
 *	The primary aim of this project is to develop an application that can subnet a 
 *	given IPv4 address using three different subnetting methods. Additionally, the 
 *	application validates the input IPv4 address and computes the corresponding network 
 *	IPv4 address. For each network, the application determines the first, last, and 
 *	broadcast IPv4 addresses and also computes the number of available usable IP 
 *	addresses. The goal of this project is to accomplish these tasks without relying on 
 *	any pre-existing network libraries.
 *	Strategic Pattern Design will be utilized in this project.
 */
public class MainClass extends JFrame{
	/*
	- The MainClass represents the main GUI application for NetSlicer.
	- The purpose of this project is to develop an application that can subnet a given IPv4 address
	*/
	
	private static final long serialVersionUID = 1L;
	
	// GUI components
	private netLabel inputLabel, IpFormat, message, resultsLable; // resultsLable later
	private JTextField inputField;	
	private netButton subnetCal, numOfSubnets, VLSM, hostsPSub;
	 
	public MainClass() {
		super("NetSlicer");
		Font font = new Font("Arial", Font.BOLD, 18);
		setLayout(new BorderLayout());
		
		// labels and text fields
		inputLabel = new netLabel(" Enter IP Address:",font);
		IpFormat = new netLabel(" Example: 192.168.100.117/24",font);
		message = new netLabel("Copyright © 2023 Ahmed Alkinani - aaljanabi@kckcc.edu",font);
		inputField = new JTextField(20);
		inputField.setFont(font);
		
		// buttons
		subnetCal = new netButton("Network Information",font);	        
        numOfSubnets = new netButton("Subnet By Network Requirements",font);
        hostsPSub=new netButton("Subnet By Host Requirments",font);
        VLSM = new netButton("Subnet Using VLSM", font);
        
        // Add the components to the frame
        JPanel inputPanel = new JPanel(); // hold the label and text field
        inputPanel.setLayout(new GridLayout(3, 1));
        inputPanel.add(inputLabel);
        inputPanel.add(inputField);
        inputPanel.add(IpFormat);
        add(inputPanel, BorderLayout.NORTH);
        
        JPanel CentralPanel = new JPanel(); // hold the buttons and the results section
        CentralPanel.setSize(800,600);
        
        JPanel buttonPanel = new JPanel();	        
        buttonPanel.setLayout(new GridLayout(2, 2));
        buttonPanel.add(subnetCal);
        buttonPanel.add(numOfSubnets);
        buttonPanel.add(hostsPSub);
        buttonPanel.add(VLSM);
        
        JPanel subNetsPanel = new JPanel();
        
        JPanel outputsLeftPanel = new JPanel();
        outputsLeftPanel.setBackground(Color.BLACK);
        JTextArea leftLabel = new JTextArea("");
        leftLabel.setEditable(false);
        leftLabel.setFont(font);
        leftLabel.setForeground(Color.WHITE);
        leftLabel.setBackground(Color.BLACK);
        
        JScrollPane scrollPane = new JScrollPane(leftLabel);
        scrollPane.setPreferredSize(new Dimension(380, 500));
        scrollPane.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        outputsLeftPanel.add(scrollPane);
        
        //outputsLeftPanel.add(leftLabel);
        
        JPanel outputsRightPanel = new JPanel();        
        outputsRightPanel.setBackground(Color.BLACK);
        JTextArea rightLabel = new JTextArea("");
        rightLabel.setEditable(false);
        rightLabel.setFont(font);
        rightLabel.setForeground(Color.WHITE);
        rightLabel.setBackground(Color.BLACK);     
        outputsRightPanel.add(rightLabel);
        
        subNetsPanel.setLayout(new GridLayout(1, 2));        
        subNetsPanel.add(outputsLeftPanel);
        subNetsPanel.add(outputsRightPanel);
             
        CentralPanel.add(buttonPanel,BorderLayout.NORTH);
        CentralPanel.add(subNetsPanel,BorderLayout.CENTER);
                
        Dimension size = new Dimension(CentralPanel.getWidth(), CentralPanel.getHeight());
        buttonPanel.setPreferredSize(new Dimension(size.width, size.height/7));
        subNetsPanel.setPreferredSize(new Dimension(size.width, size.height*6/7));
        
        add(CentralPanel, BorderLayout.CENTER);

        // the copy right section
        JPanel messagePanel = new JPanel();
        messagePanel.add(message);
        add(messagePanel,BorderLayout.SOUTH);
        
        // action listeners 
        subnetCal.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String netInfo=NetCal.networkInfo(inputField.getText().trim());
                rightLabel.setText("Network Information\n\n"+netInfo.toString());
                System.out.println(netInfo);
            }
        });
        
        numOfSubnets.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	ArrayList<String> subnets=NetCal.netSubReq(inputField.getText().trim());
            	String temp="   Network IP Addresses:\n";
            	rightLabel.setText("");
            	if(subnets!=null) {
            		temp+="   Based on Entered Infoormation\n   (requesting "+subnets.get(0)+" subets),\n";
            		subnets.remove(0);
            		if(subnets.size()==0)
            			temp+="   Required subnets not feasible\n\n";
            		else
            			temp+="   the required subets are:\n\n";
            		for(String sub : subnets) {
            			//System.out.println(sub);
            			temp+="   "+sub+"\n";
            		}
            		leftLabel.setText(temp);
            		scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
            	}else{
            		temp+="\n   Unable to calculate the IP addresses";
            		leftLabel.setText(temp);
            	}

            }
        });

        hostsPSub.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	ArrayList<String> subnets=NetCal.hostSubReq(inputField.getText().trim());
            	String temp="   Network IP Addresses:\n";
            	rightLabel.setText("");
            	if(subnets!=null) {
            		temp+="   Based on Entered Infoormation\n   (requesting "+subnets.get(0)+" hosts per subets),\n";
            		subnets.remove(0);
            		temp+="   "+subnets.size()+" subnets can be created.\n\n";
            		for(String sub : subnets) {
            			//System.out.println(sub);
            			temp+="   "+sub+"\n";
            		}
            		leftLabel.setText(temp);
            		scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
            	}else{
            		temp+="\n   Unable to calculate the IP addresses";
            		leftLabel.setText(temp);
            	}
            	
            }
        });

        VLSM.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	ArrayList<String> subnets=NetCal.VLSMSubReq(inputField.getText().trim());
            	String temp="   Network IP Addresses:\n";
            	rightLabel.setText("");
            	if(subnets!=null) {
            		String hostsArray=subnets.get(0);	
            		temp+="   Based on Entered Infoormation\n   (requesting "+hostsArray+"\n   hosts per subets respectively).\n";
            		subnets.remove(0);
            		// converting the String hostsArray to an array if integers   
            		hostsArray = hostsArray.substring(1, hostsArray.length() - 1); // remove the brackets
            		String[] strArr = hostsArray.split(", "); // split the string by ", " delimiter
            		int[] intArr = new int[strArr.length];
            		for (int i = 0; i < strArr.length; i++) {
            		    intArr[i] = Integer.parseInt(strArr[i]);
            		}
            		//---------------------------------------------
            		if(subnets.size()==strArr.length) {
            			temp+="   "+subnets.size()+" subnets could be created.\n\n";
            		}else {
            			temp+="   Only "+subnets.size()+" subnets can be created.\n\n";
            		}
            		int counter=0;
            		for(String sub : subnets) {
            			//System.out.println(sub);
            			temp+="   "+sub+" - "+intArr[counter]+" hosts\n";
            			counter++;
            		}
            		leftLabel.setText(temp);
            		scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
            	}else{
            		temp+="\n   Unable to calculate the IP addresses";
            		leftLabel.setText(temp);
            	}
            	
            }
        });

        leftLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                // Check if text is selected
                String selectedText = (leftLabel.getSelectedText()).trim();
                
                if (selectedText != null) {
                    // Show pop-up menu
                    JPopupMenu menu = new JPopupMenu();
                    JMenuItem printItem = new JMenuItem("Subnet The Selected IP");
                    printItem.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                        	String netInfo=NetCal.networkInfo(selectedText);
                            rightLabel.setText("Network Information\n\n"+netInfo.toString());
                            System.out.println(netInfo);
                        }
                    });
                    menu.add(printItem);
                    menu.show(leftLabel, e.getX(), e.getY());
                }
            }
        });
        
        // Set up the window
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 750);
        setVisible(true);
        setLocationRelativeTo(null);
        setResizable(false);
	}
	 
	public static void main(String[] args) {
		MainClass mainFrame = new MainClass();
		
	}

}

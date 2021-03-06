package com.perisic.beds.client;


import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.rmi.Naming;
import java.util.*;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.JTextArea;

import com.perisic.beds.RecyclingRMI;
/**
 * This is the machine located within the company headquarters.
 * @author staff
 *
 */
public class RecyclingClient {

	private static RecyclingRMI rc; 

	/**
	 * If the password is correct, log in is granted and a cookie is returned.
	 */
	
	public static void setSessionCookie() { 

		final JFrame frame = new JFrame("HQ - Authenticate");
		JLabel jlbPassword = new JLabel("Enter your password: ");
		JPasswordField jpwName = new JPasswordField(10);
		jpwName.setEchoChar('*');
		jpwName.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				final JPasswordField input = (JPasswordField) e.getSource();
				String password = new String(input.getPassword());
				try { 
					rc.enterPassword(password); 
					String sessionCookie = rc.enterPassword(password);  
					if(sessionCookie.equals("#noPassword#")) { 
						JOptionPane.showMessageDialog(frame, "Wrong Password");
						input.setText(""); 
					} else { 
						frame.setVisible(false); 
						startGUI(sessionCookie); 
					} 
				} catch (Exception exception) {
					System.err.println("JavaClient: " + exception);
					JOptionPane.showMessageDialog(frame, "No Server!");
					input.setText(""); 
				}
			}
		});
		JPanel jplContentPane = new JPanel(new BorderLayout());
		jplContentPane.setBorder(BorderFactory.createEmptyBorder(20, 20,
				20, 20));
		jplContentPane.add(jlbPassword, BorderLayout.WEST);
		jplContentPane.add(jpwName, BorderLayout.CENTER);
 
		frame.setContentPane(jplContentPane);
		frame.addWindowListener(new WindowAdapter() {

			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		});
		frame.pack();
		frame.setVisible(true);
	}
	/**
	 * Used for remote testing; simulates buttons on Recycling machine
	 * @param sessionCookie
	 * @param butNum
	 * @return
	 */
	private static String emulateButtonPress(String sessionCookie, int butNum) {
		try { 
			String result = ""+rc.testButton(sessionCookie, butNum); 
			//System.out.println("The result is: "+result ); 					
			//outputField.setText(result); 
			return result;

		} catch (Exception exception) {
			System.err.println("JavaClient: " + exception);
			return null;
		}
	}
	
	
	/**
	 * Starts GUI, delcares GUI elements 
	 */
	public static void startGUI(final String sessionCookie) { 
		final JFrame frame = new JFrame("HQ - GUI");
		
		JButton numberOfItems = new JButton("No. of Items"); 
		JButton receipt = new JButton("View Receipt");
		JButton summary = new JButton("View Summary");
		JButton feedback = new JButton ("View Feedback");
		JButton slot1 = new JButton ("Test Slot 1");
		JButton slot2 = new JButton ("Test Slot 2");
		JButton slot3 = new JButton ("Test Slot 3");
		JButton slot4 = new JButton ("Test Slot 4");
		JButton logout = new JButton("Logout"); 
		
		final JTextArea outputField = new JTextArea();
		outputField.setEditable(false); 
		
		
		//Action Listeners
		numberOfItems.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try { 
					String result = ""+rc.getNumberOfItemsInMachine(sessionCookie); 
					System.out.println("The result is: "+result ); 					
					outputField.setText(result); 

				} catch (Exception exception) {
					System.err.println("JavaClient: " + exception);
				}
			}
		});
		receipt.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				try { 
					String result = ""+rc.getReceipt(sessionCookie); 
					System.out.println("The result is: "+result ); 					
					outputField.setText(result); 

				} catch (Exception exception) {
					System.err.println("JavaClient: " + exception);
				}
			}
		});
		summary.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try { 
					String result = ""+rc.getSummary(sessionCookie); 
					System.out.println("The result is: "+result ); 					
					outputField.setText(result); 

				} catch (Exception exception) {
					System.err.println("JavaClient: " + exception);
				}
			}
		});
		feedback.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try { 
					String result = ""+rc.getFeedback(sessionCookie); 
					System.out.println("The result is: "+result ); 					
					outputField.setText(result); 

				} catch (Exception exception) {
					System.err.println("JavaClient: " + exception);
				}
			}
		});
		slot1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				emulateButtonPress(sessionCookie, 1);
				outputField.setText("Can Added");
			}
		});
		slot2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				emulateButtonPress(sessionCookie, 2);
				outputField.setText("Bottle Added");
			}
		});
		slot3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				emulateButtonPress(sessionCookie, 3);
				outputField.setText("Crate Added");
			}
		});
		slot4.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				emulateButtonPress(sessionCookie, 4);
				outputField.setText("Paper Bag Added");
			}
		});
		logout.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String result = "-2";
				try { 
					result = ""+rc.logout(sessionCookie); 					
					outputField.setText(result); 

				} catch (Exception exception) {
					System.err.println("JavaClient: " + exception);
				}
				if(result.equals("1")) {
				frame.dispose();	
				}
				else{
					JOptionPane.showMessageDialog(frame, "Error!\n"+result);
				}
				
			}
		});
		
		//Layout
		JPanel jplContentPane = new JPanel(new GridBagLayout());
		jplContentPane.setPreferredSize(new Dimension(700, 250));
		jplContentPane.setBorder(BorderFactory.createEmptyBorder(20, 20,
				20, 20));
		
		GridBagConstraints c = new GridBagConstraints();
		
		c.weightx = 0.5;
		c.weighty = 0.5;
		c.insets = new Insets(5,5,5,5);
		c.gridx = 0;
		c.gridy = 0;	
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridy = 0;
		jplContentPane.add(numberOfItems, c);
		c.gridy = 1;
		jplContentPane.add(receipt, c);
		c.gridy = 2;
		jplContentPane.add(summary, c);
		c.gridy = 3;
		jplContentPane.add(feedback, c);

		c.gridx = 1;
		c.gridy = 0;
		jplContentPane.add(slot1, c);
		c.gridy = 1;
		jplContentPane.add(slot2, c);
		c.gridy = 2;
		jplContentPane.add(slot3, c);
		c.gridy = 3;
		jplContentPane.add(slot4, c);
		c.gridy = 4;
		jplContentPane.add(logout, c);
		
		c.gridx = 2;
		c.gridy = 0;
		c.gridwidth = 0;
		c.gridheight = 5;
		c.weighty = 1;
		outputField.setLineWrap(true);
		outputField.setWrapStyleWord(true);
		c.fill = GridBagConstraints.BOTH;
		jplContentPane.add(outputField, c); 
		
		frame.setContentPane(jplContentPane);

		//Close Listener
		frame.addWindowListener(new WindowAdapter() {

			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		});
		//Final stuff
		frame.pack();
		frame.setVisible(true);

	}
	public static void connectServer(String url) {
		try {
			rc = (RecyclingRMI) Naming.lookup(url); 
            
			setSessionCookie(); 

		} catch (Exception exception) {
			System.err.println("JavaClient: " + exception);
			final JFrame frame = new JFrame("HQ - Error!");
			JOptionPane.showMessageDialog(frame, "No Server!\n"+exception);
		}
	}
		/**
		 * Defines how HQ connects to external machines, with IP addreeses.
		 */
	public static void machineSelect() {
		
		final JFrame frame = new JFrame("HQ - Machine Select");
				
		JLabel title = new JLabel("Login",javax.swing.SwingConstants.CENTER);
		JButton mach1 = new JButton("Machine 1 - Luton"); 
		JButton mach2 = new JButton("Machine 2 - Bedford"); 
		JButton mach3 = new JButton("Machine 3 - Harpenden"); 
		JButton mach4 = new JButton("Machine 4 - St. Albans");
		final JTextField inputField = new JTextField(2);
		JButton inputMach = new JButton("Connect");
		
		mach1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//System.err.println("MACHINE 1 BUTTON");
				connectServer("rmi://localhost/RecyclingService");
			}
		});
		mach2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//System.err.println("MACHINE 2 BUTTON");
				connectServer("rmi://10.1.27.192/RecyclingService");
			}
		});
		mach3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//System.err.println("MACHINE 3 BUTTON");
				connectServer("rmi://10.1.27.146/RecyclingService");
			}
		});
		mach4.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//System.err.println("MACHINE 4 BUTTON");
				connectServer("rmi://localhost/RecyclingService");
			}
		});
		inputMach.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//System.err.println("MACHINE 5 BUTTON");
				connectServer(inputField.getText());
			}
		});
		
		
		JPanel jplContentPane = new JPanel(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();

		c.weightx = 0.5;
		c.weighty = 0.5;
		c.insets = new Insets(5,5,5,5);
		c.gridx = 0;
		c.gridy = 0;	
		c.fill = GridBagConstraints.HORIZONTAL;
		jplContentPane.add(title, c);
		c.gridy = 1;
		jplContentPane.add(mach1, c);
		c.gridy = 2;
		jplContentPane.add(mach2, c);
		c.gridy = 3;
		jplContentPane.add(mach3, c); 
		c.gridy = 4;
		jplContentPane.add(mach4, c);
		c.gridy = 5;
		c.ipadx = 90;
		jplContentPane.add(inputField, c);
		c.gridy = 6;
		c.ipadx = 0;
		jplContentPane.add(inputMach, c);
		
		frame.setContentPane(jplContentPane);
		frame.addWindowListener(new WindowAdapter() {

			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		});
		frame.pack();
		frame.setVisible(true);
		
	}
	
	
	/** 
	 * Starts up HQ program, launches GUI for selecting machines. 
	 * @param args
	 */

	public static void main (String [] args) {
		machineSelect();
		
	}
}


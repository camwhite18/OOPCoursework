package uk.ac.lboro.CameronWhite;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JTextField;
import java.awt.BorderLayout;
import javax.swing.JPanel;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JButton;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class ComputerCompany {
	private static ArrayList<Object> userAccounts = new ArrayList<Object>();
	private JFrame frame;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ComputerCompany window = new ComputerCompany();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 * @throws FileNotFoundException 
	 */
	public ComputerCompany() throws FileNotFoundException {
		initialize();
	}

	/**
	 * Initialise the contents of the frame.
	 * @throws FileNotFoundException 
	 */
	private void initialize() throws FileNotFoundException {
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JComboBox comboBox = new JComboBox();
		comboBox.setBounds(171, 68, 89, 20);
		getUsernames();
		for (Object user: userAccounts) { 
			if (user instanceof Admin) {
				comboBox.addItem(((Admin) user).getUsername()); 
			} else {
				comboBox.addItem(((Customer) user).getUsername()); 
			} 
		}
		frame.getContentPane().add(comboBox);
		//Creates a combo box containing all of the usernames to choose from
		
		JLabel lblNewLabel = new JLabel("List of Usernames:");
		lblNewLabel.setBounds(171, 11, 140, 14);
		frame.getContentPane().add(lblNewLabel);
		
		JButton btnNewButton = new JButton("Login");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				frame.dispose();
				try {
					openMenu(comboBox);
				} catch (FileNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
		btnNewButton.setBounds(171, 143, 89, 23);
		frame.getContentPane().add(btnNewButton);
		//Creates a button that when pressed closes the first window and opens either the admin menu or customer menu depending on which username was selected
	}
	
	private void openMenu(JComboBox comboBox) throws FileNotFoundException {
		String choice = (String) comboBox.getSelectedItem();
		Object user = getUser(choice);
        if (user instanceof Admin) {
        	AdminMenu window2 = new AdminMenu((Admin) user);
            window2.NewAdminMenu((Admin) user);
            //Opens the admin window
        } else if (user instanceof Customer) {
        	CustomerMenu window2 = new CustomerMenu((Customer) user);
            window2.NewCustomerMenu((Customer) user);
            //Opens the customer window
        }
    }
	
	private static Object getUser(String choice) {
        for (Object user: userAccounts) {
            if (user instanceof Admin) {
                if (((Admin) user).getUsername().equals(choice)) {
                    return user;
                }
            } else {
                if (((Customer) user).getUsername().equals(choice)) {
                    return user;
                }
            }
        }
        return null;
    }
	
	private static void getUsernames() throws FileNotFoundException {
        File inputFile = new File("UserAccounts.txt");
        Scanner scanner = new Scanner(inputFile);
        while (scanner.hasNextLine()) {
            String[] temp = scanner.nextLine().split(", ");
            if (temp[6].equals("admin")) {
                Admin admin = new Admin(Integer.parseInt(temp[0]), temp[1], temp[2], temp[3], temp[4], temp[5]);
                userAccounts.add(admin);
            } else if (temp[6].equals("customer")) {
                Customer customer = new Customer(Integer.parseInt(temp[0]), temp[1], temp[2], temp[3], temp[4], temp[5]);
                userAccounts.add(customer);
            }
        }
        //Gets all of the usernames from the userAccounts file
    }
}

package uk.ac.lboro.CameronWhite;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.ListIterator;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.JComboBox;
import javax.swing.JRadioButton;
import javax.swing.JFormattedTextField;

public class CustomerMenu {

	private JFrame frame;
	private JTable table;
	private DefaultTableModel dtmTable;
	private JTable basket;
	private DefaultTableModel dtmBasket;

	/**
	 * Launch the application.
	 */
	public static void NewCustomerMenu(Customer customer) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					CustomerMenu window = new CustomerMenu(customer);
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public CustomerMenu(Customer customer) throws FileNotFoundException{
		initialize(customer);
	}

	/**
	 * Initialise the contents of the frame.
	 */
	private void initialize(Customer customer) {
		frame = new JFrame();
		frame.setBounds(100, 100, 1129, 761);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(new BorderLayout(0, 0));
		
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		frame.getContentPane().add(tabbedPane, BorderLayout.CENTER);
		
		JPanel panel = new JPanel();
		tabbedPane.addTab("Products", null, panel, null);
		
		JButton btnNewButton = new JButton("View All Products");
		btnNewButton.setFont(new Font("Tahoma", Font.PLAIN, 10));
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//Runs subroutine if button is pressed
				dtmTable.setRowCount(0);
				//Clears table
				try {
					ArrayList<String[]> stock = customer.getStock();
					for (String[] item: stock) {
						if (item[1].contentEquals("mouse") && !item[6].contentEquals("0")) {
							Object[] rowData = new Object[] {item[0],item[1],item[2],item[3],item[4],item[5],item[6],"£"+String.format("%.2f",Double.parseDouble(item[8])),item[9],"n/a"};
							dtmTable.addRow(rowData);
							//Adds product data to table
						} else if (item[1].contentEquals("keyboard") && !item[6].contentEquals("0")){
							Object[] rowData = new Object[] {item[0],item[1],item[2],item[3],item[4],item[5],item[6],"£"+String.format("%.2f",Double.parseDouble(item[8])),"n/a",item[9]};
							dtmTable.addRow(rowData);
						}
					}
				} catch (FileNotFoundException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
				
			}
		});
		panel.setLayout(null);
		btnNewButton.setBounds(10, 23, 135, 21);
		panel.add(btnNewButton);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(168, 5, 895, 601);
		panel.add(scrollPane);
		//Allows table to be scrolled up or down
		
		table = new JTable();
		scrollPane.setViewportView(table);
		table.setBounds(26,32,300,250);
		
		dtmTable = new DefaultTableModel();
		dtmTable.setColumnIdentifiers(new Object[] {"Barcode","Product","Type","Brand","Colour","Connection","Quantity in stock","Price","Number of buttons","Keyboard layout"});
		table.setModel(dtmTable);
		//Creates the table columns and sets the table model
		
		JComboBox comboBox = new JComboBox();
		comboBox.setBounds(10, 545, 135, 20);
		try {
			ArrayList<String[]> stock = customer.getStock();
			for (String[] item: stock) {
				if (!item[6].equals("0")) {
					comboBox.addItem(item[0]);
				}
			}
			//Adds all of the products barcodes as options to the combo box
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		panel.add(comboBox);
		
		JLabel lblNewLabel = new JLabel("");
		lblNewLabel.setBounds(12, 621, 154, 14);
		panel.add(lblNewLabel);
		
		JButton btnNewButton_1 = new JButton("Add to Basket");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String barcodeNo = comboBox.getSelectedItem().toString();
				customer.addItemToBasket(barcodeNo);
				//If the button is pressed then the item selected on the combo box is added to the basket
				lblNewLabel.setText("");
				lblNewLabel.setText(barcodeNo + " added to basket");
			}
		});
		btnNewButton_1.setBounds(10, 576, 135, 23);
		panel.add(btnNewButton_1);
		
		JLabel lblNewLabel_1 = new JLabel("Filter Products");
		lblNewLabel_1.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblNewLabel_1.setBounds(35, 64, 110, 14);
		panel.add(lblNewLabel_1);
		
		JRadioButton rdbtnNewRadioButton = new JRadioButton("View Mice");
		rdbtnNewRadioButton.setBounds(10, 85, 109, 23);
		panel.add(rdbtnNewRadioButton);
		
		JRadioButton rdbtnNewRadioButton_1 = new JRadioButton("View Keyboards");
		rdbtnNewRadioButton_1.setSelected(true);
		rdbtnNewRadioButton_1.setBounds(10, 111, 124, 23);
		panel.add(rdbtnNewRadioButton_1);
		
		ButtonGroup type = new ButtonGroup();
		type.add(rdbtnNewRadioButton);
		type.add(rdbtnNewRadioButton_1);
		//Groups the two radio buttons together so that only one can be selected at a time
		
		JLabel lblNewLabel_2 = new JLabel("Brand:");
		lblNewLabel_2.setBounds(10, 142, 73, 14);
		panel.add(lblNewLabel_2);
		
		JFormattedTextField formattedTextField = new JFormattedTextField();
		formattedTextField.setBounds(10, 167, 92, 20);
		panel.add(formattedTextField);
		
		JLabel lblNewLabel_3 = new JLabel("Keyboard Layout");
		lblNewLabel_3.setBounds(10, 198, 111, 14);
		panel.add(lblNewLabel_3);
		
		JFormattedTextField formattedTextField_1 = new JFormattedTextField();
		formattedTextField_1.setBounds(10, 223, 92, 20);
		panel.add(formattedTextField_1);
		
		JButton btnNewButton_2 = new JButton("Filter");
		btnNewButton_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String choice = null;
				String brand = formattedTextField.getText();
				String layout = "";
				if (rdbtnNewRadioButton.isSelected()) {
					choice = "mouse";
				} else if (rdbtnNewRadioButton_1.isSelected()) {
					choice = "keyboard";
					layout = formattedTextField_1.getText();
				}
				//Gets the values entered into the text boxes by the user
				dtmTable.setRowCount(0);
				//Clears the table
				try {
					ArrayList<String[]> stock = customer.getStock();
					for (String[] item: stock) {
						if (choice.equals("mouse")) {
							if (brand.equals("")){
								if (item[1].equals("mouse") && !item[6].equals("0")) {
									Object[] rowData = new Object[] {item[0],item[1],item[2],item[3],item[4],item[5],item[6],"£"+String.format("%.2f",Double.parseDouble(item[8])),item[9],"n/a"};
									dtmTable.addRow(rowData);
								}
							} else {
								if (item[1].equals("mouse") && !item[6].equals("0") && item[3].equals(brand)) {
									Object[] rowData = new Object[] {item[0],item[1],item[2],item[3],item[4],item[5],item[6],"£"+String.format("%.2f",Double.parseDouble(item[8])),item[9],"n/a"};
									dtmTable.addRow(rowData);
								}
							}
						} else if (choice.equals("keyboard")) {
							if (brand.equals("") && layout.equals("")){
								if (item[1].equals("keyboard") && !item[6].equals("0")){
									Object[] rowData = new Object[] {item[0],item[1],item[2],item[3],item[4],item[5],item[6],"£"+String.format("%.2f",Double.parseDouble(item[8])),"n/a",item[9]};
									dtmTable.addRow(rowData);
								}
							} else if (brand.equals("") && !layout.equals("")) {
								if (item[1].equals("keyboard") && !item[6].equals("0") && item[9].equals(layout)){
									Object[] rowData = new Object[] {item[0],item[1],item[2],item[3],item[4],item[5],item[6],"£"+String.format("%.2f",Double.parseDouble(item[8])),"n/a",item[9]};
									dtmTable.addRow(rowData);
								}
							} else if (!brand.equals("") && layout.equals("")) {
								if (item[1].equals("keyboard") && !item[6].equals("0") && item[3].equals(brand)){
									Object[] rowData = new Object[] {item[0],item[1],item[2],item[3],item[4],item[5],item[6],"£"+String.format("%.2f",Double.parseDouble(item[8])),"n/a",item[9]};
									dtmTable.addRow(rowData);
								}
							} else {
								if (item[1].equals("keyboard") && !item[6].equals("0") && item[3].equals(brand) && item[9].equals(layout)){
									Object[] rowData = new Object[] {item[0],item[1],item[2],item[3],item[4],item[5],item[6],"£"+String.format("%.2f",Double.parseDouble(item[8])),"n/a",item[9]};
									dtmTable.addRow(rowData);
								}
							}
						}
						//Outputs the filtered results
					}
				} catch (FileNotFoundException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		btnNewButton_2.setBounds(32, 272, 89, 23);
		panel.add(btnNewButton_2);
		
		rdbtnNewRadioButton_1.addActionListener(new ActionListener() {
	        @Override
	        public void actionPerformed(ActionEvent e) {
	        	formattedTextField_1.setEditable(true);

	        }
	    });

		rdbtnNewRadioButton.addActionListener(new ActionListener() {
	        @Override
	        public void actionPerformed(ActionEvent e) {
	        	formattedTextField_1.setEditable(false);

	        }
	    });
		
		JPanel panel_1 = new JPanel();
		tabbedPane.addTab("Basket", null, panel_1, null);
		panel_1.setLayout(null);
		//Adds a new tab to display the basket and its options
		
		JScrollPane scrollPane2 = new JScrollPane();
		scrollPane2.setBounds(10, 11, 1088, 419);
		panel_1.add(scrollPane2);
		
		basket = new JTable();
		scrollPane2.setViewportView(basket);
		basket.setBounds(26,32,300,250);
		
		dtmBasket = new DefaultTableModel();
		dtmBasket.setColumnIdentifiers(new Object[] {"Barcode","Product","Type","Brand","Colour","Connection","Number of buttons","Keyboard layout","Price"});
		basket.setModel(dtmBasket);
		//Creates a table to display the basket and initialises the columns
		
		
		JLabel lblNewLabel_4 = new JLabel("Pay for items");
		lblNewLabel_4.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblNewLabel_4.setBounds(107, 446, 116, 14);
		panel_1.add(lblNewLabel_4);
		
		JRadioButton rdbtnNewRadioButton_2 = new JRadioButton("PayPal");
		rdbtnNewRadioButton_2.setBounds(77, 467, 72, 23);
		panel_1.add(rdbtnNewRadioButton_2);
		
		JRadioButton rdbtnNewRadioButton_3 = new JRadioButton("Credit Card");
		rdbtnNewRadioButton_3.setSelected(true);
		rdbtnNewRadioButton_3.setBounds(151, 467, 109, 23);
		panel_1.add(rdbtnNewRadioButton_3);
		
		ButtonGroup payment = new ButtonGroup();
		payment.add(rdbtnNewRadioButton_2);
		payment.add(rdbtnNewRadioButton_3);
		//Groups radio buttons so that only one can be selected
		
		JFormattedTextField formattedTextField_2 = new JFormattedTextField();
		formattedTextField_2.setBounds(77, 530, 146, 20);
		panel_1.add(formattedTextField_2);
		
		JLabel lblNewLabel_5 = new JLabel("Card number:");
		lblNewLabel_5.setBounds(75, 505, 109, 14);
		panel_1.add(lblNewLabel_5);
		
		JLabel lblNewLabel_6 = new JLabel("Security code:");
		lblNewLabel_6.setBounds(77, 561, 107, 14);
		panel_1.add(lblNewLabel_6);
		
		JFormattedTextField formattedTextField_3 = new JFormattedTextField();
		formattedTextField_3.setBounds(77, 586, 146, 20);
		panel_1.add(formattedTextField_3);
		
		rdbtnNewRadioButton_3.addActionListener(new ActionListener() {
	        @Override
	        public void actionPerformed(ActionEvent e) {
	        	lblNewLabel_5.setText("Card Number:");
	        	lblNewLabel_6.setText("Security Code:");
	        	formattedTextField_3.setEditable(true);
	        	//Changes the labels text and allows the second text field to be edited if they wish to pay via credit card
	        }
	    });

		rdbtnNewRadioButton_2.addActionListener(new ActionListener() {
	        @Override
	        public void actionPerformed(ActionEvent e) {
	        	lblNewLabel_5.setText("Email address:");
	        	lblNewLabel_6.setText("");
	        	formattedTextField_3.setEditable(false);
	        	//Changes the labels text and doesn't allow the second text field to be edited if they wish to pay via paypal
	        }
	    });
		
		JLabel lblNewLabel_8 = new JLabel("");
		lblNewLabel_8.setBounds(43, 651, 1008, 14);
		panel_1.add(lblNewLabel_8);
		
		JButton btnNewButton_3 = new JButton("Make Payment");
		btnNewButton_3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				//Runs subroutine if button is pressed
				String method = "";
				String email = "";
				String cardNumber = "";
				String securityCode = "";
				if (rdbtnNewRadioButton_2.isSelected()) {
					method = "PayPal";
					email = formattedTextField_2.getText();
				} else if (rdbtnNewRadioButton_3.isSelected()) {
					method = "Credit Card";
					cardNumber = formattedTextField_2.getText();
					securityCode = formattedTextField_3.getText();
				}
				//Gets the entries made by the user
				try {
					customer.payForItemsInBasket(customer, method, email, cardNumber, securityCode, lblNewLabel_8);
					//Changes stock file and activity log file then empties the basket
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		btnNewButton_3.setBounds(77, 617, 146, 23);
		panel_1.add(btnNewButton_3);
		
		JLabel lblNewLabel_7 = new JLabel("");
		lblNewLabel_7.setBounds(446, 589, 94, 14);
		panel_1.add(lblNewLabel_7);
		
		JButton btnNewButton_4 = new JButton("Save Basket");
		btnNewButton_4.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//Runs subroutine if button is clicked
				try {
					customer.saveBasketForLater(customer);
					lblNewLabel_7.setText("Basket Saved");
					//Changes activity log file and empties the basket
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		btnNewButton_4.setBounds(436, 557, 116, 23);
		panel_1.add(btnNewButton_4);
		
		JLabel lblNewLabel_7_1 = new JLabel("");
		lblNewLabel_7_1.setBounds(761, 589, 94, 14);
		panel_1.add(lblNewLabel_7_1);
		
		JButton btnNewButton_5 = new JButton("Empty Basket");
		btnNewButton_5.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//Runs subroutine if button is clicked
				try {
					customer.emptyCustomerBasket(customer);
					lblNewLabel_7_1.setText("Basket Emptied");
					//Changes activity log file and empties the basket
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		btnNewButton_5.setBounds(751, 557, 116, 23);
		panel_1.add(btnNewButton_5);
		
		JButton btnNewButton_6 = new JButton("Re-load Basket");
		btnNewButton_6.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//Runs subroutine if button is clicked
				dtmBasket.setRowCount(0);
				customer.returnBasket(dtmBasket);
				//Clears current table showing the basket and then re-loads it
			}
		});
		btnNewButton_6.setBounds(422, 441, 146, 23);
		panel_1.add(btnNewButton_6);
		
				
	}
}

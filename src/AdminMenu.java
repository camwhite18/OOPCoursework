package uk.ac.lboro.CameronWhite;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import java.awt.Font;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.awt.event.ActionEvent;
import java.awt.GridLayout;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import javax.swing.JTabbedPane;
import java.awt.BorderLayout;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.JScrollPane;
import javax.swing.JFormattedTextField;
import javax.swing.JToggleButton;
import javax.swing.JRadioButton;
import javax.swing.JScrollBar;
import javax.swing.JList;
import javax.swing.JComboBox;
import javax.swing.JTextField;

public class AdminMenu {

	private JFrame frame;
	private JTable table;
	private DefaultTableModel dtmTable;

	/**
	 * Launch the application.
	 */
	public static void NewAdminMenu(Admin admin) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					AdminMenu window = new AdminMenu(admin);
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
	public AdminMenu(Admin admin) throws FileNotFoundException {
		initialize(admin);
	}

	/**
	 * Initialise the contents of the frame.
	 */
	private void initialize(Admin admin) throws FileNotFoundException {
		frame = new JFrame();
		frame.setBounds(100, 100, 1129, 761);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		frame.getContentPane().add(tabbedPane, BorderLayout.CENTER);
		//Allows tabs to be added to the admin window
		
		JPanel panel = new JPanel();
		tabbedPane.addTab("View all products in stock", null, panel, null);
		panel.setLayout(null);
		//Creates the tab in which all of the products will be shown
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(121, 5, 953, 661);
		panel.add(scrollPane);
		//Allows the table containing the products to be scrolled up and down
		
		table = new JTable();
		scrollPane.setViewportView(table);
		table.setBounds(26,32,300,250);
		//Initialises the table
		
		dtmTable = new DefaultTableModel();
		dtmTable.setColumnIdentifiers(new Object[] {"Barcode","Product","Type","Brand","Colour","Connection","Quantity in stock","Original cost","Price","Number of buttons","Keyboard layout"});
		table.setModel(dtmTable);
		//Creates the table columns and sets the table model
		
		JButton btnNewButton = new JButton("Load Products");
		btnNewButton.setFont(new Font("Tahoma", Font.PLAIN, 10));
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//Runs subroutine if button is pressed
				dtmTable.setRowCount(0);
				//Clears the table
				try {
					ArrayList<String[]> stock = admin.getStock();
					for (String[] item: stock) {
						if (item[1].contentEquals("mouse") && !item[6].contentEquals("0")) {
							Object[] rowData = new Object[] {item[0],item[1],item[2],item[3],item[4],item[5],item[6],"£"+String.format("%.2f",Double.parseDouble(item[7])),"£"+String.format("%.2f",Double.parseDouble(item[8])),item[9],"n/a"};
							dtmTable.addRow(rowData);
							//Adds the mouse's data to a new row in the table
						} else if (item[1].contentEquals("keyboard") && !item[6].contentEquals("0")){
							Object[] rowData = new Object[] {item[0],item[1],item[2],item[3],item[4],item[5],item[6],"£"+String.format("%.2f",Double.parseDouble(item[7])),"£"+String.format("%.2f",Double.parseDouble(item[8])),"n/a",item[9]};
							dtmTable.addRow(rowData);
							//Adds the keyboard's data to a new row in the table
						}
					}
				} catch (FileNotFoundException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
				
			}
		});
		btnNewButton.setBounds(0, 104, 116, 23);
		panel.add(btnNewButton);
		
		JPanel panel_1 = new JPanel();
		tabbedPane.addTab("Add a product to the current product list", null, panel_1, null);
		panel_1.setLayout(null);
		//Adds a new tab to the pane that is for adding a new product
		
		JLabel lblNewLabel = new JLabel("Enter the barcode number:");
		lblNewLabel.setBounds(26, 21, 170, 14);
		panel_1.add(lblNewLabel);
		//Adds a label to the window
		
		JFormattedTextField formattedTextField = new JFormattedTextField();
		formattedTextField.setBounds(26, 46, 145, 20);
		panel_1.add(formattedTextField);
		
		JLabel lblEnterTheProduct = new JLabel("Choose the product type:");
		lblEnterTheProduct.setBounds(26, 77, 216, 14);
		panel_1.add(lblEnterTheProduct);
		
		JLabel lblEnterTheBrand = new JLabel("Enter the brand name:");
		lblEnterTheBrand.setBounds(26, 133, 216, 14);
		panel_1.add(lblEnterTheBrand);
		
		JFormattedTextField formattedTextField_1_1 = new JFormattedTextField();
		formattedTextField_1_1.setBounds(26, 158, 145, 20);
		panel_1.add(formattedTextField_1_1);
		
		JLabel lblEnterTheProduct_1 = new JLabel("Enter the product colour:");
		lblEnterTheProduct_1.setBounds(26, 189, 216, 14);
		panel_1.add(lblEnterTheProduct_1);
		
		JLabel lblEnterTheProduct_1_1 = new JLabel("Choose the product connectivity:");
		lblEnterTheProduct_1_1.setBounds(26, 245, 216, 14);
		panel_1.add(lblEnterTheProduct_1_1);
		
		JRadioButton rdbtnNewRadioButton = new JRadioButton("Wired");
		rdbtnNewRadioButton.setSelected(true);
		rdbtnNewRadioButton.setBounds(26, 266, 62, 23);
		panel_1.add(rdbtnNewRadioButton);
		//Adds a radio button to the window
		
		JRadioButton rdbtnWireless = new JRadioButton("Wireless");
		rdbtnWireless.setBounds(99, 266, 81, 23);
		panel_1.add(rdbtnWireless);
		
		ButtonGroup connectivity = new ButtonGroup();
		connectivity.add(rdbtnNewRadioButton);
		connectivity.add(rdbtnWireless);
		//Groups the two radio buttons together so that only one can be selected at a time
		
		JRadioButton rdbtnKeyboard = new JRadioButton("Keyboard");
		rdbtnKeyboard.setSelected(true);
		rdbtnKeyboard.setBounds(26, 98, 87, 23);
		panel_1.add(rdbtnKeyboard);
				
		JRadioButton rdbtnMouse = new JRadioButton("Mouse");
		rdbtnMouse.setBounds(115, 98, 81, 23);
		panel_1.add(rdbtnMouse);
		
		ButtonGroup type = new ButtonGroup();
		type.add(rdbtnMouse);
		type.add(rdbtnKeyboard);
		
		JLabel lblEnterTheProduct_1_2 = new JLabel("Enter the quantity in stock:");
		lblEnterTheProduct_1_2.setBounds(26, 296, 216, 14);
		panel_1.add(lblEnterTheProduct_1_2);
		
		JComboBox comboBox = new JComboBox();
		comboBox.setBounds(26, 321, 103, 20);
		for (int i=1;i<1000;i++) {
			comboBox.addItem(i);
		}
		panel_1.add(comboBox);
		//Adds a combo box with numbers from 1-999 to choose from for the number of a product to add
		
		JComboBox comboBox_1 = new JComboBox();
		comboBox_1.setBounds(26, 214, 103, 20);
		String[] colours = {"white","black","grey","blue","red","green","yellow"};
		for (String colour: colours) {
			comboBox_1.addItem(colour);
		}
		panel_1.add(comboBox_1);
		//Adds a combo box for the different colours of the product
		
		JLabel lblEnterTheProduct_1_2_1 = new JLabel("Enter the original cost:");
		lblEnterTheProduct_1_2_1.setBounds(26, 352, 216, 14);
		panel_1.add(lblEnterTheProduct_1_2_1);
		
		JLabel lblNewLabel_1 = new JLabel("\u00A3");
		lblNewLabel_1.setBounds(26, 377, 12, 14);
		panel_1.add(lblNewLabel_1);
		
		JLabel lblEnterTheProduct_1_2_1_1 = new JLabel("Enter the retail price:");
		lblEnterTheProduct_1_2_1_1.setBounds(26, 402, 216, 14);
		panel_1.add(lblEnterTheProduct_1_2_1_1);
		
		JLabel lblNewLabel_1_1 = new JLabel("\u00A3");
		lblNewLabel_1_1.setBounds(26, 427, 12, 14);
		panel_1.add(lblNewLabel_1_1);
		
		JLabel lblEnterTheProduct_1_2_2 = new JLabel("Enter the keyboard type:");
		lblEnterTheProduct_1_2_2.setBounds(26, 455, 216, 14);
		panel_1.add(lblEnterTheProduct_1_2_2);
		
		
		JLabel lblEnterTheProduct_1_2_2_1 = new JLabel("Enter the keyboard layout:");
		lblEnterTheProduct_1_2_2_1.setBounds(26, 511, 216, 14);
		panel_1.add(lblEnterTheProduct_1_2_2_1);
		
		JFormattedTextField formattedTextField_2 = new JFormattedTextField();
		formattedTextField_2.setBounds(26, 536, 145, 20);
		panel_1.add(formattedTextField_2);
		
		JFormattedTextField formattedTextField_3 = new JFormattedTextField();
		formattedTextField_3.setBounds(35, 374, 81, 20);
		panel_1.add(formattedTextField_3);
		
		JFormattedTextField formattedTextField_3_1 = new JFormattedTextField();
		formattedTextField_3_1.setBounds(36, 424, 81, 20);
		panel_1.add(formattedTextField_3_1);
		
		JComboBox comboBox_2 = new JComboBox();
		comboBox_2.setBounds(26, 480, 145, 20);
		panel_1.add(comboBox_2);
		comboBox_2.addItem("standard");
    	comboBox_2.addItem("gaming");
    	comboBox_2.addItem("flexible");
    	comboBox_2.addItem("internet");
    	
    	JLabel lblNewLabel_2 = new JLabel("");
		lblNewLabel_2.setBounds(26, 640, 334, 14);
		panel_1.add(lblNewLabel_2);
		
		JButton btnNewButton_1 = new JButton("Submit");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (rdbtnKeyboard.isSelected()) {
					String barcode = formattedTextField.getText();
					String brand = formattedTextField_1_1.getText();
					String colour = comboBox_1.getSelectedItem().toString();
					//Gets the text entered by the user into the text field and the option selected in the combo box
					String productConnectivity = null;
					if (rdbtnNewRadioButton.isSelected()) {
						productConnectivity = "wired";
					} else if (rdbtnWireless.isSelected()) {
						productConnectivity = "wireless";
					}
					//Gets the radio button that is selected
					String quantityInStock = comboBox.getSelectedItem().toString();
					String originalCost = formattedTextField_3.getText();
					String retailPrice = formattedTextField_3_1.getText();
					String keyboardType = comboBox_2.getSelectedItem().toString();
					String keyboardLayout = formattedTextField_2.getText();
					if (!barcode.contentEquals("") && !brand.contentEquals("") && !originalCost.equals("") && !retailPrice.equals("") && !keyboardType.contentEquals("") && !keyboardLayout.contentEquals("")) {
						try {
							admin.addProductToSystem(barcode, "keyboard", brand, colour, productConnectivity, quantityInStock, originalCost, retailPrice, keyboardType, keyboardLayout, "", "", lblNewLabel_2);
						} catch (FileNotFoundException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
					}
					//Adds the product to the system
				} else if (rdbtnMouse.isSelected()){
					String barcode = formattedTextField.getText();
					String brand = formattedTextField_1_1.getText();
					String colour = comboBox_1.getSelectedItem().toString();
					String productConnectivity = null;
					if (rdbtnNewRadioButton.isSelected()) {
						productConnectivity = "wired";
					} else if (rdbtnWireless.isSelected()) {
						productConnectivity = "wireless";
					}
					String quantityInStock = comboBox.getSelectedItem().toString();
					String originalCost = formattedTextField_3.getText();
					String retailPrice = formattedTextField_3_1.getText();
					String mouseType = comboBox_2.getSelectedItem().toString();
					String noOfButtons = formattedTextField_2.getText();
					if (!barcode.contentEquals("") && !brand.contentEquals("") && !originalCost.equals("") && !retailPrice.equals("") && !mouseType.contentEquals("") && !noOfButtons.contentEquals("")) {
						try {
							admin.addProductToSystem(barcode, "mouse", brand, colour, productConnectivity, quantityInStock, originalCost, retailPrice, "", "", mouseType, noOfButtons, lblNewLabel_2);
						} catch (FileNotFoundException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
					}
				}
				formattedTextField.setText("");
				formattedTextField_1_1.setText("");
				formattedTextField_3.setText("");
				formattedTextField_3_1.setText("");
				formattedTextField_2.setText("");
				//Wipes all of the text fields that have been filled in
			}
		});
		btnNewButton_1.setBounds(40, 593, 89, 23);
		panel_1.add(btnNewButton_1);
		
		class RadioButtonActionListener implements ActionListener {
		    @Override
		    public void actionPerformed(ActionEvent event) {
		        JRadioButton button = (JRadioButton) event.getSource();
		 
		        if (button == rdbtnKeyboard) {
		 
		        	lblEnterTheProduct_1_2_2.setText("Enter the keyboard type:");
		        	comboBox_2.removeAllItems();
		        	comboBox_2.addItem("standard");
		        	comboBox_2.addItem("gaming");
		        	comboBox_2.addItem("flexible");
		        	comboBox_2.addItem("internet");
		        	lblEnterTheProduct_1_2_2_1.setText("Enter the keyboard layout (UK/US):");
		 
		        } else if (button == rdbtnMouse) {
		 
		        	lblEnterTheProduct_1_2_2.setText("Enter the mouse type:");
		        	comboBox_2.removeAllItems();
		        	comboBox_2.addItem("standard");
		        	comboBox_2.addItem("gaming");
		        	lblEnterTheProduct_1_2_2_1.setText("Enter the number of buttons:");
		 
		        } 
		    }
		}
		//Changes the text of two text fields depending on which radio button is selected
		RadioButtonActionListener actionListener = new RadioButtonActionListener();
		rdbtnKeyboard.addActionListener(actionListener);
		rdbtnMouse.addActionListener(actionListener);
		
		
	}
}

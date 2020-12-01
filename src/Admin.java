package uk.ac.lboro.CameronWhite;

import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.JLabel;

public class Admin extends User {

    public Admin(int userID, String username, String surname, String houseNumber, String postcode, String city) {
        super(userID, username, surname, houseNumber, postcode, city);
    }

    public void addProductToSystem(String barcode, String product, String brand, String colour, String connectivity, String quantityInStock, String originalCost, String retailPrice, String keyboardType, String keyboardLayout, String mouseType, String noOfButtons, JLabel lblNewLabel_2) throws FileNotFoundException {
        ArrayList<String[]> stock = super.getStock();
        boolean barcodeUsed = false;
        for (String[] item: stock) {
        	if (barcode.equals(item[0])) {
        		barcodeUsed = true;
        	}
        }
        if (!barcodeUsed) {
        	lblNewLabel_2.setText("");	
    	if (product.equals("keyboard")) {
            try {
                FileWriter fileWriter = new FileWriter("Stock.txt", true);
                //Opens the file in append mode
                fileWriter.write(System.getProperty( "line.separator" ));
                //Adds a new line to the end of the file
                fileWriter.write(barcode + ", " + product + ", " + keyboardType + ", " + brand + ", " + colour + ", " + connectivity + ", "
                        + quantityInStock + ", " + originalCost + ", " + retailPrice + ", " + keyboardLayout);
                //Adds the product details to the file
                fileWriter.close();
                //Saves and closes the file
            }
            catch (IOException e) {
            }
        } else{
            try {
                FileWriter fileWriter = new FileWriter("Stock.txt", true);
                fileWriter.write(System.getProperty( "line.separator" ));
                fileWriter.write(barcode + ", " + product + ", " + mouseType + ", " + brand + ", " + colour + ", " + connectivity + ", "
                        + quantityInStock + ", " + originalCost + ", " + retailPrice + ", " + noOfButtons);
                fileWriter.close();
            }
            catch (IOException e){
            }
        }
        } else {
        	lblNewLabel_2.setText("Error - product with barcode "+barcode+" already exists");
        }
    }
    
}
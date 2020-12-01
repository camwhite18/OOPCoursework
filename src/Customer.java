package uk.ac.lboro.CameronWhite;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import javax.swing.JLabel;
import javax.swing.table.DefaultTableModel;

public class Customer extends User{
    private Basket basket = new Basket();
    private ArrayList<String[]> stock = super.getStock();

    public Customer(int userID, String username, String surname, String houseNumber, String postcode, String city) throws FileNotFoundException {
        super(userID, username, surname, houseNumber, postcode, city);
    }

    public void addItemToBasket(String barcodeNo) {
        for (String[] product: stock) {
            if (product[0].equals(barcodeNo)) {
                if (product[1].equals("mouse")) {
                    Mouse mouse = new Mouse(Integer.parseInt(product[0]),IsGamingMouse.valueOf(product[2]),
                            String.valueOf(product[3]),Colour.valueOf(product[4]),WirelessConnectivity.valueOf(product[5]),
                            Integer.parseInt(product[6]),Double.parseDouble(product[7]),Double.parseDouble(product[8]),
                            Integer.parseInt(product[9]));
                    basket.addItemToBasket(mouse);
                    //Creates a new mouse object by finding its details in the stock list, then adds it to the basket
                } else if (product[1].equals("keyboard")) {
                    Keyboard keyboard = new Keyboard(Integer.parseInt(product[0]),KeyboardType.valueOf(product[2]),
                            String.valueOf(product[3]),Colour.valueOf(product[4]),WirelessConnectivity.valueOf(product[5]),
                            Integer.parseInt(product[6]),Double.parseDouble(product[7]),Double.parseDouble(product[8]),
                            IsUKLayout.valueOf(product[9]));
                    basket.addItemToBasket(keyboard);
                  //Creates a new keyboard object by finding its details in the stock list, then adds it to the basket
                }
                break;
            }
        }
        
    }

    public void returnBasket(DefaultTableModel dtmBasket) {
    	basket.returnBasket(dtmBasket);
    }
    
    public void payForItemsInBasket(Customer customer, String choice, String email, String cardNumber, String securityCode, JLabel label) throws IOException {
        //Scanner scanner = new Scanner(System.in);
        double basketTotal = basket.getBasketTotal();
        //Gets the total of the basket
        if (choice.equals("PayPal")) {
            if (email.contains("@")) {
                String barcode =  changeStock();
                //Gets all the barcodes of products that are out of stock if there are any
                if (barcode.equals("")) {
                    writeToActivityLog(customer, "purchased", "PayPal");
                    basket.emptyBasket();
                    //System.out.println("�" + basketTotal + " paid using PayPal");
                    label.setText("�" + String.format("%.2f",basketTotal) + " paid using PayPal");
                    //Changes the text of a label in the GUI
                } else {
                    //System.out.println("Item(s) with barcode(s) "+barcode+ " is out of stock");
                	label.setText("Item(s) with barcode(s) "+barcode+ " is out of stock");
                	//Changes the text of a label in the GUI
                }
            } else {
                //System.out.println("Invalid email address");
                label.setText("Invalid email address");
            }
        } else if (choice.equals("Credit Card")) {
            //System.out.print("Enter card number: ");
            //String cardNumber = scanner.next();
            //System.out.print("Enter security code: ");
            //String securityCode = scanner.next();
            if (cardNumber.length()==16 && securityCode.length()==3 && cardNumber.matches("[0-9]+") && securityCode.matches("[0-9]+")) {
            	//Checks to see if the card number entered is 16 digits, the security code is 3 digits and they only contains digits from 0-9
                String barcode = changeStock();
                //Gets all the barcodes of products that are out of stock if there are any
                if (barcode.equals("")) {
                    writeToActivityLog(customer, "purchased", "Credit Card");
                    basket.emptyBasket();
                    //System.out.println("�" + basketTotal + " paid using Credit Card");
                    label.setText("�" + String.format("%.2f",basketTotal) + " paid using Credit Card");
                } else {
                    //System.out.println("Item(s) with barcode(s) "+barcode+" is out of stock");
                	label.setText("Item(s) with barcode(s) "+barcode+" is out of stock");
                }
            } else {
                //System.out.println("Invalid credit card details");
            	label.setText("Invalid credit card details");
            }
        } else {
            //System.out.println("Invalid choice");
        	label.setText("Invalid choice");
        }
    }

    private String changeStock() throws IOException {
        ArrayList<String[]> stock = getStock();
        FileWriter fileWriter = new FileWriter("Stock.txt", false);
        LinkedList<Object> customerBasket = basket.getBasket();
        ArrayList<Object> itemsToRemove = new ArrayList<Object>();
        String returnValue = "";
        for (Object item: customerBasket) {
            for (String[] stockItem: stock) {
                if (item instanceof Mouse) {
                    if (((Mouse) item).getBarcode() == Integer.parseInt(stockItem[0])) {
                        String temp = stockItem[6];
                        int newStock = Integer.parseInt(temp) - 1;
                        if (newStock < 0) {
                            returnValue = stockItem[0]+ " " +returnValue;
                            itemsToRemove.add(item);
                        } else {
                            stockItem[6] = Integer.toString(newStock);
                            //System.out.println("Item with barcode "+ stockItem[0] +" purchased");
                        }
                    }
                } else {
                    if (((Keyboard) item).getBarcode() == Integer.parseInt(stockItem[0])) {
                        String temp = stockItem[6];
                        int newStock = Integer.parseInt(temp) - 1;
                        if (newStock < 0) {
                            returnValue = stockItem[0]+ " " +returnValue;
                            itemsToRemove.add(item);
                        } else {
                            stockItem[6] = Integer.toString(newStock);
                            //System.out.println("Item with barcode "+ stockItem[0] +" purchased");
                        }
                    }
                }
            }
        }
        //Checks to see which items are in stock so that the transaction can go through
        for (Object item: itemsToRemove) {
            basket.removeItemFromBasket(item);
        }
        //Removes all of the out of stock items from the basket
        for (String[] item: stock) {
            fileWriter.write(String.join(", ", item));
            fileWriter.write(System.getProperty("line.separator"));
        }
        //Writes the new stock list back to the 'stock' file
        fileWriter.close();
        return returnValue;
    }


    public void saveBasketForLater(Customer customer) throws IOException {
        writeToActivityLog(customer, "saved", "");
        basket.emptyBasket();
    }

    private void writeToActivityLog(Customer customer, String function, String payment) throws IOException {
        LinkedList<String> activityLog = getActivityLog();
        //Stores the data in the activity log in the LinkedList
        LinkedList<String> itemsToAdd = basket.getItemsToAdd();
        ArrayList<String> barcodes = new ArrayList<String>();
        ArrayList<String> nums = new ArrayList<String>();
        int [] numInBasket = new int [itemsToAdd.size()];  
        FileWriter fileWriter = new FileWriter("ActivityLog.txt", false);
        DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        Date date = new Date();

        int visited = -1;  
        for(int i = 0; i < itemsToAdd.size(); i++){  
            int count = 1;  
            for(int j = i+1; j < itemsToAdd.size(); j++){  
                if(itemsToAdd.get(i).equals(itemsToAdd.get(j))){  
                    count++;  
                    //To avoid counting same element again  
                    numInBasket[j] = visited;  
                }  
            }  
            if(numInBasket[i] != visited)  
                numInBasket[i] = count;    
        }
        
        for(int i = 0; i < numInBasket.length; i++){  
            if(numInBasket[i] != visited) { 
                barcodes.add(itemsToAdd.get(i));
            	nums.add(String.valueOf(numInBasket[i]));
            }	
        }
        //Gets all of the unique barcodes in the shopping basket and adds then to a new list along with the number of times they occur in another list 
        int counter = 0;
        for (String barcode: barcodes) {
        	boolean itemAdded = false;
        	for (Object item: basket.getBasket()) {
        		if (item instanceof Mouse) {
        			Mouse mouse = (Mouse) item;
        			if (!itemAdded && barcode.equals(String.valueOf(mouse.getBarcode()))) {
        				itemAdded = true;
        				activityLog.add(0, customer.getUserID()+", "+customer.getPostcode()+", "+((Mouse) item).getBarcode()+
                                ", "+String.format("%.2f",((Mouse) item).getRetailPrice())+", "+nums.get(counter)+", "+function+", "+payment+", "+dateFormat.format(date));
        			}
        			//Adds a new line to the start of the activity log list containing the details of what actions the user has just done
        		} else if (item instanceof Keyboard) {
        			Keyboard keyboard = (Keyboard) item;
        			if (!itemAdded && barcode.equals(String.valueOf(keyboard.getBarcode()))) {
        				itemAdded = true;
        				activityLog.add(0, customer.getUserID()+", "+customer.getPostcode()+", "+((Keyboard) item).getBarcode()+
                                ", "+String.format("%.2f",((Keyboard) item).getRetailPrice())+", "+nums.get(counter)+", "+function+", "+payment+", "+dateFormat.format(date));
        			}
        		}
        	}
        	counter++;
        }

        for (String activity: activityLog) {
            fileWriter.write(activity);
            fileWriter.write(System.getProperty("line.separator"));
        }
        //Writes the updated list to the file
        fileWriter.close();

    }

    public void emptyCustomerBasket(Customer customer) throws IOException {
        writeToActivityLog(customer, "cancelled", "");
        basket.emptyBasket();
    }

    private LinkedList<String> getActivityLog() throws FileNotFoundException {
        File file = new File("ActivityLog.txt");
        Scanner scanner = new Scanner(file);
        LinkedList<String> activityLog = new LinkedList<String>();
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            activityLog.add(line);
        }
        //Reads in each line of the activity log file to the list
        scanner.close();
        return activityLog;
    }
}

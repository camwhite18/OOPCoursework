package uk.ac.lboro.CameronWhite;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.ListIterator;

import javax.swing.table.DefaultTableModel;

public class Basket {
    private LinkedList<Object> basket;

    public LinkedList<Object> getBasket() {
        return basket;
    }

    public Basket() {
        this.basket = new LinkedList<Object>();
    }

    public void addItemToBasket(Object item) {
    	this.basket.add(item);
    	//Adds an item to the LinkedList 'basket'
    }

    public void returnBasket(DefaultTableModel dtmBasket) {
    	ListIterator<Object> listIterator = this.basket.listIterator();
    	//Creates a list iterator to be used to iterate over the list
        while (listIterator.hasNext()) {
            Object object = listIterator.next();
            //Creates an object out of each item in the list
            if (object instanceof Mouse) {
                Mouse mouse = (Mouse) object;
                //If the object is a mouse, then it is casted to a mouse
                Object[] rowData = new Object[] {mouse.getBarcode(),"mouse",mouse.isGamingMouse(),mouse.getBrand(),mouse.getColour(),mouse.isWirelessConnectivity(),mouse.getNoOfButtons(),"n/a","£"+String.format("%.2f",mouse.getRetailPrice())};
                dtmBasket.addRow(rowData);
                //The mouse's data is added to the table in the GUI showing the basket
            } else if (object instanceof Keyboard) {
                Keyboard keyboard = (Keyboard) object;
              //If the object is a keyboard, then it is casted to a keyboard
            	Object[] rowData = new Object[] {keyboard.getBarcode(),"keyboard",keyboard.getKeyBoardType(),keyboard.getBrand(),keyboard.getColour(),keyboard.isWirelessConnectivity(),"n/a",keyboard.isUKLayout(),"£"+String.format("%.2f",keyboard.getRetailPrice())};
                dtmBasket.addRow(rowData);
              //The keyboard's data is added to the table in the GUI showing the basket
            }
        }
    }
    
    public LinkedList<String> getItemsToAdd() {
    	LinkedList<String> itemBarcodes = new LinkedList<String>();
    	for (Object item: this.basket) {
    		if (item instanceof Mouse) {
    			Mouse mouse = (Mouse) item;
    			itemBarcodes.add(String.valueOf(mouse.getBarcode()));
    		} else if (item instanceof Keyboard) {
    			Keyboard keyboard = (Keyboard) item;
    			itemBarcodes.add(String.valueOf(keyboard.getBarcode()));
    		}
    	}
    	//Creates a list of all the products barcodes in the basket
    	return itemBarcodes;
    }
    
    public double getBasketTotal() {
        ListIterator<Object> listIterator = this.basket.listIterator();
        double basketTotal = 0;
        while (listIterator.hasNext()) {
            double price = 0;
            Object object = listIterator.next();
            if (object instanceof Mouse) {
                Mouse mouse = (Mouse) object;
                price = mouse.getRetailPrice();
            } else if (object instanceof Keyboard) {
                Keyboard keyboard = (Keyboard) object;
                price = keyboard.getRetailPrice();
            }
            basketTotal += price;
            //Gets the price of each product in the basket, then adds it to the total
        }
        return basketTotal;
    }

    public void emptyBasket() {
        this.basket = new LinkedList<Object>();
        //Overwrites the current list containing the basket to create an empty basket
    }

    public void removeItemFromBasket(Object item) {
        this.basket.remove(item);
        //Removes the item from the basket
    }
}
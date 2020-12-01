package uk.ac.lboro.CameronWhite;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

abstract public class User {
    private int userID;
    private String username;
    private String surname;
    private String houseNumber;
    private String postcode;
    private String city;

    public User(int userID, String username, String surname, String houseNumber, String postcode, String city) {
        this.userID = userID;
        this.username = username;
        this.surname = surname;
        this.houseNumber = houseNumber;
        this.postcode = postcode;
        this.city = city;
    }

    public int getUserID() {
        return userID;
    }

    public String getUsername() {
        return username;
    }

    public String getSurname() {
        return surname;
    }

    public String getHouseNumber() {
        return houseNumber;
    }

    public String getPostcode() {
        return postcode;
    }

    public String getCity() {
        return city;
    }

    public ArrayList<String[]> getStock() throws FileNotFoundException {
        File inputFile = new File("Stock.txt");
        Scanner scanner = new Scanner(inputFile);
        ArrayList<String[]> stock = new ArrayList<String[]>();
        while (scanner.hasNextLine()) {
            String productDetails = scanner.nextLine();
            String[] temp = productDetails.split(", ");
            stock.add(temp);
        }
        //Reads in all of the stock from the stock file line by line and stores it in the arrayList stock
        int i = 0, n = stock.size();
        boolean swapNeeded = true;
        while (i < n - 1 && swapNeeded) {
            swapNeeded = false;
            for (int j = 1; j < n - i; j++) {
                String[] item = stock.get(j-1);
                String[] item2 = stock.get(j);
                if (Integer.parseInt(item[6]) < Integer.parseInt(item2[6])) {
                    stock.remove(j-1);
                    stock.add(j-1, item2);
                    stock.remove(j);
                    stock.add(j, item);
                    swapNeeded = true;
                }
            }
            if(!swapNeeded) {
                break;
            }
            i++;
        }
        //Bubble sort to sort the list of stock into order by quantity in stock
        return stock;
    }

}
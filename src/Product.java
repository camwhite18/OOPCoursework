package uk.ac.lboro.CameronWhite;

abstract public class Product {
    private int barcode;
    private String brand;
    private Colour colour;
    private WirelessConnectivity wirelessConnectivity;
    private int quantityInStock;
    private double originalCost;
    private double retailPrice;

    public Product(int barcode, String brand, Colour colour, WirelessConnectivity wirelessConnectivity,
                   int quantityInStock, double originalCost, double retailPrice) {
        this.barcode = barcode;
        this.brand = brand;
        this.colour = colour;
        this.wirelessConnectivity = wirelessConnectivity;
        this.quantityInStock = quantityInStock;
        this.originalCost = originalCost;
        this.retailPrice = retailPrice;
    }

    public int getBarcode() {
        return barcode;
    }

    public String getBrand() {
        return brand;
    }

    public Colour getColour() {
        return colour;
    }

    public WirelessConnectivity isWirelessConnectivity() {
        return wirelessConnectivity;
    }

    public int getQuantityInStock() {
        return quantityInStock;
    }

    public double getOriginalCost() {
        return originalCost;
    }

    public double getRetailPrice() {
        return retailPrice;
    }
}

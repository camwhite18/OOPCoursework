package uk.ac.lboro.CameronWhite;

public class Mouse extends Product {
    private int noOfButtons;
    private IsGamingMouse isGamingMouse;

    public Mouse(int barcode, IsGamingMouse isGamingMouse, String brand, Colour colour, WirelessConnectivity wirelessConnectivity, int quantityInStock,
                 double originalCost, double retailPrice, int noOfButtons) {
        super(barcode, brand, colour, wirelessConnectivity, quantityInStock, originalCost, retailPrice);
        this.noOfButtons = noOfButtons;
        this.isGamingMouse = isGamingMouse;
    }

    public int getNoOfButtons() {
        return noOfButtons;
    }

    public IsGamingMouse isGamingMouse() {
        return isGamingMouse;
    }
}
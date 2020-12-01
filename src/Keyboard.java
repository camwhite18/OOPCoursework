package uk.ac.lboro.CameronWhite;

public class Keyboard extends Product {
    private KeyboardType keyBoardType;
    private IsUKLayout isUKLayout;

    public Keyboard(int barcode, KeyboardType keyBoardType, String brand, Colour colour, WirelessConnectivity wirelessConnectivity, int quantityInStock,
                    double originalCost, double retailPrice, IsUKLayout isUKLayout) {
        super(barcode, brand, colour, wirelessConnectivity, quantityInStock, originalCost, retailPrice);
        this.keyBoardType = keyBoardType;
        this.isUKLayout = isUKLayout;
    }

    public KeyboardType getKeyBoardType() {
        return keyBoardType;
    }

    public IsUKLayout isUKLayout() {
        return isUKLayout;
    }
}
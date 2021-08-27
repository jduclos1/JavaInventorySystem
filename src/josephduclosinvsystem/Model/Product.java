package josephduclosinvsystem.Model;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
//extends Part
public class Product  { 
    private static ObservableList<Part> associatedParts = FXCollections.observableArrayList();
    private final IntegerProperty prodID;
    private final StringProperty prodName;
    private final DoubleProperty prodPrice;
    private final IntegerProperty prodInstock;
    private final IntegerProperty prodMin;
    private final IntegerProperty prodMax;
        
    public Product(){
        prodID = new SimpleIntegerProperty();
        prodName = new SimpleStringProperty();
        prodPrice = new SimpleDoubleProperty();
        prodInstock = new SimpleIntegerProperty();
        prodMin = new SimpleIntegerProperty();
        prodMax = new SimpleIntegerProperty();;
    }
    
    //Product Getters
    public int getProdID() {
        return this.prodID.get();
    }
    
    public String getProdName() {
        return this.prodName.get();
    }
    
    public int getProdInstock() {
        return this.prodInstock.get();
    }
    
    public double getProdPrice() {
        return this.prodPrice.get();
    }
    
    public int getProdMin() {
        return this.prodMin.get();
    }

    public int getProdMax() {
        return this.prodMax.get();
    }
    
    public ObservableList lookupAssociatedParts(){
        return associatedParts;
    }
   
    //Product Setters
    public void setProdID(int i) {
        this.prodID.set(i);
    }
    
    public void setProdName(String pName) {
        this.prodName.set(pName);
    }
    
    public void setProdInstock(int i) {
        this.prodInstock.set(i);
    }
    
    public void setProdPrice(double d) {
        this.prodPrice.set(d);
    }
    
    public void setProdMin(int i) {
        this.prodMin.set(i);
    }

    public void setProdMax(int i) {
        this.prodMax.set(i);
    }
    
    public void addAssociatedParts(ObservableList<Part> associatedParts){
        this.associatedParts = associatedParts;
    } 
    
    //Product Properties
    public IntegerProperty getProdIDProperty(){
        return prodID;
    }
    
    public StringProperty getProdNameProperty(){
        return prodName;
    }
    
    public IntegerProperty getProdInstockProperty() {
        return prodInstock;
    }
    
    public DoubleProperty getProdPriceProperty() {
        return prodPrice;
    }
 
    public static String prodValidCheck(String name, int min, int max, int instock, double price, ObservableList<Part> aParts, String error){
        double allParts = 0.00;
        for (int i = 0; i < aParts.size(); i++){
            allParts = allParts + aParts.get(i).getPartPrice();
        }
        if (name.equals("")){
            error = error + ("The product's name field is empty! ");
        }
        if (min < 1){
            error = error + ("Inventory must contain more than 0! ");
        } 
        if (instock < 1){
            error = error + ("At least 1 part must be entered! ");
        }
        if (price < 1){
            error = error + ("The part's price must be more than 0! ");
        }//having error here
        if (min > max){
            error = error + ("The minimum must be less than the maximum! ");
        }
        if (instock < min || instock > max){
            error = error + ("Inventory amount must be between the minimum and maximum! ");
        }
        if (aParts.size() < 1){
            error = error + ("Product must have at least 1 part! ");
        }
        if (allParts > price){
            error = error + ("Product price must be higher than total cost of parts! ");
        }
        return error;
    }
  
}

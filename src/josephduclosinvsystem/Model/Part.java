
package josephduclosinvsystem.Model;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public abstract class Part {
    private final IntegerProperty partID;
    private final StringProperty partName;
    private final IntegerProperty partInstock;
    private final IntegerProperty partMin;
    private final IntegerProperty partMax;
    private final DoubleProperty partPrice;
    
    public Part(){
        partName = new SimpleStringProperty();
        partID = new SimpleIntegerProperty();
        partInstock = new SimpleIntegerProperty();
        partMin = new SimpleIntegerProperty();
        partMax = new SimpleIntegerProperty();
        partPrice = new SimpleDoubleProperty();
    }
    
    //Part getters
    public String getPartName() {
        return partName.get();
    }

    public int getPartID() {
        return partID.get();
    }

    public int getPartInstock() {
        return partInstock.get();
    }
    
    public int getPartMin(){
        return partMin.get();
    }

    public int getPartMax() {
        return partMax.get();
    }

    public double getPartPrice() {
        return partPrice.get();
    }
    
    //Part properties
    public StringProperty getPartNameProperty() {
        return partName;
    }

    public IntegerProperty getPartIDProperty() {
        return partID;
    }

    public IntegerProperty getPartInstockProperty() {
        return partInstock;
    }

    public DoubleProperty getPartPriceProperty() {
        return this.partPrice;
    }
    
    //Part setters    
    public void setPartName(String name) {
        this.partName.set(name);
    }

    public void setPartID(int p) {
        this.partID.set(p);
    }

    public void setPartInstock(int instock) {
        this.partInstock.set(instock);
    }

    public void setPartMin(int min) {
        this.partMin.set(min);
    }

    public void setPartMax(int max) {
        this.partMax.set(max);
    }

    public void setPartPrice(double p) {
        this.partPrice.set(p);
    }  
   
    //Error detection
    public static String partValidCheck(String name, int min, int max, int instock, 
            double price, String error){
        if (name == null){
            error = error + ("The part's name field is empty!");
        }
        if (instock < 1){
            error = error + ("At least 1 part must be entered!");
        }
        if (price < 1){
            error = error + ("The part's price must be more than 0!");
        }
        if (min > max){
            error = error + ("The minimum must be higher than the maximum!");
        }
        if (instock < min || instock > max){
            error = error + ("Inventory amount must be between the minimum and maximum!");
        }
        return error;
    }
}

    
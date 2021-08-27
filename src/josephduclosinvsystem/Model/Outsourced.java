
package josephduclosinvsystem.Model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Outsourced extends Part{
    private StringProperty companyName;
  
    public Outsourced(){
        super();
        companyName = new SimpleStringProperty();
    }
    
    public void setCompanyName(String s){
        this.companyName.set(s);
    }
    
    public String getCompanyName(){
        return this.companyName.get();
    } 
}

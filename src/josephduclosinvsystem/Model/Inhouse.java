package josephduclosinvsystem.Model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;

public class Inhouse extends Part{
    private final IntegerProperty machineID;

    public Inhouse() {
       super();
       this.machineID = new SimpleIntegerProperty();
    }

    public void setMachineID(int i){
        this.machineID.set(i);
    }
    
    public int getMachineID(){
        return this.machineID.get();
    }
}

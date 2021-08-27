package josephduclosinvsystem.Model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Inventory {
    private static ObservableList<Part> allParts = FXCollections.observableArrayList();
    private static ObservableList<Product> products = FXCollections.observableArrayList();
    
    //auto generated ids
    private static int autoPartID = 0;
    private static int autoProdID = 0;
    
    public Inventory(){
    }
    
    public static ObservableList<Product> getProdInv(){
            return products;
    }
    
    public static ObservableList<Part> getPartInv(){
        return allParts;
    }
    
    public static void addProduct(Product p){
        products.add(p);
    }
    
    public static void addPart(Part p){
        allParts.add(p);
    }

    public static void deleteProduct(Product p){
        products.remove(p);
    }
    
    public static void deletePart(Part p){
        allParts.remove(p);
    }

    public static int lookupProduct(String s){
        boolean prodValid = false;
        int index = 0;
        if (isValid(s)){
            for (int i = 0; i < products.size(); i++){
                if (Integer.parseInt(s) == products.get(i).getProdID()){
                    index = i;
                    prodValid = true;
                }
            }
        }else {
           for (int i = 0; i < products.size(); i++){
                if (s.equals(products.get(i).getProdName())){
                    index = i;
                    prodValid = true;
                }
           }
        }
        if (prodValid = true){
            return index;
        }else{
            System.out.println("No products found");
            return -1;
        }
    }
    
    public static int lookupPart(String s){
        boolean partValid = false;
        int index = 0;
        if (isValid(s)){
            for (int i = 0; i < allParts.size(); i++){
                if (Integer.parseInt(s) == allParts.get(i).getPartID()){
                    index = i;
                    partValid = true;
                }
            }
        }else {
           for (int i = 0; i < allParts.size(); i++){
                if (s.equals(allParts.get(i).getPartName())){
                    index = i;
                    partValid = true;
                } 
           }
        }
        if (partValid = true){
            return index;
        }else{
            System.out.println("No parts found!");
            return -1;
        }
    }
    
    public static void updateProd(int index, Product p){
        products.set(index, p);
    }

    public static void updatePart(int index, Part p){
        allParts.set(index, p);
    }
  
    
    //Auto generates part ids
    public static int getAutoGenPartID(){
        autoPartID++;
        return autoPartID;
    }
    
    //Auto generates product ids
    public static int getAutoGenProdID(){
        autoProdID++;
        return autoProdID;
    }
    
    public static boolean isValid(String s){
        try{
            Integer.parseInt(s);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

}

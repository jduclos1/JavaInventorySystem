package josephduclosinvsystem.View_Controller;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import josephduclosinvsystem.MainApp;
import josephduclosinvsystem.Model.Inventory;
import static josephduclosinvsystem.Model.Inventory.getPartInv;
import static josephduclosinvsystem.Model.Inventory.getProdInv;
import josephduclosinvsystem.Model.Part;
import josephduclosinvsystem.Model.Product;
import static josephduclosinvsystem.Model.Inventory.deleteProduct;
import static josephduclosinvsystem.Model.Inventory.deletePart;

/**
 *
 * @author jduclos1
 */
public class MainScreenController implements Initializable {

    @FXML private TableView<Part> partsTable;
    @FXML private TableColumn<Part, Integer> idCol;
    @FXML private TableColumn<Part, String> nameCol;
    @FXML private TableColumn<Part, Integer> stockCol;
    @FXML private TableColumn<Part, Double> priceCol;
    @FXML private TableView<Product> prodTable;
    @FXML private TableColumn<Product, Integer> idCol2;
    @FXML private TableColumn<Product, String> nameCol2;
    @FXML private TableColumn<Product, Integer> stockCol2;
    @FXML private TableColumn<Product, Double> priceCol2;
    @FXML private BorderPane MainScreen;
    @FXML private TextField mainScreenPartSearch;
    @FXML private TextField mainScreenProdSearch;
    private MainApp mainApp;
    private static int modPartIndex;
    private static int modProdIndex;

    public MainScreenController() {
    }
    
    @FXML private void searchPartHandler(ActionEvent event) {
        String partSearch = mainScreenPartSearch.getText();
        int index = -1;
        
        if (Inventory.lookupPart(partSearch) == -1) {
            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("Error");
            alert.setHeaderText("Part not found");
            alert.setContentText("No part found!");
            alert.showAndWait();
        } else {
            index = Inventory.lookupPart(partSearch);
            Part tempPart = Inventory.getPartInv().get(index);
            ObservableList<Part> tempPartList = FXCollections.observableArrayList();
            tempPartList.add(tempPart);
            partsTable.setItems(tempPartList);
        }
    }

    @FXML private void deletePartHandler(ActionEvent event) {
        Part myPart = partsTable.getSelectionModel().getSelectedItem();

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.initModality(Modality.NONE);
        alert.setTitle("Confirmation");
        alert.setHeaderText("Delete Part?");
        alert.setContentText("Delete part " + myPart.getPartName() + " from parts?");
        Optional<ButtonType> result = alert.showAndWait();

        if (result.get() == ButtonType.OK) {
            deletePart(myPart);
            refreshPartTable();
            System.out.println(myPart.getPartName() + " has been removed.");
        } else {
            System.out.println("");
        }
    }
   
    @FXML private void searchProdHandler(ActionEvent event) {
        String prodSearch = mainScreenProdSearch.getText();
        int index = -1;
        
        if (Inventory.lookupPart(prodSearch) == -1) {
            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("Error");
            alert.setHeaderText("Product not found");
            alert.setContentText("No product found!");
            alert.showAndWait();
        } else {
            index = Inventory.lookupPart(prodSearch);
            Product tempProd = Inventory.getProdInv().get(index);
            ObservableList<Product> tempProdList = FXCollections.observableArrayList();
            tempProdList.add(tempProd);
            prodTable.setItems(tempProdList);
        }
    }

    @FXML private void deleteProdHandler(ActionEvent event) {
        Product myProd = prodTable.getSelectionModel().getSelectedItem();

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.initModality(Modality.NONE);
        alert.setTitle("Confirmation");
        alert.setHeaderText("Delete product?");
        alert.setContentText("Delete product " + myProd.getProdName() + " from products?");
        Optional<ButtonType> result = alert.showAndWait();

        if (result.get() == ButtonType.OK) {
            deleteProduct(myProd);
            refreshPartTable();
            refreshProdTable();
        } else {
            System.out.println("");
        }
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        idCol.setCellValueFactory(cellData -> cellData.getValue().getPartIDProperty().asObject());
        nameCol.setCellValueFactory(cellData -> cellData.getValue().getPartNameProperty());
        stockCol.setCellValueFactory(cellData -> cellData.getValue().getPartInstockProperty().asObject());
        priceCol.setCellValueFactory(cellData -> cellData.getValue().getPartPriceProperty().asObject());
        idCol2.setCellValueFactory(cellData -> cellData.getValue().getProdIDProperty().asObject());
        nameCol2.setCellValueFactory(cellData -> cellData.getValue().getProdNameProperty());
        stockCol2.setCellValueFactory(cellData -> cellData.getValue().getProdInstockProperty().asObject());
        priceCol2.setCellValueFactory(cellData -> cellData.getValue().getProdPriceProperty().asObject());
 
        refreshPartTable();
        refreshProdTable();
    }
    
   public void refreshPartTable(){
       partsTable.setItems(getPartInv());
   } 
    
   public void refreshProdTable(){
       prodTable.setItems(getProdInv());
   }
    
   public void setMainApp(MainApp mainApp){
       refreshPartTable();
       refreshProdTable();
   }     

    @FXML private void addProdHandler(ActionEvent event) throws IOException {
        Parent addProdParent = FXMLLoader.load(getClass().getResource("AddProductScreen.fxml"));
        Scene scene = new Scene(addProdParent);
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(scene);
        window.show();
    }
    
    @FXML private void addPartHandler(ActionEvent event) throws IOException {
        Parent addPartParent = FXMLLoader.load(getClass().getResource("AddPartScreen.fxml"));
        Scene scene = new Scene(addPartParent);
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(scene);
        window.show();
    }
    
    @FXML private void modifyProdHandler(ActionEvent event) throws IOException {
        Parent modProdParent = FXMLLoader.load(getClass().getResource("ModifyProductScreen.fxml"));
        Scene scene = new Scene(modProdParent);
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(scene);
        window.show();
    }

    @FXML private void modifyPartHandler(ActionEvent event) throws IOException {
        Parent modPartParent = FXMLLoader.load(getClass().getResource("ModifyPartScreen.fxml"));
        Scene scene = new Scene(modPartParent);
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(scene);
        window.show(); 
    }
  
    @FXML private void exitAppHandler(ActionEvent event) {
        Platform.exit();
    }
    
    public static int modifyPartIndex() {
        return modPartIndex;
    }

    public static int modifyProductIndex() {
        return modProdIndex;
    }
    
}

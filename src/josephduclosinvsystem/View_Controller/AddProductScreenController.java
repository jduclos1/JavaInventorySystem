package josephduclosinvsystem.View_Controller;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
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
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import josephduclosinvsystem.Model.Inventory;
import static josephduclosinvsystem.Model.Inventory.getPartInv;
import josephduclosinvsystem.Model.Part;
import josephduclosinvsystem.Model.Product;

public class AddProductScreenController implements Initializable {

    @FXML private AnchorPane addProductScreen;
    @FXML private Button addProdSearch;
    @FXML private TextField addProdText;
    @FXML private TableView<Part> addPartToProdTable;
    @FXML private TableColumn<Part, Integer> addPartIDCol;
    @FXML private TableColumn<Part, String> addPartNameCol;
    @FXML private TableColumn<Part, Integer> addPartInvCol;
    @FXML private TableColumn<Part, Double> addPartPriceCol;
    @FXML private Button addPartToProdButton;
    @FXML private Button delPartToProdButton;
    @FXML private TextField addProdNameText;
    @FXML private TextField addProdInstockText;
    @FXML private TextField addProdPriceText;
    @FXML private TextField addProdMaxText;
    @FXML private TextField addProdMinText;
    @FXML private TextField addProdIDText;
    private String error = new String();
    private ObservableList<Part> tempParts = FXCollections.observableArrayList();
    private int autoProdID;
    @FXML private TableView<Part> stagingPartToProdTable;
    @FXML private TableColumn<Part, Integer> stagingPartIDCol;
    @FXML private TableColumn<Part, String> stagingPartNameCol;
    @FXML private TableColumn<Part, Integer> stagingPartInvCol;
    @FXML private TableColumn<Part, Double> stagingPartPriceCol;
    @FXML private Button clearProdSearch;
    

    public AddProductScreenController() {
    }
    
    
    @FXML private void searchProdHandler(ActionEvent event) {
        String partSearch = addProdText.getText();
        int index = -1;

        if (Inventory.lookupPart(partSearch) == -1) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Search Part");
            alert.setHeaderText("Part not found");
            alert.setContentText("The part was not found.");
            alert.showAndWait();
        } else {
            index = Inventory.lookupPart(partSearch);
            Part tempPart = Inventory.getPartInv().get(index);

            ObservableList<Part> tempPartList = FXCollections.observableArrayList();
            tempPartList.add(tempPart);
            addPartToProdTable.setItems(tempPartList);
        }
    }

    @FXML private void addPartToProdHandler(ActionEvent event) {
        Part myPart = addPartToProdTable.getSelectionModel().getSelectedItem();
        tempParts.add(myPart);
        refreshStagingPartTable();
    }

    /*this must be changed to take data from single search bar instead of secondary
    String partSearch = addProdText.getText();
        int index = -1;

        if (Inventory.lookupPart(partSearch) == -1) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Search");
            alert.setHeaderText("Part not found");
            alert.setContentText("The part was not found.");
            alert.showAndWait();
        } else {
            index = Inventory.lookupPart(partSearch);
            Part tempPart = Inventory.getPartInv().get(index);

            ObservableList<Part> tempProdList = FXCollections.observableArrayList();
            tempProdList.add(tempPart);
            delPartToProdTable.setItems(tempProdList);
        }
    */
    
    
    @FXML private void delPartToProdHandler(ActionEvent event) {
        Part part = stagingPartToProdTable.getSelectionModel().getSelectedItem();

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.initModality(Modality.NONE);
        alert.setTitle("Confirmation");
        alert.setHeaderText("Delete Part?");
        alert.setContentText("Delete part " + part.getPartName() + " from parts?");
        Optional<ButtonType> result = alert.showAndWait();

        if (result.get() == ButtonType.OK) {
            System.out.println("Part deleted.");
            tempParts.remove(part);
        } else {
            System.out.println("");
        }
    }

    @FXML private void addPartToProdSaveHandler(ActionEvent event) throws IOException {
        String name = addProdNameText.getText();
        String inv = addProdInstockText.getText();
        String price = addProdPriceText.getText();
        String min = addProdMinText.getText();
        String max = addProdMaxText.getText();

        try {
            error = Product.prodValidCheck(name, Integer.parseInt(min), Integer.parseInt(max), Integer.parseInt(inv), 
                    Double.parseDouble(price), tempParts, error);
                    
            if (error.length() > 0){
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Product Not Added!");
                alert.setHeaderText("Error");
                alert.setContentText(error);
                alert.showAndWait();
                error = "";
            }else{
                Product myProd = new Product();
                myProd.setProdID(autoProdID); 
                myProd.setProdName(name);
                myProd.setProdPrice(Double.parseDouble(price));
                myProd.setProdInstock(Integer.parseInt(inv));
                myProd.setProdMax(Integer.parseInt(max));
                myProd.setProdMin(Integer.parseInt(min));
                myProd.addAssociatedParts(tempParts);
                Inventory.addProduct(myProd);
                     
                Parent prodSave = FXMLLoader.load(getClass().getResource("MainScreen.fxml"));
                Scene scene = new Scene(prodSave);
                Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
                window.setScene(scene);
                window.show();
            }
        }catch (NumberFormatException e){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Product Not Added!");
            alert.setHeaderText("Error");    
            alert.setContentText("Missing data!");
            alert.showAndWait();
        }
    }

    @FXML
    private void addPartToProdCancelHandler(ActionEvent event) throws IOException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.initModality(Modality.NONE);
        alert.setTitle("Cancel");
        alert.setHeaderText("Cancel?");
        alert.setContentText("Do you wish to cancel adding products?");
        Optional<ButtonType> result = alert.showAndWait();

        if (result.get() == ButtonType.OK) {
            Parent prodCancel = FXMLLoader.load(getClass().getResource("MainScreen.fxml"));
            Scene scene = new Scene(prodCancel);
            Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
            window.setScene(scene);
            window.show();
        } else {
            System.out.println("");
        }    
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        autoProdID = Inventory.getAutoGenProdID();
        addProdIDText.setText("AUTO GEN: " + autoProdID);

        addPartIDCol.setCellValueFactory(cellData -> cellData.getValue().getPartIDProperty().asObject());
        addPartNameCol.setCellValueFactory(cellData -> cellData.getValue().getPartNameProperty());
        addPartInvCol.setCellValueFactory(cellData -> cellData.getValue().getPartInstockProperty().asObject());
        addPartPriceCol.setCellValueFactory(cellData -> cellData.getValue().getPartPriceProperty().asObject());
        
        
        stagingPartIDCol.setCellValueFactory(cellData -> cellData.getValue().getPartIDProperty().asObject());
        stagingPartNameCol.setCellValueFactory(cellData -> cellData.getValue().getPartNameProperty());
        stagingPartInvCol.setCellValueFactory(cellData -> cellData.getValue().getPartInstockProperty().asObject());
        stagingPartPriceCol.setCellValueFactory(cellData -> cellData.getValue().getPartPriceProperty().asObject());
        
        refreshPartTable();
        refreshStagingPartTable();
    }  


    public void refreshPartTable(){
       addPartToProdTable.setItems(getPartInv());
    } 
    
    public void refreshStagingPartTable(){
       stagingPartToProdTable.setItems(tempParts);
    }    

    //added clear button for added usibility
    @FXML private void clearProdHandler(ActionEvent event) {
        refreshPartTable();
        addProdText.setText("");
    }
    
}

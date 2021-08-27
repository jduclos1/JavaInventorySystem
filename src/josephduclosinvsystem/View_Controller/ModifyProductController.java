/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
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
import static josephduclosinvsystem.Model.Inventory.getProdInv;
import josephduclosinvsystem.Model.Part;
import josephduclosinvsystem.Model.Product;
import static josephduclosinvsystem.View_Controller.MainScreenController.modifyProductIndex;

/**
 * FXML Controller class
 *
 * @author jduclos1
 */
public class ModifyProductController implements Initializable {

    @FXML private AnchorPane addProductScreen;
    @FXML private TextField modProdIDText;
    @FXML private TextField modProdNameText;
    @FXML private TextField modProdInstockText;
    @FXML private TextField modProdPriceText;
    @FXML private TextField modProdMaxText;
    @FXML private TextField modProdMinText;
    @FXML private Button modProdSearch;
    @FXML private TextField modSearchText;
    @FXML private Button clearSearch;
    @FXML private TableView<Part> addPartToProdTable;
    @FXML private TableColumn<Part, Integer> addPartIDCol;
    @FXML private TableColumn<Part, String> addPartNameCol;
    @FXML private TableColumn<Part, Integer> addPartInvCol;
    @FXML private TableColumn<Part, Double> addPartPriceCol;
    @FXML private Button addPartToProdButton;
    @FXML private TableView<Part> stagingPartToProdTable;
    @FXML private TableColumn<Part, Integer> stagingPartIDCol;
    @FXML private TableColumn<Part, String> stagingPartNameCol;
    @FXML private TableColumn<Part, Integer> stagingPartInvCol;
    @FXML private TableColumn<Part, Double> stagingPartPriceCol;
    @FXML private Button delPartToProdButton;
    private String error = new String();
    private ObservableList<Part> tempParts = FXCollections.observableArrayList();
    private int autoProdID;
    int index = modifyProductIndex();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        Product myProd = getProdInv().get(index);
        autoProdID = getProdInv().get(index).getProdID();
        modProdIDText.setText(Integer.toString(autoProdID));
        modProdNameText.setText(myProd.getProdName()); 
        modProdInstockText.setText(Integer.toString(myProd.getProdInstock())); 
        modProdPriceText.setText(Double.toString(myProd.getProdPrice()));
        modProdMaxText.setText(Integer.toString(myProd.getProdMax()));
        modProdMinText.setText(Integer.toString(myProd.getProdMin())); 
        tempParts = myProd.lookupAssociatedParts();
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

    @FXML private void addPartToProdSaveHandler(ActionEvent event) throws IOException {
        String name = modProdNameText.getText();
        String inv = modProdInstockText.getText();
        String price = modProdPriceText.getText();
        String min = modProdMinText.getText();
        String max = modProdMaxText.getText();
        
        try{
            error = Product.prodValidCheck(name, Integer.parseInt(min), Integer.parseInt(max),
                    Integer.parseInt(inv), Double.parseDouble(price), tempParts, error);

            if (error.length() > 0){
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Product Not Modified!");
                alert.setHeaderText("Error");
                alert.setContentText(error);
                alert.showAndWait();
                error = "";
            }else{
                Product product = new Product();
                    product.setProdInstock(Integer.parseInt(inv));
                    product.setProdMax(Integer.parseInt(max));
                    product.setProdMin(Integer.parseInt(min));
                    product.setProdID(autoProdID);
                    product.setProdName(name);
                    product.setProdPrice(Double.parseDouble(price));
                    Inventory.updateProd(index, product);
                }
                    Parent partSave = FXMLLoader.load(getClass().getResource("MainScreen.fxml"));
                    Scene scene = new Scene(partSave);
                    Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
                    window.setScene(scene);
                    window.show();
               
            }catch (NumberFormatException e){
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Part Not Added!");
                alert.setHeaderText("Error");    
                alert.setContentText("Missing data!");
                alert.showAndWait();
            }
    }

    @FXML
    private void searchProdHandler(ActionEvent event) {
        String partSearch = modSearchText.getText();
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

    

    @FXML
    private void addPartToProdHandler(ActionEvent event) {
        Part myPart = addPartToProdTable.getSelectionModel().getSelectedItem();
        tempParts.add(myPart);
        refreshStagingPartTable();
    }

    @FXML
    private void delPartToProdHandler(ActionEvent event) {
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
    
    public void refreshPartTable(){
       addPartToProdTable.setItems(getPartInv());
    } 
    
    public void refreshStagingPartTable(){
       stagingPartToProdTable.setItems(tempParts);
    }    
    
    @FXML private void clearHandler(ActionEvent event) {
        refreshPartTable();
        modSearchText.setText("");
    }
    
    @FXML private void addPartToProdCancelHandler(ActionEvent event) throws IOException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.initModality(Modality.NONE);
        alert.setTitle("Cancel");
        alert.setHeaderText("Cancel?");
        alert.setContentText("Do you wish to cancel modifying products?");
        Optional<ButtonType> result = alert.showAndWait();

        if (result.get() == ButtonType.OK) {
            Parent modCancel = FXMLLoader.load(getClass().getResource("MainScreen.fxml"));
        Scene scene = new Scene(modCancel);
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(scene);
        window.show();
        } else {
            System.out.println("");
        }
    }
    
}

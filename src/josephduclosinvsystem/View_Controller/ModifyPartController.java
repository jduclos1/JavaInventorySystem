package josephduclosinvsystem.View_Controller;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import josephduclosinvsystem.Model.Inhouse;
import josephduclosinvsystem.Model.Inventory;
import static josephduclosinvsystem.Model.Inventory.getPartInv;
import josephduclosinvsystem.Model.Outsourced;
import josephduclosinvsystem.Model.Part;
import static josephduclosinvsystem.View_Controller.MainScreenController.modifyPartIndex;

public class ModifyPartController implements Initializable {

    @FXML private AnchorPane modifyPartScreen;
    @FXML private RadioButton inhouseB;
    @FXML private RadioButton outsourcedB;
    @FXML private Label pIdLabel;
    @FXML private Label pNameLabel;
    @FXML private Label pInvLabel;
    @FXML private Label pCostLabel;
    @FXML private Label pMaxLabel;
    @FXML private TextField pIdText;
    @FXML private TextField pNameText;
    @FXML private TextField pInvText;
    @FXML private TextField pCostText;
    @FXML private TextField pMaxText;
    @FXML private TextField inOrOutText;
    @FXML private TextField pMinText;
    @FXML private Label pMinLabel;
    private boolean inout;
    private int autoPartID;
    private String error = new String();
    int index = modifyPartIndex();
    @FXML private Label inOrOut;
       
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        Part myPart = getPartInv().get(index);
        autoPartID = getPartInv().get(index).getPartID();
        pIdText.setText(Integer.toString(autoPartID));
        pNameText.setText(myPart.getPartName()); 
        pInvText.setText(Integer.toString(myPart.getPartInstock())); 
        pCostText.setText(Double.toString(myPart.getPartPrice()));
        pMaxText.setText(Integer.toString(myPart.getPartMax()));
        pMinText.setText(Integer.toString(myPart.getPartMin())); 
        if (myPart instanceof Inhouse){
            inOrOut.setText(Integer.toString(((Inhouse) getPartInv().get(index)).getMachineID()));
            inOrOutText.setText("Machine ID");
            inhouseB.setSelected(true);
        }else{
            inOrOut.setText((((Outsourced) getPartInv().get(index)).getCompanyName()));
            inOrOutText.setText("Company Name");
            outsourcedB.setSelected(true);
        }
    }    

    @FXML private void inhouseRadio(ActionEvent event) {
        inout = true;
        inOrOut.setText("Machine ID");  
    }

    @FXML private void outsourcedRadio(ActionEvent event) {
        inout = false;
        inOrOut.setText("Company Name");
    }

    @FXML private void savePartHandler(ActionEvent event) throws IOException {
        String name = pNameText.getText();
        String inv = pInvText.getText();
        String price = pCostText.getText();
        String min = pMinText.getText();
        String max = pMaxText.getText();
        String inXout = inOrOutText.getText();
        
        try{
            error = Part.partValidCheck(name, Integer.parseInt(min), Integer.parseInt(max),
                    Integer.parseInt(inv), Double.parseDouble(price), error);
            if (error.length() > 0){
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Part Not Modified!");
                alert.setHeaderText("Error");
                alert.setContentText(error);
                alert.showAndWait();
                error = "";
            }else{
                if (inout == true) {
                    Inhouse inhouse = new Inhouse();
                    inhouse.setMachineID(Integer.parseInt(inXout));
                    inhouse.setPartInstock(Integer.parseInt(inv));
                    inhouse.setPartMax(Integer.parseInt(max));
                    inhouse.setPartMin(Integer.parseInt(min));
                    inhouse.setPartID(autoPartID);
                    inhouse.setPartName(name);
                    inhouse.setPartPrice(Double.parseDouble(price));
                    Inventory.updatePart(index, inhouse);
                }else{
                    Outsourced outsourced = new Outsourced();
                    outsourced.setCompanyName(inXout);
                    outsourced.setPartInstock(Integer.parseInt(inv));
                    outsourced.setPartMax(Integer.parseInt(max));
                    outsourced.setPartMin(Integer.parseInt(min));
                    outsourced.setPartID(autoPartID);
                    outsourced.setPartName(name);
                    outsourced.setPartPrice(Double.parseDouble(price));
                    Inventory.updatePart(index, outsourced);
                }
                    
                    Parent partSave = FXMLLoader.load(getClass().getResource("MainScreen.fxml"));
                    Scene scene = new Scene(partSave);
                    Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
                    window.setScene(scene);
                    window.show();
                }
            }catch (NumberFormatException e){
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Part Not Added!");
                alert.setHeaderText("Error");    
                alert.setContentText("Missing data!");
                alert.showAndWait();
            }
    }

    @FXML private void cancelPartHandler(ActionEvent event) throws IOException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.initModality(Modality.NONE);
        alert.setTitle("Cancel");
        alert.setHeaderText("Cancel?");
        alert.setContentText("Do you wish to cancel modifying parts?");
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
package josephduclosinvsystem;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import josephduclosinvsystem.View_Controller.MainScreenController;

/**
 *
 * @author jduclos1
 */
public class MainApp extends Application {
    
    public MainApp(){
    }
   
    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(MainApp.class.getResource("View_Controller/MainScreen.fxml"));
        Parent root = (Parent) loader.load();
        MainScreenController control = loader.getController();
        control.setMainApp(this);
        Scene scene = new Scene(root);
        stage.setTitle("Inventory System");
        stage.setScene(scene);
        stage.show();
    }
    
    public static void main(String[] args) {
        launch(args);
    }
    
}

package rmit.fp;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import sqlitedb.ValidateLogin;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;

public class LoginController {
    private static Scene scene;
    private static Stage stage;
    

    @FXML
    private TextField usernameField;
    @FXML
    private PasswordField passwordField;

    public void loginButtonClicked(){
        String username = usernameField.getText();
        String password = passwordField.getText();
        String validUser;

        ValidateLogin vl = new ValidateLogin();

        if (vl.Validation(username,password )){

            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("Login Success");
            validUser = username;
//loads the dashboard page after successful login
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("dashboard.fxml")); 
                Parent root = loader.load();


                DashboardController dc = loader.getController() ;

                Stage stage = (Stage) usernameField.getScene().getWindow(); 
                dc.setUserMessage(validUser);
                Scene scene = new Scene(root);
                stage.setScene(scene);
                stage.setTitle("Dashboard");
                stage.show();}

            catch (IOException e){
                e.printStackTrace();
                Alert errorAlert = new Alert(AlertType.ERROR);
                errorAlert.setContentText("Could not load dashboard.");
                errorAlert.showAndWait();

            }
        }
        else{
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Login Failed");
            alert.setContentText("Invalid UserName AND/OR password"); 
            System.out.println("Invalid UserName AND/OR password");
            alert.showAndWait();
        }
    }

    public void dash(Stage stage) throws IOException {
        scene = new Scene(loadFXML("dashboard"), 640, 480);
        stage.setScene(scene);
        stage.show();
    }

    static void setRoot(String fxml) throws IOException {
        scene.setRoot(loadFXML(fxml));
    }

    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml + ".fxml"));
        return fxmlLoader.load();
    }

 
    
}

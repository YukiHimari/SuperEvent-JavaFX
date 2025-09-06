package rmit.fp;


import java.io.IOException;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class ConfirmationController {
    @FXML
    private TextField digitField;


    public void confirmButtonClicked() {
        String digits = digitField.getText();
        if (digits.length() == 6) {
            // Logic to handle confirmation with the 6-digit code
            System.out.println("Confirmation code entered: " + digits);

            try{
                int confirmationCode = Integer.parseInt(digits);

                 Alert confirmAlert = new Alert(AlertType.INFORMATION);
                confirmAlert.setTitle("Confirmation");
                confirmAlert.setHeaderText(null);
                confirmAlert.setContentText("Confirmation Successful!!");
                confirmAlert.showAndWait();

             //since confirmaton is successfull we can view the orders   
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("order.fxml")); 
                Parent root = loader.load();


                OrderController oc = loader.getController() ;
                Stage stage = (Stage) digitField.getScene().getWindow();
                Scene scene = new Scene(root);
                stage.setScene(scene);
                stage.setTitle("orders");
                stage.show();}

            catch (IOException e){
                e.printStackTrace();
                Alert errorAlert = new Alert(AlertType.ERROR);
                errorAlert.setContentText("Could not load orders.");
                errorAlert.showAndWait();

            }

            }
            catch (NumberFormatException e) {
                // Handle the case where the input is not a valid integer
                Alert errorAlert = new Alert(AlertType.ERROR);
                errorAlert.setTitle("Invalid Confirmation Code");
                errorAlert.setHeaderText(null);
                errorAlert.setContentText("Invalid Confirmation code: Please enter 6 digits only.");
                errorAlert.showAndWait();
                System.out.println("Invalid confirmation code. Please enter a valid 6-digit code.");
            }
        } else {
            // Show an error message if the code is not 6 digits
                Alert lengthAlert = new Alert(AlertType.ERROR);
                lengthAlert.setTitle("Invalid Confirmation Code length");
                lengthAlert.setHeaderText(null);
                lengthAlert.setContentText("Please enter a 6 digit confirmation code.");
                lengthAlert.showAndWait();

        }
    }
    
}

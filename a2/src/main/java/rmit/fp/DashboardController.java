package rmit.fp;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import sqlitedb.UpdateSoldTickets;
import sqlitedb.viewEvents;
import sqlitedb.AddToCart;
import javafx.scene.layout.VBox;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.control.Button;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

import java.io.IOException;
import java.util.List;

public class DashboardController {
     @FXML
    private Text UserMessage;
    @FXML
    private VBox eventsContainer;

    public void setUserMessage(String username){
        UserMessage.setText(username);

    }
    Event e = new Event("","", "", 0.0, 0, 0); // Placeholder for Event object

    public void initialize() {
        List<Event> events = viewEvents.getEventsfromDB(); //getting the events from the database
        if (events.isEmpty()) { //If there are no events
            Text noEventsText = new Text("No events available at the moment.");
            noEventsText.setFill(Color.WHITE);
            eventsContainer.getChildren().add(noEventsText);
            return;
        }
        for (Event event : events) {
            Pane eventPane = createEventPane(event);
            eventsContainer.getChildren().add(eventPane);
        }
    }

    private Pane createEventPane(Event event) {
        Pane pane = new Pane();
        pane.setPrefSize(500, 150);
        pane.setStyle("-fx-border-color: #1e90ff; -fx-background-color: #112233; -fx-background-radius: 8; -fx-border-radius: 8; -fx-effect: dropshadow(gaussian, #1e90ff, 4, 0, 0, 2);");
        Text name = new Text(20, 35, event.getName());
        name.setFill(Color.WHITE);
        Text venue = new Text(20, 60, event.getVenue());
        venue.setFill(Color.WHITE);
        Text day = new Text(20, 85, event.getDay());
        day.setFill(Color.WHITE);
        Text price = new Text(20, 110, "Price: $" + event.getPrice());
        price.setFill(Color.WHITE);
        Text soldTickets = new Text(20, 135, "Tickets Sold: " + event.getSoldTickets() + "/" + event.getTotalTickets());
        soldTickets.setFill(Color.WHITE);
        Text availableTickets = new Text(300, 135, "Available: " + (event.getTotalTickets() - event.getSoldTickets()));
        availableTickets.setFill(Color.WHITE);
        
        int [] available = {event.getTotalTickets() - event.getSoldTickets()};

        
        Spinner<Integer> quantitySpinner = new Spinner<>();
        quantitySpinner.setValueFactory(
            new SpinnerValueFactory.IntegerSpinnerValueFactory(1,15,1,1) //assuming that the max tickets that can be selected per event at a time is 15
        );
        quantitySpinner.setPrefWidth(60);
        quantitySpinner.setLayoutX(380);
        quantitySpinner.setLayoutY(60);  // Adjust Y as needed

        Button AddToCartButton = new Button("Add to Cart");
        AddToCartButton.setLayoutX(400);
        AddToCartButton.setLayoutY(110);
        AddToCartButton.setStyle("-fx-background-color: #1e90ff; -fx-text-fill: white; -fx-font-weight: bold; -fx-padding: 5px 10px; -fx-background-radius: 5;");
        AddToCartButton.setOnAction(actionEvent -> {
            // Logic to add the event to the cart
            if (available[0] < 1) {
                Alert alert = new Alert(AlertType.WARNING);
                alert.setTitle("No Tickets Available");
                alert.setHeaderText(null);
                alert.setContentText("Sorry!! No tickets available for " + event.getName() + ".");
                alert.showAndWait();
                return;
            }
            //checking id the quantity selected is higher than the available tickets
            int quantity = quantitySpinner.getValue();
            if (quantity > available[0]) {
                Alert alert = new Alert(AlertType.WARNING);
                alert.setTitle("Insufficient Tickets");
                alert.setHeaderText(null);
                alert.setContentText("Only " + available + " tickets are available for " + event.getName() + ".");
                alert.showAndWait();
                return;
            }
            else{
            UpdateSoldTickets.updateTickets(event.getId(), quantity, event.getName()); // Update the sold tickets in the database
            event.setSoldTickets(event.getSoldTickets() + quantity); // Update the sold tickets in the event object
            }


            //Update the Dashboard to show changes
            soldTickets.setText("Tickets Sold: " + event.getSoldTickets() + "/" + event.getTotalTickets());
             // Update the available tickets text
            available[0] = event.getTotalTickets() - event.getSoldTickets();
            availableTickets.setText("Available: " +  available[0] );



           Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("Event Added");
            alert.setHeaderText(null);
            AddToCart.addEventToCart(event, quantity,UserMessage.getText()); // Add the event to the cart in the database
            alert.setContentText(quantity + " tickets for " + event.getName() + " have been added to your cart.");
            // alert.setContentText(event.getName() + " has been added to your cart.");
            alert.showAndWait();
        });


        pane.getChildren().addAll(name, venue, day, price, soldTickets, availableTickets, AddToCartButton, quantitySpinner);
        return pane;
    }
//when the checkout button is clicked the cart will be loaded
    public void checkoutButtonClicked() {

            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("cart.fxml")); 
                Parent root = loader.load();


                CartController cc = loader.getController() ;
                cc.setUsername(UserMessage.getText());
                Stage stage = (Stage) UserMessage.getScene().getWindow();
                Scene scene = new Scene(root);
                stage.setScene(scene);
                stage.setTitle("cart");
                stage.show();}

            catch (IOException e){
                e.printStackTrace();
                Alert errorAlert = new Alert(AlertType.ERROR);
                errorAlert.setContentText("Could not load cart.");
                errorAlert.showAndWait();

            }
    }
}

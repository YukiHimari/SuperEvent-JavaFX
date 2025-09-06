package rmit.fp;

import java.io.IOException;
import java.util.List;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import sqlitedb.UpdateCart;
import sqlitedb.UpdateSoldTickets;
import sqlitedb.viewAvailableTickets;
import sqlitedb.viewCart;

public class CartController {
     @FXML
    private VBox AddedEventsContainer;
    @FXML
    private VBox OrderSummaryContainer;

    private String username;
    public void setUsername(String username) {
        this.username = username;
        loadCart(); // Load the cart when the username is set
    }

        public void loadCart() {
        List<Cart> CartEvents = viewCart.getCartfromDB(username); //getting the events from the database
        if (CartEvents.isEmpty()) { //If there are no events
            Text noEventsText = new Text("No events available at the moment.");
            noEventsText.setFill(Color.WHITE);
            AddedEventsContainer.getChildren().add(noEventsText);
            return;
        }
        for (Cart ce : CartEvents) {
            Pane eventPane = createCartPane(ce);
            AddedEventsContainer.getChildren().add(eventPane);
        }
         initializeOrderSummary(CartEvents);
        }

        //initialising the order summary
        public void initializeOrderSummary(List<Cart> CartEvents) {
            OrderSummaryContainer.getChildren().clear(); // Clear previous order summary
            double totalPrice = 0;
            for (Cart ce: CartEvents) {
                totalPrice += ce.getCalcPrice(); // Adding the calcPrice of each event
            }
            final double finalTotalPrice = totalPrice;

            //displaying the total price
            Text totalText = new Text("Total Price: $" + finalTotalPrice);
            totalText.setFill(Color.BLACK);

            Button payButton = new Button("Proceed to payment →");
            payButton.setStyle("-fx-background-color: #112233; -fx-text-fill: white; -fx-font-weight: bold; -fx-padding: 10px 20px; -fx-background-radius: 5;");

            payButton.setOnAction(actionEvent -> {
                // Logic to proceed to payment
                System.out.println("Proceeding to payment with total: $" + finalTotalPrice);
                paymentButtonClicked(); // Call the method to handle payment
                // Here you can add the logic to handle the payment process
            });
            Pane summaryPane = new Pane();
            summaryPane.setPrefSize(180, 500);
            summaryPane.setStyle("-fx-border-color: #1e90ff; -fx-background-color: #f0f0f0; -fx-background-radius: 8; -fx-border-radius: 8; -fx-effect: dropshadow(gaussian, #1e90ff, 4, 0, 0, 2);");
            payButton.setLayoutX(10);
            payButton.setLayoutY(200);
            totalText.setLayoutX(20);
            totalText.setLayoutY(30);
            totalText.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");
            summaryPane.getChildren().addAll(totalText, payButton);
            OrderSummaryContainer.getChildren().add(summaryPane);


        }


        //initialising the cart pane
    private Pane createCartPane(Cart ce) {
        Pane pane = new Pane();
        pane.setPrefSize(300, 120);
        pane.setStyle("-fx-border-color: #1e90ff; -fx-background-color: #112233; -fx-background-radius: 8; -fx-border-radius: 8; -fx-effect: dropshadow(gaussian, #1e90ff, 4, 0, 0, 2);");
        Text name = new Text(20, 35, ce.getEventName());
        name.setFill(Color.WHITE);
        Text venue = new Text(20, 60, ce.getVenue());
        venue.setFill(Color.WHITE);
        Text day = new Text(20, 85, ce.getDay());
        day.setFill(Color.WHITE);
        Text price = new Text(20, 110, "Price: $" + ce.getPrice());
        price.setFill(Color.WHITE);
        


        //spinner for ticket quantity
    Spinner<Integer> qtySpinner = new Spinner<>(); 
    int availableTickets = viewAvailableTickets.getAvailableTicketsFromDB(ce.getEventId()); // gets available tickets after deducting the current qty
    System.out.println("Total available tickets: " + availableTickets); // Debugging line
    int currentQty = ce.getTicketQuantity(); // 5
    System.out.println("current qty: " + currentQty);
    int maxQty = availableTickets + currentQty; // Maximum quantity based on available tickets
    System.out.println("max qty: " + maxQty);

    // Clamp currentQty
    if (currentQty > maxQty) currentQty = maxQty;

    qtySpinner.setValueFactory(
        new SpinnerValueFactory.IntegerSpinnerValueFactory(1, maxQty, currentQty)
    );
   qtySpinner.setPrefWidth(60);
   qtySpinner.setLayoutX(200);
    qtySpinner.setLayoutY(35);

    // Optionally, handle quantity changes
    qtySpinner.valueProperty().addListener((obs, oldVal, newVal) -> {
        if (newVal != null && !newVal.equals(oldVal)) {
            ce.setTicketQuantity(newVal);
            UpdateCart.updateQuantity(ce.getEventId(), newVal);//updates the quantity in the database

            int currentSoldTickets = viewAvailableTickets.getSoldTicketsFromDB(ce.getEventId());
            int newSoldTickets = currentSoldTickets - oldVal + newVal; // Update sold tickets based on the change
            System.out.println("Updating sold tickets for event ID " + ce.getEventId() + ": " + newSoldTickets);
            if (ce.getEvent() != null) {
                ce.getEvent().setSoldTickets(newSoldTickets);
            }
            UpdateSoldTickets.updateSoldTicketsFromCart(ce.getEventId(), newSoldTickets, ce.getEventName()); // Update the sold tickets in the database

            //Updating the order summary
            ce.setCalcPrice(ce.getPrice() * newVal); // Update the calculated price based on the new quantity
            initializeOrderSummary(viewCart.getCartfromDB(username)); // Refresh the order summary with updated cart
        }
    });
    
    Text qtyLabel = new Text(200, 30, "Qty:");
    qtyLabel.setFill(Color.WHITE);

    Button removeButton = new Button("Remove");
    removeButton.setLayoutX(200);
    removeButton.setLayoutY(70);
    removeButton.setStyle("-fx-background-color:rgb(147, 33, 13); -fx-text-fill: white; -fx-font-weight: bold; -fx-padding: 5px 10px; -fx-background-radius: 5;");
    final int finalCurrentQty = currentQty;
    removeButton.setOnAction(actionEvent -> {
        // Logic to remove the event from the cart
        UpdateCart.removeEventFromCart(ce.getEventId());
        UpdateSoldTickets.removeSold(ce.getEventId(), finalCurrentQty, ce.getEventName());
        AddedEventsContainer.getChildren().remove(pane);

    });

    pane.getChildren().addAll(name, venue, day, price, qtyLabel, qtySpinner, removeButton);
    return pane;
    }

//loads the confirmation page when the payment button is clicked
    public void paymentButtonClicked() {

            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("confirmation.fxml")); 
                Parent root = loader.load();


                ConfirmationController cc = loader.getController() ;
                Stage stage = (Stage) AddedEventsContainer.getScene().getWindow();
                Scene scene = new Scene(root);
                stage.setScene(scene);
                stage.setTitle("cart");
                stage.show();}

            catch (IOException e){
                e.printStackTrace();
                Alert errorAlert = new Alert(AlertType.ERROR);
                errorAlert.setContentText("Could not load payment.");
                errorAlert.showAndWait();

            }
    }

    //back button to go back to the dashboard
    public void backButtonClicked() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("dashboard.fxml")); 
            Parent root = loader.load();

            DashboardController dc = loader.getController();
            dc.setUserMessage(username);
            Stage stage = (Stage) AddedEventsContainer.getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.setTitle("Dashboard");
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            Alert errorAlert = new Alert(AlertType.ERROR);
            errorAlert.setContentText("Could not load dashboard.");
            errorAlert.showAndWait();
        }
    }
}

package rmit.fp;

import javafx.fxml.FXML;
import javafx.scene.control.TreeTableView;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.control.TreeItem;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

import java.io.IOException;
import java.util.List;

public class OrderController {

    @FXML
    private TreeTableView<Object> ordersTreeTable;
    @FXML
    private TreeTableColumn<Object, String> orderNumberCol;
    @FXML
    private TreeTableColumn<Object, String> eventNameCol;
    @FXML
    private TreeTableColumn<Object, Integer> quantityCol;
    @FXML
    private TreeTableColumn<Object, Double> priceCol;
    @FXML
    private TreeTableColumn<Object, Double> totalCol;


    @FXML
    public void initialize() {
        // Set up columns
        orderNumberCol.setCellValueFactory(param -> {
            if (param.getValue().getValue() instanceof Order) {
                int orderNum = ((Order) param.getValue().getValue()).getOrderNumber();
                return new SimpleStringProperty(String.format("%04d", orderNum));
            }
            return new SimpleStringProperty("");
        });
        eventNameCol.setCellValueFactory(param -> {
            if (param.getValue().getValue() instanceof OrderItem) {
                return new SimpleStringProperty(((OrderItem) param.getValue().getValue()).getEventName());
            }
            return new SimpleStringProperty("");
        });
        quantityCol.setCellValueFactory(param -> {
            if (param.getValue().getValue() instanceof OrderItem) {
                return new SimpleIntegerProperty(((OrderItem) param.getValue().getValue()).getTicketQuantity()).asObject();
            }
            return null;
        });
        priceCol.setCellValueFactory(param -> {
            if (param.getValue().getValue() instanceof OrderItem) {
                return new SimpleDoubleProperty(((OrderItem) param.getValue().getValue()).getPrice()).asObject();
            }
            return null;
        });
        totalCol.setCellValueFactory(param -> {
            if (param.getValue().getValue() instanceof Order) {
                return new SimpleDoubleProperty(((Order) param.getValue().getValue()).getTotalPrice()).asObject();
            }
            return null;
        });}
        //Load the orders from the database
        // List<Order> orders = sqlitedb.viewOrders.getOrdersfromDB(username);

    @FXML
    public void LogoutClicked() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("login.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) ordersTreeTable.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.setTitle("login");
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            Alert errorAlert = new Alert(AlertType.ERROR);
            errorAlert.setContentText("Could not logout and load login page.");
            errorAlert.showAndWait();
        }
    }
}

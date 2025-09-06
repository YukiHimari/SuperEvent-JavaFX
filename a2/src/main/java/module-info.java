module rmit.fp {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens rmit.fp to javafx.fxml;
    exports rmit.fp;
}

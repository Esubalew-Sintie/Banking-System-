module com.example.loginpage {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens com.example.bankingSystem to javafx.fxml;
    exports com.example.bankingSystem;
}
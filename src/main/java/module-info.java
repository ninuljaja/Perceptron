module com.example.ai_assignment3 {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.ai_assignment3 to javafx.fxml;
    exports com.example.ai_assignment3;
}
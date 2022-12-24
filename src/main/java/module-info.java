module com.example.quizfront {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.quizfront to javafx.fxml;
    exports com.example.quizfront;
}
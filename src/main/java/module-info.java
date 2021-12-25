module com.example.apsjfxx {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.apsjfxx to javafx.fxml;
    exports com.example.apsjfxx;
}
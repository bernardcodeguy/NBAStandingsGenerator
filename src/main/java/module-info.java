module fx.app.nbastandingsgenerator {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.net.http;
    requires com.google.gson;


    opens fx.app.nbastandingsgenerator to javafx.fxml,com.google.gson;

    exports fx.app.nbastandingsgenerator;
}
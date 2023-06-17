module com.graph.bankmanager {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens com.graph to javafx.fxml;
    exports com.graph;
    opens com.entity;
    exports com.entity;
}
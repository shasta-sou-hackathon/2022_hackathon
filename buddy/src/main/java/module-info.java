module com.buddy {
    requires transitive javafx.graphics;
    requires javafx.controls;
    requires javafx.fxml;

    opens com.buddy to javafx.fxml;
    exports com.buddy;
}

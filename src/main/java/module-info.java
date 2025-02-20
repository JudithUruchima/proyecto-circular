module ec.edu.uees.proyectocircular {
    requires javafx.controls;
    requires javafx.fxml; 
    requires javafx.media;
    requires java.sql;
    
    opens ec.edu.uees.proyectocircular to javafx.fxml;
    exports ec.edu.uees.proyectocircular;
}

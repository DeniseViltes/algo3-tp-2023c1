import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Paint;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.util.List;

public class CreadorDeAlerta {

    public void mostrarAlerta (String titulo, List<String> descripcion) {

        HBox alerta = new HBox(10);
        alerta.setAlignment(Pos.CENTER);
        alerta.setPadding(new Insets(25));

        ImageView imagen = new ImageView(new Image("otros/attention.png"));
        imagen.setFitHeight(150);
        imagen.setFitWidth(125);

        VBox contenedor = new VBox();
        Label tituloAlerta = new Label(titulo);

        tituloAlerta.setTextFill(Paint.valueOf("#e70e02ff"));
        tituloAlerta.setAlignment(Pos.CENTER);

        contenedor.getChildren().add(tituloAlerta);

        for(String i:descripcion){
            Label texto = new Label(i);
            texto.setAlignment(Pos.CENTER);
            contenedor.getChildren().add(texto);
        }

        alerta.getChildren().add(imagen);
        alerta.getChildren().add(contenedor);

        final Stage stage = new Stage();
        Scene scene = new Scene(alerta);
        stage.setScene(scene);
        stage.setResizable(false);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.showAndWait();
    }

}


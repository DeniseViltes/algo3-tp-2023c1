import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.text.TextFlow;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;

public class CreadorDeAlerta {

    @FXML
    private TextFlow descripcionAlerta;

    @FXML
    private Label tituloAlerta;

    public void mostrarAlerta (String titulo, List<String> descripcion) {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/alertas/alerta.fxml"));
            HBox view = loader.load();
            final Stage stage = new Stage();
            Scene scene = new Scene(view);
            stage.setScene(scene);
            //TODO ver porque esto se supone que es null
//            tituloAlerta.setText(titulo);
//            tituloAlerta.setTextFill(Paint.valueOf("#e70e02ff"));
//            tituloAlerta.setAlignment(Pos.CENTER);
//            var listaDeTexto = new ArrayList<Text>();
//            for(String i:descripcion){
//                Text texto = new Text(i);
//                texto.setTextAlignment(TextAlignment.CENTER);
//                listaDeTexto.add(texto);
//            }
//            descripcionAlerta.getChildren().addAll(listaDeTexto);
            stage.setResizable(false);
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.showAndWait();
        }
        catch (IOException e){
            throw new RuntimeException("error en mostrar alerta");
        }
    }

}


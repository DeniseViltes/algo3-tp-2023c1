import fechas.Mes;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.time.LocalDateTime;

public class ControladorMostrarInformacion {

    public void setearEstadoCheck(CheckBox check){
        check.setSelected(!check.isSelected());
    }

    public void mostrar_informacion(ElementoCalendario el, Node btn){
        final Stage dialog = new Stage();
        VBox dialogVbox = new VBox(10);
        dialogVbox.setAlignment(Pos.TOP_LEFT);
        dialogVbox.setPadding(new Insets(25));

        Label titulo = new Label(el.getTitulo());
        titulo.setStyle("-fx-font-size: 30;");

        CheckBox todoElDia = new CheckBox("Todo el dia");
        if(el.isEsDeDiaCompleto())
            todoElDia.setSelected(true);

        todoElDia.setOnAction(actionEvent -> setearEstadoCheck(todoElDia));

        TextArea descripcion = new TextArea(el.getDescripcion());
        descripcion.setWrapText(true);
        descripcion.setStyle("-fx-font-size: 15;-fx-background-radius: 10px; -fx-border-radius: 10px; -fx-text-fill: black; -fx-background-color: white; ");
        descripcion.setPadding(new Insets(5));
        descripcion.setEditable(false);

        dialogVbox.getChildren().add(titulo);
        LocalDateTime dia = el.getFecha();

        if(el.tieneVencimiento()){
            HBox fecha = new HBox();
            Label fechaInicio;
            if(el.isEsDeDiaCompleto()){
                fechaInicio = new Label(dia.getDayOfMonth() + " " + Mes.valueOf(dia.getMonth().toString()).getMesEspanol() +" "+ dia.getYear());
                fecha.getChildren().add(fechaInicio);
            }
            else{
                LocalDateTime diaFinal = ((Evento) el).getFechaYHoraFinal();
                fechaInicio = new Label(dia.getDayOfMonth() + " " + Mes.valueOf(dia.getMonth().toString()).getMesEspanol() +" "+ dia.getYear() + " " + dia.getHour()+":"+ String.format("%02d", dia.getMinute()));
                Label intermedia = new Label("  a  ");
                Label fechaFinal = new Label( diaFinal.getDayOfMonth() + " " + Mes.valueOf(diaFinal.getMonth().toString()).getMesEspanol() +" "+ diaFinal.getYear()+ " " + diaFinal.getHour()+":"+ String.format("%02d", diaFinal.getMinute()));

                fechaFinal.setStyle("-fx-background-radius: 10px; -fx-border-radius: 10px; -fx-text-fill: black; -fx-background-color: white; ");
                fechaFinal.setPadding(new Insets(5));
                intermedia.setPadding(new Insets(5));
                fecha.getChildren().add(fechaInicio);
                fecha.getChildren().add(intermedia);
                fecha.getChildren().add(fechaFinal);
            }


            fechaInicio.setStyle("-fx-background-radius: 10px; -fx-border-radius: 10px; -fx-text-fill: black; -fx-background-color: white; ");
            fechaInicio.setPadding(new Insets(5));
            fecha.setStyle("-fx-font-size: 15;");


            dialogVbox.getChildren().add(fecha);
            dialogVbox.getChildren().add(todoElDia);

            Label repeticion = new Label(((Evento) el).descripcionRepeticion());
            repeticion.setStyle("-fx-font-size: 15;");

            dialogVbox.getChildren().add(repeticion);
        }
        else{
            Label fecha;
            if(el.isEsDeDiaCompleto())
                fecha = new Label(dia.getDayOfMonth() + " " + Mes.valueOf(dia.getMonth().toString()).getMesEspanol() +" "+ dia.getYear());
            else
                fecha = new Label(dia.getDayOfMonth() + " " + Mes.valueOf(dia.getMonth().toString()).getMesEspanol() +" "+ dia.getYear() + " " + dia.getHour()+":"+ String.format("%02d", dia.getMinute()));

            fecha.setStyle("-fx-font-size: 15;-fx-background-radius: 10px; -fx-border-radius: 10px; -fx-text-fill: black; -fx-background-color: white; ");
            fecha.setPadding(new Insets(5));

            dialogVbox.getChildren().add(fecha);
            dialogVbox.getChildren().add(todoElDia);

            CheckBox completa = new CheckBox("No esta completa");
            if(((Tarea) el).estaCompleta()){
                completa.setSelected(true);
                completa.setText("Completa");
            }
            completa.setOnAction(actionEvent -> setearEstadoCheck(completa));

            dialogVbox.getChildren().add(completa);
            ((CheckBox) btn).setSelected(!((CheckBox) btn).isSelected());
        }

        for(Alarma alarma : el.getAlarmas()){
            Image img = new Image("Alarma.jpg");
            ImageView view = new ImageView(img);
            view.setFitHeight(20);
            view.setPreserveRatio(true);
            Label label = new Label(alarma.getEfecto().toString() + " " + alarma.getIntervalo().toMinutes() + " minutos");
            label.setGraphic(view);
            dialogVbox.getChildren().add(label);
        }

        if(!el.getDescripcion().isEmpty())
            dialogVbox.getChildren().add(descripcion);

        Scene dialogScene = new Scene(dialogVbox, 500, 300);
        dialog.setTitle(el.getTitulo());
        dialog.setScene(dialogScene);
        dialog.show();
    }
}

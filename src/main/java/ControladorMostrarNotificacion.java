import fechas.Mes;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.time.LocalDateTime;

public class ControladorMostrarNotificacion {


    public void mostrar_informacion(ElementoCalendario el){
        final Stage dialog = new Stage();
        dialog.setResizable(false);
        dialog.initModality(Modality.APPLICATION_MODAL);
        VBox dialogVbox = new VBox(10);
        dialogVbox.setAlignment(Pos.TOP_LEFT);
        dialogVbox.setPadding(new Insets(25));

        Label actividad = new Label("¡Tenes una actividad!");
        actividad.setStyle("-fx-font-size: 30;");

        dialogVbox.getChildren().add(actividad);

        Label titulo = new Label(el.getTitulo());
        titulo.setStyle("-fx-font-size: 25;");

        CheckBox todoElDia = new CheckBox("Todo el día");
        if(el.isEsDeDiaCompleto()){
            todoElDia.setSelected(true);
        }
        todoElDia.setStyle("-fx-font-size: 15;-fx-background-radius: 10px; -fx-border-radius: 10px; -fx-text-fill: black; -fx-background-color: white; ");
        todoElDia.setPadding(new Insets(5));

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

            todoElDia.setDisable(true);

            dialogVbox.getChildren().add(fecha);
            dialogVbox.getChildren().add(todoElDia);
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

            CheckBox completa = new CheckBox("Completa");
            if(((Tarea) el).estaCompleta()){
                completa.setSelected(true);
            }
            completa.setStyle("-fx-font-size: 15;-fx-background-radius: 10px; -fx-border-radius: 10px; -fx-text-fill: black; -fx-background-color: white; ");
            completa.setPadding(new Insets(5));

            completa.setDisable(true);

            dialogVbox.getChildren().add(completa);

        }

        Scene dialogScene;
        if(!el.getDescripcion().isEmpty()){
            dialogVbox.getChildren().add(descripcion);
            dialogScene = new Scene(dialogVbox, 500, 450);
        } else
            dialogScene = new Scene(dialogVbox, 500, 350);

        dialog.setTitle(el.getTitulo());
        dialog.setScene(dialogScene);
        dialog.show();
    }
}

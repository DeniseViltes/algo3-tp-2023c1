import fechas.Mes;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.time.LocalDateTime;

public class ControladorMostrarInformacion {

    private Controlador controlador;

    /*
    Muestra toda la informacion del elemento en una nueva ventana
     */
    public void mostrar_informacion(Controlador controlador, ControladorTipoDeVista controladorVista, ElementoCalendario el, Node btn){
        this.controlador = controlador;
        final Stage dialog = new Stage();
        dialog.initModality(Modality.APPLICATION_MODAL);
        VBox dialogVbox = new VBox(10);
        dialogVbox.setAlignment(Pos.TOP_LEFT);
        dialogVbox.setPadding(new Insets(25));

        Label titulo = new Label(el.getTitulo());
        titulo.setStyle("-fx-font-size: 30;");

        CheckBox todoElDia = new CheckBox("Todo el día");
        if(el.isEsDeDiaCompleto()){
            todoElDia.setSelected(true);
        }
        todoElDia.setStyle("-fx-font-size: 15;-fx-background-radius: 10px; -fx-border-radius: 10px; -fx-text-fill: black; -fx-background-color: white; ");
        todoElDia.setPadding(new Insets(5));
        todoElDia.setOnAction(actionEvent -> setearDiaCompleto(todoElDia,el));

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
            repeticion.setStyle("-fx-font-size: 15;-fx-background-radius: 10px; -fx-border-radius: 10px; -fx-text-fill: black; -fx-background-color: white; ");
            repeticion.setPadding(new Insets(5));


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

            CheckBox completa = new CheckBox("Completa");
            if(((Tarea) el).estaCompleta()){
                completa.setSelected(true);
            }
            completa.setStyle("-fx-font-size: 15;-fx-background-radius: 10px; -fx-border-radius: 10px; -fx-text-fill: black; -fx-background-color: white; ");
            completa.setPadding(new Insets(5));
            completa.setOnAction(actionEvent -> setearEstadoTarea(completa,(Tarea) el));

            dialogVbox.getChildren().add(completa);
            ((CheckBox) btn).setSelected(!((CheckBox) btn).isSelected());

        }

        for(Alarma alarma : el.getAlarmas()){
            Image img = new Image("otros/alarma.png");
            ImageView view = new ImageView(img);
            view.setFitHeight(20);
            view.setPreserveRatio(true);
            Label label = new Label(alarma.getEfecto().toString() + " " + alarma.getIntervalo().toMinutes() + " minutos");
            label.setGraphic(view);
            label.setStyle("-fx-font-size: 15;-fx-background-radius: 10px; -fx-border-radius: 10px; -fx-text-fill: black; -fx-background-color: white; ");
            label.setPadding(new Insets(5));
            dialogVbox.getChildren().add(label);
        }

        var botonEliminar = botonEliminar(el, controladorVista);
        var botonEditar = botorEditarElemento(el, controladorVista);

        HBox editarEliminar = new HBox();
        editarEliminar.getChildren().add(botonEditar);
        editarEliminar.getChildren().add(botonEliminar);
        editarEliminar.setSpacing(15);
        dialogVbox.getChildren().add(editarEliminar);
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

    private void setearEstadoTarea(CheckBox estado, Tarea tarea){
        controlador.cambiarEstadoTarea(tarea,estado.isSelected());
    }

    private void setearDiaCompleto(CheckBox estado, ElementoCalendario el){
        controlador.cambiarDiaCompleto(el,estado.isSelected());
    }
    /*
    Agrega el boton para editar el elemento
     */
    private Button botorEditarElemento(ElementoCalendario elemento, ControladorTipoDeVista controladorVista) {
        Image lapiz = new Image("otros/pencil.png");
        Button editar = new Button();
        editar.setMinWidth(25);
        editar.setMinHeight(25);
        editar.setGraphic(new ImageView(lapiz));

        editar.setOnAction(event -> {
            if(elemento.tieneVencimiento()) {
                controlador.modificarEvento((Evento) elemento);
            }
            else {
                controlador.modificarTarea((Tarea)elemento);
            }
            Stage viejo = (Stage) editar.getScene().getWindow();
            viejo.close();
            controladorVista.limpiarCalendario();
            controladorVista.actualizarCalendario(controlador.calendario);
        });

        return editar;
    }

    /*
    Agrega el boton para eliminar el elemento
     */
    private Button botonEliminar(ElementoCalendario elemento, ControladorTipoDeVista controladorVista){
        Button eliminar = new Button();
        Image bin = new Image("otros/bin.png");
        eliminar.setMinWidth(25);
        eliminar.setMinHeight(25);
        eliminar.setGraphic(new ImageView(bin));
        eliminar.setOnAction(event -> {
            controlador.eliminarElementoCalendario(elemento);
            Stage  stageViejo = (Stage) eliminar.getScene().getWindow();
            stageViejo.close();
            controladorVista.limpiarCalendario();
            controladorVista.actualizarCalendario(controlador.calendario);
        });
        return eliminar;
    }
}

package com.angel.practicahibernatenan.controller;

import com.angel.practicahibernatenan.DAO.*;
import com.angel.practicahibernatenan.model.*;
import com.angel.practicahibernatenan.util.*;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.FileChooser;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class EquiposController {

    @FXML private TextField txtNombreJugador;
    @FXML private TextField txtEdad;
    @FXML private TextField txtDorsal;
    @FXML private ComboBox<Equipo> cmbEquipos;
    @FXML private ComboBox<Partido> cmbPartidos;

    @FXML private Button btnImportarJSON;
    @FXML private Button btnLimpiar;
    @FXML private Button btnAnadir;
    @FXML private Button btnEliminarPartidoJugador;

    @FXML private Button btnGuardar;
    @FXML private Button btnModificar;
    @FXML private Button btnBorrar;
    @FXML private Button btnDeshacer;

    @FXML private TableView<Partido> tablePartidosJugador;
    @FXML private TableColumn<Partido, Integer> colIdPartidoJugador;
    @FXML private TableColumn<Partido, String> colPartidosJugador;

    @FXML private TableView<Jugador> tableJugadores;
    @FXML private TableColumn<Jugador, String> colNombre;
    @FXML private TableColumn<Jugador, Integer> colEdad;
    @FXML private TableColumn<Jugador, Integer> colDorsal;
    @FXML private TableColumn<Jugador, String> colEquipo;

    private BaloncestoDAO dao = new BaloncestoDAOImpl();
    private ObservableList<Partido> partidosEnEdicion = FXCollections.observableArrayList();
    private Jugador ultimoJugadorBorrado = null;

    @FXML
    public void initialize() {
        configurarTablas();
        cargarDatos();
        listeners();
    }

    private void configurarTablas() {
        colNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        colEdad.setCellValueFactory(new PropertyValueFactory<>("edad"));
        colDorsal.setCellValueFactory(new PropertyValueFactory<>("dorsal"));
        colEquipo.setCellValueFactory(cell ->
                new SimpleStringProperty(
                        cell.getValue().getEquipo() != null ? cell.getValue().getEquipo().getNombre() : "Sin Equipo"
                ));

        colIdPartidoJugador.setCellValueFactory(new PropertyValueFactory<>("id"));
        colPartidosJugador.setCellValueFactory(cell ->
                new SimpleStringProperty(cell.getValue().toString()));
        tablePartidosJugador.setItems(partidosEnEdicion);
    }

    private void listeners() {
        tableJugadores.getSelectionModel().selectedItemProperty().addListener((obs, oldV, newV) -> {
            if (newV != null) cargarFormulario(newV);
        });

        btnImportarJSON.setOnAction(this::importarEquiposJSON);
        btnLimpiar.setOnAction(e -> limpiar());
        btnGuardar.setOnAction(this::guardarJugador);
        btnModificar.setOnAction(this::guardarJugador);
        btnBorrar.setOnAction(this::borrarJugador);
        btnDeshacer.setOnAction(this::recuperarBorrado);

        btnAnadir.setOnAction(e -> agregarPartidoALista());
        btnEliminarPartidoJugador.setOnAction(e -> quitarPartidoDeLista());
        cmbEquipos.valueProperty().addListener((obs, oldVal, newVal) -> {
            if (oldVal != null && newVal != null && !oldVal.equals(newVal)) {
                partidosEnEdicion.clear();
            }
        });
    }

    private void cargarDatos() {
        tableJugadores.setItems(FXCollections.observableArrayList(dao.listarJugadores()));
        cmbEquipos.setItems(FXCollections.observableArrayList(dao.listarEquipos()));
        cmbPartidos.setItems(FXCollections.observableArrayList(dao.listarPartidos()));
    }

    private void cargarFormulario(Jugador j) {
        txtNombreJugador.setText(j.getNombre());
        txtEdad.setText(String.valueOf(j.getEdad()));
        txtDorsal.setText(String.valueOf(j.getDorsal()));
        cmbEquipos.setValue(j.getEquipo());
        partidosEnEdicion.setAll(j.getPartidos());
    }

    private void guardarJugador(ActionEvent event) {
        try {
            if (!Validador.esTextoValido(txtNombreJugador.getText())) throw new Exception("Nombre vacío");
            int edad = Integer.parseInt(txtEdad.getText());
            int dorsal = Integer.parseInt(txtDorsal.getText());

            if (!Validador.esEdadValida(edad)) throw new Exception("Edad inválida (16-50)");
            if (!Validador.esDorsalValido(dorsal)) throw new Exception("Dorsal inválido (0-99)");
            if (cmbEquipos.getValue() == null) throw new Exception("Selecciona un equipo");

            Jugador jugadorAGuardar;
            Button botonPulsado = (Button) event.getSource();
            Jugador seleccionado = tableJugadores.getSelectionModel().getSelectedItem();

            if (botonPulsado == btnModificar) {
                if (seleccionado == null) throw new Exception("Selecciona un jugador para modificar");
                jugadorAGuardar = seleccionado;
                jugadorAGuardar.setNombre(txtNombreJugador.getText());
                jugadorAGuardar.setEdad(edad);
                jugadorAGuardar.setDorsal(dorsal);
            } else {
                jugadorAGuardar = new Jugador(txtNombreJugador.getText(), edad, dorsal);
            }

            jugadorAGuardar.setEquipo(cmbEquipos.getValue());
            jugadorAGuardar.setPartidos(new ArrayList<>(partidosEnEdicion));

            dao.guardarJugador(jugadorAGuardar);

            AlertUtils.mostrarInformacion("Operación realizada con éxito");
            limpiar();
            cargarDatos();

        } catch (NumberFormatException e) {
            AlertUtils.mostrarError("Edad y Dorsal deben ser números");
        } catch (Exception e) {
            AlertUtils.mostrarError("Error: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void borrarJugador(ActionEvent event) {
        Jugador j = tableJugadores.getSelectionModel().getSelectedItem();
        if (j == null) {
            AlertUtils.mostrarError("Selecciona un jugador");
            return;
        }
        try {
            ultimoJugadorBorrado = j;
            dao.borrarJugador(j);
            limpiar();
            cargarDatos();
            AlertUtils.mostrarInformacion("Jugador borrado (lógico)");
        } catch (Exception e) { AlertUtils.mostrarError(e.getMessage()); }
    }

    private void recuperarBorrado(ActionEvent event) {
        if (ultimoJugadorBorrado != null) {
            try {
                ultimoJugadorBorrado.setBorrado(false);
                dao.guardarJugador(ultimoJugadorBorrado);
                ultimoJugadorBorrado = null;
                cargarDatos();
                AlertUtils.mostrarInformacion("Recuperado con éxito");
            } catch (Exception e) { AlertUtils.mostrarError(e.getMessage()); }
        } else {
            AlertUtils.mostrarInformacion("Nada que recuperar");
        }
    }

    @FXML
    private void agregarPartidoALista() {
        Partido partidoSeleccionado = cmbPartidos.getValue();
        Equipo equipoJugador = cmbEquipos.getValue();

        if (partidoSeleccionado == null) {
            AlertUtils.mostrarError("Por favor, selecciona un partido del desplegable.");
            return;
        }
        if (equipoJugador == null) {
            AlertUtils.mostrarError("Primero debes seleccionar un Equipo para el jugador.\n" +
                    "Necesito saber su equipo para validar si puede jugar este partido.");
            return;
        }

        int idEquipoJugador = equipoJugador.getId();
        int idLocal = partidoSeleccionado.getEquipoLocal() != null ? partidoSeleccionado.getEquipoLocal().getId() : -1;
        int idVisitante = partidoSeleccionado.getEquipoVisitante() != null ? partidoSeleccionado.getEquipoVisitante().getId() : -1;

        boolean participaSuEquipo = (idEquipoJugador == idLocal) || (idEquipoJugador == idVisitante);

        if (!participaSuEquipo) {
            AlertUtils.mostrarError("Error de validación:\n" +
                    "El jugador pertenece al equipo '" + equipoJugador.getNombre() + "', " +
                    "pero este partido es entre '" + partidoSeleccionado.getEquipoLocal().getNombre() +
                    "' y '" + partidoSeleccionado.getEquipoVisitante().getNombre() + "'.");
            return;
        }

        if (!partidosEnEdicion.contains(partidoSeleccionado)) {
            partidosEnEdicion.add(partidoSeleccionado);
        } else {
            AlertUtils.mostrarError("Este partido ya está añadido a la lista.");
        }
    }

    private void quitarPartidoDeLista() {
        Partido p = tablePartidosJugador.getSelectionModel().getSelectedItem();
        if (p != null) partidosEnEdicion.remove(p);
    }

    private void importarEquiposJSON(ActionEvent event) {
        FileChooser fc = new FileChooser();
        fc.getExtensionFilters().add(new FileChooser.ExtensionFilter("JSON", "*.json"));
        File f = fc.showOpenDialog(btnImportarJSON.getScene().getWindow());
        if (f != null) {
            try {
                List<Equipo> list = EquipoJSON.importar(f);
                int nuevos = 0;
                for (Equipo e : list) {
                    if (dao.buscarEquipoPorNombre(e.getNombre()) == null) {
                        dao.guardarEquipo(e);
                        nuevos++;
                    }
                }
                cargarDatos();
                AlertUtils.mostrarInformacion("Importados " + nuevos + " equipos nuevos.");
            } catch (Exception e) { AlertUtils.mostrarError("Error importando: " + e.getMessage()); }
        }
    }

    private void limpiar() {
        txtNombreJugador.clear(); txtEdad.clear(); txtDorsal.clear();
        cmbEquipos.getSelectionModel().clearSelection();
        cmbPartidos.getSelectionModel().clearSelection();
        partidosEnEdicion.clear();
        tableJugadores.getSelectionModel().clearSelection();
    }
}
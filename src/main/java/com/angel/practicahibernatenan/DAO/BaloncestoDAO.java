package com.angel.practicahibernatenan.DAO;

import com.angel.practicahibernatenan.model.*;
import java.util.List;

public interface BaloncestoDAO {
    List<Jugador> listarJugadores();
    List<Equipo> listarEquipos();
    List<Partido> listarPartidos();

    void guardarJugador(Jugador jugador) throws Exception;
    void borrarJugador(Jugador jugador) throws Exception;

    void guardarEquipo(Equipo equipo) throws Exception;
    Equipo buscarEquipoPorNombre(String nombre);
}
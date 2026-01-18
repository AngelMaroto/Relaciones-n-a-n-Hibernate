package com.angel.practicahibernatenan.model;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "Partido")
public class Partido {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idpartido")
    private int id;

    @Column(name = "fecha")
    private LocalDate fecha;

    @Column(name = "puntos_local")
    private int puntosLocal;

    @Column(name = "puntos_visitante")
    private int puntosVisitante;

    @Version
    private int version;

    @ManyToOne
    @JoinColumn(name = "equipo_local_id")
    private Equipo equipoLocal;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "equipo_visitante_id")
    private Equipo equipoVisitante;

    @ManyToMany(mappedBy = "partidos")
    private List<Jugador> jugadores;

    public Partido() {}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public int getPuntosLocal() {
        return puntosLocal;
    }

    public void setPuntosLocal(int puntosLocal) {
        this.puntosLocal = puntosLocal;
    }

    public int getPuntosVisitante() {
        return puntosVisitante;
    }

    public void setPuntosVisitante(int puntosVisitante) {
        this.puntosVisitante = puntosVisitante;
    }

    public Equipo getEquipoLocal() {
        return equipoLocal;
    }

    public void setEquipoLocal(Equipo equipoLocal) {
        this.equipoLocal = equipoLocal;
    }

    public Equipo getEquipoVisitante() {
        return equipoVisitante;
    }

    public void setEquipoVisitante(Equipo equipoVisitante) {
        this.equipoVisitante = equipoVisitante;
    }

    @Override
    public String toString() {
        String local = (equipoLocal != null) ? equipoLocal.getNombre() : "Local";
        String visit = (equipoVisitante != null) ? equipoVisitante.getNombre() : "Visitante";

        return String.format("%s vs %s [%d-%d] (%s)",
                local, visit, puntosLocal, puntosVisitante, fecha.toString());
    }
}
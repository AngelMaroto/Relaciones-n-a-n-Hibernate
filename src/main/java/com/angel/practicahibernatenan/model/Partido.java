package com.angel.practicahibernatenan.model;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "Partido")
public class Partido {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idpartido")
    private int id;

    @Temporal(TemporalType.DATE)
    private Date fecha;

    // --- NUEVOS CAMPOS MAPEADOS ---
    @Column(name = "puntos_local")
    private int puntosLocal;

    @Column(name = "puntos_visitante")
    private int puntosVisitante;

    @Version
    private int version;

    // Relación con el Equipo Local (Para saber su nombre)
    @ManyToOne(fetch = FetchType.EAGER) // Eager para que cargue el nombre al momento
    @JoinColumn(name = "equipo_local_id")
    private Equipo equipoLocal;

    // Relación con el Equipo Visitante
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "equipo_visitante_id")
    private Equipo equipoVisitante;

    @ManyToMany(mappedBy = "partidos")
    private List<Jugador> jugadores;

    public Partido() {}

    // --- GETTERS Y SETTERS ---
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public Date getFecha() { return fecha; }
    public void setFecha(Date fecha) { this.fecha = fecha; }

    public int getPuntosLocal() { return puntosLocal; }
    public void setPuntosLocal(int puntosLocal) { this.puntosLocal = puntosLocal; }

    public int getPuntosVisitante() { return puntosVisitante; }
    public void setPuntosVisitante(int puntosVisitante) { this.puntosVisitante = puntosVisitante; }

    public Equipo getEquipoLocal() { return equipoLocal; }
    public void setEquipoLocal(Equipo equipoLocal) { this.equipoLocal = equipoLocal; }

    public Equipo getEquipoVisitante() { return equipoVisitante; }
    public void setEquipoVisitante(Equipo equipoVisitante) { this.equipoVisitante = equipoVisitante; }

    // --- EL MÉTODO MÁGICO QUE ACTUALIZA LA TABLA ---
    @Override
    public String toString() {
        String local = (equipoLocal != null) ? equipoLocal.getNombre() : "Local";
        String visit = (equipoVisitante != null) ? equipoVisitante.getNombre() : "Visitante";

        // Formato: "Lakers vs Bulls [88-82] (2025-11-04)"
        return String.format("%s vs %s [%d-%d] (%s)",
                local, visit, puntosLocal, puntosVisitante, fecha.toString());
    }
}
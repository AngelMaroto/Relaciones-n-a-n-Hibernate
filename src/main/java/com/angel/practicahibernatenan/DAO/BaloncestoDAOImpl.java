package com.angel.practicahibernatenan.DAO;

import com.angel.practicahibernatenan.model.*;
import com.angel.practicahibernatenan.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;
import java.util.List;

public class BaloncestoDAOImpl implements BaloncestoDAO {

    @Override
    public List<Jugador> listarJugadores() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            String hql = "SELECT DISTINCT j FROM Jugador j LEFT JOIN FETCH j.equipo WHERE j.borrado = false";
            return session.createQuery(hql, Jugador.class).list();
        }
    }

    @Override
    public List<Equipo> listarEquipos() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery("FROM Equipo", Equipo.class).list();
        }
    }

    @Override
    public List<Partido> listarPartidos() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            String hql = "SELECT DISTINCT p FROM Partido p " +
                    "LEFT JOIN FETCH p.equipoLocal " +
                    "LEFT JOIN FETCH p.equipoVisitante";
            return session.createQuery(hql, Partido.class).list();
        }
    }

    @Override
    public void guardarJugador(Jugador jugador) throws Exception {
        Transaction tx = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            tx = session.beginTransaction();
            session.saveOrUpdate(jugador);
            tx.commit();
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            throw e;
        }
    }

    @Override
    public void borrarJugador(Jugador jugador) throws Exception {
        Transaction tx = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            tx = session.beginTransaction();
            jugador.setBorrado(true);
            session.update(jugador);
            tx.commit();
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            throw e;
        }
    }

    @Override
    public void guardarEquipo(Equipo equipo) throws Exception {
        Transaction tx = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            tx = session.beginTransaction();
            session.saveOrUpdate(equipo);
            tx.commit();
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            throw e;
        }
    }

    @Override
    public Equipo buscarEquipoPorNombre(String nombre) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery("FROM Equipo WHERE nombre = :nom", Equipo.class)
                    .setParameter("nom", nombre)
                    .uniqueResult();
        } catch (Exception e) { return null; }
    }
}
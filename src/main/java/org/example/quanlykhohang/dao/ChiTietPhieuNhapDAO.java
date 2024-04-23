/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.example.quanlykhohang.dao;
import jakarta.persistence.*;

import jakarta.persistence.EntityManager;
import java.util.List;
import org.example.quanlykhohang.entity.ChiTietPhieuNhap;
import org.example.quanlykhohang.util.JpaUtils;
/**
 *
 * @author pc
 */
public class ChiTietPhieuNhapDAO implements InterfaceDAO<ChiTietPhieuNhap, Integer> {

    @Override
    public void create(ChiTietPhieuNhap chiTietPhieuNhap) {
        EntityManager em = JpaUtils.getEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(chiTietPhieuNhap);
            em.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
            em.getTransaction().rollback();
            throw e;
        } finally {
            em.close();
        }    
    }

    @Override
    public void update(ChiTietPhieuNhap chiTietPhieuNhap) {
        EntityManager em = JpaUtils.getEntityManager();
        try {
            em.getTransaction().begin();
            em.merge(chiTietPhieuNhap);
            em.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
            em.getTransaction().rollback();
            throw e;
        } finally {
            em.close();
        }    
    }

    @Override
    public void delete(Integer id) {
        EntityManager em = JpaUtils.getEntityManager();
        try {
            em.getTransaction().begin();
            ChiTietPhieuNhap chiTietPhieuNhap = em.find(ChiTietPhieuNhap.class, id);
            em.remove(chiTietPhieuNhap);
            em.getTransaction().commit();
        } catch (Exception e) {
            em.getTransaction().rollback();
            e.printStackTrace();
        } finally {
            em.close();
        }    
    }

    @Override
    public ChiTietPhieuNhap findById(Integer id) {
        EntityManager em = JpaUtils.getEntityManager();
        ChiTietPhieuNhap entity = em.find(ChiTietPhieuNhap.class, id);
        em.close();
        return entity;    
    }

    @Override
    public long count() {
        EntityManager em = JpaUtils.getEntityManager();
        String japl = "select count(u) from ChiTietPhieuNhap u";
        Query query = em.createQuery(japl);
        em.close();
        return ((Long) query.getSingleResult()).intValue();    
    }

    @Override
    public List<ChiTietPhieuNhap> findAll() {
        EntityManager em = JpaUtils.getEntityManager();
        String japl = "SELECT u FROM ChiTietPhieuNhap u order by u.id";
        TypedQuery<ChiTietPhieuNhap> query = em.createQuery(japl, ChiTietPhieuNhap.class);
        em.close();
        return query.getResultList();    
    }

    @Override
    public boolean existsById(Integer id) {
        EntityManager em = JpaUtils.getEntityManager();
        String jql = "SELECT COUNT(u.id) FROM ChiTietPhieuNhap u WHERE u.id = :id";
        TypedQuery<Long> query = em.createQuery(jql, Long.class);
        query.setParameter("id", id);
        Long count = query.getSingleResult();
        em.close();
        return count == 1;    
    }
    
}

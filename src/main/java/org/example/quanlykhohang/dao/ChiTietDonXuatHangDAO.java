/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.example.quanlykhohang.dao;
import jakarta.persistence.*;

import jakarta.persistence.EntityManager;
import java.util.List;
import org.example.quanlykhohang.util.JpaUtils;
import org.example.quanlykhohang.entity.ChiTietDonXuatHang;

/**
 *
 * @author pc
 */
public class ChiTietDonXuatHangDAO implements InterfaceDAO<ChiTietDonXuatHang, Integer> {

    @Override
    public void create(ChiTietDonXuatHang chiTietDonXuatHang) {
        EntityManager em = JpaUtils.getEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(chiTietDonXuatHang);
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
    public void update(ChiTietDonXuatHang chiTietDonXuatHang) {
        EntityManager em = JpaUtils.getEntityManager();
        try {
            em.getTransaction().begin();
            em.merge(chiTietDonXuatHang);
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
            ChiTietDonXuatHang chiTietDonXuatHang = em.find(ChiTietDonXuatHang.class, id);
            em.remove(chiTietDonXuatHang);
            em.getTransaction().commit();
        } catch (Exception e) {
            em.getTransaction().rollback();
            e.printStackTrace();
        } finally {
            em.close();
        }    
    }

    @Override
    public ChiTietDonXuatHang findById(Integer id) {
        EntityManager em = JpaUtils.getEntityManager();
        ChiTietDonXuatHang entity = em.find(ChiTietDonXuatHang.class, id);
        em.close();
        return entity;    
    }

    @Override
    public long count() {
        EntityManager em = JpaUtils.getEntityManager();
        String japl = "select count(u) from ChiTietDonXuat u";
        Query query = em.createQuery(japl);
        em.close();
        return ((Long) query.getSingleResult()).intValue();    
    }

    @Override
    public List<ChiTietDonXuatHang> findAll() {
        EntityManager em = JpaUtils.getEntityManager();
        String japl = "SELECT u FROM ChiTietDonXuat u order by u.id";
        TypedQuery<ChiTietDonXuatHang> query = em.createQuery(japl, ChiTietDonXuatHang.class);
        em.close();
        return query.getResultList();    
    }

    @Override
    public boolean existsById(Integer id) {
        EntityManager em = JpaUtils.getEntityManager();
        String jql = "SELECT COUNT(u.id) FROM ChiTietDonXuat u WHERE u.id = :id";
        TypedQuery<Long> query = em.createQuery(jql, Long.class);
        query.setParameter("id", id);
        Long count = query.getSingleResult();
        em.close();
        return count == 1;    
    }
    
}

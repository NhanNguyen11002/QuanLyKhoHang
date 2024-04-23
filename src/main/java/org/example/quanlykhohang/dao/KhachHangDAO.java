/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.example.quanlykhohang.dao;

import jakarta.persistence.*;

import jakarta.persistence.EntityManager;
import java.util.List;
import org.example.quanlykhohang.util.JpaUtils;
import org.example.quanlykhohang.entity.KhachHang;

/**
 *
 * @author pc
 */
public class KhachHangDAO implements InterfaceDAO<KhachHang, Integer> {

    @Override
    public void create(KhachHang khachHang) {
        EntityManager em = JpaUtils.getEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(khachHang);
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
    public void update(KhachHang khachHang) {
        EntityManager em = JpaUtils.getEntityManager();
        try {
            em.getTransaction().begin();
            em.merge(khachHang);
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
    public void delete(Integer maKhachHang) {
        EntityManager em = JpaUtils.getEntityManager();
        try {
            em.getTransaction().begin();
            KhachHang khachHang = em.find(KhachHang.class, maKhachHang);
            em.remove(khachHang);
            em.getTransaction().commit();
        } catch (Exception e) {
            em.getTransaction().rollback();
            e.printStackTrace();
        } finally {
            em.close();
        }    
    }

    @Override
    public KhachHang findById(Integer maKhachHang) {
        EntityManager em = JpaUtils.getEntityManager();
        KhachHang entity = em.find(KhachHang.class, maKhachHang);
        em.close();
        return entity;    
    }

    @Override
    public long count() {
        EntityManager em = JpaUtils.getEntityManager();
        String japl = "select count(u) from KhachHang u";
        Query query = em.createQuery(japl);
        em.close();
        return ((Long) query.getSingleResult()).intValue();    
    }

    @Override
    public List<KhachHang> findAll() {
        EntityManager em = JpaUtils.getEntityManager();
        String japl = "SELECT u FROM KhachHang u order by u.maKhachHang";
        TypedQuery<KhachHang> query = em.createQuery(japl, KhachHang.class);
        em.close();
        return query.getResultList();    
    }

    @Override
    public boolean existsById(Integer maKhachHang) {
        EntityManager em = JpaUtils.getEntityManager();
        String jql = "SELECT COUNT(u.maKhachHang) FROM KhachHang u WHERE u.maKhachHang = :maKhachHang";
        TypedQuery<Long> query = em.createQuery(jql, Long.class);
        query.setParameter("maKhachHang", maKhachHang);
        Long count = query.getSingleResult();
        em.close();
        return count == 1;    
    }
    
}

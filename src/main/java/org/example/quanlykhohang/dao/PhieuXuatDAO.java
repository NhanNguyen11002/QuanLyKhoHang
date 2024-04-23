/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.example.quanlykhohang.dao;

import org.example.quanlykhohang.entity.PhieuXuat;
import jakarta.persistence.*;

import jakarta.persistence.EntityManager;
import java.util.List;
import org.example.quanlykhohang.util.JpaUtils;
/**
 *
 * @author pc
 */
public class PhieuXuatDAO implements InterfaceDAO<PhieuXuat, String> {

    @Override
    public void create(PhieuXuat phieuXuat) {
        EntityManager em = JpaUtils.getEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(phieuXuat);
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
    public void update(PhieuXuat phieuXuat) {
        EntityManager em = JpaUtils.getEntityManager();
        try {
            em.getTransaction().begin();
            em.merge(phieuXuat);
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
    public void delete(String maPhieu) {
        EntityManager em = JpaUtils.getEntityManager();
        try {
            em.getTransaction().begin();
            PhieuXuat phieuXuat = em.find(PhieuXuat.class, maPhieu);
            em.remove(phieuXuat);
            em.getTransaction().commit();
        } catch (Exception e) {
            em.getTransaction().rollback();
            e.printStackTrace();
        } finally {
            em.close();
        }    
    }

    @Override
    public PhieuXuat findById(String maPhieu) {
        EntityManager em = JpaUtils.getEntityManager();
        PhieuXuat entity = em.find(PhieuXuat.class, maPhieu);
        em.close();
        return entity;    
    }

    @Override
    public long count() {
        EntityManager em = JpaUtils.getEntityManager();
        String japl = "select count(u) from PhieuXuat u";
        Query query = em.createQuery(japl);
        em.close();
        return ((Long) query.getSingleResult()).intValue();    
    }

    @Override
    public List<PhieuXuat> findAll() {
        EntityManager em = JpaUtils.getEntityManager();
        String japl = "SELECT u FROM PhieuXuat u order by u.maPhieu";
        TypedQuery<PhieuXuat> query = em.createQuery(japl, PhieuXuat.class);
        em.close();
        return query.getResultList();    
    }

    @Override
    public boolean existsById(String maPhieu) {
        EntityManager em = JpaUtils.getEntityManager();
        String jql = "SELECT COUNT(u.maPhieu) FROM PhieuXuat u WHERE u.maPhieu = :maPhieu";
        TypedQuery<Long> query = em.createQuery(jql, Long.class);
        query.setParameter("maPhieu", maPhieu);
        Long count = query.getSingleResult();
        em.close();
        return count == 1;    
    }
    
}

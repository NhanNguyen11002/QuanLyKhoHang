/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.example.quanlykhohang.dao;
import jakarta.persistence.*;

import jakarta.persistence.EntityManager;
import java.util.List;
import org.example.quanlykhohang.entity.NhaCungCap;
import org.example.quanlykhohang.util.JpaUtils;

/**
 *
 * @author pc
 */
public class NhaCungCapDAO implements InterfaceDAO<NhaCungCap, Integer> {

    @Override
    public void create(NhaCungCap nhaCungCap) {
        EntityManager em = JpaUtils.getEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(nhaCungCap);
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
    public void update(NhaCungCap nhaCungCap) {
        EntityManager em = JpaUtils.getEntityManager();
        try {
            em.getTransaction().begin();
            em.merge(nhaCungCap);
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
    public void delete(Integer maNhaCungCap) {
        EntityManager em = JpaUtils.getEntityManager();
        try {
            em.getTransaction().begin();
            NhaCungCap nhaCungCap = em.find(NhaCungCap.class, maNhaCungCap);
            em.remove(nhaCungCap);
            em.getTransaction().commit();
        } catch (Exception e) {
            em.getTransaction().rollback();
            e.printStackTrace();
        } finally {
            em.close();
        }    
    }

    @Override
    public NhaCungCap findById(Integer maNhaCungCap) {
        EntityManager em = JpaUtils.getEntityManager();
        NhaCungCap entity = em.find(NhaCungCap.class, maNhaCungCap);
        em.close();
        return entity;    
    }

    @Override
    public long count() {
        EntityManager em = JpaUtils.getEntityManager();
        String japl = "select count(u) from NhaCungCap u";
        Query query = em.createQuery(japl);
        em.close();
        return ((Long) query.getSingleResult()).intValue();    
    }

    @Override
    public List<NhaCungCap> findAll() {
        EntityManager em = JpaUtils.getEntityManager();
        String japl = "SELECT u FROM NhaCungCap u order by u.maNhaCungCap";
        TypedQuery<NhaCungCap> query = em.createQuery(japl, NhaCungCap.class);
        em.close();
        return query.getResultList();    
    }

    @Override
    public boolean existsById(Integer maNhaCungCap) {
        EntityManager em = JpaUtils.getEntityManager();
        String jql = "SELECT COUNT(u.maNhaCungCap) FROM NhaCungCap u WHERE u.maNhaCungCap = :maNhaCungCap";
        TypedQuery<Long> query = em.createQuery(jql, Long.class);
        query.setParameter("maNhaCungCap", maNhaCungCap);
        Long count = query.getSingleResult();
        em.close();
        return count == 1;    
    }
    
}

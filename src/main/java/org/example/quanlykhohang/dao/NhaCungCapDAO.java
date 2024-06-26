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
            throw e;
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
        try{
            String japl = "select count(u) from NhaCungCap u";
            Query query = em.createQuery(japl);
            return ((Long) query.getSingleResult()).intValue();
        }  catch (Exception e){
            e.printStackTrace();
            throw e;
        } finally {
            em.close();
        }
    }

    @Override
    public List<NhaCungCap> findAll() {
        EntityManager em = JpaUtils.getEntityManager();
        try{
            String japl = "SELECT u FROM NhaCungCap u order by u.maNhaCungCap";
            TypedQuery<NhaCungCap> query = em.createQuery(japl, NhaCungCap.class);
            return query.getResultList();
        } catch (Exception e){
            throw e;
        } finally {
            em.close();
        }
    }

    @Override
    public boolean existsById(Integer maNhaCungCap) {
        EntityManager em = JpaUtils.getEntityManager();
        try{

            String jql = "SELECT COUNT(u.maNhaCungCap) FROM NhaCungCap u WHERE u.maNhaCungCap = :maNhaCungCap";
            TypedQuery<Long> query = em.createQuery(jql, Long.class);
            query.setParameter("maNhaCungCap", maNhaCungCap);
            Long count = query.getSingleResult();
            em.close();
            return count == 1;
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        } finally {
            em.close();
        }

    }
    public boolean existByEmail(String email) {
        EntityManager em = JpaUtils.getEntityManager();
        try{

            String jql = "SELECT COUNT(u.email) FROM NhaCungCap u WHERE u.email = :email";
            TypedQuery<Long> query = em.createQuery(jql, Long.class);
            query.setParameter("email", email);
            Long count = query.getSingleResult();
            return count == 1;
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        } finally {
            em.close();
        }

    }
    public boolean existBySDT(String sdt) {
        EntityManager em = JpaUtils.getEntityManager();
        try{

            String jql = "SELECT COUNT(u.sdt) FROM NhaCungCap u WHERE u.sdt = :sdt";
            TypedQuery<Long> query = em.createQuery(jql, Long.class);
            query.setParameter("sdt", sdt);
            Long count = query.getSingleResult();
            return count == 1;
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        } finally {
            em.close();
        }
    }
    public boolean existByTen(String ten) {
        EntityManager em = JpaUtils.getEntityManager();
        try{

            String jql = "SELECT COUNT(u.tenNhaCungCap) FROM NhaCungCap u WHERE u.tenNhaCungCap = :ten";
            TypedQuery<Long> query = em.createQuery(jql, Long.class);
            query.setParameter("ten", ten);
            Long count = query.getSingleResult();
            return count == 1;
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        } finally {
            em.close();
        }
    }
    
    public NhaCungCap findByName(String name) {
    	EntityManager em = JpaUtils.getEntityManager();
        try{
            String japl = "SELECT u FROM NhaCungCap u WHERE u.tenNhaCungCap LIKE :name ";
            TypedQuery<NhaCungCap> query = em.createQuery(japl, NhaCungCap.class);
            query.setParameter("name", "%" + name + "%");
            return query.getSingleResult();
        } catch (Exception e){
            throw e;
        } finally {
            em.close();
        }
    }
    
    public List<NhaCungCap> findByKeyword(String keyword) {
        EntityManager em = JpaUtils.getEntityManager();
        try{
            String japl = "SELECT u FROM NhaCungCap u WHERE " +
                    "u.tenNhaCungCap LIKE :keyword " +
                    "OR u.sdt LIKE :keyword " +
                    "OR u.diaChi LIKE :keyword " +
                    "OR u.email LIKE :keyword " +
                    "ORDER BY CASE " +
                    "WHEN u.tenNhaCungCap LIKE :keyword THEN 1 " +
                    "WHEN u.sdt LIKE :keyword THEN 2 " +
                    "WHEN u.diaChi LIKE :keyword THEN 3 " +
                    "ELSE 4 " +
                    "END";
            TypedQuery<NhaCungCap> query = em.createQuery(japl, NhaCungCap.class);
            query.setParameter("keyword", "%" + keyword + "%");
            return query.getResultList();
        } catch (Exception e){
            throw e;
        } finally {
            em.close();
        }
    }
    
}

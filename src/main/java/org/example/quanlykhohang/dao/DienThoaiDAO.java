/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.example.quanlykhohang.dao;

import jakarta.persistence.*;

import java.util.List;
import org.example.quanlykhohang.entity.DienThoai;
import org.example.quanlykhohang.entity.NhaCungCap;
import org.example.quanlykhohang.util.JpaUtils;

/**
 *
 * @author pc
 */
public class DienThoaiDAO implements InterfaceDAO<DienThoai, String> {

    @Override
    public void create(DienThoai dienThoai) {
        EntityManager em = JpaUtils.getEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(dienThoai);
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
    public void update(DienThoai dienThoai) {
        EntityManager em = JpaUtils.getEntityManager();
        try {
            em.getTransaction().begin();
            em.merge(dienThoai);
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
    public void delete(String maDT) {
        EntityManager em = JpaUtils.getEntityManager();
        try {
            em.getTransaction().begin();
            DienThoai dienThoai = em.find(DienThoai.class, maDT);
            em.remove(dienThoai);
            em.getTransaction().commit();
        } catch (Exception e) {
            em.getTransaction().rollback();
            e.printStackTrace();
        } finally {
            em.close();
        }    
    }

    @Override
    public DienThoai findById(String maDT) {
        EntityManager em = JpaUtils.getEntityManager();
        DienThoai entity = em.find(DienThoai.class, maDT);
        em.close();
        return entity;    
    }

    @Override
    public long count() {
        EntityManager em = JpaUtils.getEntityManager();
        try{
            String japl = "select count(u) from DienThoai u";
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
    public List<DienThoai> findAll() {
        EntityManager em = JpaUtils.getEntityManager();
        String japl = "SELECT u FROM DienThoai u order by u.maDT";
        TypedQuery<DienThoai> query = em.createQuery(japl, DienThoai.class);
        List<DienThoai> resultList = query.getResultList();
        em.close();
        return resultList;    
    }

    @Override
    public boolean existsById(String maDT) {
        EntityManager em = JpaUtils.getEntityManager();
        String jql = "SELECT COUNT(u.maDT) FROM DienThoai u WHERE u.maDT = :maDT";
        TypedQuery<Long> query = em.createQuery(jql, Long.class);
        query.setParameter("maDT", maDT);
        Long count = query.getSingleResult();
        em.close();
        return count == 1;    
    }
    
    public List<DienThoai> findByKeyword(String keyword) {
        EntityManager em = JpaUtils.getEntityManager();
        try{
            String japl = "SELECT u FROM DienThoai u WHERE " +
                    "u.maDT LIKE :keyword " +
                    "OR u.tenDT LIKE :keyword " +
                    "OR u.giaNhap = :giaNhap " +
                    "OR u.giaXuat = :giaXuat " +
                    "ORDER BY CASE " +
                    "WHEN u.maDT LIKE :keyword THEN 1 " +
                    "WHEN u.tenDT LIKE :keyword THEN 2 " +
                    "WHEN u.giaNhap = :giaNhap THEN 3 " +
                    "ELSE 4 " +
                    "END";
            TypedQuery<DienThoai> query = em.createQuery(japl, DienThoai.class);
            query.setParameter("keyword", "%" + keyword + "%");
            try {
                double doubleValue = Double.parseDouble(keyword);
                query.setParameter("giaNhap", doubleValue); 
                query.setParameter("giaXuat", doubleValue);
            } catch (NumberFormatException e) {
                query.setParameter("giaNhap", null);
                query.setParameter("giaXuat", null);
            }
            return query.getResultList();
        } catch (Exception e){
            throw e;
        } finally {
            em.close();
        }
    }
}

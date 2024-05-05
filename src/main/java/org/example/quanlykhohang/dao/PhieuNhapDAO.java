/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.example.quanlykhohang.dao;
import jakarta.persistence.*;

import java.util.List;

import org.example.quanlykhohang.entity.DienThoai;
import org.example.quanlykhohang.entity.PhieuNhap;
import org.example.quanlykhohang.util.JpaUtils;

/**
 *
 * @author pc
 */
public class PhieuNhapDAO implements InterfaceDAO<PhieuNhap, String> {

    @Override
    public void create(PhieuNhap phieuNhap) {
        EntityManager em = JpaUtils.getEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(phieuNhap);
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
    public void update(PhieuNhap phieuNhap) {
        EntityManager em = JpaUtils.getEntityManager();
        try {
            em.getTransaction().begin();
            em.merge(phieuNhap);
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
            PhieuNhap phieuNhap = em.find(PhieuNhap.class, maPhieu);
            em.remove(phieuNhap);
            em.getTransaction().commit();
        } catch (Exception e) {
            em.getTransaction().rollback();
            e.printStackTrace();
        } finally {
            em.close();
        }    
    }

    @Override
    public PhieuNhap findById(String maPhieu) {
        EntityManager em = JpaUtils.getEntityManager();
        PhieuNhap entity = em.find(PhieuNhap.class, maPhieu);
        em.close();
        return entity;    
    }

    @Override
    public long count() {
        EntityManager em = JpaUtils.getEntityManager();
        String japl = "select count(u) from PhieuNhap u";
        Query query = em.createQuery(japl);
        em.close();
        return ((Long) query.getSingleResult()).intValue();    
    }

    @Override
    public List<PhieuNhap> findAll() {
        EntityManager em = JpaUtils.getEntityManager();
        String japl = "SELECT u FROM PhieuNhap u order by u.maPhieu";
        TypedQuery<PhieuNhap> query = em.createQuery(japl, PhieuNhap.class);
        List<PhieuNhap> resultList = query.getResultList();
        em.close();
        return resultList;    
    }
    
    public List<PhieuNhap> findByKeyword(String keyword) {
        EntityManager em = JpaUtils.getEntityManager();
        try{
            String japl = "SELECT u FROM PhieuNhap u " +
            		"JOIN u.nhaCungCap m " +
                    "WHERE u.maPhieu LIKE :keyword " +
                    "OR m.tenNhaCungCap LIKE :keyword " +
                    "OR u.tongTien = :tongTien " +
                    "ORDER BY CASE " +
                    "WHEN u.maPhieu LIKE :keyword THEN 1 " +
                    "WHEN m.tenNhaCungCap LIKE :keyword THEN 2 " +
                    "WHEN u.tongTien = :tongTien THEN 3 " +
                    "ELSE 4 " +
                    "END";
            TypedQuery<PhieuNhap> query = em.createQuery(japl, PhieuNhap.class);
            query.setParameter("keyword", "%" + keyword + "%");
            try {
                double doubleValue = Double.parseDouble(keyword);
                query.setParameter("tongTien", doubleValue); 
            } catch (NumberFormatException e) {
                query.setParameter("tongTien", null);
            }
            return query.getResultList();
        } catch (Exception e){
            throw e;
        } finally {
            em.close();
        }
    }

    @Override
    public boolean existsById(String maPhieu) {
        EntityManager em = JpaUtils.getEntityManager();
        String jql = "SELECT COUNT(u.maPhieu) FROM PhieuNhap u WHERE u.maPhieu = :maPhieu";
        TypedQuery<Long> query = em.createQuery(jql, Long.class);
        query.setParameter("maPhieu", maPhieu);
        Long count = query.getSingleResult();
        em.close();
        return count == 1;    
    }
    
}

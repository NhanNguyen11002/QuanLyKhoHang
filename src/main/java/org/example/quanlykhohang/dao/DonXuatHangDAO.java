/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.example.quanlykhohang.dao;
import jakarta.persistence.*;

import jakarta.persistence.EntityManager;
import java.util.List;
import org.example.quanlykhohang.entity.DonXuatHang;
import org.example.quanlykhohang.entity.KhachHang;
import org.example.quanlykhohang.util.JpaUtils;

/**
 *
 * @author pc
 */
public class DonXuatHangDAO implements InterfaceDAO<DonXuatHang, String> {

    @Override
    public void create(DonXuatHang donXuatHang) {
        EntityManager em = JpaUtils.getEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(donXuatHang);
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
    public void update(DonXuatHang donXuatHang) {
        EntityManager em = JpaUtils.getEntityManager();
        try {
            em.getTransaction().begin();
            em.merge(donXuatHang);
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
    public void delete(String maDon) {
        EntityManager em = JpaUtils.getEntityManager();
        try {
            em.getTransaction().begin();
            DonXuatHang donXuatHang = em.find(DonXuatHang.class, maDon);
            em.remove(donXuatHang);
            em.getTransaction().commit();
        } catch (Exception e) {
            em.getTransaction().rollback();
            e.printStackTrace();
        } finally {
            em.close();
        }    
    }

    @Override
    public DonXuatHang findById(String maDon) {
        EntityManager em = JpaUtils.getEntityManager();
        DonXuatHang entity = em.find(DonXuatHang.class, maDon);
        em.close();
        return entity;    
    }

    @Override
    public long count() {
        EntityManager em = JpaUtils.getEntityManager();
        String japl = "select count(u) from DonXuatHang u";
        Query query = em.createQuery(japl);
        em.close();
        return ((Long) query.getSingleResult()).intValue();    
    }

    @Override
    public List<DonXuatHang> findAll() {
        EntityManager em = JpaUtils.getEntityManager();
        try{
            String japl = "SELECT u FROM DonXuatHang u join fetch u.chiTietDonXuatHangList order by u.maDon";
            TypedQuery<DonXuatHang> query = em.createQuery(japl, DonXuatHang.class);
            return query.getResultList();
        } catch (Exception e){
            throw e;
        } finally {
            em.close();
        }
    }

    @Override
    public boolean existsById(String maDon) {
        EntityManager em = JpaUtils.getEntityManager();
        String jql = "SELECT COUNT(u.maDon) FROM DonXuatHang u WHERE u.maDon = :maDon";
        TypedQuery<Long> query = em.createQuery(jql, Long.class);
        query.setParameter("maDon", maDon);
        Long count = query.getSingleResult();
        em.close();
        return count == 1;    
    }

    public List<DonXuatHang> searchByKeyword(String keyword, String status){
        EntityManager em = JpaUtils.getEntityManager();
        try{
            String jpql = "SELECT u FROM DonXuatHang u join fetch u.chiTietDonXuatHangList WHERE " +
                    "(u.khachHang.tenKhachHang LIKE :keyword " +
                    "OR u.maDon LIKE :keyword " +
                    "OR u.nhanVien.ten LIKE :keyword " +
                    "OR u.nhanVien.ho LIKE :keyword) " +
                    "AND (:status = '0' OR u.trangThai = :status) " +
                    "ORDER BY CASE " +
                    "WHEN u.maDon LIKE :keyword THEN 1 " +
                    "WHEN u.khachHang.tenKhachHang LIKE :keyword THEN 2 " +
                    "WHEN u.nhanVien.ten LIKE :keyword THEN 3 " +
                    "WHEN u.nhanVien.ho LIKE :keyword THEN 4 " +
                    "ELSE 4 " +
                    "END";
            TypedQuery<DonXuatHang> query = em.createQuery(jpql, DonXuatHang.class);
            query.setParameter("keyword", "%" + keyword + "%");
            String cvt = status.equals("all")?"0":status;
            System.out.println("stt "+ cvt );
            query.setParameter("status",status.equals("all")?"0":status);
            return query.getResultList();
        } catch (Exception e){
            throw e;
        } finally {
            em.close();
        }
    }
}

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.example.quanlykhohang.dao;

import org.example.quanlykhohang.entity.DonXuatHang;
import org.example.quanlykhohang.entity.PhieuStatus;
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
        try{
            String jpql = "SELECT u FROM PhieuXuat u join fetch u.donXuatHang don join fetch don.chiTietDonXuatHangList order by u.maPhieu";
            TypedQuery<PhieuXuat> query = em.createQuery(jpql, PhieuXuat.class);
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
        String jql = "SELECT COUNT(u.maPhieu) FROM PhieuXuat u WHERE u.maPhieu = :maPhieu";
        TypedQuery<Long> query = em.createQuery(jql, Long.class);
        query.setParameter("maPhieu", maPhieu);
        Long count = query.getSingleResult();
        em.close();
        return count == 1;    
    }
    public List<PhieuXuat> searchByKeyword(String keyword, String status){
        EntityManager em = JpaUtils.getEntityManager();
        try{
            String jpql = "SELECT px FROM PhieuXuat px " +
                    "JOIN FETCH px.donXuatHang don " +
                    "JOIN FETCH don.chiTietDonXuatHangList chiTiet " +
                    "WHERE (don.khachHang.tenKhachHang LIKE :keyword " +
                    "OR px.maPhieu LIKE :keyword " +
                    "OR don.maDon LIKE :keyword " +
                    "OR px.bienSoXe LIKE :keyword " +
                    "OR px.nguoiTao.ten LIKE :keyword " +
                    "OR px.nguoiTao.ho LIKE :keyword) " +
                    "AND (:status = '0' OR px.status = :status) " +
                    "ORDER BY CASE " +
                    "WHEN px.maPhieu LIKE :keyword THEN 1 " +
                    "WHEN px.bienSoXe LIKE :keyword THEN 2 " +
                    "WHEN don.khachHang.tenKhachHang LIKE :keyword THEN 3 " +
                    "WHEN px.nguoiTao.ten LIKE :keyword THEN 4 " +
                    "WHEN px.nguoiTao.ho LIKE :keyword THEN 5 " +
                    "ELSE 6 " +
                    "END";
            TypedQuery<PhieuXuat> query = em.createQuery(jpql, PhieuXuat.class);
            query.setParameter("keyword", "%" + keyword + "%");
            PhieuStatus cvt = status.equals("done")?PhieuStatus.Done:PhieuStatus.Deleted;
            query.setParameter("status",status.equals("all")?"0":cvt);
            return query.getResultList();
        } catch (Exception e){
            throw e;
        } finally {
            em.close();
        }
    }
    public List<PhieuXuat> searchByKeyword(String keyword){
        EntityManager em = JpaUtils.getEntityManager();
        try{
            String jpql = "SELECT px FROM PhieuXuat px " +
                    "JOIN FETCH px.donXuatHang don " +
                    "JOIN FETCH don.chiTietDonXuatHangList chiTiet " +
                    "WHERE (don.khachHang.tenKhachHang LIKE :keyword " +
                    "OR px.maPhieu LIKE :keyword " +
                    "OR don.maDon LIKE :keyword " +
                    "OR px.bienSoXe LIKE :keyword " +
                    "OR px.nguoiTao.ten LIKE :keyword " +
                    "OR px.nguoiTao.ho LIKE :keyword) " +
                    "ORDER BY CASE " +
                    "WHEN px.maPhieu LIKE :keyword THEN 1 " +
                    "WHEN px.bienSoXe LIKE :keyword THEN 2 " +
                    "WHEN don.khachHang.tenKhachHang LIKE :keyword THEN 3 " +
                    "WHEN px.nguoiTao.ten LIKE :keyword THEN 4 " +
                    "WHEN px.nguoiTao.ho LIKE :keyword THEN 5 " +
                    "ELSE 6 " +
                    "END";
            TypedQuery<PhieuXuat> query = em.createQuery(jpql, PhieuXuat.class);
            query.setParameter("keyword", "%" + keyword + "%");
            return query.getResultList();
        } catch (Exception e){
            throw e;
        } finally {
            em.close();
        }
    }
    
}

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.example.quanlykhohang.dao;
import jakarta.persistence.*;

import jakarta.persistence.EntityManager;
import java.util.List;
import org.example.quanlykhohang.entity.NhanVien;
import org.example.quanlykhohang.entity.TaiKhoan;
import org.example.quanlykhohang.entity.UserSession;
import org.example.quanlykhohang.util.JpaUtils;

/**
 *
 * @author pc
 */
public class NhanVienDAO implements InterfaceDAO<NhanVien, Integer> {

    @Override
    public void create(NhanVien nhanVien) {
        EntityManager em = JpaUtils.getEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(nhanVien);
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
    public void update(NhanVien nhanVien) {
        EntityManager em = JpaUtils.getEntityManager();
        try {
            em.getTransaction().begin();
            em.merge(nhanVien);
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
    public void delete(Integer maNhanVien) {
        EntityManager em = JpaUtils.getEntityManager();
        try {
            em.getTransaction().begin();
            NhanVien nhanVien = em.find(NhanVien.class, maNhanVien);
            em.remove(nhanVien);
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
    public NhanVien findById(Integer maNhanVien) {
        EntityManager em = JpaUtils.getEntityManager();
        NhanVien entity = em.find(NhanVien.class, maNhanVien);
        em.close();
        return entity;    
    }

    @Override
    public long count() {
        EntityManager em = JpaUtils.getEntityManager();
        String japl = "select count(u) from NhanVien u";
        Query query = em.createQuery(japl);
        em.close();
        return ((Long) query.getSingleResult()).intValue();    
    }

    @Override
    public List<NhanVien> findAll() {
        EntityManager em = JpaUtils.getEntityManager();
//        String japl = "SELECT * FROM NhanVien order by maNhanVien";
	TypedQuery<NhanVien> query = em.createNamedQuery("NhanVien.findAll", NhanVien.class);
//        em.close();
        return query.getResultList();    
    }

    @Override
    public boolean existsById(Integer maNhanVien) {
        EntityManager em = JpaUtils.getEntityManager();
        String jql = "SELECT COUNT(u.maNhanVien) FROM NhanVien u WHERE u.maNhanVien = :maNhanVien";
        TypedQuery<Long> query = em.createQuery(jql, Long.class);
        query.setParameter("maNhanVien", maNhanVien);
        Long count = query.getSingleResult();
        em.close();
        return count == 1;    
    }
    public boolean existsByEmail(NhanVien nhanVien) {
        EntityManager em = JpaUtils.getEntityManager();
        String jql = "SELECT u FROM NhanVien u WHERE u.email = :email";
        TypedQuery<NhanVien> query = em.createQuery(jql, NhanVien.class);
        query.setParameter("email", nhanVien.getEmail());
        List<NhanVien> resultList = query.getResultList();
        em.close();
        return !resultList.isEmpty();
    }

    public void updatePrivateAccount(NhanVien nhanVien) {
        EntityManager em = JpaUtils.getEntityManager();
        try {
            em.getTransaction().begin();
            // Tìm đối tượng TaiKhoan dựa trên maNhanVien
//            Integer maNhanVien = UserSession.getInstance().getMaNhanVien();

            NhanVien existingNhanVien = em.createQuery("SELECT t FROM NhanVien t WHERE t.maNhanVien = :maNhanVien", NhanVien.class)
                    .setParameter("maNhanVien", nhanVien.getMaNhanVien())
                    .getSingleResult();
            if (existingNhanVien != null) {
                existingNhanVien.setHo(nhanVien.getHo());
                existingNhanVien.setTen(nhanVien.getTen());
                existingNhanVien.setGioiTinh(nhanVien.getGioiTinh());
                existingNhanVien.setNgaySinh(nhanVien.getNgaySinh());
                existingNhanVien.setSdt(nhanVien.getSdt());
                existingNhanVien.setDiaChi(nhanVien.getDiaChi());
                existingNhanVien.setEmail(nhanVien.getEmail());


                // Merge đối tượng cập nhật vào persistence context
                em.merge(existingNhanVien);
            } else {
                throw new EntityNotFoundException("Nhan viên with maNhanVien " + nhanVien.getMaNhanVien() + " not found");
            }
            em.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
            em.getTransaction().rollback();
            throw e;
        } finally {
            em.close();
        }
    }
    
}

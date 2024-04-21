package org.example.quanlykhohang.dao;

import jakarta.persistence.*;
import org.example.quanlykhohang.entity.TaiKhoan;
import org.example.quanlykhohang.util.JpaUtils;

import java.util.List;

public class TaiKhoanDAO implements InterfaceDAO<TaiKhoan, Integer>{

    @Override
    public void create(TaiKhoan taiKhoan) {
        EntityManager em = JpaUtils.getEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(taiKhoan);
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
    public void update(TaiKhoan taiKhoan) {
        EntityManager em = JpaUtils.getEntityManager();
        try {
            em.getTransaction().begin();
            em.merge(taiKhoan);
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
            TaiKhoan taiKhoan = em.find(TaiKhoan.class, id);
            em.remove(taiKhoan);
            em.getTransaction().commit();
        } catch (Exception e) {
            em.getTransaction().rollback();
            e.printStackTrace();
        } finally {
            em.close();
        }
    }

    @Override
    public TaiKhoan findById(Integer id) {
        EntityManager em = JpaUtils.getEntityManager();
        TaiKhoan entity = em.find(TaiKhoan.class, id);
        em.close();
        return entity;
    }

    @Override
    public long count() {
        EntityManager em = JpaUtils.getEntityManager();
        String japl = "select count( u) from TaiKhoan u";
        Query query = em.createQuery(japl);
        em.close();
        return ((Long) query.getSingleResult()).intValue();
    }

    @Override
    public List<TaiKhoan> findAll() {
        EntityManager em = JpaUtils.getEntityManager();
        String japl = "SELECT u FROM TaiKhoan u order by u.id";
        TypedQuery<TaiKhoan> query = em.createNamedQuery(japl, TaiKhoan.class);
        em.close();
        return query.getResultList();
    }

    @Override
    public boolean existsById(Integer id) {
        EntityManager em = JpaUtils.getEntityManager();
        String jql = "SELECT COUNT(u.id) FROM TaiKhoan u WHERE u.id = :id";
        TypedQuery<Long> query = em.createQuery(jql, Long.class);
        query.setParameter("id", id);
        Long count = query.getSingleResult();
        em.close();
        return count == 1;
    }
    public TaiKhoan checkLogin(String username, String password) {
        EntityManager em = JpaUtils.getEntityManager();
        String japl = "SELECT u FROM TaiKhoan u WHERE u.username = :Username AND u.password = :Password";
        TypedQuery<TaiKhoan> query = em.createQuery(japl, TaiKhoan.class);
        query.setParameter("Username", username);
        query.setParameter("Password", password);
        TaiKhoan result = null;
        try {
            result = query.getSingleResult();
        } catch (NoResultException e) {
            e.printStackTrace();
            throw e;
        } finally {
            em.close();
        }
        return result;
    }
    public void testDao(){
        EntityManager em = JpaUtils.getEntityManager();
        System.out.println("chạy tới đây là kết nối hibernate ok");
        em.close();
    }
}

package org.example.quanlykhohang.dao;

import jakarta.persistence.*;
import org.example.quanlykhohang.entity.Role;
import org.example.quanlykhohang.entity.TaiKhoan;
import org.example.quanlykhohang.entity.UserSession;
import org.example.quanlykhohang.util.JpaUtils;
import org.mindrot.jbcrypt.BCrypt;

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
            // Tìm đối tượng TaiKhoan dựa trên maNhanVien
            TaiKhoan existingTaiKhoan = em.createQuery("SELECT t FROM TaiKhoan t WHERE t.nhanVien.maNhanVien = :maNhanVien", TaiKhoan.class)
                                        .setParameter("maNhanVien", taiKhoan.getNhanVien().getMaNhanVien())
                                        .getSingleResult();
            if (existingTaiKhoan != null) {
                // Cập nhật thuộc tính của đối tượng existingTaiKhoan từ đối tượng taiKhoan
                existingTaiKhoan.setUsername(taiKhoan.getUsername());
//                existingTaiKhoan.setPassword(taiKhoan.getPassword());
                existingTaiKhoan.setVaiTro(taiKhoan.getVaiTro());
                existingTaiKhoan.setDangHoatDong(taiKhoan.isDangHoatDong());

                // Merge đối tượng cập nhật vào persistence context
                em.merge(existingTaiKhoan);
            } else {
                throw new EntityNotFoundException("TaiKhoan with maNhanVien " + taiKhoan.getNhanVien().getMaNhanVien() + " not found");
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

    public void updatePassword(TaiKhoan taiKhoan) {
        EntityManager em = JpaUtils.getEntityManager();
        try {
            em.getTransaction().begin();
            TaiKhoan existingTaiKhoan = em.createQuery("SELECT t FROM TaiKhoan t WHERE t.id = :id", TaiKhoan.class)
                    .setParameter("id", taiKhoan.getId())
                    .getSingleResult();
            if (existingTaiKhoan != null) {
                existingTaiKhoan.setPassword(taiKhoan.getPassword());
                // Merge đối tượng cập nhật vào persistence context
                em.merge(existingTaiKhoan);
            } else {
                throw new EntityNotFoundException("TaiKhoan with id " + taiKhoan.getId() + " not found");
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
	    TypedQuery<TaiKhoan> query = em.createNamedQuery("TaiKhoan.findAll", TaiKhoan.class);
//        em.close();
        return query.getResultList();
    }
    public TaiKhoan findByMaNhanVien1(int id) {
        EntityManager em = JpaUtils.getEntityManager();
        TypedQuery<TaiKhoan> query = em.createQuery("SELECT t FROM TaiKhoan t join NhanVien u on t.nhanVien.maNhanVien = u.maNhanVien WHERE u.maNhanVien = :id", TaiKhoan.class);
        query.setParameter("id", id);

        TaiKhoan taiKhoan = null;
        try {
            taiKhoan = query.getSingleResult();
        } catch (NoResultException e) {
            return null;
        } finally {
            em.close(); // Đóng EntityManager sau khi sử dụng
        }
        return taiKhoan;
    }
    public List<TaiKhoan> findByRole(Role vaiTro) {
        EntityManager em = JpaUtils.getEntityManager();
        TypedQuery<TaiKhoan> query = em.createQuery("SELECT t FROM TaiKhoan t join NhanVien u on t.nhanVien.maNhanVien = u.maNhanVien WHERE t.vaiTro = :vaiTro", TaiKhoan.class);
        query.setParameter("vaiTro", vaiTro);

        List<TaiKhoan> taiKhoans = null;
        try {
            taiKhoans = query.getResultList();
        } catch (NoResultException e) {
            return null;
        } finally {
            em.close(); // Đóng EntityManager sau khi sử dụng
        }
        return taiKhoans;
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
        String jql = "SELECT u FROM TaiKhoan u WHERE u.username = :username";
        TypedQuery<TaiKhoan> query = em.createQuery(jql, TaiKhoan.class);
        query.setParameter("username", username);
        TaiKhoan taiKhoan = null;
        try {
            taiKhoan = query.getSingleResult();
            // Kiểm tra mật khẩu
            if (BCrypt.checkpw(password, taiKhoan.getPassword())) {
                // Mật khẩu đúng
                return taiKhoan;
            } else {
                // Mật khẩu không đúng
                return null;
            }
        } catch (NoResultException e) {
            return  null;
        } finally {
            em.close();
        }
    }
    public TaiKhoan findByMaNhanVien(Integer maNhanVien){
        EntityManager em = JpaUtils.getEntityManager();
        try {
            // Query to find TaiKhoan based on maNhanVien
            TaiKhoan taiKhoan = em.createQuery("SELECT t FROM TaiKhoan t WHERE t.nhanVien.maNhanVien = :maNhanVien", TaiKhoan.class)
                                    .setParameter("maNhanVien", maNhanVien)
                                    .getSingleResult();
            return taiKhoan;
        } catch (NoResultException e) {
            // Handle case where no TaiKhoan is found with the given maNhanVien
            return null;
        } finally {
            em.close();
        }
    }
    public TaiKhoan findByEmail(String email) {
        EntityManager em = JpaUtils.getEntityManager();
        try {
            // Query to find TaiKhoan based on email
            TaiKhoan taiKhoan = em.createQuery(
                            "SELECT t FROM TaiKhoan t JOIN FETCH t.nhanVien n WHERE n.email = :email",
                            TaiKhoan.class)
                    .setParameter("email", email)
                    .getSingleResult();
            return taiKhoan;
        } catch (NoResultException e) {
            // Handle case where no TaiKhoan is found with the given email
            return null;
        } finally {
            em.close();
        }
    }


    public void testDao(){
        EntityManager em = JpaUtils.getEntityManager();
        System.out.println("chạy tới đây là kết nối hibernate ok");
        em.close();
    }
}

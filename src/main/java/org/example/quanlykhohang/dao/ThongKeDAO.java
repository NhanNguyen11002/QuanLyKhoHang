package org.example.quanlykhohang.dao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;
import org.example.quanlykhohang.entity.PhieuNhap;
import org.example.quanlykhohang.entity.PhieuStatus;
import org.example.quanlykhohang.entity.PhieuXuat;
import org.example.quanlykhohang.util.JpaUtils;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class ThongKeDAO {
    public List<Object> searchAllPhieu(String keyword){
        EntityManager em = JpaUtils.getEntityManager();
        try{
            List<Object> list = new ArrayList<>();
            String jpql1 = "SELECT u FROM PhieuNhap u WHERE " +
                    "u.maPhieu LIKE :keyword " +
                    "OR u.nguoiTao.ten LIKE :keyword " +
                    "OR u.nguoiTao.ho LIKE :keyword " +
                    "ORDER BY CASE " +
                    "WHEN u.maPhieu LIKE :keyword THEN 1 " +
                    "WHEN u.nguoiTao.ten LIKE :keyword THEN 2 " +
                    "WHEN u.nguoiTao.ho LIKE :keyword THEN 3 " +
                    "ELSE 4 " +
                    "END";
            TypedQuery<PhieuNhap> query = em.createQuery(jpql1, PhieuNhap.class);
            query.setParameter("keyword", "%" + keyword + "%");
            List<PhieuNhap> phieuNhapList = query.getResultList();

            String jpql2 = "SELECT u FROM PhieuXuat u join fetch u.donXuatHang don join fetch don.chiTietDonXuatHangList WHERE " +
                    "u.maPhieu LIKE :keyword " +
                    "OR u.nguoiTao.ten LIKE :keyword " +
                    "OR u.nguoiTao.ho LIKE :keyword " +
                    "ORDER BY CASE " +
                    "WHEN u.maPhieu LIKE :keyword THEN 1 " +
                    "WHEN u.nguoiTao.ten LIKE :keyword THEN 2 " +
                    "WHEN u.nguoiTao.ho LIKE :keyword THEN 3 " +
                    "ELSE 4 " +
                    "END";
            TypedQuery<PhieuXuat> query2 = em.createQuery(jpql2, PhieuXuat.class);
            query2.setParameter("keyword", "%" + keyword + "%");
            List<PhieuXuat> phieuXuatList = query2.getResultList();

            list.addAll(phieuXuatList);
            list.addAll(phieuNhapList);

            return list;
        } catch (Exception e){
            e.printStackTrace();
            throw e;
        } finally {
            em.close();
        }
    }
    public List<Object> searchAllPhieu(String keyword, Timestamp from, Timestamp to){
        EntityManager em = JpaUtils.getEntityManager();
        try{
            List<Object> list = new ArrayList<>();
            String jpql1 = "SELECT u FROM PhieuNhap u WHERE " +
                    "(u.maPhieu LIKE :keyword " +
                    "OR u.nguoiTao.ten LIKE :keyword " +
                    "OR u.nguoiTao.ho LIKE :keyword) " +
                    "AND (u.thoiGianTao between :from and :to)"+
                    "ORDER BY CASE " +
                    "WHEN u.maPhieu LIKE :keyword THEN 1 " +
                    "WHEN u.nguoiTao.ten LIKE :keyword THEN 2 " +
                    "WHEN u.nguoiTao.ho LIKE :keyword THEN 3 " +
                    "ELSE 4 " +
                    "END";
            TypedQuery<PhieuNhap> query = em.createQuery(jpql1, PhieuNhap.class);
            query.setParameter("keyword", "%" + keyword + "%");
            query.setParameter("from",from);
            query.setParameter("to",to);
            List<PhieuNhap> phieuNhapList = query.getResultList();
            String jpql2 = "SELECT u FROM PhieuXuat u join fetch u.donXuatHang don join fetch don.chiTietDonXuatHangList chitiet WHERE " +
                    "(u.maPhieu LIKE :keyword " +
                    "OR u.nguoiTao.ten LIKE :keyword " +
                    "OR u.nguoiTao.ho LIKE :keyword) " +
                    "AND (u.thoiGianTao between :from and :to)"+
                    "ORDER BY CASE " +
                    "WHEN u.maPhieu LIKE :keyword THEN 1 " +
                    "WHEN u.nguoiTao.ten LIKE :keyword THEN 2 " +
                    "WHEN u.nguoiTao.ho LIKE :keyword THEN 3 " +
                    "ELSE 4 " +
                    "END";
            TypedQuery<PhieuXuat> query2 = em.createQuery(jpql2, PhieuXuat.class);
            query2.setParameter("keyword", "%" + keyword + "%");
            query2.setParameter("from",from);
            query2.setParameter("to",to);
            List<PhieuXuat> phieuXuatList = query2.getResultList();
            list.addAll(phieuXuatList);
            list.addAll(phieuNhapList);
            return list;
        } catch (Exception e){
            e.printStackTrace();
            throw e;
        } finally {
            em.close();
        }
    }
    public List<Object> searchAllPhieu(String keyword, double from, double to){
        EntityManager em = JpaUtils.getEntityManager();
        try{
            List<Object> list = new ArrayList<>();
            String jpql1 = "SELECT u FROM PhieuNhap u WHERE " +
                    "(u.maPhieu LIKE :keyword " +
                    "OR u.nguoiTao.ten LIKE :keyword " +
                    "OR u.nguoiTao.ho LIKE :keyword) " +
                    "AND (u.tongTien between :from and :to)"+
                    "ORDER BY CASE " +
                    "WHEN u.maPhieu LIKE :keyword THEN 1 " +
                    "WHEN u.nguoiTao.ten LIKE :keyword THEN 2 " +
                    "WHEN u.nguoiTao.ho LIKE :keyword THEN 3 " +
                    "ELSE 4 " +
                    "END";
            TypedQuery<PhieuNhap> query = em.createQuery(jpql1, PhieuNhap.class);
            query.setParameter("keyword", "%" + keyword + "%");
            query.setParameter("from",from);
            query.setParameter("to",to);
            List<PhieuNhap> phieuNhapList = query.getResultList();


            String jpql2 = "SELECT u FROM PhieuXuat u join fetch u.donXuatHang don join fetch don.chiTietDonXuatHangList chitiet WHERE " +
                    "(u.maPhieu LIKE :keyword " +
                    "OR u.nguoiTao.ten LIKE :keyword " +
                    "OR u.nguoiTao.ho LIKE :keyword) " +
                    "AND (don.tongTien between :from and :to)"+
                    "ORDER BY CASE " +
                    "WHEN u.maPhieu LIKE :keyword THEN 1 " +
                    "WHEN u.nguoiTao.ten LIKE :keyword THEN 2 " +
                    "WHEN u.nguoiTao.ho LIKE :keyword THEN 3 " +
                    "ELSE 4 " +
                    "END";
            TypedQuery<PhieuXuat> query2 = em.createQuery(jpql2, PhieuXuat.class);
            query2.setParameter("keyword", "%" + keyword + "%");
            query2.setParameter("from",from);
            query2.setParameter("to",to);
            List<PhieuXuat> phieuXuatList = query2.getResultList();


            list.addAll(phieuXuatList);
            list.addAll(phieuNhapList);
            return list;
        } catch (Exception e){
            e.printStackTrace();
            throw e;
        } finally {
            em.close();
        }
    }
    public List<Object> searchAllPhieu(String keyword, Timestamp fromTime, Timestamp toTime, double from, double to){
        EntityManager em = JpaUtils.getEntityManager();
        try{
            List<Object> list = new ArrayList<>();
            String jpql1 = "SELECT u FROM PhieuNhap u WHERE " +
                    "(u.maPhieu LIKE :keyword " +
                    "OR u.nguoiTao.ten LIKE :keyword " +
                    "OR u.nguoiTao.ho LIKE :keyword) " +
                    "AND (u.tongTien between :from and :to)"+
                    "AND (u.thoiGianTao between :fromTime and :toTime)"+
                    "ORDER BY CASE " +
                    "WHEN u.maPhieu LIKE :keyword THEN 1 " +
                    "WHEN u.nguoiTao.ten LIKE :keyword THEN 2 " +
                    "WHEN u.nguoiTao.ho LIKE :keyword THEN 3 " +
                    "ELSE 4 " +
                    "END";
            TypedQuery<PhieuNhap> query = em.createQuery(jpql1, PhieuNhap.class);
            query.setParameter("keyword", "%" + keyword + "%");
            query.setParameter("from",from);
            query.setParameter("to",to);
            query.setParameter("fromTime",fromTime);
            query.setParameter("toTime",toTime);
            List<PhieuNhap> phieuNhapList = query.getResultList();


            String jpql2 = "SELECT u FROM PhieuXuat u join fetch u.donXuatHang don join fetch don.chiTietDonXuatHangList chitiet  WHERE " +
                    "(u.maPhieu LIKE :keyword " +
                    "OR u.nguoiTao.ten LIKE :keyword " +
                    "OR u.nguoiTao.ho LIKE :keyword) " +
                    "AND (don.tongTien between :from and :to)"+
                    "AND (u.thoiGianTao between :fromTime and :toTime)"+
                    "ORDER BY CASE " +
                    "WHEN u.maPhieu LIKE :keyword THEN 1 " +
                    "WHEN u.nguoiTao.ten LIKE :keyword THEN 2 " +
                    "WHEN u.nguoiTao.ho LIKE :keyword THEN 3 " +
                    "ELSE 4 " +
                    "END";
            TypedQuery<PhieuXuat> query2 = em.createQuery(jpql2, PhieuXuat.class);
            query2.setParameter("keyword", "%" + keyword + "%");
            query2.setParameter("from",from);
            query2.setParameter("to",to);
            query2.setParameter("fromTime",fromTime);
            query2.setParameter("toTime",toTime);
            List<PhieuXuat> phieuXuatList = query2.getResultList();


            list.addAll(phieuXuatList);
            list.addAll(phieuNhapList);
            return list;
        } catch (Exception e){
            e.printStackTrace();
            throw e;
        } finally {
            em.close();
        }


    }
    public long count(){
        EntityManager em = JpaUtils.getEntityManager();
        try{
            String jpql = "select count(u) from PhieuNhap u";
            String jpql2 = "select count(u) from PhieuXuat u";
            Query query = em.createQuery(jpql);
            Query query2 = em.createQuery(jpql2);
            return ((Long) query.getSingleResult()).intValue() + ((Long) query2.getSingleResult()).intValue();
        }  catch (Exception e){
            e.printStackTrace();
            throw e;
        } finally {
            em.close();
        }
    }
    public double getTotal(){
        EntityManager em = JpaUtils.getEntityManager();
        try{
            String jpql = "select sum(u.tongTien) from PhieuNhap u where u.status = :status";
            String jpql2 = "select sum(u.donXuatHang.tongTien) from PhieuXuat u where u.status = :status";
            Query query = em.createQuery(jpql);
            query.setParameter("status", PhieuStatus.Done);
            Double totalPn = query.getSingleResult() !=null?(Double) query.getSingleResult():0;
            Query query2 = em.createQuery(jpql2);
            query2.setParameter("status", PhieuStatus.Done);
            Double totalPx = query2.getSingleResult() !=null ?(Double) query2.getSingleResult():0;
            return totalPn + totalPx;
        }  catch (Exception e){
            e.printStackTrace();
            throw e;
        } finally {
            em.close();
        }
    }
}

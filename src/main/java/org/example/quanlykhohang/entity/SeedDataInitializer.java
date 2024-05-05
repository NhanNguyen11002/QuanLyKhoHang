package org.example.quanlykhohang.entity;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;
import org.example.quanlykhohang.dao.NhanVienDAO;
import org.example.quanlykhohang.dao.TaiKhoanDAO;
import org.mindrot.jbcrypt.BCrypt;

import java.time.LocalDate;

public class SeedDataInitializer {
    private boolean seedingCompleted = false;

    public void initialize() {
        if (!seedingCompleted) {
            seedData();
            seedingCompleted = true;
        }
    }

    private void seedData() {
        try {
            TaiKhoanDAO taiKhoanDAO = new TaiKhoanDAO();
            if (taiKhoanDAO.count()==0)
            {
                NhanVien nhanVien = new NhanVien(1,"Admin", "First", LocalDate.now(), Gender.Male, "0123456789", "Mặc định", "DefaultEmail@gmail.com");
                NhanVienDAO nhanVienDAO = new NhanVienDAO();
                nhanVienDAO.update(nhanVien);
                // Tạo một tài khoản
                NhanVienDAO nhanVienDAO1 = new NhanVienDAO();
                NhanVien nv1 = nhanVienDAO1.findById(1);
                TaiKhoan taiKhoan = new TaiKhoan("admin", BCrypt.hashpw("12345678", BCrypt.gensalt()) , Role.Admin, true, nv1);
                TaiKhoanDAO taiKhoanDAO1 = new TaiKhoanDAO();
                taiKhoanDAO1.create(taiKhoan);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

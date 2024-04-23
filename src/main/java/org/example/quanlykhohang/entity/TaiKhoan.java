package org.example.quanlykhohang.entity;

import jakarta.persistence.*;

@Entity
@Table(name="TaiKhoan")
@NamedQuery(name = "TaiKhoan.findAll", query = "SELECT u FROM TaiKhoan u join NhanVien t on u.nhanVien.maNhanVien = t.maNhanVien order by u.id")
public class TaiKhoan {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private Integer id;
    @Column(name="username")
    private String username;
    @Column(name="password")
    private String password;
    @Enumerated(EnumType.STRING) // Định nghĩa cách ánh xạ enum
    @Column(name="vaiTro")
    private Role vaiTro;
    @Column(name="dangHoatDong")
    private boolean dangHoatDong;
    @OneToOne
    @JoinColumn(name = "maNhanVien", nullable = false)
    private NhanVien nhanVien;    

    public TaiKhoan() {
    }

    public TaiKhoan(Integer id, String username, String password, Role vaiTro, boolean dangHoatDong, NhanVien nhanVien) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.vaiTro = vaiTro;
        this.dangHoatDong = dangHoatDong;
        this.nhanVien = nhanVien;
    }

    public TaiKhoan(String username, String password, Role vaiTro, boolean dangHoatDong, NhanVien nhanVien) {
        this.username = username;
        this.password = password;
        this.vaiTro = vaiTro;
        this.dangHoatDong = dangHoatDong;
        this.nhanVien = nhanVien;
    }

    public TaiKhoan(String username, Role vaiTro, boolean dangHoatDong, NhanVien nhanVien) {
//        this.id = id;
        this.username = username;
        this.vaiTro = vaiTro;
        this.dangHoatDong = dangHoatDong;
        this.nhanVien = nhanVien;
    }

    
    
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Role getVaiTro() {
        return vaiTro;
    }

    public void setVaiTro(Role vaiTro) {
        this.vaiTro = vaiTro;
    }

    public boolean isDangHoatDong() {
        return dangHoatDong;
    }

    public void setDangHoatDong(boolean dangHoatDong) {
        this.dangHoatDong = dangHoatDong;
    }

    public NhanVien getNhanVien() {
        return nhanVien;
    }

    public void setNhanVien(NhanVien nhanVien) {
        this.nhanVien = nhanVien;
    }
}

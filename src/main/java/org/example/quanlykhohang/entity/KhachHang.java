package org.example.quanlykhohang.entity;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name="KhachHang")
public class KhachHang {
    @Id
    @Column(name="maKhachHang")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer maKhachHang;
    @Column(name="tenKhachHang")
    private String tenKhachHang;
    @Column(name="sdt")
    private String sdt;
    @Column(name="diaChi")
    private String diaChi;
    @Column(name="email")
    private String email;
    @OneToMany(mappedBy = "khachHang")
    private List<DonXuatHang> donXuatHangList;
    public KhachHang() {
    }

    public KhachHang(Integer maKhachHang, String tenKhachHang, String sdt, String diaChi, String email) {
        this.maKhachHang = maKhachHang;
        this.tenKhachHang = tenKhachHang;
        this.sdt = sdt;
        this.diaChi = diaChi;
        this.email = email;
    }

    public Integer getMaKhachHang() {
        return maKhachHang;
    }

    public void setMaKhachHang(Integer maKhachHang) {
        this.maKhachHang = maKhachHang;
    }

    public String getTenKhachHang() {
        return tenKhachHang;
    }

    public void setTenKhachHang(String tenKhachHang) {
        this.tenKhachHang = tenKhachHang;
    }

    public String getSdt() {
        return sdt;
    }

    public void setSdt(String sdt) {
        this.sdt = sdt;
    }

    public String getDiaChi() {
        return diaChi;
    }

    public void setDiaChi(String diaChi) {
        this.diaChi = diaChi;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
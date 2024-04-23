package org.example.quanlykhohang.entity;

import jakarta.persistence.*;
import java.sql.Timestamp;
@Entity
@Table(name="PhieuXuat")
public class PhieuXuat {
    @Id
    @Column(name="maPhieu")
    private String maPhieu;
    @ManyToOne
    @JoinColumn(name = "nguoiTao", nullable = false)
    private NhanVien nguoiTao;
    @ManyToOne
    @JoinColumn(name = "maDon")
    private DonXuatHang donXuatHang;
    @Column(name="bienSoXe")
    private String bienSoXe;
    @Column(name="thoiGianTao")
    private Timestamp thoiGianTao;

    public PhieuXuat() {
    }

    public PhieuXuat(String maPhieu, NhanVien nguoiTao, DonXuatHang donXuatHang, String bienSoXe, Timestamp thoiGianTao) {
        this.maPhieu = maPhieu;
        this.nguoiTao = nguoiTao;
        this.donXuatHang = donXuatHang;
        this.bienSoXe = bienSoXe;
        this.thoiGianTao = thoiGianTao;
    }

    public String getMaPhieu() {
        return maPhieu;
    }

    public void setMaPhieu(String maPhieu) {
        this.maPhieu = maPhieu;
    }

    public NhanVien getNguoiTao() {
        return nguoiTao;
    }

    public void setNguoiTao(NhanVien nguoiTao) {
        this.nguoiTao = nguoiTao;
    }

    public DonXuatHang getDonXuatHang() {
        return donXuatHang;
    }

    public void setDonXuatHang(DonXuatHang donXuatHang) {
        this.donXuatHang = donXuatHang;
    }

    public String getBienSoXe() {
        return bienSoXe;
    }

    public void setBienSoXe(String bienSoXe) {
        this.bienSoXe = bienSoXe;
    }

    public Timestamp getThoiGianTao() {
        return thoiGianTao;
    }

    public void setThoiGianTao(Timestamp thoiGianTao) {
        this.thoiGianTao = thoiGianTao;
    }
}

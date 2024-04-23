package org.example.quanlykhohang.entity;

import jakarta.persistence.*;
@Entity
@Table(name="ChiTietPhieuNhap")
public class ChiTietPhieuNhap {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "maPhieu")
    private PhieuNhap phieuNhap;

    @ManyToOne
    @JoinColumn(name = "maDT")
    private DienThoai dienThoai;

    @Column(name="soLuong")
    private Integer soLuong;

    @Column(name="donGia")
    private Double donGia;

    public ChiTietPhieuNhap() {
    }

    public ChiTietPhieuNhap(Integer id, PhieuNhap phieuNhap, DienThoai dienThoai, Integer soLuong, Double donGia) {
        this.id = id;
        this.phieuNhap = phieuNhap;
        this.dienThoai = dienThoai;
        this.soLuong = soLuong;
        this.donGia = donGia;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public PhieuNhap getPhieuNhap() {
        return phieuNhap;
    }

    public void setPhieuNhap(PhieuNhap phieuNhap) {
        this.phieuNhap = phieuNhap;
    }

    public DienThoai getDienThoai() {
        return dienThoai;
    }

    public void setDienThoai(DienThoai dienThoai) {
        this.dienThoai = dienThoai;
    }

    public Integer getSoLuong() {
        return soLuong;
    }

    public void setSoLuong(Integer soLuong) {
        this.soLuong = soLuong;
    }

    public Double getDonGia() {
        return donGia;
    }

    public void setDonGia(Double donGia) {
        this.donGia = donGia;
    }
    
}

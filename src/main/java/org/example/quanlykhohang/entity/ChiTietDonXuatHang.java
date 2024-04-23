package org.example.quanlykhohang.entity;
import jakarta.persistence.*;
@Entity
@Table(name="ChiTietDonXuat")
public class ChiTietDonXuatHang {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "maDon")
    private DonXuatHang donXuatHang;

    @ManyToOne
    @JoinColumn(name = "maDT")
    private DienThoai dienThoai;

    @Column(name="soLuong")
    private Integer soLuong;

    @Column(name="donGia")
    private Double donGia;

    public ChiTietDonXuatHang() {
    }

    public ChiTietDonXuatHang(Integer id, DonXuatHang donXuatHang, DienThoai dienThoai, Integer soLuong, Double donGia) {
        this.id = id;
        this.donXuatHang = donXuatHang;
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

    public DonXuatHang getDonXuatHang() {
        return donXuatHang;
    }

    public void setDonXuatHang(DonXuatHang donXuatHang) {
        this.donXuatHang = donXuatHang;
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

package org.example.quanlykhohang.entity;

import jakarta.persistence.*;

import java.sql.Timestamp;

@Entity
@Table(name="DonXuatHang")
public class DonXuatHang {
    @Id
    @Column(name="maDon")
    private String maDon;

    @Column(name="tongTien")
    private Double tongTien;
    @Column(name="thoiGianTao")
    private Timestamp thoiGianTao;
    @Column(name="trangThai")
    private String trangThai;
    @ManyToOne
    @JoinColumn(name = "nguoiTao")
    private NhanVien nhanVien;
    @ManyToOne
    @JoinColumn(name = "maKhachHang")
    private KhachHang khachHang;
    // one to many chi tiet don
    // one to one/many phieu xuat
}

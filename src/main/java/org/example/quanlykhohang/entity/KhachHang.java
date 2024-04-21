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
}

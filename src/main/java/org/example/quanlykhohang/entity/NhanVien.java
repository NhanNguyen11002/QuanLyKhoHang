package org.example.quanlykhohang.entity;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name="NhanVien")
public class NhanVien {
    @Id
    @Column(name="maNhanVien")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer maNhanVien;
    @Column(name="ten")
    private String ten;
    @Column(name="ho")
    private String ho;
    @Column(name="ngaySinh")
    private LocalDate ngaySinh;
    @Enumerated(EnumType.STRING)
    @Column(name="gioiTinh")
    private Gender gioiTinh;
    @Column(name="ngayBatDau")
    private LocalDate ngayBatDau;
    @Column(name="ngayKetThuc")
    private LocalDate ngayKetThuc;
    @Column(name="sdt")
    private String sdt;
    @Column(name="diaChi")
    private String diaChi;
    @Column(name="email")
    private String email;
    @OneToMany(mappedBy = "nhanVien")
    private List<DonXuatHang> donXuatHangList;

}

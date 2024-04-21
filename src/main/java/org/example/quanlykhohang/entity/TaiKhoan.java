package org.example.quanlykhohang.entity;

import jakarta.persistence.*;

@Entity
@Table(name="TaiKhoan")
public class TaiKhoan {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private Integer id;
    @Column(name="username")
    private String username;
    @Column(name="password")
    private String password;
    @Column(name="vaiTro")
    private Role vaiTro;
    @Column(name="dangHoatDong")
    private boolean dangHoatDong;
    @OneToOne(mappedBy = "taiKhoan")
    @JoinColumn(name = "maNhanVien")
    private NhanVien nhanVien;
}

package org.example.quanlykhohang.entity;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name="NhanVien")
@NamedQuery(name = "NhanVien.findAll", query = "SELECT u FROM NhanVien u join TaiKhoan t on u.maNhanVien = t.nhanVien.maNhanVien order by u.maNhanVien")
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
    @OneToOne(mappedBy = "nhanVien", cascade = CascadeType.ALL)
    private TaiKhoan taiKhoan;


    public NhanVien() {
    }

    public NhanVien(Integer maNhanVien, String ten, String ho, LocalDate ngaySinh, Gender gioiTinh, LocalDate ngayBatDau, LocalDate ngayKetThuc, String sdt, String diaChi, String email) {
        this.maNhanVien = maNhanVien;
        this.ten = ten;
        this.ho = ho;
        this.ngaySinh = ngaySinh;
        this.gioiTinh = gioiTinh;
        this.ngayBatDau = ngayBatDau;
        this.ngayKetThuc = ngayKetThuc;
        this.sdt = sdt;
        this.diaChi = diaChi;
        this.email = email;
    }

    public NhanVien(String ten, String ho, LocalDate ngaySinh, Gender gioiTinh, LocalDate ngayBatDau, LocalDate ngayKetThuc, String sdt, String diaChi, String email) {
        this.ten = ten;
        this.ho = ho;
        this.ngaySinh = ngaySinh;
        this.gioiTinh = gioiTinh;
        this.ngayBatDau = ngayBatDau;
        this.ngayKetThuc = ngayKetThuc;
        this.sdt = sdt;
        this.diaChi = diaChi;
        this.email = email;
    }

    public NhanVien(Integer maNhanVien, String ten, String ho, LocalDate ngaySinh, Gender gioiTinh, String sdt, String diaChi, String email) {
        this.maNhanVien = maNhanVien;
        this.ten = ten;
        this.ho = ho;
        this.ngaySinh = ngaySinh;
        this.gioiTinh = gioiTinh;
        this.sdt = sdt;
        this.diaChi = diaChi;
        this.email = email;
    }

    public NhanVien(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return "NhanVien{" + "maNhanVien=" + maNhanVien + ", ten=" + ten + ", ho=" + ho + ", ngaySinh=" + ngaySinh + ", gioiTinh=" + gioiTinh + ", ngayBatDau=" + ngayBatDau + ", ngayKetThuc=" + ngayKetThuc + ", sdt=" + sdt + ", diaChi=" + diaChi + ", email=" + email + '}';
    }

    

    

    
    public Integer getMaNhanVien() {
        return maNhanVien;
    }

    public void setMaNhanVien(Integer maNhanVien) {
        this.maNhanVien = maNhanVien;
    }

    public String getTen() {
        return ten;
    }

    public void setTen(String ten) {
        this.ten = ten;
    }

    public String getHo() {
        return ho;
    }

    public void setHo(String ho) {
        this.ho = ho;
    }

    public LocalDate getNgaySinh() {
        return ngaySinh;
    }

    public void setNgaySinh(LocalDate ngaySinh) {
        this.ngaySinh = ngaySinh;
    }

    public Gender getGioiTinh() {
        return gioiTinh;
    }

    public void setGioiTinh(Gender gioiTinh) {
        this.gioiTinh = gioiTinh;
    }

    public LocalDate getNgayBatDau() {
        return ngayBatDau;
    }

    public void setNgayBatDau(LocalDate ngayBatDau) {
        this.ngayBatDau = ngayBatDau;
    }

    public LocalDate getNgayKetThuc() {
        return ngayKetThuc;
    }

    public void setNgayKetThuc(LocalDate ngayKetThuc) {
        this.ngayKetThuc = ngayKetThuc;
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

    public TaiKhoan getTaiKhoan() {
        return taiKhoan;
    }

    public void setTaiKhoan(TaiKhoan taiKhoan) {
        this.taiKhoan = taiKhoan;
    }
    
}

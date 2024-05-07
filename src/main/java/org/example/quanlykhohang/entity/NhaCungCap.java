package org.example.quanlykhohang.entity;

import jakarta.persistence.*;
import org.example.quanlykhohang.util.Validator;

import java.util.List;

@Entity
@Table(name="NhaCungCap")
public class NhaCungCap {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="maNhaCungCap")
    private Integer maNhaCungCap;
    @Column(name="tenNhaCungCap")
    private String tenNhaCungCap;
    @Column(name="sdt")
    private String sdt;
    @Column(name="diaChi")
    private String diaChi;
    @Column(name="email")
    private String email;
    @OneToMany(cascade = {CascadeType.MERGE},fetch= FetchType.EAGER, mappedBy = "nhaCungCap")
    private List<PhieuNhap> phieuNhapList;
    public NhaCungCap() {
    }

    public NhaCungCap(Integer maNhaCungCap, String tenNhaCungCap, String sdt, String diaChi, String email) {
        if(!Validator.validatePhoneNumber(sdt)) throw new IllegalArgumentException("Sai định dạng SĐT");
        if(!Validator.validateEmail(email)) throw new IllegalArgumentException("Sai định dạng email");
        this.maNhaCungCap = maNhaCungCap;
        this.tenNhaCungCap = tenNhaCungCap;
        this.sdt = sdt;
        this.diaChi = diaChi;
        this.email = email;
    }

    public NhaCungCap(String tenNhaCungCap, String sdt, String diaChi, String email) {
        if(!Validator.validatePhoneNumber(sdt)) throw new IllegalArgumentException("Sai định dạng SĐT");
        if(!Validator.validateEmail(email)) throw new IllegalArgumentException("Sai định dạng email");
        this.tenNhaCungCap = tenNhaCungCap;
        this.sdt = sdt;
        this.diaChi = diaChi;
        this.email = email;
    }

    public Integer getMaNhaCungCap() {
        return maNhaCungCap;
    }

    public void setMaNhaCungCap(Integer maNhaCungCap) {
        this.maNhaCungCap = maNhaCungCap;
    }

    public String getTenNhaCungCap() {
        return tenNhaCungCap;
    }

    public void setTenNhaCungCap(String tenNhaCungCap) {
        this.tenNhaCungCap = tenNhaCungCap;
    }

    public String getSdt() {
        return sdt;
    }

    public void setSdt(String sdt) {
        if(!Validator.validatePhoneNumber(sdt)) throw new IllegalArgumentException("Sai định dạng SĐT");
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
        if(!Validator.validateEmail(email)) throw new IllegalArgumentException("Sai định dạng email");
        this.email = email;
    }

    public List<PhieuNhap> getPhieuNhapList() {
        return phieuNhapList;
    }

    public void setPhieuNhapList(List<PhieuNhap> phieuNhapList) {
        this.phieuNhapList = phieuNhapList;
    }
}

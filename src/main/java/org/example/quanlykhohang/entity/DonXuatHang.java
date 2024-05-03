package org.example.quanlykhohang.entity;

import jakarta.persistence.*;

import java.sql.Timestamp;
import java.util.List;

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
    // one to many chi tiet don xuat hang
    @OneToMany(mappedBy = "donXuatHang", cascade = CascadeType.ALL)
    private List<ChiTietDonXuatHang> chiTietDonXuatHangList;
    // one to one/many phieu xuat
    @OneToOne(mappedBy = "donXuatHang", cascade = CascadeType.ALL)
    private PhieuXuat phieuXuat;

    public DonXuatHang() {
    }

    public DonXuatHang(String maDon, Double tongTien, Timestamp thoiGianTao, String trangThai, NhanVien nhanVien, KhachHang khachHang, List<ChiTietDonXuatHang> chiTietDonXuatHangList, PhieuXuat phieuXuat) {
        this.maDon = maDon;
        this.tongTien = tongTien;
        this.thoiGianTao = thoiGianTao;
        this.trangThai = trangThai;
        this.nhanVien = nhanVien;
        this.khachHang = khachHang;
        this.chiTietDonXuatHangList = chiTietDonXuatHangList;
        this.phieuXuat = phieuXuat;
    }

    public String getMaDon() {
        return maDon;
    }

    public void setMaDon(String maDon) {
        this.maDon = maDon;
    }

    public Double getTongTien() {
        return tongTien;
    }

    public void setTongTien(Double tongTien) {
        this.tongTien = tongTien;
    }

    public Timestamp getThoiGianTao() {
        return thoiGianTao;
    }

    public void setThoiGianTao(Timestamp thoiGianTao) {
        this.thoiGianTao = thoiGianTao;
    }

    public String getTrangThai() {
        return trangThai;
    }

    public void setTrangThai(String trangThai) {
        this.trangThai = trangThai;
    }

    public NhanVien getNhanVien() {
        return nhanVien;
    }

    public void setNhanVien(NhanVien nhanVien) {
        this.nhanVien = nhanVien;
    }

    public KhachHang getKhachHang() {
        return khachHang;
    }

    public void setKhachHang(KhachHang khachHang) {
        this.khachHang = khachHang;
    }

    public List<ChiTietDonXuatHang> getChiTietDonXuatHangList() {
        return chiTietDonXuatHangList;
    }

    public void setChiTietDonXuatHangList(List<ChiTietDonXuatHang> chiTietDonXuatHangList) {
        this.chiTietDonXuatHangList = chiTietDonXuatHangList;
    }

    public PhieuXuat getPhieuXuat() {
        return phieuXuat;
    }

    public void setPhieuXuat(PhieuXuat phieuXuat) {
        this.phieuXuat = phieuXuat;
    }

    @Override
    public String toString() {
        return "DonXuatHang{" +
                "maDon='" + maDon + '\'' +
                ", tongTien=" + tongTien +
                ", thoiGianTao=" + thoiGianTao +
                ", trangThai='" + trangThai + '\'' +
                ", nhanVien=" + nhanVien +
                ", khachHang=" + khachHang +
                ", chiTietDonXuatHangList=" + chiTietDonXuatHangList +
                ", phieuXuat=" + phieuXuat +
                '}';
    }
}

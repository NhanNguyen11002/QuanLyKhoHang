package org.example.quanlykhohang.entity;

import java.util.Objects;

public class SanPhamTrongDonHangDTO {
    private String maDT;
    private String tenDT;
    private Double giaXuat;
    private Integer soLuong;
    private Integer soLuongTon;
    private DienThoai dienThoai;

    public SanPhamTrongDonHangDTO(String maDT, String tenDT, Double giaXuat, Integer soLuong, Integer soLuongTon) {
        this.maDT = maDT;
        this.tenDT = tenDT;
        this.giaXuat = giaXuat;
        this.soLuong = soLuong;
        this.soLuongTon = soLuongTon;
    }

    public SanPhamTrongDonHangDTO(String maDT, String tenDT, Double giaXuat, Integer soLuong, Integer soLuongTon, DienThoai dienThoai) {
        this.maDT = maDT;
        this.tenDT = tenDT;
        this.giaXuat = giaXuat;
        this.soLuong = soLuong;
        this.soLuongTon = soLuongTon;
        this.dienThoai = dienThoai;
    }

    public DienThoai getDienThoai() {
        return dienThoai;
    }

    public void setDienThoai(DienThoai dienThoai) {
        this.dienThoai = dienThoai;
    }

    public SanPhamTrongDonHangDTO() {
    }

    public String getMaDT() {
        return maDT;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SanPhamTrongDonHangDTO that = (SanPhamTrongDonHangDTO) o;
        return Objects.equals(maDT, that.maDT);
    }

    @Override
    public int hashCode() {
        return Objects.hash(maDT);
    }

    public void setMaDT(String maDT) {
        this.maDT = maDT;
    }

    public String getTenDT() {
        return tenDT;
    }

    public void setTenDT(String tenDT) {
        this.tenDT = tenDT;
    }

    public Double getGiaXuat() {
        return giaXuat;
    }

    public void setGiaXuat(Double giaXuat) {
        this.giaXuat = giaXuat;
    }

    public Integer getSoLuong() {
        return soLuong;
    }

    public void setSoLuong(Integer soLuong) {
        this.soLuong = soLuong;
    }

    public Integer getSoLuongTon() {
        return soLuongTon;
    }

    public void setSoLuongTon(Integer soLuongTon) {
        this.soLuongTon = soLuongTon;
    }
}

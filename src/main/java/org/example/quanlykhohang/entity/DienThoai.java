package org.example.quanlykhohang.entity;
import jakarta.persistence.*;
@Entity
@Table(name="DienThoai")
public class DienThoai {
    @Id
    @Column(name="maDT")
    private String maDT;
    @Column(name="tenDT")
    private String tenDT;
    @Column(name="giaNhap")
    private Double giaNhap;
    @Column(name="giaXuat")
    private Double giaXuat;
    @Column(name="soLuong")
    private Integer soLuong;

    public DienThoai() {
    }

    public DienThoai(String maDT, String tenDT, Double giaNhap, Double giaXuat, Integer soLuong) {
        if (maDT == null || tenDT == null) {
            throw new IllegalArgumentException("Mã hoặc tên điện thoại không được để trống");
        }
        if (giaNhap == null || giaNhap <= 0) {
            throw new IllegalArgumentException("Giá nhập phải là số dương");
        }
        if (giaXuat == null || giaXuat <= 0) {
            throw new IllegalArgumentException("Giá nhập phải là số dương");
        }
        if (soLuong == null || soLuong <= 0) {
            throw new IllegalArgumentException("Số lượng phải là số nguyên dương");
        }
        this.maDT = maDT;
        this.tenDT = tenDT;
        this.giaNhap = giaNhap;
        this.giaXuat = giaXuat;
        this.soLuong = soLuong;
    }

    public String getMaDT() {
        return maDT;
    }

    public void setMaDT(String maDT) {
    	if (maDT == null) {
            throw new IllegalArgumentException("Mã điện thoại không được để trống");
        }
        this.maDT = maDT;
    }

    public String getTenDT() {
        return tenDT;
    }

    public void setTenDT(String tenDT) {
    	if (tenDT == null) {
            throw new IllegalArgumentException("Tên điện thoại không được để trống");
        }
        this.tenDT = tenDT;
    }

    public Double getGiaNhap() {
        return giaNhap;
    }

    public void setGiaNhap(Double giaNhap) {
    	if (giaNhap == null || giaNhap <= 0) {
            throw new IllegalArgumentException("Giá nhập phải là số dương");
        }
        this.giaNhap = giaNhap;
    }

    public Double getGiaXuat() {
        return giaXuat;
    }

    public void setGiaXuat(Double giaXuat) {
    	if (giaXuat == null || giaXuat <= 0) {
            throw new IllegalArgumentException("Giá xuất phải là số dương");
        }
        this.giaXuat = giaXuat;
    }

    public Integer getSoLuong() {
        return soLuong;
    }

    public void setSoLuong(Integer soLuong) {
    	if (soLuong == null || soLuong <= 0) {
            throw new IllegalArgumentException("Số lượng phải là số nguyên dương");
        }
        this.soLuong = soLuong;
    }

	@Override
	public String toString() {
		return "DienThoai [maDT=" + maDT + ", tenDT=" + tenDT + ", giaNhap=" + giaNhap + ", giaXuat=" + giaXuat
				+ ", soLuong=" + soLuong + "]";
	}
    
}

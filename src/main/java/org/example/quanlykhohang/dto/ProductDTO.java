package org.example.quanlykhohang.dto;

public class ProductDTO {
	private String maDT;
    private String tenDT;
    private Long soLuongNhap;
    private Long soLuongXuat;

    public ProductDTO(String maDT, String tenDT, Long soLuongNhap, Long soLuongXuat) {
        this.maDT = maDT;
        this.tenDT = tenDT;
        this.soLuongNhap = soLuongNhap;
        this.soLuongXuat = soLuongXuat;
    }

    public String getMaDT() {
        return maDT;
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

    public Long getSoLuongNhap() {
        return soLuongNhap;
    }

    public void setSoLuongNhap(Long soLuongNhap) {
        this.soLuongNhap = soLuongNhap;
    }

    public Long getSoLuongXuat() {
        return soLuongXuat;
    }

    public void setSoLuongXuat(Long soLuongXuat) {
        this.soLuongXuat = soLuongXuat;
    }

	@Override
	public String toString() {
		return "ProductDTO [maDT=" + maDT + ", tenDT=" + tenDT + ", soLuongNhap=" + soLuongNhap + ", soLuongXuat="
				+ soLuongXuat + "]";
	}
     
}

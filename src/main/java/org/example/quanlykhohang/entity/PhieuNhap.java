package org.example.quanlykhohang.entity;

import jakarta.persistence.*;
import org.example.quanlykhohang.controller.NhaCungCapController;

import java.sql.Timestamp;

@Entity
@Table(name = "PhieuNhap")
public class PhieuNhap {

	@Id
	@Column(name = "maPhieu")
	private String maPhieu;
	@Column(name = "thoiGianTao")
	private Timestamp thoiGianTao;
	@ManyToOne
	@JoinColumn(name = "nguoiTao", nullable = false)
	private NhanVien nguoiTao;
	@ManyToOne
	@JoinColumn(name = "maNhaCungCap")
	private NhaCungCap nhaCungCap;
	@Column(name = "tongTien")
	private Double tongTien;
	@Column(name = "status")
	@Enumerated(EnumType.STRING)
	private PhieuStatus status;

	public PhieuNhap() {
		this.maPhieu = generateMaPhieu();
		this.status = PhieuStatus.Done;
	}

	public PhieuNhap(Timestamp thoiGianTao, NhanVien nguoiTao, NhaCungCap nhaCungCap, Double tongTien,
			PhieuStatus status) {
		this.maPhieu = generateMaPhieu();
		this.thoiGianTao = thoiGianTao;
		this.nguoiTao = nguoiTao;
		this.nhaCungCap = nhaCungCap;
		this.tongTien = tongTien;
		this.status = status;
	}

	private String generateMaPhieu() {
		String prefix = "PN_";
		String timestamp = String.valueOf(System.currentTimeMillis());
		return prefix + timestamp;
	}

	public String getMaPhieu() {
		return maPhieu;
	}

	public void setMaPhieu(String maPhieu) {
		this.maPhieu = maPhieu;
	}

	public Timestamp getThoiGianTao() {
		return thoiGianTao;
	}

	public void setThoiGianTao(Timestamp thoiGianTao) {
		this.thoiGianTao = thoiGianTao;
	}

	public NhanVien getNguoiTao() {
		return nguoiTao;
	}

	public void setNguoiTao(NhanVien nguoiTao) {
		this.nguoiTao = nguoiTao;
	}

	public NhaCungCap getNhaCungCap() {
		return nhaCungCap;
	}

	public void setNhaCungCap(NhaCungCap nhaCungCap) {
		this.nhaCungCap = nhaCungCap;
	}

	public Double getTongTien() {
		return tongTien;
	}

	public void setTongTien(Double tongTien) {
		this.tongTien = tongTien;
	}

	public PhieuStatus getStatus() {
		return status;
	}

	public void setStatus(PhieuStatus status) {
		this.status = status;
	}

	@Override
	public String toString() {
		return "PhieuNhap [maPhieu=" + maPhieu + ", thoiGianTao=" + thoiGianTao + ", nguoiTao=" + nguoiTao
				+ ", nhaCungCap=" + nhaCungCap + ", tongTien=" + tongTien + ", status=" + status + "]";
	}
	
}

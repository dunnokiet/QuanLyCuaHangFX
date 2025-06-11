USE master;
GO

DROP DATABASE QLCH;
Go

CREATE DATABASE QLCH;
GO

USE QLCH;
GO

CREATE TABLE NguoiDung (
    ma_nguoi_dung NVARCHAR(10) PRIMARY KEY,
    ten_dang_nhap NVARCHAR(50) NOT NULL,
    mat_khau NVARCHAR(50) NOT NULL,
    vai_tro NVARCHAR(20) NOT NULL
);
GO

CREATE TABLE SanPham (
    ma_san_pham NVARCHAR(10) PRIMARY KEY,
    ten NVARCHAR(100) NOT NULL,
    loai NVARCHAR(50) NOT NULL,
    gia FLOAT NOT NULL,
    so_luong_ton INT NOT NULL
);
GO

CREATE TABLE KhachHang (
    ma_khach_hang NVARCHAR(10) PRIMARY KEY,
    ten NVARCHAR(100) NOT NULL,
    so_dien_thoai NVARCHAR(20),
    dia_chi NVARCHAR(200)
);
GO

CREATE TABLE DonHang (
    ma_don_hang NVARCHAR(10) PRIMARY KEY,
    ma_khach_hang NVARCHAR(10) NOT NULL,
    ngay_dat DATE NOT NULL,
    tong_tien FLOAT NOT NULL,
    trang_thai NVARCHAR(20) NOT NULL,
    FOREIGN KEY (ma_khach_hang) REFERENCES KhachHang(ma_khach_hang)
        ON DELETE CASCADE  
);
GO

CREATE TABLE ChiTietDonHang (
    ma_don_hang NVARCHAR(10),
    ma_san_pham NVARCHAR(10),
    so_luong INT NOT NULL,
    don_gia FLOAT NOT NULL,
    PRIMARY KEY (ma_don_hang, ma_san_pham),
    FOREIGN KEY (ma_don_hang) REFERENCES DonHang(ma_don_hang)
        ON DELETE CASCADE,  
    FOREIGN KEY (ma_san_pham) REFERENCES SanPham(ma_san_pham)
        ON DELETE CASCADE   
);
GO

INSERT INTO NguoiDung (ma_nguoi_dung, ten_dang_nhap, mat_khau, vai_tro)
VALUES 
    (N'ND101', N'admin', N'12345678', N'Quản lý');
GO

INSERT INTO SanPham (ma_san_pham, ten, loai, gia, so_luong_ton)
VALUES 
    (N'SP301', N'Laptop Lenovo ThinkPad X1', N'Máy tính xách tay', 28000000, 15),
    (N'SP302', N'Điện thoại Vivo V30', N'Điện thoại', 14000000, 20),
    (N'SP303', N'Tai nghe Sony WF-1000XM5', N'Tai nghe', 7000000, 25),
    (N'SP304', N'Máy tính bảng Huawei MatePad 11', N'Máy tính bảng', 10000000, 12),
    (N'SP305', N'Loa Bluetooth Bose SoundLink', N'Loa', 5000000, 35),
    (N'SP306', N'Máy ảnh Fujifilm X-T5', N'Máy ảnh', 42000000, 6),
    (N'SP307', N'Màn hình ASUS ProArt 32"', N'Màn hình', 15000000, 8),
    (N'SP308', N'Đồng hồ thông minh Xiaomi Watch 2', N'Đồng hồ thông minh', 4500000, 10);
GO

INSERT INTO KhachHang (ma_khach_hang, ten, so_dien_thoai, dia_chi)
VALUES 
    (N'KH301', N'Nguyễn Văn An', N'0911234567', N'12 Lý Nam Đế, Hà Nội'),
    (N'KH302', N'Trần Thị Bảo', N'0922345678', N'34 Nguyễn Huệ, TP.HCM'),
    (N'KH303', N'Phạm Văn Cường', N'0933456789', N'56 Lê Lợi, Đà Nẵng'),
    (N'KH304', N'Lê Thị Duyên', N'0944567890', N'78 Trần Phú, Cần Thơ'),
    (N'KH305', N'Võ Văn Em', N'0955678901', N'90 Hùng Vương, Nha Trang'),
    (N'KH306', N'Hoàng Thị Phương', N'0966789012', N'112 Nguyễn Văn Cừ, Huế'),
    (N'KH307', N'Đỗ Văn Quang', N'0977890123', N'134 Phạm Văn Đồng, Hải Phòng'),
    (N'KH308', N'Bùi Thị Thảo', N'0988901234', N'156 Nguyễn Trãi, Đà Lạt');
GO

INSERT INTO DonHang (ma_don_hang, ma_khach_hang, ngay_dat, tong_tien, trang_thai)
VALUES 
    (N'DH301', N'KH301', '2025-05-02', 42000000, N'Đã giao'),
    (N'DH302', N'KH302', '2025-05-05', 7000000, N'Đang xử lý'),
    (N'DH303', N'KH303', '2025-05-08', 47000000, N'Hủy'),
    (N'DH304', N'KH304', '2025-05-11', 20000000, N'Đã giao'),
    (N'DH305', N'KH305', '2025-05-14', 5000000, N'Đang xử lý'),
    (N'DH306', N'KH306', '2025-05-17', 73000000, N'Đã giao'),
    (N'DH307', N'KH307', '2025-05-21', 14000000, N'Hủy'),
    (N'DH308', N'KH308', '2025-05-26', 24500000, N'Đang xử lý');
GO

INSERT INTO ChiTietDonHang (ma_don_hang, ma_san_pham, so_luong, don_gia)
VALUES 
    (N'DH301', N'SP301', 1, 28000000),
    (N'DH301', N'SP302', 1, 14000000),
    (N'DH302', N'SP303', 1, 7000000),
    (N'DH303', N'SP306', 1, 42000000),
    (N'DH303', N'SP305', 1, 5000000),
    (N'DH304', N'SP304', 2, 10000000),
    (N'DH305', N'SP305', 1, 5000000),
    (N'DH306', N'SP306', 1, 42000000),
    (N'DH306', N'SP301', 1, 31000000),
    (N'DH307', N'SP302', 1, 14000000),
    (N'DH308', N'SP307', 1, 15000000),
    (N'DH308', N'SP308', 1, 9500000);
GO
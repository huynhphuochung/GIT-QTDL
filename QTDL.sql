-- Tạo CSDL
CREATE DATABASE QuanLyNhanSu;
USE QuanLyNhanSu;

-- Bảng Phòng Ban
CREATE TABLE PhongBan (
    ma_phong_ban INT PRIMARY KEY AUTO_INCREMENT,
    ten_phong_ban VARCHAR(100) NOT NULL
);//nhatanh

-- Bảng Chức Vụ
CREATE TABLE ChucVu (
    ma_chuc_vu INT PRIMARY KEY AUTO_INCREMENT,
    ten_chuc_vu VARCHAR(100) NOT NULL,
    luong_co_ban DECIMAL(10,2) NOT NULL
);//nhatanh

-- Bảng Nhân Viên
CREATE TABLE NhanVien (
    ma_nhan_vien INT PRIMARY KEY AUTO_INCREMENT,
    ho_ten VARCHAR(150) NOT NULL,
    ngay_sinh DATE NOT NULL,
    gioi_tinh ENUM('Nam', 'Nữ', 'Khác') NOT NULL,
    so_dien_thoai VARCHAR(20),
    dia_chi TEXT,
    ma_phong_ban INT,
    ma_chuc_vu INT, -- Liên kết với chức vụ
    trang_thai ENUM('Đang làm', 'Đã nghỉ', 'Nghỉ hưu') DEFAULT 'Đang làm',
    hinh_anh VARCHAR(255), 
    ghi_chu TEXT,
    FOREIGN KEY (ma_phong_ban) REFERENCES PhongBan(ma_phong_ban),
    FOREIGN KEY (ma_chuc_vu) REFERENCES ChucVu(ma_chuc_vu)
);//xong

-- Bảng Lương
CREATE TABLE Luong (
    ma_luong INT PRIMARY KEY AUTO_INCREMENT,
    ma_nhan_vien INT,
    so_tien DECIMAL(10,2),
    loai_luong ENUM('Cơ bản', 'Thưởng', 'Phụ cấp') DEFAULT 'Cơ bản',
    ngay_tra DATE NOT NULL,
    FOREIGN KEY (ma_nhan_vien) REFERENCES NhanVien(ma_nhan_vien)
);//nhut anh

-- Bảng Chấm Công
CREATE TABLE ChamCong (
    ma_cham_cong INT PRIMARY KEY AUTO_INCREMENT,
    ma_nhan_vien INT,
    ngay DATE NOT NULL,
    gio_vao TIME,
    gio_ra TIME,
    trang_thai ENUM('Có mặt', 'Vắng', 'Đi trễ', 'Nghỉ phép') DEFAULT 'Có mặt',
    FOREIGN KEY (ma_nhan_vien) REFERENCES NhanVien(ma_nhan_vien)
);//nhutanh

-- Bảng Nghỉ Phép
CREATE TABLE NghiPhep (
    ma_nghi_phep INT PRIMARY KEY AUTO_INCREMENT,
    ma_nhan_vien INT,
    ngay_bat_dau DATE NOT NULL,
    ngay_ket_thuc DATE NOT NULL,
    ly_do TEXT,
    trang_thai ENUM('Chờ duyệt', 'Đã duyệt', 'Từ chối') DEFAULT 'Chờ duyệt',
    FOREIGN KEY (ma_nhan_vien) REFERENCES NhanVien(ma_nhan_vien)
);//nhatanh

-- Bảng Người Dùng (Tài khoản đăng nhập)
CREATE TABLE NguoiDung (
    ma_nguoi_dung INT PRIMARY KEY AUTO_INCREMENT,
    ten_dang_nhap VARCHAR(50) UNIQUE NOT NULL,
    mat_khau VARCHAR(255) NOT NULL,
    ngay_tao TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Chèn dữ liệu vào bảng Phòng Ban
INSERT INTO PhongBan (ten_phong_ban) VALUES
('Hành chính'),
('Kỹ thuật'),
('Nhân sự');

-- Chèn dữ liệu vào bảng Chức Vụ
INSERT INTO ChucVu (ten_chuc_vu, luong_co_ban) VALUES
('Nhân viên', 7000000),
('Trưởng phòng', 15000000),
('Giám đốc', 30000000);

-- Chèn dữ liệu vào bảng Nhân Viên
INSERT INTO NhanVien (ho_ten, ngay_sinh, gioi_tinh, so_dien_thoai, dia_chi, ma_phong_ban, ma_chuc_vu, trang_thai, hinh_anh, ghi_chu)
VALUES
('Nguyễn Văn A', '1990-05-15', 'Nam', '0912345678', 'Hà Nội', 1, 2, 'Đang làm', NULL, 'Trưởng phòng hành chính'),
('Trần Thị B', '1995-08-22', 'Nữ', '0987654321', 'TP.HCM', 2, 1, 'Đang làm', NULL, 'Nhân viên kỹ thuật'),
('Lê Văn C', '1988-12-10', 'Nam', '0934567890', 'Đà Nẵng', 3, 3, 'Đang làm', NULL, 'Giám đốc công ty'),
('Phạm Văn D', '1993-07-05', 'Nam', '0971234567', 'Hải Phòng', 2, 1, 'Đang làm', NULL, 'Nhân viên kỹ thuật'),
('Hoàng Thị E', '1997-03-18', 'Nữ', '0965432109', 'Cần Thơ', 1, 1, 'Đang làm', NULL, 'Nhân viên hành chính');

-- Chèn dữ liệu vào bảng Lương
INSERT INTO Luong (ma_nhan_vien, so_tien, loai_luong, ngay_tra)
VALUES
(1, 15000000, 'Cơ bản', '2025-03-01'),
(2, 7000000, 'Cơ bản', '2025-03-01'),
(3, 30000000, 'Cơ bản', '2025-03-01'),
(4, 7000000, 'Cơ bản', '2025-03-01'),
(5, 7000000, 'Cơ bản', '2025-03-01');

-- Chèn dữ liệu vào bảng Chấm Công
INSERT INTO ChamCong (ma_nhan_vien, ngay, gio_vao, gio_ra, trang_thai)
VALUES
(1, '2025-03-05', '08:00:00', '17:00:00', 'Có mặt'),
(2, '2025-03-05', '08:15:00', '17:00:00', 'Đi trễ'),
(3, '2025-03-05', '08:00:00', '17:30:00', 'Có mặt'),
(4, '2025-03-05', '08:30:00', '16:30:00', 'Đi trễ'),
(5, '2025-03-05', '08:00:00', '17:00:00', 'Có mặt');

-- Chèn dữ liệu vào bảng Nghỉ Phép
INSERT INTO NghiPhep (ma_nhan_vien, ngay_bat_dau, ngay_ket_thuc, ly_do, trang_thai)
VALUES
(2, '2025-03-10', '2025-03-12', 'Nghỉ phép cá nhân', 'Chờ duyệt'),
(4, '2025-03-15', '2025-03-16', 'Nghỉ bệnh', 'Đã duyệt');

-- Chèn dữ liệu vào bảng Người Dùng (Chỉ 1 tài khoản quản trị)
INSERT INTO NguoiDung (ten_dang_nhap, mat_khau)
VALUES 
('admin', 'admin123');

-- Cập nhật thông tin nhân viên
drop PROCEDURE UpdateNhanVien;

DELIMITER &&

CREATE PROCEDURE UpdateNhanVien(
    IN p_ma_nhan_vien INT,
    IN p_ho_ten VARCHAR(150),
    IN p_ngay_sinh DATE,
    IN p_gioi_tinh ENUM('Nam', 'Nữ', 'Khác'),
    IN p_so_dien_thoai VARCHAR(20),
    IN p_dia_chi TEXT,
    IN p_trang_thai ENUM('Đang làm', 'Đã nghỉ', 'Nghỉ hưu'),
    IN p_hinh_anh VARCHAR(255)
)
BEGIN
    UPDATE NhanVien
    SET
        ho_ten = p_ho_ten,
        ngay_sinh = p_ngay_sinh,
        gioi_tinh = p_gioi_tinh,
        so_dien_thoai = p_so_dien_thoai,
        dia_chi = p_dia_chi,
        trang_thai = p_trang_thai,
        hinh_anh = p_hinh_anh
    WHERE ma_nhan_vien = p_ma_nhan_vien;
END &&

DELIMITER ;

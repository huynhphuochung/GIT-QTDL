package com.example.quantridulieu;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.sql.*;

public class myjbdc {
    private static final String URL = "jdbc:mysql://127.0.0.1:3306/quanlynhansu";
    private static final String USER = "root";
    private static final String PASSWORD = "123456";
    private static final String IMAGE_DIRECTORY = "C:\\Users\\huynh\\IdeaProjects\\QUANTRIDULIEU\\src\\main\\resources\\image"; // Thư mục lưu ảnh

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);

    }

    public static ObservableList<Employee> getEmployeeList() {
        ObservableList<Employee> employeeList = FXCollections.observableArrayList();
        String sql = "{CALL Employeelist()}";

        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             CallableStatement stmt = conn.prepareCall(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                String id = rs.getString("ma_nhan_vien");
                String name = rs.getString("ho_ten");
                int sdt = rs.getInt("so_dien_thoai");
                String gioitinhnv = rs.getString("gioi_tinh");
                String trangthai = rs.getString("trang_thai");
                String chucvu = rs.getString("ma_chuc_vu");
                String ngaysinh = rs.getString("ngay_sinh");
                String diachi = rs.getString("dia_chi");
                double heluong = rs.getDouble("he_so_luong");
                String hinhanh =rs.getString("hinh_anh");
                Employee employee = new Employee(id, name, sdt, gioitinhnv, trangthai, chucvu, ngaysinh, diachi, heluong, hinhanh);
                employeeList.add(employee);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return employeeList;
    }

    public void updateEmployee(int maNhanVien, String hoTen, String ngaySinh,
                               String gioiTinh, String soDienThoai, String diaChi,
                               String trangThai, String hinhAnh) {
        String sql = "CALL UpdateNhanVien(?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, maNhanVien);
            stmt.setString(2, hoTen);
            stmt.setString(3, ngaySinh);
            stmt.setString(4, gioiTinh);
            stmt.setString(5, soDienThoai);
            stmt.setString(6, diaChi);
            stmt.setString(7, trangThai);
            stmt.setString(8, hinhAnh);

            stmt.executeUpdate();
            System.out.println("Cập nhật nhân viên thành công!");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public static double[] getTyLeNamNuFromProcedure() {
        double[] tyLe = new double[2];  // [0] -> tỷ lệ nam, [1] -> tỷ lệ nữ
        String sql = "{CALL getTyLeNamNu()}";  // Gọi stored procedure

        try (Connection conn = DriverManager.getConnection(URL,USER,PASSWORD);
             CallableStatement stmt = conn.prepareCall(sql);
             ResultSet rs = stmt.executeQuery()) {

            if (rs.next()) {
                tyLe[0] = rs.getDouble("ty_le_nam");  // Lấy tỷ lệ nam
                tyLe[1] = rs.getDouble("ty_le_nu");   // Lấy tỷ lệ nữ
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return tyLe;
    }
}

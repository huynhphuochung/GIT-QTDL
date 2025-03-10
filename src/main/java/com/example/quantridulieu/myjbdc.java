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
    private static final String PASSWORD = "123456789";
    private static final String IMAGE_DIRECTORY = "src/main/resources/images/"; // Thư mục lưu ảnh

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

                Employee employee = new Employee(id, name, sdt, gioitinhnv, trangthai, chucvu, ngaysinh);
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
    public static String uploadImage(File imageFile) {
        if (imageFile == null) {
            return null; // Nếu không có ảnh, trả về null
        }

        File directory = new File(IMAGE_DIRECTORY);
        if (!directory.exists()) {
            directory.mkdirs(); // Tạo thư mục nếu chưa có
        }

        String newFileName = System.currentTimeMillis() + "_" + imageFile.getName(); // Đổi tên ảnh tránh trùng
        File destinationFile = new File(directory, newFileName);

        try {
            Files.copy(imageFile.toPath(), destinationFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
            return IMAGE_DIRECTORY + newFileName; // Trả về đường dẫn ảnh mới
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}

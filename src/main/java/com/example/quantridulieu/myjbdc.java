package com.example.quantridulieu;

import java.sql.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class myjbdc {

    private static final String URL = "jdbc:mysql://127.0.0.1:3306/quanlynhansu";
    private static final String USER = "root";
    private static final String PASSWORD = "123456789";

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

    public static void updateEmployee(int id, String name, String birthDate, String gender,
                                      String phone, String address, String status) {
        String sql = "{CALL UpdateNhanVien(?, ?, ?, ?, ?, ?, ?)}";  // Chỉ có 7 tham số

        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             CallableStatement stmt = conn.prepareCall(sql)) {

            stmt.setInt(1, id);
            stmt.setString(2, name);
            stmt.setDate(3, Date.valueOf(birthDate));
            stmt.setString(4, gender);
            stmt.setString(5, phone);
            stmt.setString(6, address);
            stmt.setString(7, status);

            int rowsUpdated = stmt.executeUpdate();
            if (rowsUpdated > 0) {
                System.out.println("Cập nhật nhân viên thành công!");
            } else {
                System.out.println("Không tìm thấy nhân viên để cập nhật.");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}

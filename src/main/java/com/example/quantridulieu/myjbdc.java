package com.example.quantridulieu;

import java.sql.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class myjbdc {

    private static final String URL = "jdbc:mysql://127.0.0.1:3306/quanlynhansu";
    private static final String USER = "root";
    private static final String PASSWORD = "123456";

    public static ObservableList<Employee> getEmployeeList() {
        ObservableList<Employee> employeeList = FXCollections.observableArrayList();
        String sql = "{CALL Employeelist()}"; // Gọi lại stored procedure Employeelist()

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
}

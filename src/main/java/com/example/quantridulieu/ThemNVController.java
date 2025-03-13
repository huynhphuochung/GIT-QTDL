package com.example.quantridulieu;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;

import java.util.HashMap;
import java.util.Map;
import java.sql.Connection;
import java.sql.CallableStatement;
import java.sql.SQLException;
import java.sql.Date;

public class ThemNVController {

    @FXML
    private TextField hoTenField;

    @FXML
    private DatePicker ngaysinhProperty;

    @FXML
    private DatePicker ngayVaoCongTyProperty;  // Thêm trường ngày vào công ty

    @FXML
    private ComboBox<String> gioiTinhComboBox;

    @FXML
    private TextField soDienThoaiField;

    @FXML
    private TextField diaChiField;

    @FXML
    private ComboBox<String> phongBanComboBox;

    @FXML
    private ComboBox<String> chucVuComboBox;

    @FXML
    private TextArea ghiChuArea;

    private static final String DB_URL = "jdbc:mysql://localhost:3306/quanlynhansu";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "123456";

    private final Map<String, Integer> phongBanMap = new HashMap<>();
    private final Map<String, Integer> chucVuMap = new HashMap<>();

    @FXML
    public void initialize() {
        // Thiết lập dữ liệu cho ComboBox giới tính
        gioiTinhComboBox.setItems(FXCollections.observableArrayList("Nam", "Nữ"));

        // Thiết lập dữ liệu cho ComboBox phòng ban
        phongBanMap.put("Hành chính", 1);
        phongBanMap.put("Kỹ thuật", 2);
        phongBanMap.put("Nhân sự", 3);
        phongBanComboBox.setItems(FXCollections.observableArrayList(phongBanMap.keySet()));

        // Thiết lập dữ liệu cho ComboBox chức vụ
        chucVuMap.put("Nhân viên", 1);
        chucVuMap.put("Trưởng phòng", 2);
        chucVuMap.put("Giám đốc", 3);
        chucVuComboBox.setItems(FXCollections.observableArrayList(chucVuMap.keySet()));
    }

    @FXML
    private void lamMoiForm() {
        clearFields();
    }

    @FXML
    private void huyBo() {
        clearFields();
    }

    @FXML
    private void themNhanVien() {
        try {
            // Kiểm tra dữ liệu đầu vào
            if (!validateInput()) {
                return;
            }

            // Lấy dữ liệu từ các trường nhập liệu
            String hoTen = hoTenField.getText();
            LocalDate ngaySinh = ngaysinhProperty.getValue();
            LocalDate ngayVaoCongTy = ngayVaoCongTyProperty.getValue(); // Lấy ngày vào công ty
            String gioiTinh = gioiTinhComboBox.getValue();
            String soDienThoai = soDienThoaiField.getText();
            String diaChi = diaChiField.getText();
            int maPhongBan = getMaPhongBan(phongBanComboBox.getValue());
            int maChucVu = getMaChucVu(chucVuComboBox.getValue());
            String ghiChu = ghiChuArea.getText().trim();

            // Kiểm tra tuổi (đủ 18 tuổi)
            if (!kiemTraTuoi(ngaySinh)) {
                showAlert("Lỗi", "Nhân viên phải đủ 18 tuổi!", Alert.AlertType.WARNING);
                return;
            }

            // Kiểm tra số điện thoại
            if (!validatePhoneNumber(soDienThoai)) {
                showAlert("Lỗi", "Số điện thoại không hợp lệ!", Alert.AlertType.WARNING);
                return;
            }

            // Lưu nhân viên vào cơ sở dữ liệu
            if (luuNhanVien(hoTen, ngaySinh, ngayVaoCongTy, gioiTinh, soDienThoai, diaChi, maPhongBan, maChucVu, ghiChu)) {
                showAlert("Thành công", "Nhân viên đã được thêm thành công!", Alert.AlertType.INFORMATION);
                clearFields(); // Xóa sạch dữ liệu nhập sau khi thêm thành công
            } else {
                showAlert("Lỗi", "Không thể thêm nhân viên!", Alert.AlertType.ERROR);
            }
        } catch (Exception e) {
            e.printStackTrace();
            showAlert("Lỗi", "Đã xảy ra lỗi khi thêm nhân viên!", Alert.AlertType.ERROR);
        }
    }

    // Kiểm tra dữ liệu đầu vào
    private boolean validateInput() {
        if (hoTenField.getText().trim().isEmpty() ||
                ngaysinhProperty.getValue() == null ||
                ngayVaoCongTyProperty.getValue() == null ||  // Kiểm tra "Ngày vào công ty"
                gioiTinhComboBox.getValue() == null ||
                soDienThoaiField.getText().trim().isEmpty() ||
                diaChiField.getText().trim().isEmpty() ||
                phongBanComboBox.getValue() == null ||
                chucVuComboBox.getValue() == null) {
            showAlert("Lỗi", "Vui lòng điền đầy đủ thông tin!", Alert.AlertType.WARNING);
            return false;
        }
        return true;
    }

    // Kiểm tra tuổi (đủ 18 tuổi)
    private boolean kiemTraTuoi(LocalDate ngaySinh) {
        if (ngaySinh == null) {
            return false;
        }
        LocalDate today = LocalDate.now();

        // Tính tuổi bằng cách so sánh ngày sinh và ngày hiện tại
        int age = today.getYear() - ngaySinh.getYear();

        // Điều chỉnh nếu ngày sinh đã chưa qua trong năm nay
        if (today.getMonthValue() < ngaySinh.getMonthValue() ||
                (today.getMonthValue() == ngaySinh.getMonthValue() && today.getDayOfMonth() < ngaySinh.getDayOfMonth())) {
            age--;
        }

        return age >= 18;
    }


    // Kiểm tra định dạng số điện thoại
    private boolean validatePhoneNumber(String phoneNumber) {
        // Kiểm tra số điện thoại có đúng định dạng không (ví dụ: chỉ chứa số và có 10 chữ số)
        return phoneNumber.matches("\\d{10}");
    }

    // Lưu nhân viên vào cơ sở dữ liệu
    public boolean luuNhanVien(String hoTen, LocalDate ngaySinh, LocalDate ngayVaoCongTy, String gioiTinh,
                               String soDienThoai, String diaChi, int maPhongBan, int maChucVu, String ghiChu) {
        String sql = "{CALL InsertNhanVien(?, ?, ?, ?, ?, ?, ?, ?, ?)}";  // Câu lệnh gọi stored procedure

        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             CallableStatement stmt = conn.prepareCall(sql)) {

            // Thiết lập giá trị cho các tham số của stored procedure
            stmt.setString(1, hoTen);
            stmt.setDate(2, Date.valueOf(ngaySinh));  // Chuyển đổi LocalDate thành java.sql.Date
            stmt.setDate(3, Date.valueOf(ngayVaoCongTy));  // Chuyển đổi LocalDate thành java.sql.Date
            stmt.setString(4, gioiTinh);
            stmt.setString(5, soDienThoai);
            stmt.setString(6, diaChi);
            stmt.setInt(7, maPhongBan);
            stmt.setInt(8, maChucVu);
            stmt.setString(9, ghiChu);

            // Thực thi stored procedure
            int rowsAffected = stmt.executeUpdate();

            // Nếu có ít nhất 1 dòng bị ảnh hưởng, nghĩa là chèn thành công
            return rowsAffected > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;  // Trả về false nếu có lỗi
        }
    }

    // Lấy mã phòng ban từ tên phòng ban
    private int getMaPhongBan(String tenPhongBan) {
        return phongBanMap.getOrDefault(tenPhongBan, -1);
    }

    // Lấy mã chức vụ từ tên chức vụ
    private int getMaChucVu(String tenChucVu) {
        return chucVuMap.getOrDefault(tenChucVu, -1);
    }

    // Hiển thị thông báo (Alert)
    private void showAlert(String title, String message, Alert.AlertType alertType) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    // Xóa sạch dữ liệu nhập sau khi thêm thành công
    private void clearFields() {
        hoTenField.clear();
        ngaysinhProperty.setValue(null);
        ngayVaoCongTyProperty.setValue(null);  // Xóa trường "Ngày vào công ty"
        gioiTinhComboBox.getSelectionModel().clearSelection();
        soDienThoaiField.clear();
        diaChiField.clear();
        phongBanComboBox.getSelectionModel().clearSelection();
        chucVuComboBox.getSelectionModel().clearSelection();
        ghiChuArea.clear();
    }
    //
    @FXML
    public void ondanhsachnv(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/quantridulieu/P1.2.fxml"));
            Scene loginScene = new Scene(loader.load());

            Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            currentStage.setScene(loginScene);
            currentStage.show();
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Error logging out");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }
    }
    @FXML
    public void ontk(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/quantridulieu/tk.fxml"));
            Scene loginScene = new Scene(loader.load());

            Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            currentStage.setScene(loginScene);
            currentStage.show();
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Error logging out");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }
    }
}

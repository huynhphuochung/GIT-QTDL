package com.example.quantridulieu;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class LoginController {
    private Stage stage;  // Stage của ứng dụng
    public void setStage(Stage stage) {
        this.stage = stage;
    }
    @FXML
    private TextField usernameField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private Button loginButton;

    // Phương thức xử lý sự kiện khi người dùng bấm nút đăng nhập
    @FXML
    private void handleLoginButtonAction() {
        String username = usernameField.getText();
        String password = passwordField.getText();

        // Kiểm tra thông tin đăng nhập (Ví dụ đơn giản: "admin" / "admin123")
        if (isLoginValid(username, password)) {
            try {
                // Tải trang p1.fxml
                FXMLLoader loader = new FXMLLoader(getClass().getResource("P1.2.fxml"));
                Parent root = loader.load();

                // Chuyển sang trang p1.fxml
                Stage stage = (Stage) loginButton.getScene().getWindow(); // Lấy cửa sổ hiện tại
                stage.setScene(new Scene(root));  // Cập nhật scene của cửa sổ
                stage.show();  // Hiển thị trang mới
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("Thông tin đăng nhập không hợp lệ!");
            // Có thể hiển thị thông báo lỗi tại đây
        }
    }

    // Phương thức kiểm tra tính hợp lệ của thông tin đăng nhập
    private boolean isLoginValid(String username, String password) {
        // Kiểm tra username và password (Ví dụ: "admin" / "admin123")
        return "admin".equals(username) && "admin123".equals(password);
    }

}


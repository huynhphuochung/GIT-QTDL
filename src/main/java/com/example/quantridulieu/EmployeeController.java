package com.example.quantridulieu;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javafx.scene.image.Image;
import javafx.stage.FileChooser;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;

import java.io.ByteArrayInputStream;
import java.text.SimpleDateFormat;

public class EmployeeController {

    @FXML
    private TableView<Employee> employeeTableView;
    @FXML
    private TableColumn<Employee, String> idcolumn;
    @FXML
    private TableColumn<Employee, String> namecolumn;
    @FXML
    private TableColumn<Employee, Integer> sdtcolumn;
    @FXML
    private TableColumn<Employee, String> gioitinhcolumn;
    @FXML
    private TableColumn<Employee, String> trangthaicolumn;
    @FXML
    private TextField idtextfield;
    @FXML
    private TextField trangthaitextfield;
    @FXML
    private TextField gioitinhtextfield;
    @FXML
    private TextField sdttextfield;
    @FXML
    private TextField hotentextfield;
    @FXML
    private TextField chucvutextfield;
    @FXML
    private TextField ngaysinhtextfield;
    @FXML
    private TextField diachitextfield;
    @FXML
    private TextField hesoluongtextfield;
    @FXML
    private ImageView imageurl;
    @FXML
    private Button uploadImageButton;
    @FXML
    private String selectedImagePath = null;  // Đường dẫn ảnh đã chọn
    @FXML
    private myjbdc myjbdc = new myjbdc(); // Khởi tạo đối tượng kết nối

    @FXML
    private void initialize() {
        idcolumn.setCellValueFactory(cellData -> cellData.getValue().idnvProperty());
        namecolumn.setCellValueFactory(cellData -> cellData.getValue().hotennvProperty());
        gioitinhcolumn.setCellValueFactory(cellData -> cellData.getValue().gioitinhnvProperty());
        trangthaicolumn.setCellValueFactory(cellData -> cellData.getValue().trangthaiProperty());
        sdtcolumn.setCellValueFactory(cellData -> cellData.getValue().sdtProperty().asObject());

        ObservableList<Employee> employeeList = myjbdc.getEmployeeList();
        employeeTableView.setItems(employeeList);

        employeeTableView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                idtextfield.setText(newValue.getidnv().get());
                trangthaitextfield.setText(newValue.gettrangthainhnnv().get());
                gioitinhtextfield.setText(newValue.getgioitinhnnv().get());
                sdttextfield.setText(String.valueOf(newValue.getsdt().get()));
                hotentextfield.setText(newValue.gethotennv().get());
                chucvutextfield.setText(newValue.getchucvu().get());
                ngaysinhtextfield.setText(newValue.getNgaysinh().get());
                diachitextfield.setText(newValue.getdiachi().get());
                hesoluongtextfield.setText(String.valueOf(newValue.getheluong().get()));

                // Load ảnh
                String selectedImagePath = newValue.gethinhanh().get();
                if (selectedImagePath != null && !selectedImagePath.isEmpty()) {
                    File imageFile = new File("src/main/resources/" + selectedImagePath);
                    if (imageFile.exists()) {
                        Image image = new Image(imageFile.toURI().toString());
                        imageurl.setImage(image);
                    } else {
                        System.out.println("⚠ Ảnh không tồn tại: " + imageFile.getAbsolutePath());
                        imageurl.setImage(null);
                    }
                } else {
                    imageurl.setImage(null);
                }
            }
        });
    }

        @FXML
    private void onUploadImage() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.jpeg"));
        File file = fileChooser.showOpenDialog(null);

        if (file != null) {
            try {
                String destDirectory = "src/main/resources/image/";
                File destFolder = new File(destDirectory);
                if (!destFolder.exists()) {
                    destFolder.mkdirs();
                }

                String newFileName = System.currentTimeMillis() + "_" + file.getName();
                File destFile = new File(destFolder, newFileName);

                Files.copy(file.toPath(), destFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
                selectedImagePath = "image/" + newFileName; // Lưu đường dẫn tương đối

                Image image = new Image(destFile.toURI().toString());
                imageurl.setImage(image);

                System.out.println("Ảnh đã lưu tại: " + selectedImagePath);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @FXML
    private void onUpdate(ActionEvent event) {
        try {
            int id = Integer.parseInt(idtextfield.getText());
            String name = hotentextfield.getText();
            String birthDate = ngaysinhtextfield.getText();
            String gender = gioitinhtextfield.getText();
            String phone = sdttextfield.getText();
            String address = diachitextfield.getText();
            String status = trangthaitextfield.getText();

            // Kiểm tra nếu chưa có ảnh mới thì giữ ảnh cũ
            if (selectedImagePath == null || selectedImagePath.isEmpty()) {
                selectedImagePath = employeeTableView.getSelectionModel().getSelectedItem().gethinhanh().get();
            }

            myjbdc.updateEmployee(id, name, birthDate, gender, phone, address, status, selectedImagePath);
            employeeTableView.setItems(myjbdc.getEmployeeList());

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Thành công");
            alert.setHeaderText(null);
            alert.setContentText("Cập nhật nhân viên thành công!");
            alert.showAndWait();
        } catch (NumberFormatException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Lỗi");
            alert.setHeaderText("Dữ liệu không hợp lệ");
            alert.setContentText("Vui lòng nhập đúng định dạng.");
            alert.showAndWait();
        }
    }

    public void onLogout(ActionEvent event) {
        // Xử lý đăng xuất và mở lại màn hình đăng nhập
        try {
            // Tạo một cửa sổ đăng nhập mới (HelloApplication là class của màn hình đăng nhập của bạn)
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/quantridulieu/login.fxml"));
            Scene loginScene = new Scene(loader.load());

            // Lấy Stage hiện tại từ sự kiện
            Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            currentStage.setScene(loginScene);  // Chuyển cảnh
            currentStage.show();  // Hiển thị cửa sổ

        } catch (Exception e) {
            // Nếu có lỗi xảy ra khi đăng xuất, hiển thị thông báo lỗi
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Error logging out");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }
    }
}
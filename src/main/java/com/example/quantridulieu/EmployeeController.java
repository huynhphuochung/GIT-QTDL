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

        // Kiểm tra nếu danh sách không rỗng và chọn nhân viên đầu tiên
        if (!employeeList.isEmpty()) {
            employeeTableView.getSelectionModel().select(0);  // Chọn nhân viên đầu tiên
            Employee firstEmployee = employeeTableView.getSelectionModel().getSelectedItem();  // Lấy nhân viên đầu tiên
            loadEmployeeDetails(firstEmployee);  // Gọi phương thức để hiển thị chi tiết nhân viên
        }

        // Lắng nghe sự thay đổi chọn nhân viên và cập nhật thông tin chi tiết
        employeeTableView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                loadEmployeeDetails(newValue);  // Cập nhật thông tin khi chọn nhân viên mới
            }
        });
    }

    // Phương thức để tải chi tiết của nhân viên vào các trường thông tin
    private void loadEmployeeDetails(Employee employee) {
        idtextfield.setText(employee.getidnv().get());
        trangthaitextfield.setText(employee.gettrangthainhnnv().get());
        gioitinhtextfield.setText(employee.getgioitinhnnv().get());
        sdttextfield.setText(String.valueOf(employee.getsdt().get()));
        hotentextfield.setText(employee.gethotennv().get());
        chucvutextfield.setText(employee.getchucvu().get());
        ngaysinhtextfield.setText(employee.getNgaysinh().get());
        diachitextfield.setText(employee.getdiachi().get());
        hesoluongtextfield.setText(String.valueOf(employee.getheluong().get()));

        // Tải ảnh
        String selectedImagePath = employee.gethinhanh().get();
        if (selectedImagePath != null && !selectedImagePath.isEmpty()) {
            File imageFile = new File("C:\\Users\\huynh\\IdeaProjects\\QUANTRIDULIEU\\src\\main\\resources\\image" + selectedImagePath);
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
    @FXML
    private void onUploadImage() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.jpeg"));
        File file = fileChooser.showOpenDialog(null);

        if (file != null) {
            try {
                // Đường dẫn lưu ảnh trong dự án
                String destDirectory = "src/main/resources/image/";
                File destFolder = new File(destDirectory);

                // Tạo thư mục nếu chưa tồn tại
                if (!destFolder.exists()) {
                    destFolder.mkdirs();
                }

                // Tạo tên file mới
                String newFileName = System.currentTimeMillis() + "_" + file.getName();
                File destFile = new File(destFolder, newFileName);

                // Copy file vào thư mục lưu ảnh
                Files.copy(file.toPath(), destFile.toPath(), StandardCopyOption.REPLACE_EXISTING);

                // Lưu đường dẫn vào biến `selectedImagePath`
                selectedImagePath = "/" + newFileName;  // Chỉ lưu đường dẫn tương đối

                // Hiển thị ảnh
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
    @FXML
    public void onthemnv(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/quantridulieu/ThemNV(2).fxml"));
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


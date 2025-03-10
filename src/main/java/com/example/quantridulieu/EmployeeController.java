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
    private  TextField diachitextfield;
    @FXML
    private TextField hsluongtextfild;
    @FXML
    private ImageView employeeImageView;
    @FXML
    private Button uploadImageButton;
    @FXML
    private String selectedImagePath = null;  // Đường dẫn ảnh đã chọn
    @FXML
    private myjbdc myjbdc = new myjbdc(); // Khởi tạo đối tượng kết nối



    @FXML
    private void initialize() {
        // Gán dữ liệu cho các cột của TableView
     idcolumn.setCellValueFactory(cellData -> cellData.getValue().idnvProperty() );
     namecolumn.setCellValueFactory(cellData -> cellData.getValue().hotennvProperty() );
     gioitinhcolumn.setCellValueFactory(cellData -> cellData.getValue().gioitinhnvProperty() );
     trangthaicolumn.setCellValueFactory(cellData -> cellData.getValue().trangthaiProperty() );
     sdtcolumn.setCellValueFactory(cellData -> cellData.getValue().sdtProperty().asObject());

        ObservableList<Employee> employeeList = myjbdc.getEmployeeList();
     employeeTableView.setItems(employeeList);
        employeeTableView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                // Cập nhật thông tin trong các TextField khi người dùng chọn nhân viên
                idtextfield.setText(newValue.getidnv().get());
                trangthaitextfield.setText(newValue.gettrangthainhnnv().get());
                gioitinhtextfield.setText(newValue.getgioitinhnnv().get());
                sdttextfield.setText(String.valueOf(newValue.getsdt().get()));
                hotentextfield.setText(newValue.gethotennv().get());
                chucvutextfield.setText(newValue.getchucvu().get());
                ngaysinhtextfield.setText(newValue.getNgaysinh().get());
            }
        });
        if (!employeeList.isEmpty()) {
            Employee firstEmployee = employeeList.get(0); // Lấy nhân viên đầu tiên
            idtextfield.setText(firstEmployee.getidnv().get());
            trangthaitextfield.setText(firstEmployee.gettrangthainhnnv().get());
            gioitinhtextfield.setText(firstEmployee.getgioitinhnnv().get());
            sdttextfield.setText(String.valueOf(firstEmployee.getsdt().get()));
            hotentextfield.setText(firstEmployee.gethotennv().get());
            // Tự động chọn nhân viên đầu tiên trong TableView
            employeeTableView.getSelectionModel().select(0);
        }
    }


    private Stage stage;  // Stage của ứng dụng
    public void setStage(Stage stage) {
        this.stage = stage;
    }


    // Hàm để xử lý sự kiện đăng xuất
    @FXML
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
                selectedImagePath = "images/" + newFileName;  // Chỉ lưu đường dẫn tương đối

                // Hiển thị ảnh
                Image image = new Image(destFile.toURI().toString());
                employeeImageView.setImage(image);

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

}


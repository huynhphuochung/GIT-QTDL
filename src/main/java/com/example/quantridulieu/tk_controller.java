package com.example.quantridulieu;
//import thư viện
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Alert;
import javafx.stage.Stage;
//import thư viện

public class tk_controller {

    @FXML
    private PieChart piechart_nhan_vien_nam_nu; // biến dùng để kết nối với biểu đồ tròn ở tk.fxml

    public void initialize() {
        double[] tyLe = myjbdc.getTyLeNamNuFromProcedure();
        // Tạo dữ liệu cho biểu đồ
        PieChart.Data slice1 = new PieChart.Data("Nam", tyLe[0]);  // 60% là nam
        PieChart.Data slice2 = new PieChart.Data("Nữ", tyLe[1]);  // 40% là nữ

        // Thêm dữ liệu vào biểu đồ
        piechart_nhan_vien_nam_nu.getData().addAll(slice1, slice2);

    }
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

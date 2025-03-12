package com.example.quantridulieu;

import javafx.fxml.FXML;
import javafx.scene.chart.PieChart;

public class tk_controller {
    @FXML
    private PieChart piechart_nhan_vien_nam_nu;

    public void initialize() {
        // Tạo dữ liệu cho biểu đồ
        PieChart.Data slice1 = new PieChart.Data("Nam", 60);  // 60% là nam
        PieChart.Data slice2 = new PieChart.Data("Nữ", 40);  // 40% là nữ

        // Thêm dữ liệu vào biểu đồ
        piechart_nhan_vien_nam_nu.getData().addAll(slice1, slice2);

    }
}

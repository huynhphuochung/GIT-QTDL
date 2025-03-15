package com.example.quantridulieu;
import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Image;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.Document;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class EmployeePdfExporter {

    public static void exportToPdf(List<Employee> employees, String filePath) {
        // Đảm bảo rằng thư mục chứa file PDF đã tồn tại
        Path path = Paths.get(filePath);
        path.getParent().toFile().mkdirs();

        try {
            PdfWriter writer = new PdfWriter(filePath);
            PdfDocument pdf = new PdfDocument(writer);
            Document document = new Document(pdf);
            Paragraph title = new Paragraph("Employee Information")
                    .setFontSize(16)  // Kích thước font chữ tiêu đề
                    .setBold();       // In đậm tiêu đề
            document.add(title);

            Table table = new Table(7);

            float fontSize = 5f;
            // Định nghĩa các header cho bảng
            table.addCell(new Cell().add(new Paragraph("ID")));
            table.addCell(new Cell().add(new Paragraph("NAME")));
            table.addCell(new Cell().add(new Paragraph("PHONE")));

            table.addCell(new Cell().add(new Paragraph("Date of birth")));
            table.addCell(new Cell().add(new Paragraph("Address")));
            table.addCell(new Cell().add(new Paragraph("Salary")));
            table.addCell(new Cell().add(new Paragraph("Image")));

            // Thêm thông tin nhân viên vào bảng
            for (Employee employee : employees) {
                table.addCell(employee.getidnv().get());
                table.addCell(employee.gethotennv().get());
                table.addCell(String.valueOf(employee.sdtProperty().get()));
                table.addCell(employee.getNgaysinh().get());
                table.addCell(employee.getdiachi().get());
                table.addCell(String.valueOf(employee.getheluong().get()));
                String basePath = "C:\\Users\\huynh\\IdeaProjects\\QUANTRIDULIEU\\src\\main\\resources\\image";  // Đường dẫn thư mục chứa hình ảnh
                String imagePath = basePath + employee.gethinhanh().get();  // Nối chuỗi
                System.out.println(imagePath);
                Image img = new Image(ImageDataFactory.create(imagePath));
                img.scaleToFit(100, 100);
                table.addCell(new Cell().add(img));

            }

            // Thêm bảng vào tài liệu
            document.add(table);

            // Đóng tài liệu PDF
            document.close();

            System.out.println("PDF đã được xuất thành công tại: " + filePath);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            System.err.println("Lỗi khi tạo file PDF: " + e.getMessage());
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    private static String generateFileName() {
        // Lấy thời gian hiện tại và định dạng tên file
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss");
        String timeStamp = LocalDateTime.now().format(formatter);

        // Tạo tên file với định dạng: employee_info_yyyyMMdd_HHmmss.pdf
        return "employee_info_" + timeStamp + ".pdf";
    }
    public static void main(String[] args) {
        // Lấy danh sách nhân viên từ cơ sở dữ liệu
        List<Employee> employees = myjbdc.getEmployeeList();
        String filename = generateFileName();
        // Gọi phương thức để xuất PDF
        exportToPdf(employees, "output/"+filename);
    }
}

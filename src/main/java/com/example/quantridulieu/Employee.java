package com.example.quantridulieu;
//khai báo các thư viện câ thiết
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;



// Khai báo các thuộc tính của lớp nhân viên
public class Employee {
    private SimpleStringProperty idnhanvien;
    private SimpleStringProperty hotennv;
    private SimpleIntegerProperty sdt;
    private SimpleStringProperty gioitinh;
    private SimpleStringProperty trangthai;
    private SimpleStringProperty  chucvu;
    private SimpleStringProperty  ngaysinh;
    private SimpleStringProperty  diachi;
    private SimpleDoubleProperty heluong;
    private SimpleStringProperty hinhanh;
    //hàm xây dựng nhân viên
    public Employee(String idnhanvien, String hotennv, int sdt , String gioitinh, String trangthai, String chucvu, String ngaysinh, String diachi, Double heluong, String hinhanh  )

    {
        this.idnhanvien= new SimpleStringProperty(idnhanvien);
        this.hotennv= new SimpleStringProperty(hotennv);
        this.sdt= new SimpleIntegerProperty(sdt);
        this.gioitinh=new SimpleStringProperty(gioitinh);
        this.trangthai=new SimpleStringProperty(trangthai);
        this.chucvu= new SimpleStringProperty(chucvu);
        this.ngaysinh = new SimpleStringProperty (ngaysinh);
        this.diachi = new SimpleStringProperty(diachi);
        this.heluong = new SimpleDoubleProperty(heluong);
        this.hinhanh = new SimpleStringProperty(hinhanh) ;
    }
    // lấy id nhân viên
    public SimpleStringProperty idnvProperty() {
        return idnhanvien;
    }
    public SimpleStringProperty getidnv()
    {
        return idnhanvien;
    }
     // lấy họ tên nhan vien
    public SimpleStringProperty hotennvProperty()
    {
        return this.hotennv;
    }
    public SimpleStringProperty gethotennv()
    {
        return hotennv;
    }
    //lấy sdt nhan vien
    public SimpleIntegerProperty sdtProperty() {
        return sdt;
    }
    public SimpleIntegerProperty getsdt()
    {
        return sdt;
    }
   // lấy gioitinhnhân viên
   public SimpleStringProperty gioitinhnvProperty()
   {
       return this.gioitinh;
   }
    public SimpleStringProperty getgioitinhnnv()
    {
        return gioitinh;
    }
    //trạng thái nhân viên
    public SimpleStringProperty trangthaiProperty()
    {
        return this.trangthai;
    }
    public SimpleStringProperty gettrangthainhnnv()
    {
        return trangthai;
    }
    //lấy chức vụ nhân viên
    public SimpleStringProperty getchucvu()
    {
        return chucvu;
    }
    //lấy ngày sinh
    public SimpleStringProperty  getNgaysinh() {
        return ngaysinh;
    }
    // lấy địa chỉ nhân viên
    public SimpleStringProperty getdiachi()
    {
        return diachi;
    }

    //lay he luong
    public SimpleDoubleProperty getheluong()
    {
        return heluong;
    }
    //lay đường dẫn hình ảnh
    public SimpleStringProperty gethinhanh() {
    return hinhanh;
}



}

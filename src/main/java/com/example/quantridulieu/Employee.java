package com.example.quantridulieu;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

import java.io.File;
import java.util.Date;

// hàm xây dựng
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
    private byte[] hinhanh;
    public Employee(String idnhanvien, String hotennv, int sdt , String gioitinh, String trangthai, String chucvu, String ngaysinh, String diachi, Double heluong, byte[] hinhanh  )
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
        this.hinhanh = hinhanh;
    }
    //idnhan vien
    public SimpleStringProperty idnvProperty() {
        return idnhanvien;
    }
    public SimpleStringProperty getidnv()
    {
        return idnhanvien;
    }
    public void setidnhanvien(SimpleStringProperty idnhanvien)
    {
        this.idnhanvien.set(String.valueOf(idnhanvien));
    }
    //
     // họ tên nhan vien
    public SimpleStringProperty hotennvProperty()
    {
        return this.hotennv;
    }
    public SimpleStringProperty gethotennv()
    {
        return hotennv;
    }
    public void sethotennv(String hotennv)
    {
        this.hotennv.set(hotennv);
    }
    //sdt nhan vien
    public SimpleIntegerProperty sdtProperty() {
        return sdt;
    }
    public SimpleIntegerProperty getsdt()
    {
        return sdt;
    }
    public void setsdt(int sdt)
    {
        this.sdt.set(sdt);
    }
   //gioitinhnhân viên
   public SimpleStringProperty gioitinhnvProperty()
   {
       return this.gioitinh;
   }
    public SimpleStringProperty getgioitinhnnv()
    {
        return gioitinh;
    }
    public void setgioitinhnv(String gioitinh)
    {
        this.gioitinh.set(gioitinh);
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
    public void settrangthainv(String trangthai)
    {
        this.trangthai.set(trangthai);
    }
    //chức vụ nhân viên
    public SimpleStringProperty chucvuProperty()
    {
        return this.chucvu;
    }
    public SimpleStringProperty getchucvu()
    {
        return chucvu;
    }
    public void setchucvu(String chucvu)
    {
        this.chucvu.set(chucvu);
    }
    public SimpleStringProperty  getNgaysinh() {
        return ngaysinh;
    }

    public void setNgaysinh(SimpleStringProperty  ngaysinh) {
        this.ngaysinh = ngaysinh;
    }
    //địa chỉ nhân viên
    public SimpleStringProperty diachiProperty()
    {
        return this.diachi;
    }
    public SimpleStringProperty getdiachi()
    {
        return diachi;
    }
    public void setdiachi(String diachi)
    {
        this.diachi.set(diachi);
    }
    //he luong
    public SimpleDoubleProperty heluongProperty()
    {
        return this.heluong;
    }
    public SimpleDoubleProperty getheluong()
    {
        return heluong;
    }
    public void setheluong(Double heluong)
    {
        this.heluong.set(heluong);
    }
//hinh anh
public byte[] gethinhanh() {
    return hinhanh;
}

    public void sethinhanh(byte[] hinhanh) {
        this.hinhanh = hinhanh;
    }

}

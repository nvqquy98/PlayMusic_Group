package com.example.playmusic_group;

import java.io.File;
import java.io.Serializable;

public class BaiHat implements Serializable {
    private String TenBaiHat;
    private String TenCaSi;
    private int File;

    private String filePath;

    private int Id;

    public String getTenCaSi() {
        return TenCaSi;
    }


    public void setTenCaSi(String tenCaSi) {
        TenCaSi = tenCaSi;
    }


    public BaiHat(String tenBaiHat, String tenCaSi, int file) {
        TenBaiHat = tenBaiHat;
        TenCaSi = tenCaSi;
        File = file;
    }

    public BaiHat(int id, String tenBaiHat, String tenCaSi, String filePath) {
        TenBaiHat = tenBaiHat;
        TenCaSi = tenCaSi;
        this.filePath = filePath;
    }

    public BaiHat(int id, String tenBaiHat, String tenCaSi, int file) {
        Id = id;
        TenBaiHat = tenBaiHat;
        TenCaSi = tenCaSi;
        File = file;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public String getFilePath() {
        return filePath;
    }

    public int getFile() {
        return File;
    }

    public void setFile(int file) {
        File = file;
    }

    public String getTenBaiHat() {
        return TenBaiHat;
    }

    public void setTenBaiHat(String tenBaiHat) {
        TenBaiHat = tenBaiHat;
    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }
}

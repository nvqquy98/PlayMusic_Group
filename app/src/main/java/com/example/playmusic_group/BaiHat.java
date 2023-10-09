package com.example.playmusic_group;

import java.io.File;

public class BaiHat {
    private String TenBaiHat;
    private int File;

    public BaiHat(String tenBaiHat, int file) {
        TenBaiHat = tenBaiHat;
        File = file;
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
}

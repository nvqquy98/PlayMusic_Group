package context;

import com.example.playmusic_group.BaiHat;
import com.example.playmusic_group.R;

import java.util.ArrayList;

public class DataMusic{

    public ArrayList<BaiHat> arrayBaiHat;
    public  DataMusic(){
        AddBaiHat();
    }


    public void AddBaiHat() {
        arrayBaiHat = new ArrayList<>();
        arrayBaiHat.add(new BaiHat(1,"Việt Nam Những Chuyến Đi", "Vicky Nhung", R.raw.viet_nam_va_nhung_chuyen_di));
        arrayBaiHat.add(new BaiHat(2,"Anh Ba Khía Miền Tây", "Đan Trường", R.raw.anh_ba_khia));
        arrayBaiHat.add(new BaiHat(3,"Kiếp Ve Sầu", "Đan Trường", R.raw.kiep_ve_sau));
        arrayBaiHat.add(new BaiHat(4,"Mưa Trên Cuộc Tình", "Đan Trường", R.raw.mua_tren_cuoc_tinh));
        arrayBaiHat.add(new BaiHat(5,"Nếu Phôi Pha Ngày Mai", "Đan Trường", R.raw.phoi_phai));
        arrayBaiHat.add(new BaiHat(6,"Anh Đã Quen Với Cô Đơn", "Sobun Hoàng Sơn", R.raw.anh_da_quen_voi_co_don));
        arrayBaiHat.add(new BaiHat(7,"Buông Đôi Tay Nhau Ra", "Sơn Tùng MTP", R.raw.buong_doi_tay_nhau_ra));
        arrayBaiHat.add(new BaiHat(8,"Có Chàng Trai Viết Lên Cây", "Phan Mạnh Quỳnh", R.raw.co_chang_trai_viet_len_cay));
        arrayBaiHat.add(new BaiHat(9,"Gửi Anh Xa Nhớ", "Bích Phương", R.raw.gui_anh_xa_nho));
        arrayBaiHat.add(new BaiHat(10,"Như Phút Ban Đầu", "Noo Phước Thịnh", R.raw.nhu_phut_ban_dau));
        arrayBaiHat.add(new BaiHat(11,"Vợ Người Ta", "Phan Mạnh Quỳnh", R.raw.vo_nguoi_ta));
        arrayBaiHat.add(new BaiHat(12,"Yêu Một Người Vô Tâm", "Bảo Anh", R.raw.yeu_mot_nguoi_vo_tam));
        arrayBaiHat.add(new BaiHat(13,"Yêu Nhau Nửa Ngày", "Phan Mạnh Quỳnh", R.raw.yeu_nhau_nua_ngay));
    }
}




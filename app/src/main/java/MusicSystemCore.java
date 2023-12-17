import android.media.MediaPlayer;

public class MusicSystemCore {

    public  MusicSystemCore(){
        _mediaPlayer = new MediaPlayer();
    }
    MediaPlayer _mediaPlayer;

    //    1.CheckIsPlay()
    //    Phương thức này chỉ trả về true/false cho biết bài hát được chơi hay không
    boolean CheckIsPlay(){
        return _mediaPlayer.isPlaying();
    }

//    2.seekTo (position)
//
//    Phương thức này có một số nguyên, và di chuyển bài hát đến giây phút đặc biệt
//3.getCurrentDuration()
//
//    Phương thức này trả về vị trí hiện tại của bài hát trong mili giây
//4.getDuration()
//
//    Phương thức này trả về tổng thời gian của bài hát trong mili giây
//5.reset()
//
//    Phương thức này reset máy nghe nhạc phương tiện truyền thông
//6.release()
//
//    Phương thức này phát hành bất kỳ tài nguyên gắn với đối tượng MediaPlayer
//7.setVolume(float leftVolume, float rightVolume)
//
//    Phương thức này đặt âm lượng lên xuống cho cầu thủ này
//8.setDataSource(FileDescriptor fd)
//
//    Phương thức này tập hợp các nguồn dữ liệu của tập tin âm thanh / video
//9.selectTrack(int index)
//
//    Phương thức này có một số nguyên, và chọn bài hát từ danh sách trên chỉ số đặc biệt
//10.getTrackInfo()

}

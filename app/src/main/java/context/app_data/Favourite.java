package context.app_data;

public class Favourite extends  BaseEntity{
    public  Favourite(){

    }
    public  Favourite(int customerId, int songId){
        CustomerId = customerId;
        SongId = songId;
    }
    public int CustomerId;
    public int SongId;
}

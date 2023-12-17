package context.app_data;

/**
 *
 */
public class Songs  {
    public int Id;
    public String Name;
    private String Describe;
    public String Singer;
    private int FileId;
    private int ImageId;

    public Songs(){

    }
    public Songs(int id, String name, String singer, String describe, int fileId, int imageId) {
        Id = id;
        Name = name;
        Singer = singer;
        FileId = fileId;
        Describe = describe;
        ImageId = imageId;
    }


    public int getFileId() {
        return FileId;
    }

    public void setFileId(int fileId) {
        FileId = fileId;
    }

    public int getImageId() {
        return ImageId;
    }

    public void setImageId(int imageId) {
        ImageId = imageId;
    }
}

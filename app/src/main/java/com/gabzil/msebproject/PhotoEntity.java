package com.gabzil.msebproject;

/**
 * Created by Yogesh on 10/23/2015.
 */
public class PhotoEntity
{
    private String Photo_Name;
    public String getPhotoName()
    {
        return Photo_Name;
    }
    public void setPhotoName(String PhotoName)
    {
        Photo_Name = PhotoName;
    }

    private String Path_Inner="";
    public String getPath()
    {
        return Path_Inner;
    }
    public void setPath(String Path)
    {
        Path_Inner = Path;
    }
}

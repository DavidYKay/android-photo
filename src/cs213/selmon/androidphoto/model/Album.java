package cs213.selmon.androidphoto.model;

import java.util.ArrayList;

public class Album {

  private ArrayList<Photo> mPhotos = new ArrayList<Photo>(); 
  private String mName;

  public Album(String name) {
    mName = name;
  }

  public String getName() {
    return mName;
  }

  public void setName(String mName) {
    this.mName = mName;
  }
  
  
  public ArrayList<Photo> getPhotos() {
    return mPhotos;
  }
  
  public Photo getPhoto(int index) {
    return mPhotos.get(index);
  }

}

package cs213.selmon.androidphoto.model;

import java.io.Serializable;
import java.util.ArrayList;

public class Album implements Serializable {

  /**
   * 
   */
  private static final long serialVersionUID = 5890285590644289421L;
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
  
  public void addPhoto(Photo photo) {
    mPhotos.add(photo);
  }

}

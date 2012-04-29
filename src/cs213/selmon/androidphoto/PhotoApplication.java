package cs213.selmon.androidphoto;

import android.app.Application;
import cs213.selmon.androidphoto.model.Album;
import cs213.selmon.androidphoto.model.Photo;
import cs213.selmon.androidphoto.util.DataStore;

public class PhotoApplication extends Application {

  private DataStore mDataStore;
  
  private Album mCurrentAlbum;
  private Photo mCurrentPhoto;
  
  @Override
  public void onCreate() {
    super.onCreate();
    
    mDataStore = new DataStore();
  }

  public DataStore getDataStore() {
    return mDataStore;
  }

  public void setCurrentAlbum(Album album) {
    mCurrentAlbum = album;
  }
  public Album getCurrentAlbum() {
    return mCurrentAlbum;
  }
  
  public void setCurrentPhoto(Photo photo) {
    mCurrentPhoto = photo;
  }
  public Photo getCurrentPhoto() {
    return mCurrentPhoto;
  }

}

package cs213.selmon.androidphoto.util;

import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import android.os.Environment;
import cs213.selmon.androidphoto.model.Album;

public class DataStore {

  public static final String FILE_NAME = "photoData.dat";
  
  public static DataStore gDataStore;
  public static final DataStore getInstance() {
    if (gDataStore == null) {
      gDataStore = new DataStore();
    }
    return gDataStore;
  }

  /** 
   * Uses the SD Card
   */
  private static File getFilePath() {
    //return FILE_NAME;
    //File f = new File(Environment.getExternalStorageDirectory() + "/somedir");
    File f = new File(Environment.getExternalStorageDirectory() + "/" + FILE_NAME);
    return f;
  }

  public static void saveAlbums(Collection<Album> albumList){
    try {
      FileOutputStream fos = new FileOutputStream(getFilePath());
      ObjectOutputStream oos = new ObjectOutputStream(fos);
      for(Album album: albumList){
        oos.writeObject(album);
      }			
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    }catch (IOException e) {
      e.printStackTrace();
    }
  }

  public static List<Album> loadAlbums() {
    ArrayList<Album> albums = new ArrayList<Album>();
    try {
      FileInputStream in = new FileInputStream(getFilePath());

      ObjectInputStream oIn = new ObjectInputStream(in);
      Object tmp = null;
      while((tmp = oIn.readObject()) != null){
        if(tmp instanceof Album){
          Album u = (Album)tmp;
          albums.add(u);
        }else{
          System.err.println("Class:" + tmp.getClass().toString() + " not expected.");
          return null;
        }
      }

    } catch (EOFException e){

    }catch (FileNotFoundException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    } catch (ClassNotFoundException e) {
      e.printStackTrace();
    }
    return albums;
  }
}

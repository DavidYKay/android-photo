package cs213.selmon.androidphoto.model;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

public class Photo implements Serializable {

  /**
   * 
   */
  private static final long serialVersionUID = -6666709811395471715L;
  
  private Set<Tag> mTags = new HashSet<Tag>();
  private String mFilePath;

  public Photo(String filePath) {
    mFilePath = filePath;
  }

  public String getPath() {
    return mFilePath;
  }

  public void addTag(Tag tag) {
    mTags.add(tag);
  }
  
  public void removeTag(Tag tag) {
    mTags.remove(tag);
  }
 
  
}

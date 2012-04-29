package cs213.selmon.androidphoto.model;

import java.io.Serializable;

public class Tag implements Serializable {

  /**
   * 
   */
  private static final long serialVersionUID = 7575947964891100304L;
  private String mName;
  private TagType mType;
  
  public Tag(String name, TagType type) {
    super();
    mName = name;
    mType = type;
  }


  public String getName() {
    return mName;
  }


  public TagType getType() {
    return mType;
  }

  public enum TagType {
    PERSON,
    LOCATION    
  }
  
}

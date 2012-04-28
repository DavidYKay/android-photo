package cs213.selmon.androidphoto.model;

public class Tag {

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

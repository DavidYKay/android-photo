package cs213.selmon.androidphoto;

import cs213.selmon.androidphoto.util.DataStore;
import android.app.ListActivity;
import android.os.Bundle;

public class PhotoActivity extends ListActivity {
  
  private DataStore mDataStore;

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState); 

    setContentView(R.layout.photo);
    
    mDataStore = ((PhotoApplication) this.getApplication()).getDataStore();
  }
  
  @Override
  protected void onStop() {
    mDataStore.saveStateToDisk();    
    
    super.onStop();
  }

}

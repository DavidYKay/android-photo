package cs213.selmon.androidphoto;

import java.util.List;

import android.app.ListActivity;
import android.content.Intent;
import android.database.DataSetObserver;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.TextView;
import cs213.selmon.androidphoto.model.Album;
import cs213.selmon.androidphoto.model.Photo;
import cs213.selmon.androidphoto.util.DataStore;

public class AlbumDetailActivity extends ListActivity {
        
  private static final int MEDIA_IMAGE_REQUEST_CODE = 203948; 
  
  private DataStore mDataStore;
  private Album mAlbum;

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState); 

    setContentView(R.layout.album_detail);
    
    mDataStore = ((PhotoApplication) this.getApplication()).getDataStore();
    mAlbum = ((PhotoApplication) this.getApplication()).getCurrentAlbum();

    TextView albumTitle = (TextView) findViewById(R.id.album_title);
    albumTitle.setText("Album: " +  mAlbum.getName());

    Button addButton = (Button) findViewById(R.id.add_button);
    addButton.setOnClickListener( new View.OnClickListener() {      
      @Override
      public void onClick(View v) {
        Intent getImageFromGalleryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
        startActivityForResult(getImageFromGalleryIntent, MEDIA_IMAGE_REQUEST_CODE);
      }
    });

    refresh();
  }
  
  @Override
  protected void onStop() {
    mDataStore.saveStateToDisk();    
    
    super.onStop();
  }

  protected final void onActivityResult(final int requestCode, final int resultCode, final Intent i) {
    super.onActivityResult(requestCode, resultCode, i);
    if(resultCode == RESULT_OK) {
      switch(requestCode) {
      case MEDIA_IMAGE_REQUEST_CODE:

        // Get the chosen images Uri
        Uri imageUri = i.getData();
        // save it 
        addNewPhoto(imageUri.toString());

        break;
      }
    }
  }
  
  private void addNewPhoto(String fileUri) {
    mAlbum.addPhoto(new Photo(fileUri));
    refresh();
  }

  private void refresh() {
    setListAdapter(new PhotoListAdapter(
        mAlbum.getPhotos()
        ));
  }
  
  private class PhotoListAdapter implements ListAdapter {

    private List<Photo> mPhotos;

    public PhotoListAdapter(List<Photo> photos) {
      mPhotos = photos;
    }

    @Override
    public void registerDataSetObserver(DataSetObserver observer) {
      // TODO Implement this later
    }

    @Override
    public void unregisterDataSetObserver(DataSetObserver observer) {
      // TODO Implement this later      
    }

    @Override
    public int getCount() {
      return mPhotos.size();
    }

    @Override
    public Object getItem(int position) {
      return mPhotos.get(position);
    }

    @Override
    public long getItemId(int position) {
      //return 0;
      //return position;
      return mPhotos.get(position).hashCode();
    }

    @Override
    public boolean hasStableIds() {
      return false;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
      LayoutInflater inflater = getLayoutInflater();
      View row = inflater.inflate(R.layout.photo_row, null);
      TextView text = (TextView) row.findViewById(R.id.text);

      Photo photo = mPhotos.get(position);
      text.setText(photo.getPath());

      return(row);
    }

    @Override
    public int getItemViewType(int position) {
      return 1;
    }

    @Override
    public int getViewTypeCount() {
      return 1;
    }

    @Override
    public boolean isEmpty() {
      return getCount() == 0;
    }

    @Override
    public boolean areAllItemsEnabled() {      
      return true;
    }

    @Override
    public boolean isEnabled(int position) {
      return true;
    }

  }

}

package cs213.selmon.androidphoto;

import java.io.FileNotFoundException;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.database.DataSetObserver;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import cs213.selmon.androidphoto.model.Album;
import cs213.selmon.androidphoto.model.Photo;
import cs213.selmon.androidphoto.util.DataStore;
import cs213.selmon.androidphoto.util.PhotoHelper;

public class AlbumDetailActivity extends Activity {
        
  private static final int MEDIA_IMAGE_REQUEST_CODE = 203948; 
  
  private DataStore mDataStore;
  private Album mAlbum;
  private GridView mGridView;

  private PhotoHelper mPhotoHelper;
  
  private PhotoListAdapter mAdapter;

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState); 

    setContentView(R.layout.album_detail);
    
    mPhotoHelper = new PhotoHelper(this);
    
    mDataStore = ((PhotoApplication) this.getApplication()).getDataStore();
    mAlbum = ((PhotoApplication) this.getApplication()).getCurrentAlbum();

    mGridView = (GridView) findViewById(R.id.gridview);
    mGridView.setOnItemClickListener(new OnItemClickListener() {
      public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
        //Toast.makeText(AlbumDetailActivity.this, "" + position, Toast.LENGTH_SHORT).show();
    
        Photo photo = (Photo) mAdapter.getItem(position);

        PhotoApplication application = ((PhotoApplication) AlbumDetailActivity.this.getApplication());
        application.setCurrentPhoto(photo);

        Intent intent = new Intent(AlbumDetailActivity.this, PhotoActivity.class);
        startActivity(intent);
      }
    });

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

    registerForContextMenu(mGridView);  

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
  
  @Override
  public void onCreateContextMenu(ContextMenu menu, View v,
                                  ContextMenuInfo menuInfo) {
    if (v == mGridView) {
      AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo)menuInfo;
      menu.setHeaderTitle("");
      String[] menuItems = getResources().getStringArray(R.array.photo_list_context_choices);
      for (int i = 0; i<menuItems.length; i++) {
        menu.add(Menu.NONE, i, i, menuItems[i]);
      }
    }
  }

  @Override
  public boolean onContextItemSelected(MenuItem item) {
    AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo)item.getMenuInfo();
    int menuItemIndex = item.getItemId();
    
    String[] menuItems = getResources().getStringArray(R.array.photo_list_context_choices);
    String menuItemName = menuItems[menuItemIndex];

    Photo photo = (Photo) mAdapter.getItem(info.position);    

    if (menuItemName.equals("Delete")) {
      deletePhoto(photo);
    }

    // Handle which option was chosen

    //TextView text = (TextView)findViewById(R.id.footer);
    //text.setText(String.format("Selected %s for item %s", menuItemName, listItemName));

    return true;
  }
  
  private void deletePhoto(Photo photo) {
    mAlbum.deletePhoto(photo);
    refresh();
  }

  private void addNewPhoto(String fileUri) {
    mAlbum.addPhoto(new Photo(fileUri));
    refresh();
  }

  private void refresh() {
    mAdapter = new PhotoListAdapter(mAlbum.getPhotos());
    mGridView.setAdapter(mAdapter);
  }
  
  private class PhotoListAdapter extends BaseAdapter {

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
      
      if (convertView == null) {
      
        LayoutInflater inflater = getLayoutInflater();
        convertView = inflater.inflate(R.layout.photo_row, null);
      }

      Photo photo = mPhotos.get(position);
      
      ImageView imageView = (ImageView) convertView.findViewById(R.id.image);
      
      imageView.setImageURI(Uri.parse(photo.getPath()));
      //imageView.setImageBitmap(mPhotoHelper.decodeImageAtPath(photo.getPath()));
      try {
        imageView.setImageBitmap(mPhotoHelper.decodeUri(Uri.parse(photo.getPath())));
      } catch (FileNotFoundException e) {
        e.printStackTrace();
      }

      return(convertView);
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

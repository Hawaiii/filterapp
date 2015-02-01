package com.example.filter;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

import com.example.filter.improc.Filter;
import com.example.filter.improc.ImgProcessor;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

/**
 * Upload the picture to use the filter.
 * @author zhifanli
 *
 */
public class Upload_b extends Activity {

	private static final int PICK_IMAGE = 1;
	Button btnCamera;
	Button btnUpload;
	Button btnYes;
	Button btnNo;
	ImageView imgFavorite;
	Bitmap bitmap;
	boolean isFilterPhoto;
	Bitmap finalimg;
	/**
     * Define the number of items visible when the carousel is first shown.
     */
    private static final float INITIAL_ITEMS_COUNT = 3.5F;

    /**
     * Carousel container layout
     */
    private LinearLayout mCarouselContainer;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        setContentView(R.layout.activity_select);
        
        btnYes = (Button) findViewById(R.id.yes);
        btnNo = (Button) findViewById(R.id.no);
        btnYes.setOnClickListener(save);
        btnNo.setOnClickListener(back);
        
        //open the gallery
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE);
        imgFavorite = (ImageView)findViewById(R.id.imageView1);

        // Get reference to carousel container
        mCarouselContainer = (LinearLayout) findViewById(R.id.carousel);
        refresh();
        
    }
    
    OnClickListener save = new OnClickListener() {
        @Override
        public void onClick(View v) {
        	String thepath = saveImage(finalimg);
        	Intent i=new Intent(
                    Upload_b.this,
                    Save.class);
             startActivity(i);
        }
      };
      
    OnClickListener back = new OnClickListener() {
    	
    	  @Override
          public void onClick(View v) {
          	Intent i=new Intent(
                      Upload_b.this,
                      Filter_Complete.class);
               startActivity(i);
          }
      };
    
    
    

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
        case PICK_IMAGE:
            if (resultCode == Activity.RESULT_OK) {
                Uri selectedImageUri = data.getData();
                String filePath = null;
                               
				try {
					bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), selectedImageUri);
					imgFavorite.setImageBitmap(bitmap);
					Log.v("v","already set the img");
				} catch (FileNotFoundException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
            }
            break;
        default:
        }
    }
 
    
    
//    protected void onPostCreate(Bundle savedInstanceState) {
//        super.onPostCreate(savedInstanceState);
    protected void refresh(){
        // Compute the width of a carousel item based on the screen width and number of initial items.
        final DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        final int imageWidth = (int) (displayMetrics.widthPixels / INITIAL_ITEMS_COUNT);
        
      
        
        final HashMap<String, Filter> map = (HashMap<String, Filter>)loadSerializedObject(new File("/sdcard/save_object.bin")); //get the serialized object from the sdcard and caste it into the Person class.
        // Get the array of puppy resources
        final ArrayList<String> testArray = new ArrayList<String>();
 
        File dir = new File(Environment.getExternalStorageDirectory()+ "/Pictures/peel");
        File[] filelist = dir.listFiles();
        
        int count = 0;
        
        for (File pic : filelist) {
        	if (count > 3) {
        		break;
        	}
        	testArray.add(pic.getAbsolutePath());   
        	count ++;
        }
        	
        ArrayList<OnClickListener> listeners = new ArrayList<OnClickListener>();
        for (int i = 0; i < testArray.size(); ++i) {
        	
        	 final int idx = i;
              
             OnClickListener filter = new OnClickListener() {
          		
          		@Override
          		public void onClick(View v) {
          			Log.v("v", "map size:"+map.size());
          			String path = testArray.get(idx);
          			Filter f = map.get(path);
          			Log.v("lalala", f.toString());
          			BitmapFactory.Options options = new BitmapFactory.Options();
                    options.inPreferredConfig = Bitmap.Config.ARGB_8888;
                    Bitmap bitmap1 = BitmapFactory.decodeFile(path, options);
          			Bitmap filtered = ImgProcessor.applyFilter(bitmap, f);
          			imgFavorite.setImageBitmap(filtered);
          			finalimg = filtered;
          			bitmap1 = null;
          			System.gc();
          		}
          	};
          	
          	listeners.add(filter);
        }
        // Populate the carousel with items
        ArrayList<ImageView> imageItems = new ArrayList<ImageView>();
        
        ImageView imageItem;
        
        for (int i = 0 ; i < testArray.size() ; ++i) {
            // Create new ImageView
            imageItem = new ImageView(this);

            // Set the shadow background
            imageItem.setBackgroundResource(R.drawable.shadow);

            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inPreferredConfig = Bitmap.Config.ARGB_8888;
            Bitmap bitmap1 = BitmapFactory.decodeFile(testArray.get(i), options);
           
            imageItem.setImageBitmap(bitmap1);
            
            // Set the size of the image view to the previously computed value
            imageItem.setLayoutParams(new LinearLayout.LayoutParams(imageWidth, imageWidth));
            
            imageItem.setOnClickListener(listeners.get(i));

            imageItems.add(imageItem);
            /// Add image view to the carousel container
            mCarouselContainer.addView(imageItem);
            
            bitmap1 = null;
            System.gc();
            
        }
    }

    private String saveImage(Bitmap finalBitmap) {
	    String root = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES).toString();
	    System.out.println(root +" Root value in saveImage Function");
	    File myDir = new File(root + "/peel");

	    if (!myDir.exists()) {
	    	myDir.mkdirs();
        }

	    Random generator = new Random();
	    int n = 10000;
	    n = generator.nextInt(n);
	    String iname = "Image-" + n + ".jpg";
	    
	    File file = new File(myDir, iname);
	    if (file.exists())
	        file.delete();
	    try {
	        FileOutputStream out = new FileOutputStream(file);
	        finalBitmap.compress(Bitmap.CompressFormat.JPEG, 90, out);
	        out.flush();
	        out.close();
	    }
	    catch (Exception e) {
	        e.printStackTrace();
	    }

	    // Tell the media scanner about the new file so that it is
	    // immediately available to the user.
	    MediaScannerConnection.scanFile(this, new String[] { file.toString() }, null,
	            new MediaScannerConnection.OnScanCompletedListener() {
	                public void onScanCompleted(String path, Uri uri) {
	                    Log.i("ExternalStorage", "Scanned " + path + ":");
	                    Log.i("ExternalStorage", "-> uri=" + uri);
	                }
	    });

	    String Image_path = Environment.getExternalStorageDirectory()+ "/Pictures/peelresults/"+iname;
	    Log.v("v",Image_path);
//	    File[] files = myDir.listFiles();
//    	int numberOfImages = files.length;
//    	System.out.println("Total images in Folder "+numberOfImages);
	    return Image_path;
	}
    
    public Object loadSerializedObject(File f)
    {
        try
        {
            ObjectInputStream ois = new ObjectInputStream(new FileInputStream(f));
            Object o = ois.readObject();
            return o;
        }
        catch(Exception ex)
        {
        Log.v("Serialization Read Error : ",ex.getMessage());
            ex.printStackTrace();
        }
        return null;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
    
   
    
}

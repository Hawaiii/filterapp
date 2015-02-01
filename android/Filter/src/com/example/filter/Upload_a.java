package com.example.filter;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Random;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;

public class Upload_a extends Activity {

	private static final int PICK_IMAGE = 1;
	Button btnCamera;
	Button btnUpload;
	Button btnYes;
	Button btnNo;
	ImageView imgFavorite;
	Bitmap bitmap;
	boolean isFilterPhoto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera);
        
        btnYes = (Button) findViewById(R.id.yes);
        btnNo = (Button) findViewById(R.id.no);
        //open the gallery
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE);
        imgFavorite = (ImageView)findViewById(R.id.imageView1);


        btnYes.setText("SAVE");
        btnYes.setOnClickListener(filter_complete); //go to filter-result page
        btnNo.setOnClickListener(make_filter); //go back to make-filter page            
       
        
    }
    
    
    OnClickListener make_filter = new OnClickListener() {
        @Override
        public void onClick(View v) {
        	Intent i=new Intent(
                    Upload_a.this,
                    Make.class);
             startActivity(i);
        }
      };
      
    OnClickListener filter_complete = new OnClickListener() {
    	
    	 @Override
         public void onClick(View v) {
    		 //TODO save photo to album
    		saveImage(bitmap);
    		 
         	Intent i=new Intent(
                     Upload_a.this,
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
 
    private void saveImage(Bitmap finalBitmap) {
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

	    String Image_path = Environment.getExternalStorageDirectory()+ "/Pictures/peel/"+iname;
	    Log.v("v",Image_path);
//	    File[] files = myDir.listFiles();
//    	int numberOfImages = files.length;
//    	System.out.println("Total images in Folder "+numberOfImages);
	}
    
   

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
    
   
    
}

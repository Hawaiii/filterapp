package com.example.filter;

import java.io.FileNotFoundException;
import java.io.IOException;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;

public class Upload_b extends Activity {

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

        //TODO: deal with 1) upload filter photo 2) upload todo photo
       
        	//yes: go to choose-filter page
        	//no: go to main activity page
        
        
        
    }
    
    
    OnClickListener make_filter = new OnClickListener() {
        @Override
        public void onClick(View v) {
        	Intent i=new Intent(
                    Upload_b.this,
                    Make.class);
             startActivity(i);
        }
      };
      
    OnClickListener filter_complete = new OnClickListener() {
    	
    	 @Override
         public void onClick(View v) {
    		 //TODO save photo to album
    		 
    		 
         	Intent i=new Intent(
                     Upload_b.this,
                     Filter_Complete.class);//TODO: NEW CLASS
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
 
    
   

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
    
   
    
}

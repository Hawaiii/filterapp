package com.example.filter;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;

/**
 * after making a filter, upload pictures to try
 * @author zhifanli
 *
 */
public class Filter_Complete extends Activity {

	 ImageButton btnUpload;
	 ImageButton btnCamera;
	 Bitmap filter_complete;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_complete);
//        filter_complete = getIntent().getExtra()
        btnUpload = (ImageButton) findViewById(R.id.upload);
        btnCamera = (ImageButton) findViewById(R.id.camera);
        btnUpload.setOnClickListener(upload_photos);
        btnCamera.setOnClickListener(camera);
       
    }
    
    OnClickListener upload_photos = new OnClickListener() {
   	 @Override
        public void onClick(View v) {
        	Intent i=new Intent(
                    Filter_Complete.this,
                    Upload_b.class);
            startActivity(i);
        }
     };
     
     OnClickListener camera = new OnClickListener() {
       	 @Override
            public void onClick(View v) {
            	Intent i=new Intent(
                        Filter_Complete.this,
                        Camera.class);
                startActivity(i);
            }
         };


     
    

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
    
   
    
}

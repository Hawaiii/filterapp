package com.example.filter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;

public class MainActivity extends Activity {

	 ImageButton btnCamera;
	 ImageButton btnUpload;
	 ImageButton btnMake;
//	 Button btnCarousel;

    protected void onCreate(Bundle savedInstanceState) {
//    	getActionBar().hide();
//    	Log.d("d","0");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        Log.d("d","0");
        btnCamera = (ImageButton) findViewById(R.id.camera);
//        Log.d("d","0");
        btnUpload = (ImageButton) findViewById(R.id.upload);
//        Log.d("d","0");
        btnMake = (ImageButton) findViewById(R.id.make_filter);

        
        
        
        btnCamera.setOnClickListener(open_camera);
        btnUpload.setOnClickListener(upload_photos);
        btnMake.setOnClickListener(make_filter);
    
    }
    
   
    
    OnClickListener open_camera = new OnClickListener() {
        @Override
        public void onClick(View v) {
        	Intent i=new Intent(
                    MainActivity.this,
                    Camera.class);
             startActivity(i);
        }
      };
      
    OnClickListener upload_photos = new OnClickListener() {
    	 @Override
         public void onClick(View v) {
         	Intent i=new Intent(
                     MainActivity.this,
                     Upload_b.class);
            startActivity(i);
         }
      };
      
    OnClickListener make_filter = new OnClickListener() {
        @Override
        public void onClick(View v) {
          	Intent i=new Intent(
                      MainActivity.this,
                      Make.class);
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

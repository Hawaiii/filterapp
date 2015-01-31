package com.example.filter;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class MainActivity extends Activity {

	 Button btnCamera;
	 Button btnUpload;
	 Button btnMake;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        btnCamera = (Button) findViewById(R.id.camera);
        btnUpload = (Button) findViewById(R.id.upload);
        btnMake = (Button) findViewById(R.id.make_filter);
        
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
                     Upload.class);
         	i.putExtra("isFilterPhoto", false);
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

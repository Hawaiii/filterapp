package com.example.filter;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;

public class Make extends Activity {

	 Button btnUpload;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_make);
        
        btnUpload = (Button) findViewById(R.id.upload);
        btnUpload.setOnClickListener(upload_photos);
       
    }
    
    OnClickListener upload_photos = new OnClickListener() {
   	 @Override
        public void onClick(View v) {
        	Intent i=new Intent(
                    Make.this,
                    Upload.class);
        	i.putExtra("isFilterPhoto", true);
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

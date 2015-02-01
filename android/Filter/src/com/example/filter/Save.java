package com.example.filter;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

/**
 * Finish page after saving the finished picture.
 * @author zhifanli
 *
 */
public class Save extends Activity {


	Button btnHome; 
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_save);
        btnHome = (Button) findViewById(R.id.home);
      
        btnHome.setOnClickListener(home);
    
    }
    
    OnClickListener home = new OnClickListener() {
		
		@Override
		public void onClick(View v) {
         	Intent i=new Intent(
                    Save.this,
                    MainActivity.class);
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

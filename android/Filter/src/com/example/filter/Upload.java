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
import android.widget.Button;
import android.widget.ImageView;

public class Upload extends Activity {

	private static final int PICK_IMAGE = 1;
	Button btnCamera;
	Button btnUpload;
	ImageView imgFavorite;
	Bitmap bitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera);
        //open the gallery
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE);
        imgFavorite = (ImageView)findViewById(R.id.imageView1);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
        case PICK_IMAGE:
            if (resultCode == Activity.RESULT_OK) {
                Uri selectedImageUri = data.getData();
                String filePath = null;
                
                Bitmap bitmap;
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

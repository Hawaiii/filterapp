package com.example.filter;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.DisplayMetrics;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

public class Upload_b extends Activity {

	private static final int PICK_IMAGE = 1;
	Button btnCamera;
	Button btnUpload;
	Button btnYes;
	Button btnNo;
	ImageView imgFavorite;
	Bitmap bitmap;
	boolean isFilterPhoto;
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
        
//        btnYes = (Button) findViewById(R.id.yes);
//        btnNo = (Button) findViewById(R.id.no);
        //open the gallery
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE);
        imgFavorite = (ImageView)findViewById(R.id.imageView1);

        // Get reference to carousel container
        mCarouselContainer = (LinearLayout) findViewById(R.id.carousel);
        
        
    }
    
    
    

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
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);

        // Compute the width of a carousel item based on the screen width and number of initial items.
        final DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        final int imageWidth = (int) (displayMetrics.widthPixels / INITIAL_ITEMS_COUNT);
        
        Bitmap a = BitmapFactory.decodeResource(getResources(), R.drawable.puppy_01);
        Bitmap b = BitmapFactory.decodeResource(getResources(), R.drawable.puppy_02);
        Bitmap c = BitmapFactory.decodeResource(getResources(), R.drawable.puppy_03);
        Bitmap d = BitmapFactory.decodeResource(getResources(), R.drawable.puppy_04);
        Bitmap e = BitmapFactory.decodeResource(getResources(), R.drawable.puppy_05);
        Bitmap f = BitmapFactory.decodeResource(getResources(), R.drawable.puppy_06);
        
        // Get the array of puppy resources
        ArrayList<Bitmap> testArray = new ArrayList<Bitmap>();
        testArray.add(a);
        testArray.add(b);
        testArray.add(c);
        testArray.add(d);
        testArray.add(e);
        testArray.add(f);
        	
        // Populate the carousel with items
        ImageView imageItem;
        for (int i = 0 ; i < testArray.size() ; ++i) {
            // Create new ImageView
            imageItem = new ImageView(this);

            // Set the shadow background
            imageItem.setBackgroundResource(R.drawable.shadow);

            // Set the image view resource
            imageItem.setImageBitmap(testArray.get(i));
            
            // Set the size of the image view to the previously computed value
            imageItem.setLayoutParams(new LinearLayout.LayoutParams(imageWidth, imageWidth));

            /// Add image view to the carousel container
            mCarouselContainer.addView(imageItem);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
    
   
    
}

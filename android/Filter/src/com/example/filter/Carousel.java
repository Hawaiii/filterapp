package com.example.filter;



import java.util.ArrayList;

import android.app.Activity;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.widget.ImageView;
import android.widget.LinearLayout;


public class Carousel extends Activity {

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


        // Set content layout
        setContentView(R.layout.activity_select);

        // Get reference to carousel container
        mCarouselContainer = (LinearLayout) findViewById(R.id.carousel);
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
}

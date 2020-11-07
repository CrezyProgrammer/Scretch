package com.masum.scretch;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.navigation.NavigationView;
import com.masum.scretch.slider.SliderAdapter;
import com.masum.scretch.slider.SlidingImage_Adapter;
import com.masum.scretch.slider.VerticalViewPager;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import dev.skymansandy.scratchcardlayout.listener.ScratchListener;
import dev.skymansandy.scratchcardlayout.ui.ScratchCardLayout;

public class MainActivity extends AppCompatActivity implements ScratchListener {

    private static VerticalViewPager mPager;
    private static int currentPage = 0;
    private static int NUM_PAGES = 0;
    private static final Integer[] SLIDE_IMAGES = {R.drawable.one, R.drawable.two};
    private static final Integer[] SKETCH = {R.drawable.card_one, R.drawable.card_two,R.drawable.card_three,R.drawable.card_four};
    private static final Integer[] COIN = {R.drawable.coin_one, R.drawable.coin_two,R.drawable.coin_three,R.drawable.coin_four};

    private ArrayList<Integer> ImagesArray = new ArrayList<Integer>();
    ViewPager2 myViewPager2;

    SliderAdapter MyAdapter;
    int currentSetch = 0;
    int totalCard=14;
    int sketch=0;
    int point=0;



    //views
    ScratchCardLayout scratchCardLayout;
    TextView cardLeft,point_tv;
    ImageView img,menu;
    DrawerLayout drawerLayout;
    NavigationView navigationView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViews();
        initSlider();
        initSketch();
        DrawerHeaderItemClick();


    }
    private  void DrawerHeaderItemClick(){
        View headerview = navigationView.getHeaderView(0);
       ImageView spinner =  headerview.findViewById(R.id.spinner);
       spinner.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               drawerLayout.closeDrawer(Gravity.LEFT);
               Toast.makeText(MainActivity.this, "spinner clicked", Toast.LENGTH_SHORT).show();
           }
       });
    }
    private  void initViews(){
      navigationView = (NavigationView) findViewById(R.id.nav_view);

        cardLeft=findViewById(R.id.card_left);
        cardLeft.setText(((totalCard-1)+"/"+totalCard));
        point_tv=findViewById(R.id.point);
        img=findViewById(R.id.img);
        drawerLayout=findViewById(R.id.drawer);
        menu=findViewById(R.id.menu);
        menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                drawerLayout.openDrawer(Gravity.LEFT);
            }
        });


    }
    private  void initSketch() {
        scratchCardLayout = findViewById(R.id.scratchCard);
        setcardImage();
        scratchCardLayout.setScratchListener(this);


        //You'll have three main callback methods as scratch listeners
        //Scratch started
    }
    private void initSlider() {
        for(int i=0;i<SLIDE_IMAGES.length;i++)
            ImagesArray.add(    SLIDE_IMAGES[i]);

        mPager = (VerticalViewPager) findViewById(R.id.pager);
        mPager.setScrollDurationFactor(2.5);



        mPager.setAdapter(new SlidingImage_Adapter(MainActivity.this,ImagesArray));

        mPager.setOnItemClickListener(new VerticalViewPager.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {

                Log.i("123321", "105:"+position);
                Toast.makeText(MainActivity.this, "Position: "+position, Toast.LENGTH_SHORT).show();
                // your code
            }
        });






        NUM_PAGES =SLIDE_IMAGES.length;

        // Auto start of viewpager
        final Handler handler = new Handler();
        final Runnable Update = new Runnable() {
            public void run() {
                if (currentPage == NUM_PAGES) {
                    currentPage = 0;
                }
                mPager.setCurrentItem(currentPage++, true);
            }
        };
        Timer swipeTimer = new Timer();
        swipeTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                handler.post(Update);
            }
        }, 3000, 3000);

        // Pager listener over indicator

    }
    public   void  onScratchComplete() {
        scratchCardLayout._$_clearFindViewByIdCache();

        scratchCardLayout.setScratchEnabled(false);
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(MainActivity.this);
        // ...Irrelevant code for customizing the buttons and title
        LayoutInflater inflater = this.getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.alert_dailog, null);
        dialogBuilder.setView(dialogView);
        TextView coin=dialogView.findViewById(R.id.coin);
        ImageView claim=dialogView.findViewById(R.id.claim);
        coin.setText(point+"");

        AlertDialog alertDialog = dialogBuilder.create();
        alertDialog.setCancelable(false);
    alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        claim.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sketch++;
                alertDialog.dismiss();
                addPoint(point);
                setcardImage();
            }
        });
        alertDialog.show();
        currentSetch++;
        cardLeft.setText(((totalCard-currentSetch)+"/"+totalCard));


    }
    public void  onScratchProgress( ScratchCardLayout sdcratchCardLayout,int atLeastScratchedPercent) {

    }
    public   void  onScratchStarted() {

    }
    private  void setcardImage(){
        scratchCardLayout.resetScratch();
        if(sketch==SKETCH.length)sketch=0;
        scratchCardLayout.setScratchEnabled(true);
        genarateRandom();
       int current = (int)(Math.random() * 3 + 0);
   //   Drawable d=  getResources().getDrawable(SKETCH[sketch]);
     //   scratchCardLayout.setScratchDrawable(d);
       // img.setImageDrawable(getResources().getDrawable(COIN[sketch]));
        switch (sketch){
            case 0:
                scratchCardLayout.setScratchDrawable( getResources().getDrawable(R.drawable.card_one));
                img.setImageDrawable(getResources().getDrawable(R.drawable.coin_one));
                break;
            case 1:
                scratchCardLayout.setScratchDrawable(  getResources().getDrawable(R.drawable.card_two));
                img.setImageDrawable(getResources().getDrawable(R.drawable.coin_two));
                break;
            case 2:
            scratchCardLayout.setScratchDrawable(  getResources().getDrawable(R.drawable.card_three));
                img.setImageDrawable(getResources().getDrawable(R.drawable.coin_three));
                break;
            case 3:
                scratchCardLayout.setScratchDrawable( getResources().getDrawable(R.drawable.card_four));
                img.setImageDrawable(getResources().getDrawable(R.drawable.coin_four));
                break;
        }
        Log.i("123321", "current:"+sketch);


    }
    private  void genarateRandom(){
   point = (int)(Math.random() * 1000 + 10);
   point_tv.setText("  "+point+"\nCoins!");

    }
    private  void addPoint(int point){


        //ad point to database

    }
}
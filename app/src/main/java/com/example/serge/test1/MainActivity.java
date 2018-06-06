package com.example.serge.test1;
import android.content.DialogInterface;
import android.graphics.Point;
import android.media.AudioManager;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.design.widget.NavigationView;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SwitchCompat;
import android.util.AttributeSet;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.CompoundButton;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;


import com.example.serge.test1.CustomEvents.StartGame;
import com.example.serge.test1.CustomEvents.StartNewGame;

import java.io.IOException;


public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, CompoundButton.OnCheckedChangeListener{


    private LinearLayout inventory;
    private NavigationView navView;
    private SwitchCompat switchCompat;



    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        //Установка полноэкранного режима
        getWindow().setFlags( WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN );

        //Настройка звука
        setVolumeControlStream( AudioManager.STREAM_MUSIC);


        setContentView(R.layout.activity_main);

        Shared.eventPool = new EventPool();
        Shared.eventObserver = new Engine();
        Shared.context = this;
        Shared.activity = this;
        PropertyReader propertyReader = new PropertyReader( this );
        Shared.properties = propertyReader.load_properties( "config.properties" );

        propertyReader.save_properties( "config.properties" );
        navView = (NavigationView) findViewById(R.id.nav_view);

        navView.setNavigationItemSelectedListener( this );
        switchCompat = (SwitchCompat) navView.getMenu().getItem( 1 ).getActionView();
        switchCompat.setOnCheckedChangeListener( this );
        switchCompat.setChecked( true );
        Shared.eventObserver.start();
        Shared.eventPool.notify( new StartGame() );

    }

    protected void setMusic(boolean isChecked){
        if(isChecked)
            Music.play();
        else Music.stop();
    }

    public void switchBag(View view){

        LinearLayout container = (LinearLayout) findViewById( R.id.ContainsItem );
        if(inventory == null)
            createInventory();
        if(inventory.getVisibility() == View.INVISIBLE){
            inventory.setVisibility( View.VISIBLE );
            container = (LinearLayout) findViewById( R.id.ContainsItem );
            if(WWProgress.checkItem( 1 )){
                ImageView imgeView = new ImageView(this);
                imgeView.setBackgroundResource( R.drawable.matches2 );
                imgeView.setLayoutParams( Settings.item );
                container.addView( imgeView );

            }
            if(WWProgress.checkItem( 2 )){
                ImageView imgeView = new ImageView(this);
                imgeView.setBackgroundResource( R.drawable.map2 );
                imgeView.setLayoutParams( Settings.item );
                container.addView( imgeView );
            }
            if(WWProgress.checkItem( 3 )){
                ImageView imgeView = new ImageView(this);
                imgeView.setBackgroundResource( R.drawable.flashlight2 );
                imgeView.setLayoutParams( Settings.item );
                container.addView( imgeView );
            }


        }else {
            inventory.setVisibility( View.INVISIBLE );
            container.removeAllViews();
        };
    }

    private void createInventory(){
        inventory = (LinearLayout) findViewById( R.id.inventory );
        FrameLayout.LayoutParams params = (FrameLayout.LayoutParams) inventory.getLayoutParams();
        Display display = getWindowManager().getDefaultDisplay();
        Point point = new Point();
        display.getSize( point );
        params.setMargins( (int)(point.x*0.1), (int)(point.y*0.2),0,0 );
        params.height = (int) (point.y*0.2);
        params.width = (int) (point.x*0.8);
        inventory.setLayoutParams( params );

    }

    public void start_new_game(){
        AlertDialog.Builder ad = new AlertDialog.Builder( this );

        ad.setMessage( R.string.question_about_the_new_game )
                .setCancelable( false )
                .setPositiveButton( R.string.yes, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Shared.eventPool.stopAll();
                    Shared.eventPool.notify( new StartNewGame() );
                    }
                } )
                .setNegativeButton( R.string.no, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                } );
        ad.show();
    }

    public void onPause(){
        super.onPause();
        Music.stop();
        Shared.eventObserver.onPause();
    }


    public void onResume(){
        super.onResume();
        Shared.eventObserver.onResume();

    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        switch(id){
            case R.id.new_game: Toast.makeText( this, "Новая игра", Toast.LENGTH_SHORT ).show(); start_new_game(); break;
            case R.id.about: Toast.makeText( this, "Справка", Toast.LENGTH_SHORT ).show(); break;
            default: break;
        }
        return true;
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        int id = buttonView.getId();
        switch(id){
            case R.id.music_switch_panel: setMusic(isChecked); break;
        }
    }
}

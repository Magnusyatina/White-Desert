package org.magnusario.whitedesert.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.media.AudioManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.design.widget.NavigationView;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SwitchCompat;
import android.view.MenuItem;
import android.view.WindowManager;
import android.widget.CompoundButton;
import android.widget.Toast;

import org.magnusario.whitedesert.Credits;
import org.magnusario.whitedesert.Music;
import org.magnusario.whitedesert.PropertyReader;
import org.magnusario.whitedesert.R;
import org.magnusario.whitedesert.Shared;
import org.magnusario.whitedesert.configuration.ApplicationComponent;
import org.magnusario.whitedesert.configuration.DaggerApplicationComponent;
import org.magnusario.whitedesert.engine.Engine;
import org.magnusario.whitedesert.engine.EventPool;
import org.magnusario.whitedesert.engine.event.SetGameMode;
import org.magnusario.whitedesert.engine.event.SetMusic;
import org.magnusario.whitedesert.engine.event.StartGame;
import org.magnusario.whitedesert.engine.event.StartNewGame;

import javax.inject.Inject;


public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, CompoundButton.OnCheckedChangeListener {


    private NavigationView navView;
    private SwitchCompat switchMusicCompat;
    private SwitchCompat switchModeCompat;

    @Inject
    public EventPool eventPool;

    @Inject
    public Engine engine;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        initDI();
        //Установка полноэкранного режима
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        //Настройка звука
        setVolumeControlStream(AudioManager.STREAM_MUSIC);


        setContentView(R.layout.activity_main);
        Shared.properties = PropertyReader.load_properties(this, "config.properties");
//        Shared.eventPool = new EventPoolImpl();
//        Shared.eventObserver = new Engine();
        Shared.context = this;
        Shared.activity = this;


        navView = (NavigationView) findViewById(R.id.nav_view);

        navView.setNavigationItemSelectedListener(this);
        switchMusicCompat = (SwitchCompat) navView.getMenu().getItem(1).getActionView();
        switchMusicCompat.setOnCheckedChangeListener(this);
        switchMusicCompat.setChecked(true);
        switchModeCompat = (SwitchCompat) navView.getMenu().getItem(2).getActionView();
        switchModeCompat.setOnCheckedChangeListener(this);


        engine.start();
        eventPool.submit(new StartGame());
    }

    private void initDI() {
        ApplicationComponent applicationComponent = DaggerApplicationComponent.builder()
                .context(this)
                .mainActivity(this)
                .build();
        applicationComponent.inject(this);
    }

    public void start_new_game() {
        final AlertDialog.Builder ad = new AlertDialog.Builder(this);

        ad.setMessage(R.string.question_about_the_new_game)
                .setCancelable(false)
                .setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        eventPool.stopAll();
                        eventPool.submit(new StartNewGame());
                    }
                })
                .setNegativeButton(R.string.no, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
        ad.show();
    }

    public void onPause() {
        super.onPause();
        Music.stop();
        PropertyReader.save_properties(this, "config.properties", Shared.properties);
        engine.onPause();
    }


    public void onResume() {
        super.onResume();
        Music.play();
        engine.onResume();

    }

    public void show_credits() {
        Intent intent = new Intent(this, Credits.class);
        startActivity(intent);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.new_game:
                Toast.makeText(this, "Новая игра", Toast.LENGTH_SHORT).show();
                start_new_game();
                break;
            case R.id.about:
                Toast.makeText(this, "Справка", Toast.LENGTH_SHORT).show();
                show_credits();
                break;
            default:
                break;
        }
        return true;
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        int id = buttonView.getId();
        switch (id) {
            case R.id.music_switch_panel:
                setMusic(isChecked);
                break;
            case R.id.game_condition_switch:
                setGameMode(isChecked);
                break;
            default:
                break;
        }
    }

    protected void setMusic(boolean isChecked) {
        SetMusic setMusic = new SetMusic();
        setMusic.setEnable(isChecked);
        eventPool.submit(setMusic);
    }

    protected void setGameMode(boolean isChecked) {
        SetGameMode gameMode = new SetGameMode();
        gameMode.setFast_game(isChecked);
        eventPool.submit(gameMode);
    }
}

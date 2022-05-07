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
import org.magnusario.whitedesert.Engine;
import org.magnusario.whitedesert.EventPool;
import org.magnusario.whitedesert.Music;
import org.magnusario.whitedesert.PropertyReader;
import org.magnusario.whitedesert.R;
import org.magnusario.whitedesert.Shared;
import org.magnusario.whitedesert.event.SetGameMode;
import org.magnusario.whitedesert.event.SetMusic;
import org.magnusario.whitedesert.event.StartGame;
import org.magnusario.whitedesert.event.StartNewGame;


public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, CompoundButton.OnCheckedChangeListener {


    private NavigationView navView;
    private SwitchCompat switchMusicCompat;
    private SwitchCompat switchModeCompat;


    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        //Установка полноэкранного режима
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        //Настройка звука
        setVolumeControlStream(AudioManager.STREAM_MUSIC);


        setContentView(R.layout.activity_main);
        Shared.properties = PropertyReader.load_properties(this, "config.properties");
        Shared.eventPool = new EventPool();
        Shared.eventObserver = new Engine();
        Shared.context = this;
        Shared.activity = this;


        navView = (NavigationView) findViewById(R.id.nav_view);

        navView.setNavigationItemSelectedListener(this);
        switchMusicCompat = (SwitchCompat) navView.getMenu().getItem(1).getActionView();
        switchMusicCompat.setOnCheckedChangeListener(this);
        switchMusicCompat.setChecked(true);
        switchModeCompat = (SwitchCompat) navView.getMenu().getItem(2).getActionView();
        switchModeCompat.setOnCheckedChangeListener(this);

        Shared.eventObserver.start();
        Shared.eventPool.notify(new StartGame());


    }

    public void start_new_game() {
        final AlertDialog.Builder ad = new AlertDialog.Builder(this);

        ad.setMessage(R.string.question_about_the_new_game)
                .setCancelable(false)
                .setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Shared.eventPool.stopAll();
                        Shared.eventPool.notify(new StartNewGame());
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
        Shared.eventObserver.onPause();
    }


    public void onResume() {
        super.onResume();
        Music.play();
        Shared.eventObserver.onResume();

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
                set_game_mode(isChecked);
                break;
            default:
                break;
        }
    }

    protected void setMusic(boolean isChecked) {
        SetMusic setMusic = new SetMusic();
        setMusic.setEnable(isChecked);
        Shared.eventPool.notify(setMusic);
    }

    protected void set_game_mode(boolean isChecked) {
        SetGameMode gameMode = new SetGameMode();
        gameMode.setFast_game(isChecked);
        Shared.eventPool.notify(gameMode);
    }
}

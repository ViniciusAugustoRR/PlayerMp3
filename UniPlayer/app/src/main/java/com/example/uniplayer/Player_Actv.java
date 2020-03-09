package com.example.uniplayer;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import com.example.uniplayer.Adapters.faixaAdapter;
import com.example.uniplayer.fragments.FaixaFragment;
import com.example.uniplayer.models.faixaModel;

import java.sql.Time;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

public class Player_Actv extends AppCompatActivity implements Runnable{

    private Button btn_previus;
    private Button btn_playpause;
    private Button btn_next;

    private TextView nomeMusica;
    private SeekBar progressionMusic;
    private TextView tempoDecorrido;
    private TextView tempoTotal;

    private ImageView capaAlbum;
    private static MediaPlayer mediaPlayerSong;
    private int position;
    private faixaModel musicaAtual;
    int progPause;
    String timeTot = "";

    @SuppressLint("HandlerLeak")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player__actv);
        final Context context = this;

        btn_next = findViewById(R.id.btn_next);
        btn_previus = findViewById(R.id.btn_previus);
        btn_playpause = findViewById(R.id.btn_playpause);

        nomeMusica = findViewById(R.id.tv_musictitle);
        progressionMusic = findViewById(R.id.progressMusic);
        tempoDecorrido = findViewById(R.id.tempoDecorrido);
        tempoTotal = findViewById(R.id.tempoTotal);

        capaAlbum = findViewById(R.id.capaAlbum_playeractv);



        if(mediaPlayerSong != null){
            mediaPlayerSong.stop();
            mediaPlayerSong.release();

        }


        //RETRIEVING INTENT ITEMS
        position = getIntent().getIntExtra("position", -1);
        musicaAtual = faixaAdapter.musicsList.get(position);


        nomeMusica.setText(musicaAtual.getNomeFaixa());
        Bitmap bm = BitmapFactory.decodeByteArray(musicaAtual.getCapaAlbum(), 0, musicaAtual.getCapaAlbum().length);
        capaAlbum.setImageBitmap(bm);


        btn_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mediaPlayerSong.stop();

                position += 1;
                musicaAtual = faixaAdapter.musicsList.get(position);
                nomeMusica.setText(musicaAtual.getNomeFaixa());
                Bitmap bmp = BitmapFactory.decodeByteArray(musicaAtual.getCapaAlbum(), 0, musicaAtual.getCapaAlbum().length);
                capaAlbum.setImageBitmap(bmp);

                startMusic();
            }
        });
        btn_previus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mediaPlayerSong.stop();

                position -= 1;
                musicaAtual = faixaAdapter.musicsList.get(position);
                nomeMusica.setText(musicaAtual.getNomeFaixa());
                Bitmap bmp = BitmapFactory.decodeByteArray(musicaAtual.getCapaAlbum(), 0, musicaAtual.getCapaAlbum().length);
                capaAlbum.setImageBitmap(bmp);

                startMusic();
            }
        });



       startMusic();


        btn_playpause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mediaPlayerSong.isPlaying()) {
                    mediaPlayerSong.pause();
                    btn_playpause.setBackgroundResource(R.drawable.ic_play);
                    progPause = mediaPlayerSong.getCurrentPosition();

                }else{
                    mediaPlayerSong.seekTo(progPause);
                    mediaPlayerSong.start();
                    btn_playpause.setBackgroundResource(R.drawable.ic_pause);
                }
            }
        });

        Thread t = new Thread(this);
        t.start();

        progressionMusic.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

                if(fromUser) {
                    progressionMusic.setProgress(progress);
                    mediaPlayerSong.seekTo(progressionMusic.getProgress());

                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        try {
            if (mediaPlayerSong == null) {
                mediaPlayerSong = new MediaPlayer();
            }

            mediaPlayerSong.start();
            new Thread(this).start();

        } catch (Exception e){
            e.printStackTrace();
        }

    }


    private void startMusic(){

        if(mediaPlayerSong == null){
            mediaPlayerSong = new MediaPlayer();
        }


        Uri uri = Uri.parse(TestForData.songsDirect.get(position+1).toString());

        mediaPlayerSong = MediaPlayer.create(getApplicationContext(), uri);

        progressionMusic.setMax(mediaPlayerSong.getDuration());
        progressionMusic.setProgress(0);

        btn_playpause.setBackgroundResource(R.drawable.ic_pause);
        mediaPlayerSong.start();

    }


    @Override
    public void run(){
        int currentPos = mediaPlayerSong.getCurrentPosition();
        int totalPos = mediaPlayerSong.getDuration();

        long minTot;
        long secTot;

        minTot = mediaPlayerSong.getDuration() / 1000 / 60;
        secTot = mediaPlayerSong.getDuration() / 1000 % 60;

        timeTot = minTot + ":";
        if(secTot < 10) timeTot += "0";
        timeTot += secTot;

        tempoTotal.setText(timeTot);

        while(mediaPlayerSong != null && mediaPlayerSong.isPlaying() && mediaPlayerSong.getCurrentPosition() < mediaPlayerSong.getDuration()){
            try{

                tempoDecorrido.setText(timeLabel(mediaPlayerSong.getDuration() - mediaPlayerSong.getCurrentPosition()));
                currentPos = mediaPlayerSong.getCurrentPosition();

                Thread.sleep(1000);

            } catch (InterruptedException e){
                return;
            } catch (Exception e){
                return;
            }

            progressionMusic.setProgress(currentPos);
        }
    }

    private String timeLabel(int time){
        int min;
        int sec;

        min = mediaPlayerSong.getCurrentPosition() / 1000 / 60;
        sec = mediaPlayerSong.getCurrentPosition() / 1000 % 60;

        String tempo = min + ":";
        if(sec < 10) tempo += "0";
        tempo += sec;

        return tempo;
    }

    @Override
    protected void onDestroy(){
        super.onDestroy();
        clearMediaP();

    }

    private void clearMediaP(){
        mediaPlayerSong.stop();
        mediaPlayerSong.release();
        mediaPlayerSong = null;
    }

}

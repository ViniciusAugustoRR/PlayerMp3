package com.example.uniplayer;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ActivityManager;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaMetadataRetriever;
import android.os.Build;
import android.os.Environment;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.example.uniplayer.fragments.AlbumsFragment;
import com.example.uniplayer.fragments.FaixaFragment;
import com.example.uniplayer.fragments.PlaylistFragment;
import com.example.uniplayer.models.faixaModel;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class TestForData extends AppCompatActivity {
    private TextView textr;
    private Button btnr;
    public static ArrayList<File> songsDirect;
    public static String nomeFaixas[];
    public static ArrayList<faixaModel> musicasFaixasM = new ArrayList<>();
    private byte[] artAlbum;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_for_data);

        if(!arePermissionsDenied()){


            songsDirect = lerFaixas(Environment.getExternalStorageDirectory());
            MediaMetadataRetriever mmr = new MediaMetadataRetriever();
            nomeFaixas = new String[songsDirect.size()];



            for(int c = 1; c < songsDirect.size(); c += 1){
                mmr.setDataSource(String.valueOf(songsDirect.get(c)));

                artAlbum = mmr.getEmbeddedPicture();
                Bitmap bm = BitmapFactory.decodeByteArray(artAlbum, 0, artAlbum.length);

                musicasFaixasM.add(new faixaModel(
                        bm,
                        mmr.extractMetadata(MediaMetadataRetriever.METADATA_KEY_TITLE),
                        mmr.extractMetadata(MediaMetadataRetriever.METADATA_KEY_ALBUM)));

                //nomeFaixas[c] = mmr.extractMetadata(MediaMetadataRetriever.METADATA_KEY_TITLE);
            }


            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    Intent intent = new Intent(getBaseContext(), Menu.class);
                    startActivity(intent);
                }
            }, 1000);

        }else{
            System.out.println("------------->N TA SUSSA N IRMÃO");
        }


    }



    // PERMISSIONS
    private static final String[] PERMISSIONS = {
            Manifest.permission.READ_EXTERNAL_STORAGE
    };
    private static final int REQUEST_PERMISSIONS = 12345;
    private static final int PERMISSION_COUNT = 1;
    @SuppressLint("NewApi")
    private boolean arePermissionsDenied(){
        for(int c = 0; c < PERMISSION_COUNT; c+=1){
            if(checkSelfPermission(PERMISSIONS[c]) != PackageManager.PERMISSION_GRANTED){
                return true;
            }
        }
        return false;
    }
    @Override
    public void onRequestPermissionsResult(int resquestCode, String[] permissions, int[] grantResult){
        super.onRequestPermissionsResult(resquestCode, permissions, grantResult);

        if(arePermissionsDenied()){
            ((ActivityManager) (this.getSystemService(ACTIVITY_SERVICE))).clearApplicationUserData();
        }else{
            onResume();
        }
    }
    @Override
    protected void onResume(){
        super.onResume();
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && arePermissionsDenied()){
            requestPermissions(PERMISSIONS, REQUEST_PERMISSIONS);
            return;
        }
    }





    private ArrayList<File> lerFaixas(File root){
        ArrayList<File> array = new ArrayList<File>();
        File files[] = root.listFiles();

        for(File file: files){
            if(file.isDirectory()){
                array.addAll(lerFaixas(file));
            }else{
                if(file.getName().endsWith(".mp3")){
                    array.add(file);
                }
            }
        }
        return array;
    }
}

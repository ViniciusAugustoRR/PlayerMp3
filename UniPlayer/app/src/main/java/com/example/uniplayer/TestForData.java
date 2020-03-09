package com.example.uniplayer;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ActivityManager;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.media.MediaMetadataRetriever;
import android.os.Build;
import android.os.Environment;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;


import com.example.uniplayer.models.faixaModel;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.ArrayList;


public class TestForData extends AppCompatActivity {


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
            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    Intent intent = new Intent(getBaseContext(), Menu.class);
                    startActivity(intent);
                }
            }, 1000);

        }
    }
    @Override
    protected void onResume(){
        super.onResume();
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && arePermissionsDenied()){
            requestPermissions(PERMISSIONS, REQUEST_PERMISSIONS);

        }
    }




    public static ArrayList<File> songsDirect;
    public static ArrayList<faixaModel> musicasFaixasM = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_for_data);



        if(!arePermissionsDenied()){
            byte[] imageConverted;
            songsDirect = lerFaixas(Environment.getExternalStorageDirectory());
            ArrayList<String> itensMusica = new ArrayList<>();


            for(int c = 1; c < songsDirect.size(); c += 1){
                MediaMetadataRetriever mmr = new MediaMetadataRetriever();
                mmr.setDataSource(String.valueOf(songsDirect.get(c)));


                if( mmr.getEmbeddedPicture()!=null &&  mmr.getEmbeddedPicture().length > 0){
                    imageConverted = mmr.getEmbeddedPicture();

                }else{
                    imageConverted = drawableToBytes(getDrawable(R.drawable.generic_cover));

                }

                String faixa = mmr.extractMetadata(MediaMetadataRetriever.METADATA_KEY_TITLE);
                itensMusica.add(faixa);
                String album = mmr.extractMetadata(MediaMetadataRetriever.METADATA_KEY_ALBUM);
                itensMusica.add(album);
                String artista = mmr.extractMetadata(MediaMetadataRetriever.METADATA_KEY_ARTIST);
                itensMusica.add(artista);
                String duration =  mmr.extractMetadata(MediaMetadataRetriever.METADATA_KEY_DURATION);
                itensMusica.add(duration);
                String genero = mmr.extractMetadata(MediaMetadataRetriever.METADATA_KEY_GENRE);
                itensMusica.add(genero);
                String ano =  mmr.extractMetadata(MediaMetadataRetriever.METADATA_KEY_YEAR);
                itensMusica.add(ano);

                itensMusica = verifyNull(itensMusica);

                musicasFaixasM.add(new faixaModel(
                        imageConverted,
                        itensMusica.get(0),
                        itensMusica.get(1),
                        itensMusica.get(2),
                        itensMusica.get(3),
                        itensMusica.get(4),
                        itensMusica.get(5)
                    ));

                itensMusica.clear();

            }


            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    Intent intent = new Intent(getBaseContext(), Menu.class);
                    startActivity(intent);
                }
            }, 1000);

        }

    }




    /**
     * @param musica
     * Um arrayList contendo itens string de um arquivo .mp3
     * @return
     * Apos itens analisados, caso ele esteja null o texto "desconhecido" vai ser utilizado no lugar do null
     */
    private ArrayList<String> verifyNull(ArrayList<String> musica){
        ArrayList<String> itens = new ArrayList<>();

        for(String item : musica){
            if(item==null){
                itens.add("Desconhecido");
            }else{
                itens.add(item);
            }
        }
        return itens;
    }

    /**
     * @param root
     * Função lê o root tipo File que pode ser um diretorio ou arquivo, mas inicialmente um diretorio
     *
     * @return
     * Caso seja um diretorio, a função chama a si mesma, para fazer a leitura do diretorio e adicionar os itens(.mp3) dentro dela
     * Caso seja um arquivo(.mp3), ele adiciona o item a lista Array
     */
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

    /**
     * @param drawable
     * Recebe um arquivo drawable
     *
     * @return
     * Retorna a mesma imagem, mas do tipo bitmap
     */
    private byte[] drawableToBytes (Drawable drawable) {
        Bitmap bitmap = null;


        if(drawable.getIntrinsicWidth() <= 0 || drawable.getIntrinsicHeight() <= 0) {
            bitmap = Bitmap.createBitmap(1, 1, Bitmap.Config.ARGB_8888); // Single color bitmap will be created of 1x1 pixel
        } else {
            bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
        }

        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
        drawable.draw(canvas);

        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);

        byte[] bytes= stream.toByteArray();

        return bytes;
    }

}

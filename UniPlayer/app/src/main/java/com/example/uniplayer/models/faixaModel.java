package com.example.uniplayer.models;

import android.graphics.Bitmap;

public class faixaModel {

    private Bitmap mCapaAlbum;
    private String mNomeFaixa;
    private String mNomeAlbum;
    private String mArtista;
    private String mDuration;
    private String mGenero;
    private String mAno;

    public faixaModel(Bitmap capaAlbum, String nomeFaixa, String nomeAlbum){

        mCapaAlbum = capaAlbum;
        mNomeFaixa = nomeFaixa;
        mNomeAlbum = nomeAlbum;

    }

    public Bitmap getCapaAlbum(){
        return mCapaAlbum;
    }
    public String getNomeFaixa(){
        return mNomeFaixa;
    }
    public String getNomeAlbum(){
        return mNomeAlbum;
    }


}

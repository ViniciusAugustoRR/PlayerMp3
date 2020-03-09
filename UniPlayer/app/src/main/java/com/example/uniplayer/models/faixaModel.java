package com.example.uniplayer.models;

import android.graphics.Bitmap;
import android.os.Parcel;
import android.os.Parcelable;

public class faixaModel implements Parcelable {

    private byte[] mCapaAlbum;
    private String mNomeFaixa;
    private String mNomeAlbum;
    private String mArtista;
    private String mDuration;
    private String mGenero;
    private String mAno;



    // CONSTRUCTOR

    public faixaModel(byte[] mCapaAlbum, String mNomeFaixa, String mNomeAlbum, String mArtista, String mDuration, String mGenero, String mAno) {
        this.mCapaAlbum = mCapaAlbum;
        this.mNomeFaixa = mNomeFaixa;
        this.mNomeAlbum = mNomeAlbum;
        this.mArtista = mArtista;
        this.mDuration = mDuration;
        this.mGenero = mGenero;
        this.mAno = mAno;
    }


    //PARCELABLE METHODS

    protected faixaModel(Parcel in) {
        mCapaAlbum = in.createByteArray();
        mNomeFaixa = in.readString();
        mNomeAlbum = in.readString();
        mArtista = in.readString();
        mDuration = in.readString();
        mGenero = in.readString();
        mAno = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeByteArray(mCapaAlbum);
        dest.writeString(mNomeFaixa);
        dest.writeString(mNomeAlbum);
        dest.writeString(mArtista);
        dest.writeString(mDuration);
        dest.writeString(mGenero);
        dest.writeString(mAno);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<faixaModel> CREATOR = new Creator<faixaModel>() {
        @Override
        public faixaModel createFromParcel(Parcel in) {
            return new faixaModel(in);
        }

        @Override
        public faixaModel[] newArray(int size) {
            return new faixaModel[size];
        }
    };



    //SETTERS AND GETTERS

    public byte[] getCapaAlbum() {
        return mCapaAlbum;
    }

    public void setCapaAlbum(byte[] mCapaAlbum) {
        this.mCapaAlbum = mCapaAlbum;
    }

    public String getNomeFaixa() {
        return mNomeFaixa;
    }

    public void setNomeFaixa(String mNomeFaixa) {
        this.mNomeFaixa = mNomeFaixa;
    }

    public String getNomeAlbum() {
        return mNomeAlbum;
    }

    public void setNomeAlbum(String mNomeAlbum) {
        this.mNomeAlbum = mNomeAlbum;
    }

    public String getArtista() {
        return mArtista;
    }

    public void setArtista(String mArtista) {
        this.mArtista = mArtista;
    }

    public String getDuration() {
        return mDuration;
    }

    public void setDuration(String mDuration) {
        this.mDuration = mDuration;
    }

    public String getGenero() {
        return mGenero;
    }

    public void setGenero(String mGenero) {
        this.mGenero = mGenero;
    }

    public String getAno() {
        return mAno;
    }

    public void setAno(String mAno) { this.mAno = mAno; }

}




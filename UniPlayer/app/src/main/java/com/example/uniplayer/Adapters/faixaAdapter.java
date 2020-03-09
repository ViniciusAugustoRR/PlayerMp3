package com.example.uniplayer.Adapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.uniplayer.Menu;
import com.example.uniplayer.Player_Actv;
import com.example.uniplayer.R;
import com.example.uniplayer.TestForData;
import com.example.uniplayer.fragments.FaixaFragment;
import com.example.uniplayer.models.faixaModel;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;





public class faixaAdapter extends RecyclerView.Adapter<faixaAdapter.faixaViewHolder>{
    public static ArrayList<faixaModel> musicsList;
    private static Context atualContext;


    public static class faixaViewHolder extends RecyclerView.ViewHolder{
        public ImageView capaAlbum;
        public TextView nomeMusica;
        public TextView nomeAlbum;


        public faixaViewHolder(@NonNull final View itemView){
            super(itemView);
            capaAlbum = itemView.findViewById(R.id.capaAlbum);
            nomeMusica = itemView.findViewById(R.id.nomeMusica);
            nomeAlbum = itemView.findViewById(R.id.nomeAlbum);
        }
    }


    public faixaAdapter(ArrayList<faixaModel> musicas, Context context){
        atualContext = context;
        musicsList = musicas;
    }


    @NonNull
    @Override
    public faixaViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.card_faixa, viewGroup, false);
        faixaViewHolder holder = new faixaViewHolder(v);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull faixaViewHolder faixaViewHolder, final int i) {
        final faixaModel faixaAtual = musicsList.get(i);

        Bitmap bmp = BitmapFactory.decodeByteArray(faixaAtual.getCapaAlbum(), 0, faixaAtual.getCapaAlbum().length);

        faixaViewHolder.capaAlbum.setImageBitmap(bmp);
        faixaViewHolder.nomeAlbum.setText(faixaAtual.getNomeAlbum());
        faixaViewHolder.nomeMusica.setText(faixaAtual.getNomeFaixa());


        faixaViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(atualContext, Player_Actv.class);
                intent.putExtra("position", i);
                atualContext.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return musicsList.size();
    }


    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }
}

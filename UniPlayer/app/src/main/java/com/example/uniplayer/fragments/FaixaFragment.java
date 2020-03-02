package com.example.uniplayer.fragments;

import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.uniplayer.Adapters.faixaAdapter;
import com.example.uniplayer.R;
import com.example.uniplayer.TestForData;
import com.example.uniplayer.models.faixaModel;

import java.io.File;
import java.util.ArrayList;

public class FaixaFragment extends Fragment {

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private ArrayList<File> songsDirect = new ArrayList<>(TestForData.songsDirect);
    private String nomeFaixas[] = TestForData.nomeFaixas;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_faixas, container, false);


        ArrayList<faixaModel> listaFaixas = TestForData.musicasFaixasM;



        mRecyclerView = rootView.findViewById(R.id.recycle_faixas);
        mLayoutManager = new LinearLayoutManager(getActivity().getBaseContext());
        mAdapter = new faixaAdapter(listaFaixas, getActivity().getBaseContext());

        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);

        return rootView;

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

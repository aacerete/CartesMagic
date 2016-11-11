package com.example.a46990527d.cartesmagic;

import com.bumptech.glide.Glide;
import com.example.a46990527d.cartesmagic.databinding.LvCartesRowBinding;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import static com.example.a46990527d.cartesmagic.R.id.ivImatge;

/**
 * Created by Usuario on 22/10/2016.
 */

public class AdaptadorCartas extends ArrayAdapter<Card> {
    public AdaptadorCartas(Context context, int resource, List<Card> objects) {
        super(context, resource, objects);
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {


        Card carta = getItem(position);

        LvCartesRowBinding binding = null;

        if (convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(getContext());
            binding = DataBindingUtil.inflate(inflater,R.layout.lv_cartes_row, parent, false);
        }else{
            binding = DataBindingUtil.getBinding(convertView);
        }


        binding.tvName.setText(carta.getName());
        binding.tvRarity.setText(carta.getRarity());
        binding.tvColors.setText(carta.getColors());
        binding.tvType.setText(carta.getType());
        Glide.with(getContext()).load(carta.getImageUrl()).into(binding.ivImatge);

         return binding.getRoot();

    }
}
package com.example.a46990527d.cartesmagic;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.example.a46990527d.cartesmagic.databinding.LvCartesRowBinding;

/**
 * Created by 46990527d on 11/11/16.
 */

public class CardsCursorAdapter  extends CupboardCursorAdapter<Card> {
    public CardsCursorAdapter(Context context, Class<Card> entityClass) {
        super(context, entityClass);
    }

    @Override
    public View newView(Context context, Card model, ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(context);
        LvCartesRowBinding binding = DataBindingUtil.inflate(
                inflater, R.layout.lv_cartes_row, parent, false);

        return binding.getRoot();
    }

    @Override
    public void bindView(View view, Context context, Card model) {

        LvCartesRowBinding binding = DataBindingUtil.getBinding(view);

        binding.tvName.setText(model.getName());
        binding.tvRarity.setText(model.getRarity());
        binding.tvType.setText(model.getType());
        binding.tvColors.setText(model.getColors());
        Glide.with(context).load(model.getImageUrl()).into(binding.ivImatge);

        //binding.tvTitle.setText(model.getTitle());
        //binding.tvCriticsScore.setText(model.getCritics_score());
        //Glide.with(context).load(model.getPosterUrl()).into(binding.ivPosterImage);
    }
}


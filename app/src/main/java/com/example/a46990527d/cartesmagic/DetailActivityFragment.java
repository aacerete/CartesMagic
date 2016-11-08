package com.example.a46990527d.cartesmagic;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

/**
 * A placeholder fragment containing a simple view.
 */
public class DetailActivityFragment extends Fragment {

    private View view;
    private TextView tvCardName;
    private TextView tvRarity;
    private TextView tvColors;
    private TextView tvText;
    private ImageView ivImage;

    public DetailActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_detail, container, false);

                Intent i = getActivity().getIntent();

                if (i != null) {
            Card card = (Card) i.getSerializableExtra("card");

                    if (card != null) {
                updateUi(card);
                }
            }

                return view;
        }


    private void updateUi(Card card) {
        Log.d("Card", card.toString());

        ivImage = (ImageView) view.findViewById(R.id.ivImatge);
        tvCardName = (TextView) view.findViewById(R.id.tvCardName);
        tvRarity = (TextView) view.findViewById(R.id.tvRarity);
        tvColors = (TextView) view.findViewById(R.id.tvColors);
        tvText = (TextView) view.findViewById(R.id.tvText);

        tvCardName.setText(card.getName());
        tvRarity.setText(card.getRarity());
        tvColors.setText(card.getColors());
        tvText.setText(card.getText());
        Glide.with(getContext()).load(card.getImageUrl()).into(ivImage);



    }
}

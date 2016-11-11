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
import android.databinding.DataBindingUtil;
import com.bumptech.glide.Glide;
import com.example.a46990527d.cartesmagic.databinding.FragmentDetailBinding;

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

    private FragmentDetailBinding binding;




    public DetailActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_detail, container, false);
        View view = binding.getRoot();

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

        binding.tvText.setText(card.getText());
        binding.tvColors.setText(card.getColors());
        binding.tvCardName.setText(card.getName());
        binding.tvRarity.setText(card.getRarity());
        Glide.with(getContext()).load(card.getImageUrl()).into(binding.ivImage);






    }
}

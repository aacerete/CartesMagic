package com.example.a46990527d.cartesmagic;

import android.content.Intent;
import android.content.SharedPreferences;
import android.databinding.DataBindingUtil;
import android.net.Uri;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.a46990527d.cartesmagic.databinding.FragmentMainBinding;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;

import nl.littlerobots.cupboard.tools.provider.UriHelper;

import static nl.qbusict.cupboard.CupboardFactory.cupboard;

/**
 * A placeholder fragment containing a simple view.
 */
public class MainActivityFragment extends Fragment {

    private ArrayList<Card> items;
    private AdaptadorCartas adapter;

    public MainActivityFragment() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setHasOptionsMenu(true);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        FragmentMainBinding binding = DataBindingUtil.inflate(inflater, R.layout.fragment_main, container,false);
            View view = binding.getRoot();


        items = new ArrayList<>();

        adapter = new AdaptadorCartas(
                getContext(),
                R.layout.lv_cartes_row,
                items
        );

        binding.lvCartes.setAdapter(adapter);

        binding.lvCartes.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                Card card = (Card) adapterView.getItemAtPosition(i);

                Intent intent = new Intent(getContext(), DetailActivity.class);
                intent.putExtra("card", card);

                startActivity(intent);
            }
        });


        return view;
    }

    //agreguem el menuItem refresh al Menu
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);

        inflater.inflate(R.menu.menu_cartes_fragment, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int idmenuitem = item.getItemId();

        if (idmenuitem == R.id.action_Refresh) {
            refresh();
            return true;
        }


        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onStart() {
        super.onStart();
        refresh();
    }

    private void refresh() {

        RefreshDataTask tarea = new RefreshDataTask();
        tarea.execute();

    }

    //metode que s'executara en segon pla i fara la crida a l'api
    private class RefreshDataTask extends AsyncTask<Void, Void, Void> {
        @Override

        protected Void doInBackground(Void... voids) {

            SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getContext());
            String rarity = preferences.getString("rarity", "All");
            String colors = preferences.getString("colors", "All");

            //CardAPI api = new CardAPI();
            ArrayList <Card> result = null;

            if (rarity.equals("All") && colors.equals("All")){
                result = CardAPI.getCards();
            }else if (rarity.equalsIgnoreCase("All") && colors.equalsIgnoreCase(colors)){
                result = CardAPI.getCardsByColor(colors);
            }else if (rarity.equalsIgnoreCase(rarity) && colors.equalsIgnoreCase("All")) {
                result = CardAPI.getCardsByRarity(rarity);
            }else if (rarity.equalsIgnoreCase(rarity) && colors.equalsIgnoreCase(colors)){
                 result = CardAPI.getCardsByColorAndRarity(rarity,colors);

            }
            Log.d("DEBUG", result != null ? result.toString() : null);

            UriHelper helper = UriHelper.with(CartesMagicContentProvider.AUTHORITY);
            Uri cardUri = helper.getUri(Card.class);
            cupboard().withContext(getContext()).put(cardUri, Card.class, result);

            return null;
        }
    }
}
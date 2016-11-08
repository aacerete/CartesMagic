package com.example.a46990527d.cartesmagic;

import android.content.Intent;
import android.content.SharedPreferences;
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

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;

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
        View view = inflater.inflate(R.layout.fragment_main, container, false);

        ListView lvCartes = (ListView) view.findViewById(R.id.lvCartes);


        items = new ArrayList<>();

        adapter = new AdaptadorCartas(
                getContext(),
                R.layout.lv_cartes_row,
                items
        );

        lvCartes.setAdapter(adapter);

        lvCartes.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                Card card = (Card) adapterView.getItemAtPosition(i);

                Intent intent = new Intent(getContext(), DetailActivity.class);
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
    private class RefreshDataTask extends AsyncTask<Void, Void, ArrayList<Card>> {
        @Override
        protected ArrayList<Card> doInBackground(Void... voids) {

            SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getContext());
            String rarity = preferences.getString("rarity", "All");
            String colors = preferences.getString("colors", "All");

            CardAPI api = new CardAPI();
            ArrayList <Card> result = null;

            if (rarity.equals("All") && colors.equals("All")){
                result = api.getCards();
            }else if (rarity.equalsIgnoreCase("All") && colors.equalsIgnoreCase(colors)){
                result = api.getCardsByColor(colors);
            }else if (rarity.equalsIgnoreCase(rarity) && colors.equalsIgnoreCase("All")) {
                result = api.getCardsByRarity(rarity);
            }else if (rarity.equalsIgnoreCase(rarity) && colors.equalsIgnoreCase(colors)){
                 result = api.getCardsByColorAndRarity(rarity,colors);

            }
            Log.d("DEBUG", result != null ? result.toString() : null);

            return result;
        }

        @Override
        protected void onPostExecute(ArrayList<Card> respuesta) {
            if (respuesta != null) {
                adapter.clear();
                adapter.addAll(respuesta);
            }
        }
    }
}
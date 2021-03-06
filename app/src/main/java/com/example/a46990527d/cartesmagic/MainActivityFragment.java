package com.example.a46990527d.cartesmagic;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.databinding.DataBindingUtil;

import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;


import com.example.a46990527d.cartesmagic.databinding.FragmentMainBinding;


import java.util.ArrayList;




/**
 * A placeholder fragment containing a simple view.
 */
public class MainActivityFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor> {

    private ProgressDialog dialog;
    private CardsCursorAdapter adapter;

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

        adapter = new CardsCursorAdapter(getContext(),Card.class);

        dialog = new ProgressDialog(getContext());
        dialog.setMessage("Cargando");

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

        getLoaderManager().initLoader(0,null,this);

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

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        return DataManager.getCursorLoader(getContext());
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        adapter.swapCursor(data);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        adapter.swapCursor(null);
    }


    //metode que s'executara en segon pla i fara la crida a l'api
    private class RefreshDataTask extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            dialog.show();
        }

        @Override
        protected Void doInBackground(Void... voids) {

            SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getContext());
            String rarity = preferences.getString("rarity", "All");
            String colors = preferences.getString("colors", "All");


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

            DataManager.deleteCards(getContext());
            DataManager.saveCards(result,getContext());

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

            dialog.dismiss();
        }
    }
}
package com.example.a46990527d.cartesmagic;

import android.net.Uri;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by 46990527d on 18/10/16.
 */

public class CardAPI {

    private static final String BASE_URL = "https://api.magicthegathering.io/v1";

    public static ArrayList<Card> getCardsByColor (String colors){
        Uri builtUri = Uri.parse(BASE_URL)
                .buildUpon()
                .appendPath("cards")
                .appendQueryParameter("colors", colors)
                .build();

        return CridaApi(builtUri);
    }

    public static ArrayList<Card> getCardsByRarity (String rarity){

        Uri builtUri = Uri.parse(BASE_URL)
                .buildUpon()
                .appendPath("cards")
                .appendQueryParameter("rarity", rarity)
                .build();

        return CridaApi(builtUri);
    }

    public static ArrayList<Card> getCardsByColorAndRarity (String rarity, String colors) {

        Uri builtUri = Uri.parse(BASE_URL)
                .buildUpon()
                .appendPath("cards")
                .appendQueryParameter("colors", colors)
                .appendQueryParameter("rarity", rarity)
                .build();

        return CridaApi(builtUri);

    }


    public static ArrayList<Card> getCards() {
        Uri builtUri = Uri.parse(BASE_URL)
                .buildUpon()
                .appendPath("cards")
                .build();

       return CridaApi(builtUri);


    }

    public static ArrayList <Card> CridaApi  ( Uri builtUri ){

        try {
            String url = builtUri.toString();
            Log.d("URL", url);
            String JsonResponse = HttpUtils.get(url);
            //creem un objecte Json a partir de l'string de resposta amb el metode
            JSONObject JSONCards = TratarStringRespuesta(JsonResponse);

            return ConvertirEnCarta(JSONCards);

        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException j) {
            j.printStackTrace();
        }
        return null;
    }

    // A partir de l'String , el convertim a un objecte JSON per poder tractar-lo
    public static JSONObject TratarStringRespuesta(String respuesta) throws JSONException {

        JSONObject obj = new JSONObject(respuesta);

        return obj;
    }

    //a Partir de l'objecte JSON rebut (que conte Cards amb Totes les cartes juntes), alimentem el nostre arraylist Card per a interactuar mes endavant
    public static ArrayList<Card> ConvertirEnCarta(JSONObject cartas) throws JSONException {

        ArrayList<Card> cards = new ArrayList<>();
        JSONArray JSONCards = cartas.getJSONArray("cards");

        for (int i = 0; i < JSONCards.length(); i++) {
            JSONObject objeto = JSONCards.getJSONObject(i);
            Card Carta = new Card();
            Carta.setName(objeto.getString("name"));
            Carta.setType(objeto.getString("type"));
            Carta.setRarity(objeto.getString("rarity"));
            if (objeto.has("text")){
                Carta.setText(objeto.getString("text"));
            }

            if (objeto.has("colors")) {
                Carta.setColors(objeto.getString("colors"));
            } else {
                Carta.setColors(null);
            }

            if (objeto.has("imageUrl")){
                Carta.setImageUrl(objeto.getString("imageUrl"));
            }else{
                Carta.setImageUrl(null);
            }

            cards.add(Carta);
        }

        return cards;
    }
}



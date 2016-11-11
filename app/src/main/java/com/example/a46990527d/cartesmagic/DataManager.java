package com.example.a46990527d.cartesmagic;

import android.content.Context;
import android.net.Uri;
import android.support.v4.content.CursorLoader;

import java.util.ArrayList;

import nl.littlerobots.cupboard.tools.provider.UriHelper;

import static nl.qbusict.cupboard.CupboardFactory.cupboard;

/**
 * Created by 46990527d on 11/11/16.
 */

public class DataManager {
    private static UriHelper URI_HELPER = UriHelper.with(CartesMagicContentProvider.AUTHORITY);
    private static Uri CARD_URI = URI_HELPER.getUri(Card.class);

    static void saveCards(ArrayList<Card> cartes, Context context) {
        cupboard().withContext(context).put(CARD_URI, Card.class, cartes);
    }

    static void deleteCards (Context context){
        cupboard().withContext(context).delete(CARD_URI, "_id > ?", "0");

    }

    static CursorLoader getCursorLoader(Context context) {
        return new CursorLoader(context, CARD_URI, null, null, null, null);
    }
}

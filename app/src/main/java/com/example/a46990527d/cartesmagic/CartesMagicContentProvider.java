package com.example.a46990527d.cartesmagic;

import android.content.ContentProvider;

import nl.littlerobots.cupboard.tools.provider.CupboardContentProvider;
import static nl.qbusict.cupboard.CupboardFactory.cupboard;

/**
 * Created by 46990527d on 11/11/16.
 */

public class CartesMagicContentProvider extends CupboardContentProvider {

    public static final String AUTHORITY = BuildConfig.APPLICATION_ID + ".provider";

    static {
        cupboard().register(Card.class);
    }

    public CartesMagicContentProvider(){
        super(AUTHORITY,1);
    }

}
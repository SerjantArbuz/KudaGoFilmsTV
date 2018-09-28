package sgtmelon.kudagofilmstv.annot;

import android.support.annotation.StringDef;

@StringDef({DefFilm.INTENT, DefFilm.SHARED_ELEMENT})
public @interface DefFilm {

    String INTENT = "INTENT_ITEM_FILM";
    String SHARED_ELEMENT = "SHARED_ELEMENT_FILM";

}

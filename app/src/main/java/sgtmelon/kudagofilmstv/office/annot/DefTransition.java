package sgtmelon.kudagofilmstv.office.annot;

import android.support.annotation.StringDef;

@StringDef({DefTransition.INTENT_FILM, DefTransition.SHARED_ELEMENT_FILM})
public @interface DefTransition {

    String INTENT_FILM = "INTENT_ITEM_FILM";
    String SHARED_ELEMENT_FILM = "SHARED_ELEMENT_FILM";

}

package sgtmelon.kudagofilmstv.office.annot;

import android.support.annotation.StringDef;

@StringDef({
        DefIntent.INTENT_FILM,
        DefIntent.INTENT_POSTER,
        DefIntent.INTENT_IMAGES
})
public @interface DefIntent {

    String INTENT_FILM = "INTENT_FILM";
    String INTENT_POSTER = "INTENT_POSTER";
    String INTENT_IMAGES = "INTENT_IMAGES";

}

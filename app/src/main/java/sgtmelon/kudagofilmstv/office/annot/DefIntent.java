package sgtmelon.kudagofilmstv.office.annot;

import android.support.annotation.StringDef;

/**
 * Аннотация для передачи данных между классами
 */
@StringDef({
        DefIntent.FILM,
        DefIntent.PAGE
})
public @interface DefIntent {

    String FILM = "INTENT_FILM";
    String PAGE = "INTENT_PAGE";

}

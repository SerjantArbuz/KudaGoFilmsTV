package sgtmelon.kudagofilmstv.office.def;

import androidx.annotation.StringDef;

/**
 * Аннотация для передачи данных между классами
 */
@StringDef({
        DefIntent.ID,
        DefIntent.PAGE
})
public @interface DefIntent {

    String ID = "INTENT_FILM_ID";
    String PAGE = "INTENT_CURRENT_PAGE";

}

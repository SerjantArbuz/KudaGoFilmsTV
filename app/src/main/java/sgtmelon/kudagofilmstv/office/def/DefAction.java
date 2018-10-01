package sgtmelon.kudagofilmstv.office.def;

import androidx.annotation.IntDef;

/**
 * Аннотации для меню действий
 */
@IntDef({
        DefAction.URL_TRAILER,
        DefAction.URL_FAVORITE
})
public @interface DefAction {

    int URL_TRAILER = 0;
    int URL_FAVORITE = 1;

}

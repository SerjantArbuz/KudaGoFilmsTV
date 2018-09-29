package sgtmelon.kudagofilmstv.office.annot;

import android.support.annotation.IntDef;

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

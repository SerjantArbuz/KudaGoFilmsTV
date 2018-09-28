package sgtmelon.kudagofilmstv.annot;

import android.support.annotation.IntDef;

@IntDef({
        DefAction.URL_KUDA_GO,
        DefAction.URL_IMDB
})
public @interface DefAction {

    int URL_KUDA_GO = 0;
    int URL_IMDB = 1;

}

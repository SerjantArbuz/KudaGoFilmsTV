package sgtmelon.kudagofilmstv.annot;

import android.support.annotation.IntDef;

@IntDef({
        DefAction.URL_TRAILER,
        DefAction.URL_KUDA_GO
})
public @interface DefAction {

    int URL_TRAILER = 0;
    int URL_KUDA_GO = 1;

}

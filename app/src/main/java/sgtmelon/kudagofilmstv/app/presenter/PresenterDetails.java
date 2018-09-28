package sgtmelon.kudagofilmstv.app.presenter;

import android.support.v17.leanback.widget.AbstractDetailsDescriptionPresenter;
import sgtmelon.kudagofilmstv.app.model.ItemFilm;

public class PresenterDetails extends AbstractDetailsDescriptionPresenter {
    @Override
    protected void onBindDescription(ViewHolder viewHolder, Object o) {
        ItemFilm itemFilm = (ItemFilm) o;

        if (itemFilm != null) {
            viewHolder.getTitle().setText(itemFilm.getTitle() + " " + itemFilm.getAgeRestriction());
            viewHolder.getSubtitle().setText(itemFilm.getYear() + ", " + itemFilm.getCountry() + ", " + itemFilm.getRunningTime());
            viewHolder.getBody().setText(itemFilm.getBodyText());
        }
    }
}

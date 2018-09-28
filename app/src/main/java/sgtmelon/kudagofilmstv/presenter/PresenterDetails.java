package sgtmelon.kudagofilmstv.presenter;

import android.support.v17.leanback.widget.AbstractDetailsDescriptionPresenter;
import sgtmelon.kudagofilmstv.model.ItemFilm;

public class PresenterDetails extends AbstractDetailsDescriptionPresenter {
    @Override
    protected void onBindDescription(ViewHolder viewHolder, Object o) {
        ItemFilm itemFilm = (ItemFilm) o;

        if (itemFilm != null) {
            viewHolder.getTitle().setText(itemFilm.getTitle() + " " + itemFilm.getAge());
            viewHolder.getSubtitle().setText(itemFilm.getYear() + ", " + itemFilm.getCountry() + ", " + itemFilm.getTime());
            viewHolder.getBody().setText(itemFilm.getDetails());
        }
    }
}

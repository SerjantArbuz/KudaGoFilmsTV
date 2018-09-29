package sgtmelon.kudagofilmstv.app.presenter;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.support.annotation.ColorInt;
import android.support.v17.leanback.widget.BaseCardView;
import android.support.v17.leanback.widget.ImageCardView;
import android.support.v17.leanback.widget.Presenter;
import android.support.v4.content.ContextCompat;
import android.view.ViewGroup;
import com.squareup.picasso.Picasso;
import sgtmelon.kudagofilmstv.R;
import sgtmelon.kudagofilmstv.app.model.item.ItemFilm;

import java.net.URI;

/**
 * Презентер для карточки фильма
 */
public class PresenterFilm extends Presenter {

    private final Context context;

    private final int cardWidth, cardHeight;

    @ColorInt
    private final int clBackgroundDefault;
    @ColorInt
    private final int clBackgroundSelected;

    private final Drawable icDefault;

    public PresenterFilm(Context context) {
        this.context = context;

        Resources res = context.getResources();
        cardWidth = res.getDimensionPixelSize(R.dimen.card_width);
        cardHeight = res.getDimensionPixelSize(R.dimen.card_height);

        clBackgroundDefault = ContextCompat.getColor(context, R.color.background_details_default);
        clBackgroundSelected = ContextCompat.getColor(context, R.color.background_details_selected);

        icDefault = context.getDrawable(R.drawable.ic_default);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup) {
        ImageCardView cardView = new ImageCardView(context) {
            @Override
            public void setSelected(boolean selected) {
                updateBackgroundColor(this, selected);
                super.setSelected(selected);
            }
        };

        cardView.setCardType(BaseCardView.CARD_TYPE_INFO_UNDER);
        cardView.setInfoVisibility(BaseCardView.CARD_REGION_VISIBLE_ALWAYS);

        cardView.setFocusable(true);
        cardView.setFocusableInTouchMode(true);

        updateBackgroundColor(cardView, false);

        return new ViewHolder(cardView);
    }

    private void updateBackgroundColor(ImageCardView view, boolean selected) {
        int color = selected ? clBackgroundSelected : clBackgroundDefault;

        view.setBackgroundColor(color);
        view.findViewById(R.id.info_field).setBackgroundColor(color);
    }

    @Override
    public void onBindViewHolder(Presenter.ViewHolder viewHolder, Object o) {
        ImageCardView cardView = (ImageCardView) viewHolder.view;
        ItemFilm itemFilm = (ItemFilm) o;

        cardView.setMainImageDimensions(cardWidth, cardHeight);

        URI uri = itemFilm.getPoster();
        if (uri != null) {
            Picasso.get()
                    .load(uri.toString())
                    .resize(cardWidth, cardHeight)
                    .placeholder(icDefault)
                    .error(icDefault)
                    .centerCrop()
                    .into(cardView.getMainImageView());
        } else {
            cardView.setMainImage(icDefault);
        }

        cardView.setTitleText(itemFilm.getTitle());
        cardView.setContentText(itemFilm.getRating() == null ? "Без рейтинга" : "Рейтинг: " + itemFilm.getRating());
    }

    @Override
    public void onUnbindViewHolder(Presenter.ViewHolder viewHolder) {
        ImageCardView cardView = (ImageCardView) viewHolder.view;

        cardView.setBadgeImage(null);
        cardView.setMainImage(null);
    }

}

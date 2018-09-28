package sgtmelon.kudagofilmstv.presenter;

import android.content.Context;
import android.content.res.Resources;
import android.support.annotation.ColorInt;
import android.support.v17.leanback.widget.BaseCardView;
import android.support.v17.leanback.widget.ImageCardView;
import android.support.v17.leanback.widget.Presenter;
import android.support.v4.content.ContextCompat;
import android.view.ViewGroup;
import com.squareup.picasso.Picasso;
import sgtmelon.kudagofilmstv.R;
import sgtmelon.kudagofilmstv.model.ItemFilm;

public class PresenterFilm extends Presenter {

    private final Context context;

    private final int cardWidth, cardHeight; // TODO: 28.09.2018

    @ColorInt
    private final int clBackgroundDefault;
    @ColorInt
    private final int clBackgroundSelected;

    public PresenterFilm(Context context) {
        this.context = context;

        Resources res = context.getResources();
        cardWidth = res.getDimensionPixelSize(R.dimen.card_width);
        cardHeight = res.getDimensionPixelSize(R.dimen.card_height);

        clBackgroundDefault = ContextCompat.getColor(context, R.color.background_details_default);
        clBackgroundSelected = ContextCompat.getColor(context, R.color.background_details_selected);
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

        Picasso.get()
                .load(itemFilm.getPoster().toString())
                .resize(cardWidth, cardHeight)
                .placeholder(R.drawable.ic_default)
                .centerCrop()
                .into(cardView.getMainImageView());

        cardView.setTitleText(itemFilm.getTitle() + " " + itemFilm.getAge());
        cardView.setContentText(itemFilm.getYear() + ", " + itemFilm.getCountry() + ", " + itemFilm.getTime());
    }

    @Override
    public void onUnbindViewHolder(Presenter.ViewHolder viewHolder) {
        ImageCardView cardView = (ImageCardView) viewHolder.view;

        cardView.setBadgeImage(null);
        cardView.setMainImage(null);
    }

}

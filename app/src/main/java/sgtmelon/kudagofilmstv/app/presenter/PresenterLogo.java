package sgtmelon.kudagofilmstv.app.presenter;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.support.v17.leanback.widget.DetailsOverviewLogoPresenter;
import android.support.v17.leanback.widget.DetailsOverviewRow;
import android.support.v17.leanback.widget.FullWidthDetailsOverviewRowPresenter;
import android.support.v17.leanback.widget.Presenter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.net.URI;

import sgtmelon.kudagofilmstv.R;
import sgtmelon.kudagofilmstv.app.model.item.ItemFilm;

/**
 * Презентер логотипа фильма
 */
public class PresenterLogo extends DetailsOverviewLogoPresenter {

    private final Drawable icDefault;
    private final int logoWidth, logoHeight;

    public PresenterLogo(Context context) {
        Resources res = context.getResources();

        icDefault = res.getDrawable(R.drawable.ic_default, null);

        logoWidth = res.getDimensionPixelSize(R.dimen.detail_thumb_width);
        logoHeight = res.getDimensionPixelSize(R.dimen.detail_thumb_height);
    }

    @Override
    public Presenter.ViewHolder onCreateViewHolder(ViewGroup parent) {
        ImageView imageView = (ImageView) LayoutInflater.from(parent.getContext()).inflate(R.layout.lb_fullwidth_details_overview_logo, parent, false);

        imageView.setLayoutParams(new ViewGroup.LayoutParams(logoWidth, logoHeight));
        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);

        return new ViewHolder(imageView);
    }

    @Override
    public void onBindViewHolder(Presenter.ViewHolder viewHolder, Object item) {
        ImageView imageView = ((ImageView) viewHolder.view);

        if (item instanceof DetailsOverviewRow) {
            DetailsOverviewRow row = (DetailsOverviewRow) item;
            if (row.getItem() instanceof ItemFilm) {
                ItemFilm itemFilm = (ItemFilm) row.getItem();

                URI uri = itemFilm.getPoster();
                if (uri != null) {
                    Picasso.get()
                            .load(uri.toString())
                            .resize(logoWidth, logoHeight)
                            .centerCrop()
                            .placeholder(icDefault)
                            .error(icDefault)
                            .into(imageView);
                } else {
                    imageView.setImageDrawable(icDefault);
                }

                if (isBoundToImage((ViewHolder) viewHolder, row)) {
                    PresenterLogo.ViewHolder vh = (PresenterLogo.ViewHolder) viewHolder;
                    vh.getParentPresenter().notifyOnBindLogo(vh.getParentViewHolder());
                }
            }
        }
    }

    static class ViewHolder extends DetailsOverviewLogoPresenter.ViewHolder {
        ViewHolder(View view) {
            super(view);
        }

        public FullWidthDetailsOverviewRowPresenter getParentPresenter() {
            return mParentPresenter;
        }

        public FullWidthDetailsOverviewRowPresenter.ViewHolder getParentViewHolder() {
            return mParentViewHolder;
        }

    }

}

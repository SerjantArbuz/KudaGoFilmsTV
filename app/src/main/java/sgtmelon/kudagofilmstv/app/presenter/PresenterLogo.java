package sgtmelon.kudagofilmstv.app.presenter;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.net.URI;
import java.net.URISyntaxException;

import androidx.leanback.widget.DetailsOverviewLogoPresenter;
import androidx.leanback.widget.DetailsOverviewRow;
import androidx.leanback.widget.FullWidthDetailsOverviewRowPresenter;
import androidx.leanback.widget.Presenter;
import sgtmelon.kudagofilmstv.R;
import sgtmelon.kudagofilmstv.app.model.item.ItemFilm;
import sgtmelon.kudagofilmstv.app.model.item.ItemImage;

/**
 * Презентер логотипа фильма
 */
public final class PresenterLogo extends DetailsOverviewLogoPresenter {

    private final Context context;

    private final Drawable icDefault;
    private final int logoWidth, logoHeight;

    public PresenterLogo(Context context) {
        this.context = context;

        Resources res = context.getResources();
        icDefault = res.getDrawable(R.drawable.ic_default, null);
        logoWidth = res.getDimensionPixelSize(R.dimen.detail_thumb_width);
        logoHeight = res.getDimensionPixelSize(R.dimen.detail_thumb_height);
    }

    @Override
    public Presenter.ViewHolder onCreateViewHolder(ViewGroup parent) {
        ImageView imageView = (ImageView) LayoutInflater
                .from(context)
                .inflate(R.layout.lb_fullwidth_details_overview_logo, parent, false);

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

                ItemImage itemImage = itemFilm.getPoster();
                if (itemImage != null) {
                    try {
                        URI uri = new URI(itemImage.getImage());
                        Picasso.get()
                                .load(uri.toString())
                                .resize(logoWidth, logoHeight)
                                .centerCrop()
                                .placeholder(icDefault)
                                .error(icDefault)
                                .into(imageView);
                    } catch (URISyntaxException e) {
                        imageView.setImageDrawable(icDefault);
                    }
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

    private static final class ViewHolder extends DetailsOverviewLogoPresenter.ViewHolder {
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

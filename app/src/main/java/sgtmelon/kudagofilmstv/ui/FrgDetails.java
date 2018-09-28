package sgtmelon.kudagofilmstv.ui;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v17.leanback.app.BrowseSupportFragment;
import android.support.v17.leanback.widget.*;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.widget.Toast;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;
import sgtmelon.kudagofilmstv.R;
import sgtmelon.kudagofilmstv.annot.DefAction;
import sgtmelon.kudagofilmstv.annot.DefFilm;
import sgtmelon.kudagofilmstv.model.ItemFilm;
import sgtmelon.kudagofilmstv.presenter.PresenterDetails;
import sgtmelon.kudagofilmstv.presenter.PresenterFilm;

public class FrgDetails extends BrowseSupportFragment {

    private static final String TAG = FrgDetails.class.getSimpleName();

    private Context context;
    private ActDetails activity;

    private ItemFilm itemFilm;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        Log.i(TAG, "onCreate");
        super.onCreate(savedInstanceState);

        context = getContext();
        activity = (ActDetails) getActivity();

        if (activity != null) {
            Bundle bundle = activity.getIntent().getExtras();

            itemFilm = bundle != null
                    ? itemFilm = bundle.getParcelable(DefFilm.INTENT)
                    : savedInstanceState != null
                    ? savedInstanceState.getParcelable(DefFilm.INTENT)
                    : null;
        }


        row = new DetailsOverviewRow(itemFilm);

        Resources res = getResources();
        int logoWidth = res.getDimensionPixelSize(R.dimen.detail_thumb_width); // TODO: 28.09.2018
        int logoHeight = res.getDimensionPixelSize(R.dimen.detail_thumb_height);

        Picasso.get()
                .load(itemFilm.getPoster().toString())
                .resize(logoWidth, logoHeight)
                .centerCrop()
                .into(new Target() {
                    @Override
                    public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                        row.setImageBitmap(activity, bitmap);
                    }

                    @Override
                    public void onBitmapFailed(Exception e, Drawable errorDrawable) {

                    }

                    @Override
                    public void onPrepareLoad(Drawable placeHolderDrawable) {

                    }
                });

        detailsPresenter = new FullWidthDetailsOverviewRowPresenter(new PresenterDetails());

        detailsPresenter.setBackgroundColor(ContextCompat.getColor(activity, R.color.background_fastlane));
        detailsPresenter.setInitialState(FullWidthDetailsOverviewRowPresenter.STATE_FULL);
        detailsPresenter.setOnActionClickedListener(action -> Toast.makeText(context, action.toString(), Toast.LENGTH_SHORT).show());

        setupActionRow();
    }

    DetailsOverviewRow row;
    FullWidthDetailsOverviewRowPresenter detailsPresenter;

    private void setupActionRow() {
        Log.i(TAG, "setupActionRow");

        SparseArrayObjectAdapter adapterAction = new SparseArrayObjectAdapter();

        adapterAction.set(DefAction.URL_KUDA_GO, new Action(DefAction.URL_KUDA_GO, getResources().getString(R.string.action_uri_kuda_go)));
        adapterAction.set(DefAction.URL_IMDB, new Action(DefAction.URL_IMDB, getResources().getString(R.string.action_uri_imdb)));

        row.setActionsAdapter(adapterAction);

        ArrayObjectAdapter listRowAdapter = new ArrayObjectAdapter(new PresenterFilm(context));
        for (int i = 0; i < 10; i++) {
            ItemFilm itemFilm = new ItemFilm();
            listRowAdapter.add(itemFilm);
        }
        HeaderItem headerItem = new HeaderItem(0, getString(R.string.header_act_details));

        ClassPresenterSelector presenterSelector = new ClassPresenterSelector();
        presenterSelector.addClassPresenter(DetailsOverviewRow.class, detailsPresenter);
        presenterSelector.addClassPresenter(ListRow.class, new ListRowPresenter());

        ArrayObjectAdapter adapter = new ArrayObjectAdapter(presenterSelector);
        adapter.add(row);
        adapter.add(new ListRow(headerItem, listRowAdapter));

        setAdapter(adapter);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        Log.i(TAG, "onSaveInstanceState");
        super.onSaveInstanceState(outState);

        outState.putParcelable(DefFilm.INTENT, itemFilm);
    }
}

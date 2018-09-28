package sgtmelon.kudagofilmstv.app.ui;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.v17.leanback.app.BackgroundManager;
import android.support.v17.leanback.app.DetailsSupportFragment;
import android.support.v17.leanback.widget.*;
import android.support.v4.content.ContextCompat;
import android.util.DisplayMetrics;
import android.util.Log;
import android.widget.Toast;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;
import sgtmelon.kudagofilmstv.R;
import sgtmelon.kudagofilmstv.app.model.ItemFilm;
import sgtmelon.kudagofilmstv.app.presenter.PresenterDetails;
import sgtmelon.kudagofilmstv.app.presenter.PresenterFilm;
import sgtmelon.kudagofilmstv.app.presenter.PresenterLogo;
import sgtmelon.kudagofilmstv.office.annot.DefAction;
import sgtmelon.kudagofilmstv.office.annot.DefIntent;

import java.net.URI;
import java.util.Timer;
import java.util.TimerTask;

public class FrgDetails extends DetailsSupportFragment implements OnItemViewClickedListener {

    private static final String TAG = FrgDetails.class.getSimpleName();

    private Context context;
    private ActDetails activity;

    private ItemFilm selectedFilm;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        Log.i(TAG, "onCreate");
        super.onCreate(savedInstanceState);

        context = getContext();
        activity = (ActDetails) getActivity();

        if (activity != null) {
            Bundle bundle = activity.getIntent().getExtras();

            selectedFilm = bundle != null
                    ? selectedFilm = bundle.getParcelable(DefIntent.INTENT_FILM)
                    : savedInstanceState != null
                    ? savedInstanceState.getParcelable(DefIntent.INTENT_FILM)
                    : null;
        }

        setupBackgroundManager();
        startBackgroundTimer();

        setupAdapter();
        setupDetailsOverviewRow();
        setupListRow();

        setOnItemViewClickedListener(this);
    }

    @Override
    public void onDestroy() {
        Log.i(TAG, "onDestroy");

        if (backgroundTimer != null) {
            backgroundTimer.cancel();
            backgroundTimer = null;
        }
        backgroundManager = null;

        super.onDestroy();
    }

    @Override
    public void onStop() {
        Log.i(TAG, "onStop");

        backgroundManager.release();

        super.onStop();
    }

    private BackgroundManager backgroundManager;

    private int windowWidth, windowHeight;

    private void setupBackgroundManager() {
        Log.i(TAG, "setupBackgroundManager");

        backgroundManager = BackgroundManager.getInstance(activity);
        backgroundManager.attach(activity.getWindow());

        DisplayMetrics metrics = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(metrics);

        windowWidth = metrics.widthPixels;
        windowHeight = metrics.heightPixels;
    }

    ArrayObjectAdapter adapter;

    private void setupAdapter() {
        Log.i(TAG, "setupAdapter");

        FullWidthDetailsOverviewRowPresenter detailsPresenter = new FullWidthDetailsOverviewRowPresenter(new PresenterDetails(), new PresenterLogo(context));

        detailsPresenter.setBackgroundColor(ContextCompat.getColor(activity, R.color.background_fastlane));
        detailsPresenter.setInitialState(FullWidthDetailsOverviewRowPresenter.STATE_HALF);
        detailsPresenter.setOnActionClickedListener(action -> Toast.makeText(context, action.toString(), Toast.LENGTH_SHORT).show());

        ClassPresenterSelector presenterSelector = new ClassPresenterSelector();
        presenterSelector.addClassPresenter(DetailsOverviewRow.class, detailsPresenter);
        presenterSelector.addClassPresenter(ListRow.class, new ListRowPresenter());

        adapter = new ArrayObjectAdapter(presenterSelector);
        setAdapter(adapter);
    }

    private void setupDetailsOverviewRow() {
        Log.i(TAG, "setupDetailsOverviewRow");

        final DetailsOverviewRow row = new DetailsOverviewRow(selectedFilm);

        Resources res = getResources();
        int logoWidth = res.getDimensionPixelSize(R.dimen.detail_thumb_width);
        int logoHeight = res.getDimensionPixelSize(R.dimen.detail_thumb_height);

        URI uri = selectedFilm.getPoster();
        if (uri != null) {
            Picasso.get()
                    .load(uri.toString())
                    .resize(logoWidth, logoHeight)
                    .centerCrop()
                    .placeholder(R.drawable.ic_default)
                    .into(new Target() {
                        @Override
                        public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                            Log.i(TAG, "onBitmapLoaded");
                            row.setImageBitmap(activity, bitmap);
                        }

                        @Override
                        public void onBitmapFailed(Exception e, Drawable errorDrawable) {
                            Log.i(TAG, "onBitmapFailed");

                            row.setImageDrawable(errorDrawable);
                        }

                        @Override
                        public void onPrepareLoad(Drawable placeHolderDrawable) {
                            Log.i(TAG, "onPrepareLoad");

                            row.setImageDrawable(placeHolderDrawable);
                        }
                    });
        } else {
            row.setImageDrawable(getResources().getDrawable(R.drawable.ic_default));
        }

        SparseArrayObjectAdapter adapterAction = new SparseArrayObjectAdapter();

        adapterAction.set(DefAction.URL_TRAILER, new Action(DefAction.URL_TRAILER, getResources().getString(R.string.action_url_trailer)));
        adapterAction.set(DefAction.URL_KUDA_GO, new Action(DefAction.URL_KUDA_GO, getResources().getString(R.string.action_url_kuda_go)));

        row.setActionsAdapter(adapterAction);

        adapter.add(row);
    }

    private void setupListRow() {
        Log.i(TAG, "setupListRow");

        ArrayObjectAdapter listRowAdapter = new ArrayObjectAdapter(new PresenterFilm(context));
//        for (int i = 0; i < 10; i++) {
//            ItemFilm itemFilm = new ItemFilm();
//            listRowAdapter.add(itemFilm);
//        }
        HeaderItem headerItem = new HeaderItem(0, getString(R.string.header_act_details));

        adapter.add(new ListRow(headerItem, listRowAdapter));
    }

    private Timer backgroundTimer;

    private void startBackgroundTimer() {
        Log.i(TAG, "startBackgroundTimer");

        if (backgroundTimer != null) {
            backgroundTimer.cancel();
        }

        backgroundTimer = new Timer();
        backgroundTimer.schedule(new UpdateBackgroundTask(), getResources().getInteger(R.integer.duration_background_update));
    }

    private final Handler handler = new Handler();

    private class UpdateBackgroundTask extends TimerTask {
        @Override
        public void run() {
            Log.i(TAG, "run");

            handler.post(() -> {
                if (selectedFilm != null) {
                    updateBackground(selectedFilm);
                }
            });
        }
    }

    private void updateBackground(ItemFilm itemFilm) {
        Log.i(TAG, "updateBackground: ps=" + itemFilm.getPs());

        URI uri = itemFilm.getImages();
        if (uri != null) {
            Picasso.get()
                    .load(uri.toString())
                    .resize(windowWidth, windowHeight)
                    .centerCrop()
                    .error(R.drawable.bg_default)
                    .placeholder(R.drawable.bg_default)
                    .into(new Target() {
                        @Override
                        public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                            backgroundManager.setBitmap(bitmap);
                        }

                        @Override
                        public void onBitmapFailed(Exception e, Drawable errorDrawable) {

                        }

                        @Override
                        public void onPrepareLoad(Drawable placeHolderDrawable) {

                        }
                    });
        }

        startBackgroundTimer();
    }

    @Override
    public void onItemClicked(Presenter.ViewHolder viewHolder, Object o, RowPresenter.ViewHolder viewHolder1, Row row) {
        Log.i(TAG, "onItemClicked");

        if (o instanceof ItemFilm) {
            ItemFilm itemFilm = (ItemFilm) o;

            Intent intent = new Intent(activity, ActDetails.class);
            intent.putExtra(DefIntent.INTENT_FILM, itemFilm);

            activity.startActivity(intent);
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        Log.i(TAG, "onSaveInstanceState");
        super.onSaveInstanceState(outState);

        outState.putParcelable(DefIntent.INTENT_FILM, selectedFilm);
    }

}

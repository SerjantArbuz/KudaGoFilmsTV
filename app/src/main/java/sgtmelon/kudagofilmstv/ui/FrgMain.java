package sgtmelon.kudagofilmstv.ui;


import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v17.leanback.app.BackgroundManager;
import android.support.v17.leanback.app.BrowseSupportFragment;
import android.support.v17.leanback.widget.*;
import android.util.DisplayMetrics;
import android.util.Log;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;
import sgtmelon.kudagofilmstv.R;
import sgtmelon.kudagofilmstv.annot.DefFilm;
import sgtmelon.kudagofilmstv.model.ItemFilm;
import sgtmelon.kudagofilmstv.presenter.PresenterFilm;

import java.util.Timer;
import java.util.TimerTask;


public class FrgMain extends BrowseSupportFragment implements OnItemViewClickedListener, OnItemViewSelectedListener {

    private static final String TAG = FrgMain.class.getSimpleName();

    private Context context;
    private ActMain activity;

    @Override
    public void onAttach(Context context) {
        Log.i(TAG, "onAttach");
        super.onAttach(context);

        this.context = context;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        Log.i(TAG, "onActivityCreated");
        super.onActivityCreated(savedInstanceState);

        activity = (ActMain) getActivity();

        setupBackgroundManager();
        setupUI();
        loadRows();

        setOnItemViewClickedListener(this);
        setOnItemViewSelectedListener(this);

        setBrowseTransitionListener(new BrowseTransitionListener() {
            @Override
            public void onHeadersTransitionStart(boolean withHeaders) {
                Log.i(TAG, "onHeadersTransitionStart: " + withHeaders);

                if (withHeaders) {
                    needChange = false;

                    if (backgroundTimer != null) {
                        backgroundTimer.cancel();
                        backgroundManager.setDrawable(backgroundDefault);
                    }
                } else {
                    needChange = true;

                    startBackgroundTimer(getResources().getInteger(R.integer.duration_background_change));
                }
            }

            @Override
            public void onHeadersTransitionStop(boolean withHeaders) {

            }
        });
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

    private Drawable backgroundDefault;
    private int widthWindow, heightWindow;

    private void setupBackgroundManager() {
        Log.i(TAG, "setupBackgroundManager");

        backgroundManager = BackgroundManager.getInstance(activity);
        backgroundManager.attach(activity.getWindow());

        backgroundDefault = getResources().getDrawable(R.drawable.bg_default, null);

        DisplayMetrics metrics = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(metrics);

        widthWindow = metrics.widthPixels;
        heightWindow = metrics.heightPixels;
    }

    private void setupUI() {
        Log.i(TAG, "setupUI");

        setTitle(getString(R.string.title_act_main));

        setHeadersState(HEADERS_ENABLED);
        setHeadersTransitionOnBackEnabled(true);

        setBrandColor(getResources().getColor(R.color.background_fastlane));
        setSearchAffordanceColor(getResources().getColor(R.color.fab_search));

        backgroundManager.setDrawable(backgroundDefault);
    }

    ArrayObjectAdapter adpRows;

    private void loadRows() {
        Log.i(TAG, "loadRows");

        adpRows = new ArrayObjectAdapter(new ListRowPresenter());

        HeaderItem headerItem = new HeaderItem(0, "Header");

        PresenterFilm presenterFilm = new PresenterFilm(context);
        ArrayObjectAdapter adpGridRow = new ArrayObjectAdapter(presenterFilm);

        for (int i = 0; i < 10; i++) {
            ItemFilm itemFilm = new ItemFilm();
            adpGridRow.add(itemFilm);
        }

        adpRows.add(new ListRow(headerItem, adpGridRow));

        setAdapter(adpRows);
    }

    private Timer backgroundTimer;
    private ItemFilm currentItemFilm;
    private boolean needChange = false;

    private void startBackgroundTimer(int duration) {
        Log.i(TAG, "startBackgroundTimer");

        if (backgroundTimer != null) {
            backgroundTimer.cancel();
        }

        backgroundTimer = new Timer();
        backgroundTimer.schedule(new UpdateBackgroundTask(), duration);
    }

    private final Handler handler = new Handler();

    private class UpdateBackgroundTask extends TimerTask {
        @Override
        public void run() {
            Log.i(TAG, "run");

            handler.post(() -> {
                if (currentItemFilm != null && needChange) {
                    updateBackground(currentItemFilm);
                }
            });
        }
    }

    private void updateBackground(ItemFilm itemFilm) {
        Log.i(TAG, "updateBackground: ps=" + itemFilm.getPs());

        Picasso.get()
                .load(itemFilm.getImages().toString())
                .resize(widthWindow, heightWindow)
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

        startBackgroundTimer(getResources().getInteger(R.integer.duration_background_update));
    }

    @Override
    public void onItemClicked(Presenter.ViewHolder viewHolder, Object o, RowPresenter.ViewHolder viewHolder1, Row row) {
        Log.i(TAG, "onItemClicked");

        if (o instanceof ItemFilm) {
            ItemFilm itemFilm = (ItemFilm) o;

            Intent intent = new Intent(activity, ActDetails.class);
            intent.putExtra(DefFilm.INTENT, itemFilm);

            activity.startActivity(intent);
        }
//        ImageCardView view = (ImageCardView) viewHolder.view;
//        Bundle bundle = ActivityOptionsCompat
//                .makeSceneTransitionAnimation(activity, view.getMainImageView(), DefFilm.SHARED_ELEMENT)
//                .toBundle();
        // TODO: 28.09.2018 как в гайде (на конец)
    }

    @Override
    public void onItemSelected(Presenter.ViewHolder viewHolder, Object o, RowPresenter.ViewHolder viewHolder1, Row row) {
        Log.i(TAG, "onItemSelected");

        if (o instanceof ItemFilm) {
            currentItemFilm = (ItemFilm) o;

            startBackgroundTimer(getResources().getInteger(R.integer.duration_background_change));
        }
    }

}

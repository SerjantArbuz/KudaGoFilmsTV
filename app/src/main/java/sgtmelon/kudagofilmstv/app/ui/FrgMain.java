package sgtmelon.kudagofilmstv.app.ui;


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
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import sgtmelon.kudagofilmstv.R;
import sgtmelon.kudagofilmstv.app.model.ItemFilm;
import sgtmelon.kudagofilmstv.app.model.RepoApi;
import sgtmelon.kudagofilmstv.app.model.RepoFilm;
import sgtmelon.kudagofilmstv.app.presenter.PresenterFilm;
import sgtmelon.kudagofilmstv.office.annot.DefApi;
import sgtmelon.kudagofilmstv.office.annot.DefIntent;

import java.net.URI;
import java.util.Timer;
import java.util.TimerTask;


public class FrgMain extends BrowseSupportFragment implements OnItemViewClickedListener, OnItemViewSelectedListener {

    private static final String TAG = FrgMain.class.getSimpleName();

    private Context context;
    private ActMain activity;

    private RepoApi repoApi;

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
        setupRow();

        repoApi = new RepoApi();
        repoApi.getApi()
                .getRating(page++, 100, DefApi.all_fields, DefApi.text_format_text, DefApi.field_imdbRating)
                .enqueue(apiRatingCallback);

        setOnItemViewClickedListener(this);
        setOnItemViewSelectedListener(this);
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
    private int windowWidth, windowHeight;

    private void setupBackgroundManager() {
        Log.i(TAG, "setupBackgroundManager");

        backgroundManager = BackgroundManager.getInstance(activity);
        backgroundManager.attach(activity.getWindow());

        backgroundDefault = getResources().getDrawable(R.drawable.bg_default, null);

        DisplayMetrics metrics = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(metrics);

        windowWidth = metrics.widthPixels;
        windowHeight = metrics.heightPixels;
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

    private void setupRow() {
        ArrayObjectAdapter adapter = new ArrayObjectAdapter(new ListRowPresenter());

        PresenterFilm presenterFilm = new PresenterFilm(context);
        adpGridRow = new ArrayObjectAdapter(presenterFilm);
        HeaderItem headerItem = new HeaderItem(0, getString(R.string.header_act_main_top_rated));

        adapter.add(new ListRow(headerItem, adpGridRow));

        setAdapter(adapter);
    }

    ArrayObjectAdapter adpGridRow;

    private int page = 1;

    private final Callback<RepoFilm> apiRatingCallback = new Callback<RepoFilm>() {
        @Override
        public void onResponse(Call<RepoFilm> call, Response<RepoFilm> response) {
            Log.i(TAG, "onResponse");

            if (response.body() != null) {
                RepoFilm repoFilm = response.body();

                int start = adpGridRow.size();
                for (ItemFilm itemFilm : repoFilm.getListFilmReverse()) {
                   if (itemFilm.getRating() != null) {
                       if (Double.parseDouble(itemFilm.getRating()) >= 7.5) {
                           adpGridRow.add(itemFilm);
                       }
                   }
                }
                adpGridRow.notifyItemRangeChanged(start - 1, adpGridRow.size());

                if (repoFilm.getNext() != null) {
                    repoApi.getApi()
                            .getRating(page++, 100, DefApi.all_fields, DefApi.text_format_text, DefApi.field_imdbRating)
                            .enqueue(apiRatingCallback);
                }
            }
        }

        @Override
        public void onFailure(Call<RepoFilm> call, Throwable t) {
        }

    };

    private Timer backgroundTimer;
    private ItemFilm selectedFilm;

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

        startBackgroundTimer(getResources().getInteger(R.integer.duration_background_update));
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
    public void onItemSelected(Presenter.ViewHolder viewHolder, Object o, RowPresenter.ViewHolder viewHolder1, Row row) {
        Log.i(TAG, "onItemSelected");

        if (o instanceof ItemFilm) {
            selectedFilm = (ItemFilm) o;

            startBackgroundTimer(getResources().getInteger(R.integer.duration_background_change));
        }
    }

}

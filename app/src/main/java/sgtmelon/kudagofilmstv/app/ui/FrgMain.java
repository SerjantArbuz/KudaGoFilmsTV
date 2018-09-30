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
import android.support.v17.leanback.widget.ArrayObjectAdapter;
import android.support.v17.leanback.widget.HeaderItem;
import android.support.v17.leanback.widget.ListRow;
import android.support.v17.leanback.widget.ListRowPresenter;
import android.support.v17.leanback.widget.OnItemViewClickedListener;
import android.support.v17.leanback.widget.OnItemViewSelectedListener;
import android.support.v17.leanback.widget.Presenter;
import android.support.v17.leanback.widget.Row;
import android.support.v17.leanback.widget.RowPresenter;
import android.util.DisplayMetrics;
import android.util.Log;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.net.URI;
import java.util.Timer;
import java.util.TimerTask;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import sgtmelon.kudagofilmstv.R;
import sgtmelon.kudagofilmstv.app.model.item.ItemFilm;
import sgtmelon.kudagofilmstv.app.model.repo.RepoFilm;
import sgtmelon.kudagofilmstv.app.presenter.PresenterFilm;
import sgtmelon.kudagofilmstv.app.provider.ProviderApi;
import sgtmelon.kudagofilmstv.office.annot.DefIntent;

/**
 * Фрагмент главного меню
 */
public class FrgMain extends BrowseSupportFragment implements Callback<RepoFilm>,
        OnItemViewClickedListener, OnItemViewSelectedListener {

    private static final String TAG = FrgMain.class.getSimpleName();

    private final Handler handler = new Handler();

    private Context context;
    private ActMain activity;

    private ProviderApi providerApi;

    private ItemFilm selectedFilm;
    private long page = 1;

    private BackgroundManager backgroundManager;
    private Drawable backgroundDefault;
    private int windowWidth, windowHeight;

    private ArrayObjectAdapter adapter;
    private PresenterFilm presenterFilm;

    private Timer backgroundTimer;

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

        setupUI();
        setupBackgroundManager();
        setupAdapter();

        providerApi = new ProviderApi();
        providerApi.getApi().getAll(page).enqueue(this);

        setOnItemViewClickedListener(this);
        setOnItemViewSelectedListener(this);
    }

    @Override
    public void onStop() {
        Log.i(TAG, "onStop");

        backgroundManager.release();

        super.onStop();
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

    /**
     * Установка параметров пользовательского интерфейса
     */
    private void setupUI() {
        Log.i(TAG, "setupUI");

        setTitle(getString(R.string.title_act_main));

        setHeadersState(HEADERS_ENABLED);
        setHeadersTransitionOnBackEnabled(true);

        setBrandColor(getResources().getColor(R.color.background_fastlane));
        setSearchAffordanceColor(getResources().getColor(R.color.fab_search));
    }

    /**
     * Установка фонового менеджера, для работы с обоями
     */
    private void setupBackgroundManager() {
        Log.i(TAG, "setupBackgroundManager");

        backgroundManager = BackgroundManager.getInstance(activity);
        backgroundManager.attach(activity.getWindow());

        backgroundDefault = getResources().getDrawable(R.drawable.bg_default, null);
        backgroundManager.setDrawable(backgroundDefault);

        DisplayMetrics metrics = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(metrics);

        windowWidth = metrics.widthPixels;
        windowHeight = metrics.heightPixels;
    }

    /**
     * Установка адаптера отображения данных
     */
    private void setupAdapter() {
        Log.i(TAG, "setupAdapter");

        presenterFilm = new PresenterFilm(context);

        adapter = new ArrayObjectAdapter(new ListRowPresenter());
        setAdapter(adapter);
    }

    /**
     * Старт таймера для смены обоев
     *
     * @param delay - временная задержка
     */
    private void startBackgroundTimer(int delay) {
        Log.i(TAG, "startBackgroundTimer");

        if (backgroundTimer != null) {
            backgroundTimer.cancel();
        }

        backgroundTimer = new Timer();
        backgroundTimer.schedule(new UpdateBackgroundTask(), delay);
    }

    /**
     * Смена обоев
     *
     * @param itemFilm - модель фильма, от которой берутся обои
     */
    private void updateBackground(ItemFilm itemFilm) {
        Log.i(TAG, "updateBackground: ps=" + itemFilm.getPs());

        URI uri = itemFilm.getImages();
        if (uri != null) {
            Picasso.get()
                    .load(uri.toString())
                    .resize(windowWidth, windowHeight)
                    .centerCrop()
                    .error(backgroundDefault)
                    .placeholder(backgroundDefault)
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
    public void onResponse(Call<RepoFilm> call, Response<RepoFilm> response) {
        Log.i(TAG, "onResponse");

        if (response.body() != null) {
            ArrayObjectAdapter objectAdapter = new ArrayObjectAdapter(presenterFilm);
            HeaderItem headerItem = new HeaderItem(page - 1, "Страница " + page);

            RepoFilm repoFilm = response.body();
            for (ItemFilm itemFilm : repoFilm.getListFilm()) {
                objectAdapter.add(itemFilm);
            }

            adapter.add(new ListRow(headerItem, objectAdapter));
            adapter.notifyItemRangeChanged((int) page - 1, adapter.size());

            if (repoFilm.getNext() != null) {
                Log.i(TAG, "onResponse: have next");

                providerApi.getApi().getAll(++page).enqueue(this);
            }
        }
    }

    @Override
    public void onFailure(Call<RepoFilm> call, Throwable t) {
        Log.i(TAG, "onFailure");

        Toast.makeText(context, getString(R.string.error_internet), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onItemClicked(Presenter.ViewHolder viewHolder, Object o, RowPresenter.ViewHolder viewHolder1, Row row) {
        Log.i(TAG, "onItemClicked: " + row.getHeaderItem().getId());

        if (o instanceof ItemFilm) {
            ItemFilm itemFilm = (ItemFilm) o;

            Intent intent = new Intent(activity, ActDetails.class);
            intent.putExtra(DefIntent.FILM, itemFilm);
            intent.putExtra(DefIntent.PAGE, row.getHeaderItem().getId() + 1);

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

    private class UpdateBackgroundTask extends TimerTask {
        @Override
        public void run() {
            handler.post(() -> {
                if (selectedFilm != null) {
                    updateBackground(selectedFilm);
                }
            });
        }

    }

}

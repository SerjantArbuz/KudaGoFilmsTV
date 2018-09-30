package sgtmelon.kudagofilmstv.app.ui;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.v17.leanback.app.BackgroundManager;
import android.support.v17.leanback.app.DetailsSupportFragment;
import android.support.v17.leanback.widget.Action;
import android.support.v17.leanback.widget.ArrayObjectAdapter;
import android.support.v17.leanback.widget.ClassPresenterSelector;
import android.support.v17.leanback.widget.DetailsOverviewRow;
import android.support.v17.leanback.widget.FullWidthDetailsOverviewRowPresenter;
import android.support.v17.leanback.widget.HeaderItem;
import android.support.v17.leanback.widget.ListRow;
import android.support.v17.leanback.widget.ListRowPresenter;
import android.support.v17.leanback.widget.OnItemViewClickedListener;
import android.support.v17.leanback.widget.Presenter;
import android.support.v17.leanback.widget.Row;
import android.support.v17.leanback.widget.RowPresenter;
import android.support.v17.leanback.widget.SparseArrayObjectAdapter;
import android.support.v4.content.ContextCompat;
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
import sgtmelon.kudagofilmstv.app.presenter.PresenterDetails;
import sgtmelon.kudagofilmstv.app.presenter.PresenterFilm;
import sgtmelon.kudagofilmstv.app.presenter.PresenterLogo;
import sgtmelon.kudagofilmstv.app.provider.ProviderApi;
import sgtmelon.kudagofilmstv.office.annot.DefAction;
import sgtmelon.kudagofilmstv.office.annot.DefIntent;

/**
 * Фрагмент детальной информации
 */
public class FrgDetails extends DetailsSupportFragment implements Callback<RepoFilm>, OnItemViewClickedListener {

    private static final String TAG = FrgDetails.class.getSimpleName();

    private final Handler handler = new Handler();

    private Context context;
    private ActDetails activity;

    private ItemFilm selectedFilm;
    private long page;

    private BackgroundManager backgroundManager;
    private Drawable backgroundDefault;
    private int windowWidth, windowHeight;

    private ArrayObjectAdapter adapter;
    private PresenterFilm presenterFilm;

    private Timer backgroundTimer;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        Log.i(TAG, "onCreate");
        super.onCreate(savedInstanceState);

        context = getContext();
        activity = (ActDetails) getActivity();

        if (activity != null) {
            Bundle bundle = activity.getIntent().getExtras();

            selectedFilm = bundle != null
                    ? bundle.getParcelable(DefIntent.FILM)
                    : savedInstanceState != null
                    ? savedInstanceState.getParcelable(DefIntent.FILM)
                    : null;

            page = bundle != null
                    ? bundle.getLong(DefIntent.PAGE)
                    : savedInstanceState != null
                    ? savedInstanceState.getLong(DefIntent.PAGE)
                    : 0;
        }

        setupBackgroundManager();
        startBackgroundTimer();

        setupAdapter();
        setupDetailsOverviewRow();

        ProviderApi providerApi = new ProviderApi();
        providerApi.getApi().getAll(page).enqueue(this);

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

    /**
     * Установка фонового менеджера, для работы с обоями
     */
    private void setupBackgroundManager() {
        Log.i(TAG, "setupBackgroundManager");

        backgroundManager = BackgroundManager.getInstance(activity);
        backgroundManager.attach(activity.getWindow());

        backgroundDefault = getResources().getDrawable(R.drawable.bg_default_details, null);
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

        //Презентер детальной информации
        FullWidthDetailsOverviewRowPresenter detailsPresenter = new FullWidthDetailsOverviewRowPresenter(new PresenterDetails(), new PresenterLogo(context));

        detailsPresenter.setBackgroundColor(ContextCompat.getColor(activity, R.color.background_details));
        detailsPresenter.setActionsBackgroundColor(ContextCompat.getColor(activity, R.color.background_action));
        detailsPresenter.setInitialState(FullWidthDetailsOverviewRowPresenter.STATE_HALF);
        detailsPresenter.setOnActionClickedListener(action -> Toast.makeText(context, action.toString(), Toast.LENGTH_SHORT).show());

        //Презентер для разных типов отображаемых данных
        ClassPresenterSelector presenterSelector = new ClassPresenterSelector();
        presenterSelector.addClassPresenter(DetailsOverviewRow.class, detailsPresenter);
        presenterSelector.addClassPresenter(ListRow.class, new ListRowPresenter());

        adapter = new ArrayObjectAdapter(presenterSelector);
        setAdapter(adapter);
    }

    /**
     * Установка строки детальной информации
     */
    private void setupDetailsOverviewRow() {
        Log.i(TAG, "setupDetailsOverviewRow");

        DetailsOverviewRow row = new DetailsOverviewRow(selectedFilm);
        row.setImageDrawable(getResources().getDrawable(R.drawable.ic_default));

        //Установка кнопок действий
        SparseArrayObjectAdapter adapterAction = new SparseArrayObjectAdapter();
        adapterAction.set(DefAction.URL_TRAILER, new Action(DefAction.URL_TRAILER, getResources().getString(R.string.action_url_trailer)));
        adapterAction.set(DefAction.URL_FAVORITE, new Action(DefAction.URL_FAVORITE, getResources().getString(R.string.action_url_favorite)));

        row.setActionsAdapter(adapterAction);
        adapter.add(row);
    }

    /**
     * Старт таймера для смены обоев
     */
    private void startBackgroundTimer() {
        Log.i(TAG, "startBackgroundTimer");

        if (backgroundTimer != null) {
            backgroundTimer.cancel();
        }

        backgroundTimer = new Timer();
        backgroundTimer.schedule(new UpdateBackgroundTask(), getResources().getInteger(R.integer.duration_background_update));
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

        startBackgroundTimer();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        Log.i(TAG, "onSaveInstanceState");
        super.onSaveInstanceState(outState);

        outState.putParcelable(DefIntent.FILM, selectedFilm);
        outState.putLong(DefIntent.PAGE, page);
    }

    @Override
    public void onResponse(Call<RepoFilm> call, Response<RepoFilm> response) {
        Log.i(TAG, "onResponse");

        if (response.body() != null) {
            ArrayObjectAdapter objectAdapter = new ArrayObjectAdapter(presenterFilm);
            HeaderItem headerItem = new HeaderItem(0, "Страница " + page);

            RepoFilm repoFilm = response.body();
            for (ItemFilm itemFilm : repoFilm.getListFilm()) {
                if (itemFilm.getId() != selectedFilm.getId()) {
                    objectAdapter.add(itemFilm);
                }
            }

            adapter.add(new ListRow(headerItem, objectAdapter));
            adapter.notifyItemRangeChanged(0, adapter.size());
        }
    }

    @Override
    public void onFailure(Call<RepoFilm> call, Throwable t) {
        Log.i(TAG, "onFailure");

        Toast.makeText(context, getString(R.string.error_internet), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onItemClicked(Presenter.ViewHolder viewHolder, Object o, RowPresenter.ViewHolder viewHolder1, Row row) {
        Log.i(TAG, "onItemClicked");

        if (o instanceof ItemFilm) {
            ItemFilm itemFilm = (ItemFilm) o;

            Intent intent = new Intent(activity, ActDetails.class);
            intent.putExtra(DefIntent.FILM, itemFilm);
            intent.putExtra(DefIntent.PAGE, page);

            activity.startActivity(intent);
        }
    }

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

}

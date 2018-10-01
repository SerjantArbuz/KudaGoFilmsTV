package sgtmelon.kudagofilmstv.app.view;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.util.DisplayMetrics;
import android.util.Log;
import android.widget.Toast;
import androidx.core.content.ContextCompat;
import androidx.leanback.app.BackgroundManager;
import androidx.leanback.app.DetailsSupportFragment;
import androidx.leanback.widget.*;
import androidx.lifecycle.ViewModelProviders;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;
import sgtmelon.kudagofilmstv.R;
import sgtmelon.kudagofilmstv.app.injection.ComponentApi;
import sgtmelon.kudagofilmstv.app.injection.DaggerComponentApi;
import sgtmelon.kudagofilmstv.app.model.item.ItemFilm;
import sgtmelon.kudagofilmstv.app.presenter.PresenterDetails;
import sgtmelon.kudagofilmstv.app.presenter.PresenterLogo;
import sgtmelon.kudagofilmstv.app.provider.ProviderApi;
import sgtmelon.kudagofilmstv.app.vm.DetailsViewModel;
import sgtmelon.kudagofilmstv.office.def.DefAction;
import sgtmelon.kudagofilmstv.office.def.DefIntent;
import sgtmelon.kudagofilmstv.office.intf.IntfApi;

import javax.inject.Inject;
import java.net.URI;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Фрагмент детальной информации
 */
public final class DetailsFragment extends DetailsSupportFragment implements IntfApi.LoadPage, IntfApi.LoadFilm,
        OnItemViewClickedListener {

    private static final String TAG = DetailsFragment.class.getSimpleName();

    private final Handler handler = new Handler();

    private Context context;
    private Activity activity;

    @Inject
    ProviderApi providerApi;

    private ItemFilm selectedFilm;

    private long id;
    private long page;

    private BackgroundManager backgroundManager;
    private Drawable backgroundDefault;
    private int windowWidth, windowHeight;

    private ArrayObjectAdapter adapter;

    private Timer backgroundTimer;

    @Override
    public void onAttach(Context context) {
        Log.i(TAG, "onAttach");
        super.onAttach(context);

        this.context = context;
        activity = getActivity();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        Log.i(TAG, "onCreate");
        super.onCreate(savedInstanceState);

        ComponentApi componentApi = DaggerComponentApi.builder().build();
        componentApi.inject(this);

        DetailsViewModel vm = ViewModelProviders.of(this).get(DetailsViewModel.class);
        vm.setProviderApi(providerApi);
        vm.setLoadFilm(this);
        vm.setLoadPage(this);

        if (activity != null) {
            Bundle bundle = activity.getIntent().getExtras();

            id = bundle != null
                    ? bundle.getLong(DefIntent.ID)
                    : savedInstanceState != null
                    ? savedInstanceState.getLong(DefIntent.ID)
                    : -1;

            page = bundle != null
                    ? bundle.getLong(DefIntent.PAGE)
                    : savedInstanceState != null
                    ? savedInstanceState.getLong(DefIntent.PAGE)
                    : 0;
        }

        setupBackgroundManager();
        startBackgroundTimer();

        setupAdapter();

        vm.startLoadFilm(id);
        vm.startLoadPage(page);

        setOnItemViewClickedListener(this);
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

    @Override
    public void onSaveInstanceState(Bundle outState) {
        Log.i(TAG, "onSaveInstanceState");
        super.onSaveInstanceState(outState);

        outState.putLong(DefIntent.ID, id);
        outState.putLong(DefIntent.PAGE, page);
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

        //Презентер детальной информации
        FullWidthDetailsOverviewRowPresenter detailsPresenter = new FullWidthDetailsOverviewRowPresenter(
                new PresenterDetails(), new PresenterLogo(context)
        );

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
    public void onItemClicked(Presenter.ViewHolder viewHolder, Object o, RowPresenter.ViewHolder viewHolder1, Row row) {
        Log.i(TAG, "onItemClicked");

        if (o instanceof ItemFilm) {
            long id = ((ItemFilm) o).getId();
            long page = row.getHeaderItem().getId() + 1;

            Intent intent = new Intent(activity, DetailsActivity.class);
            intent.putExtra(DefIntent.ID, id);
            intent.putExtra(DefIntent.PAGE, page);

            activity.startActivity(intent);
        }
    }

    @Override
    public void onSuccessfully(long page, ListRow listRow) {
        Log.i(TAG, "onSuccessfully");

        adapter.add(listRow);
        adapter.notifyItemRangeChanged(0, adapter.size());
    }

    /**
     * Установка строки детальной информации
     */
    @Override
    public void onSuccessfully(ItemFilm itemFilm) {
        Log.i(TAG, "onSuccessfully");

        selectedFilm = itemFilm;

        DetailsOverviewRow detailsRow = new DetailsOverviewRow(selectedFilm);
        detailsRow.setImageDrawable(getResources().getDrawable(R.drawable.ic_default));

        //Установка кнопок действий
        SparseArrayObjectAdapter adapterAction = new SparseArrayObjectAdapter();
        adapterAction.set(DefAction.URL_TRAILER, new Action(DefAction.URL_TRAILER, getResources().getString(R.string.action_url_trailer)));
        adapterAction.set(DefAction.URL_FAVORITE, new Action(DefAction.URL_FAVORITE, getResources().getString(R.string.action_url_favorite)));

        detailsRow.setActionsAdapter(adapterAction);
        adapter.add(detailsRow);
    }

    @Override
    public void onError() {
        Log.i(TAG, "onError");

        Toast.makeText(context, getString(R.string.error_internet), Toast.LENGTH_SHORT).show();
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

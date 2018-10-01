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
import androidx.annotation.Nullable;
import androidx.leanback.app.BackgroundManager;
import androidx.leanback.app.BrowseSupportFragment;
import androidx.leanback.widget.*;
import androidx.lifecycle.ViewModelProviders;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;
import sgtmelon.kudagofilmstv.R;
import sgtmelon.kudagofilmstv.app.injection.ComponentApi;
import sgtmelon.kudagofilmstv.app.injection.DaggerComponentApi;
import sgtmelon.kudagofilmstv.app.model.item.ItemFilm;
import sgtmelon.kudagofilmstv.app.provider.ProviderApi;
import sgtmelon.kudagofilmstv.app.vm.MainViewModel;
import sgtmelon.kudagofilmstv.office.def.DefIntent;
import sgtmelon.kudagofilmstv.office.intf.IntfApi;

import javax.inject.Inject;
import java.net.URI;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Фрагмент главного меню
 */
public final class MainFragment extends BrowseSupportFragment implements IntfApi.LoadPage,
        OnItemViewClickedListener, OnItemViewSelectedListener {

    private static final String TAG = MainFragment.class.getSimpleName();

    private final Handler handler = new Handler();

    private Context context;
    private Activity activity;

    @Inject
    ProviderApi providerApi;

    private ItemFilm selectedFilm;

    private BackgroundManager backgroundManager;
    private Drawable backgroundDefault;
    private int windowWidth, windowHeight;

    private final ArrayObjectAdapter adapter = new ArrayObjectAdapter(new ListRowPresenter());

    private Timer backgroundTimer;

    @Override
    public void onAttach(Context context) {
        Log.i(TAG, "onAttach");
        super.onAttach(context);

        this.context = context;
        activity = getActivity();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        Log.i(TAG, "onActivityCreated");
        super.onActivityCreated(savedInstanceState);

        ComponentApi componentApi = DaggerComponentApi.builder().build();
        componentApi.inject(this);

        MainViewModel vm = ViewModelProviders.of(this).get(MainViewModel.class);
        vm.setProviderApi(providerApi);
        vm.setLoadPage(this);

        setupUI();
        setupBackgroundManager();

        setAdapter(adapter);

        vm.startLoadPage();

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
    public void onSuccessfully(long page, ListRow listRow) {
        Log.i(TAG, "onSuccessfully");

        adapter.add(listRow);
        adapter.notifyItemRangeChanged((int) page - 1, adapter.size());
    }

    @Override
    public void onError() {
        Log.i(TAG, "onError");

        Toast.makeText(context, getString(R.string.error_internet), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onItemClicked(Presenter.ViewHolder viewHolder, Object o, RowPresenter.ViewHolder viewHolder1, Row row) {
        Log.i(TAG, "onItemClicked: " + row.getHeaderItem().getId());

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

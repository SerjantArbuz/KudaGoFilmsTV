package sgtmelon.kudagofilmstv.app.vm;

import android.app.Application;
import androidx.annotation.NonNull;
import androidx.leanback.widget.ArrayObjectAdapter;
import androidx.leanback.widget.HeaderItem;
import androidx.leanback.widget.ListRow;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import sgtmelon.kudagofilmstv.app.model.ModelFilm;
import sgtmelon.kudagofilmstv.app.model.item.ItemFilm;
import sgtmelon.kudagofilmstv.office.def.DefApi;
import sgtmelon.kudagofilmstv.office.intf.IntfApi;

/**
 * ViewModel для DetailsFragment
 */
public final class DetailsViewModel extends MainViewModel {

    private long id = -1;

    private IntfApi.LoadFilm loadFilm;

    public DetailsViewModel(@NonNull Application application) {
        super(application);
    }

    public void setLoadFilm(IntfApi.LoadFilm loadFilm) {
        this.loadFilm = loadFilm;
    }

    public void startLoadFilm(long id) {
        providerApi.getApi()
                .getById(Long.toString(id), DefApi.val_fields_all, DefApi.val_textFormat_text)
                .enqueue(loadFilmCallback);
    }

    public void startLoadPage(long page) {
        this.page = page;
        startLoadPage();
    }

    @Override
    public void onResponse(Call<ModelFilm> call, Response<ModelFilm> response) {
        if (response.body() != null) {
            ArrayObjectAdapter objectAdapter = new ArrayObjectAdapter(presenterFilm);
            HeaderItem headerItem = new HeaderItem(0, "Страница " + page);

            ModelFilm modelFilm = response.body();
            for (ItemFilm itemFilm : modelFilm.getResult()) {
                if (itemFilm.getId() != id) {
                    objectAdapter.add(itemFilm);
                }
            }

            loadPage.onSuccessfully(page, new ListRow(headerItem, objectAdapter));
        }
    }

    private final Callback<ModelFilm> loadFilmCallback = new Callback<ModelFilm>() {
        @Override
        public void onResponse(Call<ModelFilm> call, Response<ModelFilm> response) {
            if (response.body() != null) {
                ItemFilm itemFilm = response.body().getResult().get(0);
                id = itemFilm.getId();

                loadFilm.onSuccessfully(itemFilm);
            }
        }

        @Override
        public void onFailure(Call<ModelFilm> call, Throwable t) {
            loadFilm.onError();
        }
    };

}

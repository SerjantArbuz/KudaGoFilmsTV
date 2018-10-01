package sgtmelon.kudagofilmstv.app.vm;

import android.app.Application;
import androidx.annotation.NonNull;
import androidx.leanback.widget.ArrayObjectAdapter;
import androidx.leanback.widget.HeaderItem;
import androidx.leanback.widget.ListRow;
import androidx.lifecycle.AndroidViewModel;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import sgtmelon.kudagofilmstv.app.model.ModelFilm;
import sgtmelon.kudagofilmstv.app.model.item.ItemFilm;
import sgtmelon.kudagofilmstv.app.presenter.PresenterFilm;
import sgtmelon.kudagofilmstv.app.provider.ProviderApi;
import sgtmelon.kudagofilmstv.office.def.DefApi;
import sgtmelon.kudagofilmstv.office.intf.IntfApi;

/**
 * ViewModel для MainFragment
 */
public class MainViewModel extends AndroidViewModel implements Callback<ModelFilm> {

    final PresenterFilm presenterFilm;

    long page = 1;

    ProviderApi providerApi;
    IntfApi.LoadPage loadPage;

    MainViewModel(@NonNull Application application) {
        super(application);

        presenterFilm = new PresenterFilm(application.getApplicationContext());
    }

    public void setProviderApi(ProviderApi providerApi) {
        this.providerApi = providerApi;
    }

    public void setLoadPage(IntfApi.LoadPage loadPage) {
        this.loadPage = loadPage;
    }

    public void startLoadPage() {
        providerApi.getApi()
                .getAll(page, 100, DefApi.val_fields_all, DefApi.val_textFormat_text)
                .enqueue(this);
    }

    @Override
    public void onResponse(Call<ModelFilm> call, Response<ModelFilm> response) {
        if (response.body() != null) {
            ArrayObjectAdapter objectAdapter = new ArrayObjectAdapter(presenterFilm);
            HeaderItem headerItem = new HeaderItem(page - 1, "Страница " + page);

            ModelFilm modelFilm = response.body();
            for (ItemFilm itemFilm : modelFilm.getResult()) {
                objectAdapter.add(itemFilm);
            }

            loadPage.onSuccessfully(page, new ListRow(headerItem, objectAdapter));

            if (modelFilm.getNext() != null) {
                providerApi.getApi()
                        .getAll(++page, 100, DefApi.val_fields_all, DefApi.val_textFormat_text)
                        .enqueue(this);
            }
        }
    }

    @Override
    public void onFailure(Call<ModelFilm> call, Throwable t) {
        loadPage.onError();
    }

}

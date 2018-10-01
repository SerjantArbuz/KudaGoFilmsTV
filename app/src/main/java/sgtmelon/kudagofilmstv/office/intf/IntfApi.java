package sgtmelon.kudagofilmstv.office.intf;

import androidx.leanback.widget.ListRow;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;
import sgtmelon.kudagofilmstv.app.model.ModelFilm;
import sgtmelon.kudagofilmstv.app.model.item.ItemFilm;
import sgtmelon.kudagofilmstv.office.def.DefApi;

/**
 * Итерфейс подключения к Api
 */
public interface IntfApi {

    /**
     * Интерфейс результата загрузки страниц с фильмами
     */
    interface LoadPage {

        void onSuccessfully(long page, ListRow listRow);

        void onError();

    }

    @GET(DefApi.urlExtra)
    Call<ModelFilm> getAll(
            @Query(DefApi.par_page) long page,
            @Query(DefApi.par_pageSize) int size,
            @Query(DefApi.par_fields) String fields,
            @Query(DefApi.par_textFormat) String format
    );

    /**
     * Интерфейс результата загрузки одного фильма по id
     */
    interface LoadFilm {

        void onSuccessfully(ItemFilm itemFilm);

        void onError();

    }

    @GET(DefApi.urlExtra)
    Call<ModelFilm> getById(
            @Query(DefApi.par_ids) String ids,
            @Query(DefApi.par_fields) String fields,
            @Query(DefApi.par_textFormat) String format
    );

}

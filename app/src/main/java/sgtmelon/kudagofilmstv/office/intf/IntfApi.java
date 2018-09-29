package sgtmelon.kudagofilmstv.office.intf;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;
import sgtmelon.kudagofilmstv.app.model.repo.RepoFilm;
import sgtmelon.kudagofilmstv.office.annot.DefApi;

/**
 * Итерфейс подключения к Api
 */
public interface IntfApi {

    @GET(DefApi.urlExtra +
            "?" + DefApi.par_pageSize + "=100" +
            "&" + DefApi.par_fields + "=" + DefApi.allFilmFields +
            "&" + DefApi.par_textFormat + "=" + DefApi.val_textFormat_text)
    Call<RepoFilm> getAll(
            @Query(DefApi.par_page) long page
    );

}

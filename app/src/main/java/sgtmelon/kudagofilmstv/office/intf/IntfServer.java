package sgtmelon.kudagofilmstv.office.intf;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;
import sgtmelon.kudagofilmstv.app.model.RepoFilm;
import sgtmelon.kudagofilmstv.office.annot.DefApi;

public interface IntfServer {

    @GET(DefApi.extraUrl)
    Call<RepoFilm> getRating(
            @Query(DefApi.page) int page,
            @Query(DefApi.pageSize) int pageSize,
            @Query(DefApi.fields) String fields,
            @Query(DefApi.textFormat) String textFormat,
            @Query(DefApi.orderBy) String orderBy
    );

}

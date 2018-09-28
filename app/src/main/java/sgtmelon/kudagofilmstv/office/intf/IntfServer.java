package sgtmelon.kudagofilmstv.office.intf;

import retrofit2.Call;
import retrofit2.http.GET;
import sgtmelon.kudagofilmstv.app.model.RepoFilm;
import sgtmelon.kudagofilmstv.office.annot.DefServer;

public interface IntfServer {

    @GET(DefServer.extraUrl)
    Call<RepoFilm> getListFilm(
//            @Query(DefServer.page) int page,
//                               @Query(DefServer.page_size) int pageSize,
//                               @Query(DefServer.fields) String fields,
//                               @Query(DefServer.text_format) String textFormat
    );

}

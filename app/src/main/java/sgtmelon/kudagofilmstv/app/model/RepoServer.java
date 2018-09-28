package sgtmelon.kudagofilmstv.app.model;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import sgtmelon.kudagofilmstv.office.annot.DefServer;
import sgtmelon.kudagofilmstv.office.intf.IntfServer;

public class RepoServer {

    private IntfServer api;

    public RepoServer() {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(DefServer.baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        api = retrofit.create(IntfServer.class);
    }


    public IntfServer getApi() {
        return api;
    }

}

package sgtmelon.kudagofilmstv.app.model;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import sgtmelon.kudagofilmstv.BuildConfig;
import sgtmelon.kudagofilmstv.office.annot.DefApi;
import sgtmelon.kudagofilmstv.office.intf.IntfServer;

public class RepoApi {

    private IntfServer api;

    public RepoApi() {

        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(BuildConfig.DEBUG ? HttpLoggingInterceptor.Level.BODY : HttpLoggingInterceptor.Level.NONE);

        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(interceptor)
                .build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(DefApi.baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build();

        api = retrofit.create(IntfServer.class);
    }


    public IntfServer getApi() {
        return api;
    }

}

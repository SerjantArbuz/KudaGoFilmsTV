package sgtmelon.kudagofilmstv.app.provider;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import sgtmelon.kudagofilmstv.BuildConfig;
import sgtmelon.kudagofilmstv.office.annot.DefApi;
import sgtmelon.kudagofilmstv.office.intf.IntfApi;

/**
 * Провайдер общения с Api
 */
public class ProviderApi {

    private final IntfApi api;

    public ProviderApi() {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(BuildConfig.DEBUG ? HttpLoggingInterceptor.Level.BODY : HttpLoggingInterceptor.Level.NONE);

        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(interceptor)
                .build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(DefApi.urlBase)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build();

        api = retrofit.create(IntfApi.class);
    }

    public IntfApi getApi() {
        return api;
    }

}

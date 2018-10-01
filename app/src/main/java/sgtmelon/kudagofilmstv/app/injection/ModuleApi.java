package sgtmelon.kudagofilmstv.app.injection;

import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import sgtmelon.kudagofilmstv.BuildConfig;
import sgtmelon.kudagofilmstv.app.provider.ProviderApi;
import sgtmelon.kudagofilmstv.office.def.DefApi;

/**
 * Модуль для предоставления ProviderApi с подключенным httpClient'ом
 */
@Module
final class ModuleApi {

    @ScopeApi
    @Provides
    HttpLoggingInterceptor provideInterceptor() {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(BuildConfig.DEBUG
                ? HttpLoggingInterceptor.Level.BODY
                : HttpLoggingInterceptor.Level.NONE);

        return interceptor;
    }

    @ScopeApi
    @Provides
    OkHttpClient provideClient(HttpLoggingInterceptor httpLoggingInterceptor) {
        return new OkHttpClient.Builder()
                .addInterceptor(httpLoggingInterceptor)
                .build();
    }

    @ScopeApi
    @Provides
    Retrofit provideRetrofit(OkHttpClient okHttpClient) {
        return new Retrofit.Builder()
                .baseUrl(DefApi.urlBase)
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient)
                .build();
    }

    @ScopeApi
    @Provides
    ProviderApi provideProviderApi(Retrofit retrofit) {
        return new ProviderApi(retrofit);
    }

}

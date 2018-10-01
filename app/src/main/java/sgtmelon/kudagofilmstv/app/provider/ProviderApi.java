package sgtmelon.kudagofilmstv.app.provider;

import retrofit2.Retrofit;
import sgtmelon.kudagofilmstv.office.intf.IntfApi;

/**
 * Провайдер общения с Api
 */
public final class ProviderApi {

    private final IntfApi api;

    public ProviderApi(Retrofit retrofit) {
        api = retrofit.create(IntfApi.class);
    }

    public IntfApi getApi() {
        return api;
    }

}

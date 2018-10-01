package sgtmelon.kudagofilmstv.app.injection;

import dagger.Component;
import sgtmelon.kudagofilmstv.app.view.DetailsFragment;
import sgtmelon.kudagofilmstv.app.view.MainFragment;

/**
 * Компонент для внедрения зависимостей относительно ProviderApi
 */
@ScopeApi
@Component(modules = ModuleApi.class)
public interface ComponentApi {

    void inject(MainFragment fragment);

    void inject(DetailsFragment fragment);


}

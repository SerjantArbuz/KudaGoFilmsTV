package sgtmelon.kudagofilmstv.app.injection;

import javax.inject.Scope;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Для хранение в памяти единственных копий компонентов ProviderApi
 */
@Scope
@Retention(RetentionPolicy.RUNTIME)
@interface ScopeApi {

}

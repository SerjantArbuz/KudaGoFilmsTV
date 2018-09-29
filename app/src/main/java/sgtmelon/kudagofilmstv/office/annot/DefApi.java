package sgtmelon.kudagofilmstv.office.annot;

import android.support.annotation.StringDef;

/**
 * Аннотация для описания общения с Api
 */
@StringDef({
        DefApi.par_page, DefApi.par_pageSize,
        DefApi.par_fields, DefApi.par_textFormat,

        DefApi.field_f_id,
        DefApi.field_f_siteUrl,
        DefApi.field_f_title,
        DefApi.field_f_bodyText,
        DefApi.field_f_genres,
        DefApi.field_f_country,
        DefApi.field_f_year,
        DefApi.field_f_runningTime,
        DefApi.field_f_ageRestriction,
        DefApi.field_f_stars,
        DefApi.field_f_director,
        DefApi.field_f_writer,
        DefApi.field_f_trailer,
        DefApi.field_f_images,
        DefApi.field_f_poster,
        DefApi.field_f_imdbRating,

        DefApi.field_count,
        DefApi.field_next,
        DefApi.field_results,
        DefApi.field_image,
        DefApi.field_name

})
public @interface DefApi {

    //Адресс Api
    String urlBase = "https://kudago.com/public-api/";
    String urlExtra = "v1.4/movies/";

    //Параметры
    String par_page = "page",
            par_pageSize = "page_size",
            par_fields = "fields",
            par_textFormat = "text_format";

    //Значения
    String val_textFormat_text = "text";

    //Поля фильмов
    String field_f_id = "id",
            field_f_siteUrl = "site_url",
            field_f_title = "title",
            field_f_bodyText = "body_text",
            field_f_genres = "genres",
            field_f_country = "country",
            field_f_year = "year",
            field_f_runningTime = "running_time",
            field_f_ageRestriction = "age_restriction",
            field_f_stars = "start",
            field_f_director = "director",
            field_f_writer = "writer",
            field_f_trailer = "trailer",
            field_f_images = "images",
            field_f_poster = "poster",
            field_f_imdbRating = "imdb_rating";

    String div_field_f = ",";

    String allFilmFields = field_f_id + div_field_f +
            field_f_siteUrl + div_field_f +
            field_f_title + div_field_f +
            field_f_bodyText + div_field_f +
            field_f_genres + div_field_f +
            field_f_country + div_field_f +
            field_f_year + div_field_f +
            field_f_runningTime + div_field_f +
            field_f_ageRestriction + div_field_f +
            field_f_stars + div_field_f +
            field_f_director + div_field_f +
            field_f_writer + div_field_f +
            field_f_trailer + div_field_f +
            field_f_images + div_field_f +
            field_f_poster + div_field_f +
            field_f_imdbRating;

    //Остальные поля
    String field_count = "count",
            field_next = "next",
            field_results = "results",
            field_image = "image",
            field_name = "name";

}

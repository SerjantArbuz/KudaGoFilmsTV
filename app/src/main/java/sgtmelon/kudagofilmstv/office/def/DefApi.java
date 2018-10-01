package sgtmelon.kudagofilmstv.office.def;

import androidx.annotation.StringDef;

/**
 * Аннотация для описания общения с Api
 */
@StringDef({
        DefApi.par_ids,
        DefApi.par_page,
        DefApi.par_pageSize,
        DefApi.par_fields,
        DefApi.par_textFormat,
})
public @interface DefApi {

    /**
     * Адресс Api
     */
    String urlBase = "https://kudago.com/public-api/";
    String urlExtra = "v1.4/movies/";

    /**
     * Параметры
     */
    String par_ids = "ids",
            par_page = "page",
            par_pageSize = "page_size",
            par_fields = "fields",
            par_textFormat = "text_format";

    /**
     * Названия полей
     */
    String field_count = "count",
            field_next = "next",
            field_results = "results";
    String field_id = "id",
            field_site_url = "site_url",
            field_title = "title",
            field_bodyText = "body_text",
            field_genres = "genres",
            field_country = "country",
            field_year = "year",
            field_runningTime = "running_time",
            field_ageRestriction = "age_restriction",
            field_start = "start",
            field_director = "director",
            field_writer = "writer",
            field_trailer = "trailer",
            field_images = "images",
            field_poster = "poster",
            field_imdbRating = "imdb_rating";
    String field_image = "image";
    String field_name = "name";

    /**
     * Значения для передачи api
     */
    String val_textFormat_text = "text";
    String val_fields_all = DefApi.field_id + "," +
            DefApi.field_site_url + "," +
            DefApi.field_title + "," +
            DefApi.field_bodyText + "," +
            DefApi.field_genres + "," +
            DefApi.field_country + "," +
            DefApi.field_year + "," +
            DefApi.field_runningTime + "," +
            DefApi.field_ageRestriction + "," +
            DefApi.field_start + "," +
            DefApi.field_director + "," +
            DefApi.field_writer + "," +
            DefApi.field_trailer + "," +
            DefApi.field_images + "," +
            DefApi.field_poster + "," +
            DefApi.field_imdbRating;


}

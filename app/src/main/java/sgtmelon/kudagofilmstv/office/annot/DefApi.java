package sgtmelon.kudagofilmstv.office.annot;

import android.support.annotation.StringDef;

@StringDef({
        DefApi.page, DefApi.pageSize,
        DefApi.fields, DefApi.textFormat,
        DefApi.orderBy, DefApi.actualSince, DefApi.actualUntil,

        DefApi.field_id,
        DefApi.field_siteUrl,
        DefApi.field_title,
        DefApi.field_bodyText,
        DefApi.field_isEditorsChoice,
        DefApi.field_genres,
        DefApi.field_country,
        DefApi.field_year,
        DefApi.field_budget_currency,
        DefApi.field_budget,
        DefApi.field_runningTime,
        DefApi.field_ageRestriction,
        DefApi.field_stars,
        DefApi.field_director,
        DefApi.field_writer,
        DefApi.field_trailer,
        DefApi.field_images,
        DefApi.field_poster,
        DefApi.field_imdbRating,

        DefApi.count,
        DefApi.next,
        DefApi.results,
        DefApi.image,
        DefApi.name

})
public @interface DefApi {

    String baseUrl = "https://kudago.com/public-api/";
    String extraUrl = "v1.4/movies/";

    String page = "page",
            pageSize = "page_size",
            fields = "fields",
            textFormat = "text_format",
            orderBy = "order_by",
            actualSince = "actual_since",
            actualUntil = "actual_until";

    String text_format_text = "text";

    String field_id = "id",
            field_siteUrl = "site_url",
            field_title = "title",
            field_bodyText = "body_text",
            field_isEditorsChoice = "is_editors_choice",
            field_genres = "genres",
            field_country = "country",
            field_year = "year",
            field_budget_currency = "budget_currency",
            field_budget = "budget",
            field_runningTime = "running_time",
            field_ageRestriction = "age_restriction",
            field_stars = "start",
            field_director = "director",
            field_writer = "writer",
            field_trailer = "trailer",
            field_images = "images",
            field_poster = "poster",
            field_imdbRating = "imdb_rating";

    String div = ",";

    String all_fields = field_id + div +
            field_siteUrl + div +
            field_title + div +
            field_bodyText + div +
            field_isEditorsChoice + div +
            field_genres + div +
            field_country + div +
            field_year + div +
            field_budget_currency + div +
            field_budget + div +
            field_runningTime + div +
            field_ageRestriction + div +
            field_stars + div +
            field_director + div +
            field_writer + div +
            field_trailer + div +
            field_images + div +
            field_poster + div +
            field_imdbRating;

    String count = "count",
            next = "next",
            previous = "previous",
            results = "results",
            image = "image",
            name = "name";

}

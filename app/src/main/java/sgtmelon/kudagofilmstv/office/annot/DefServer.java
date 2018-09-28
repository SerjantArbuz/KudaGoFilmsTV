package sgtmelon.kudagofilmstv.office.annot;

import android.support.annotation.StringDef;

@StringDef({
        DefServer.page, DefServer.page_size,
        DefServer.fields, DefServer.text_format,

        DefServer.field_id,
        DefServer.field_siteUrl,
        DefServer.field_title,
        DefServer.field_bodyText,
        DefServer.field_country,
        DefServer.field_year,
        DefServer.field_runningTime,
        DefServer.field_ageRestriction,
        DefServer.field_trailer,
        DefServer.field_images,
        DefServer.field_poster,

        DefServer.count,
        DefServer.next,
        DefServer.results,
        DefServer.image

})
public @interface DefServer {

    String baseUrl = "https://kudago.com/public-api/";
    String extraUrl = "v1.4/movies/";

    String page = "page",
            page_size = "page_size",
            fields = "fields",
            text_format = "text_format";

    String text_format_text = "text";

    String div = ",";

    String field_id = "id",
            field_siteUrl = "site_url",
            field_title = "title",
            field_bodyText = "body_text",
            field_country = "country",
            field_year = "year",
            field_runningTime = "running_time",
            field_ageRestriction = "age_restriction",
            field_trailer = "trailer",
            field_images = "images",
            field_poster = "poster";

    String field = field_id + div +
//            field_siteUrl + div +
            field_title + div +
            field_bodyText + div +
//            field_country + div +
//            field_year + div +
//            field_runningTime + div +
//            field_ageRestriction + div +
//            field_trailer + div +
            field_images + div +
            field_poster;

    String count = "count",
            next = "next",
            results = "results",
            image = "image";

}

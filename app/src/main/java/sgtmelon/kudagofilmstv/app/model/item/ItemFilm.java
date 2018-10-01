package sgtmelon.kudagofilmstv.app.model.item;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import sgtmelon.kudagofilmstv.office.def.DefApi;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

/**
 * Модель фильма
 */
public final class ItemFilm {

    @SerializedName(DefApi.field_id )
    @Expose
    private long id;
    @SerializedName(DefApi.field_site_url )
    @Expose
    private String siteUrl;
    @SerializedName(DefApi.field_title )
    @Expose
    private String title;
    @SerializedName(DefApi.field_bodyText )
    @Expose
    private String bodyText;
    @SerializedName(DefApi.field_genres )
    @Expose
    private List<ItemGenre> genres;
    @SerializedName(DefApi.field_country )
    @Expose
    private String country;
    @SerializedName(DefApi.field_year )
    @Expose
    private String year;
    @SerializedName(DefApi.field_runningTime )
    @Expose
    private String runningTime;
    @SerializedName(DefApi.field_ageRestriction )
    @Expose
    private String ageRestriction;
    @SerializedName(DefApi.field_start )
    @Expose
    private String stars;
    @SerializedName(DefApi.field_director )
    @Expose
    private String director;
    @SerializedName(DefApi.field_writer )
    @Expose
    private String writer;
    @SerializedName(DefApi.field_trailer )
    @Expose
    private String trailer;
    @SerializedName(DefApi.field_images )
    @Expose
    private List<ItemImage> images;
    private int ps;
    @SerializedName(DefApi.field_poster )
    @Expose
    private ItemImage poster;
    @SerializedName(DefApi.field_imdbRating )
    @Expose
    private String imdbRating;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public URI getSiteUrl() {
        try {
            return new URI(siteUrl);
        } catch (URISyntaxException e) {
            return null;
        }
    }

    public void setSiteUrl(String siteUrl) {
        this.siteUrl = siteUrl;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBodyText() {
        return bodyText;
    }

    public void setBodyText(String bodyText) {
        this.bodyText = bodyText;
    }

    public String getGenres() {
        StringBuilder gen = null;
        if (genres != null && genres.size() != 0) {
            gen = new StringBuilder();
            for (ItemGenre itemGenre : genres) {
                gen.append(itemGenre.getName());
                gen.append(", ");
            }
            gen.delete(gen.length() - 2, gen.length());
        }
        return gen != null ? gen.toString() : null;
    }

    public void setGenres(List<ItemGenre> genres) {
        this.genres = genres;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getRunningTime() {
        return runningTime;
    }

    public void setRunningTime(String runningTime) {
        this.runningTime = runningTime;
    }

    public String getAgeRestriction() {
        if (ageRestriction != null && ageRestriction.equals("0")) {
            return null;
        }
        return ageRestriction;
    }

    public void setAgeRestriction(String ageRestriction) {
        this.ageRestriction = ageRestriction;
    }

    public String getStars() {
        return stars;
    }

    public void setStars(String stars) {
        this.stars = stars;
    }

    public String getDirector() {
        return director;
    }

    public void setDirector(String director) {
        this.director = director;
    }

    public String getWriter() {
        return writer;
    }

    public void setWriter(String writer) {
        this.writer = writer;
    }

    public URI getTrailer() {
        try {
            return new URI(trailer);
        } catch (URISyntaxException e) {
            return null;
        }
    }

    public void setTrailer(String trailer) {
        this.trailer = trailer;
    }

    public URI getPoster() {
        if (poster != null) {
            try {
                return new URI(poster.getImage());
            } catch (URISyntaxException e) {
                return null;
            }
        }
        return null;
    }

    public void setPoster(ItemImage poster) {
        this.poster = poster;
    }

    public URI getImages() {
        if (images != null && images.size() != 0) {
            ItemImage image = images.get(ps);
            ps = ++ps % images.size();

            try {
                return new URI(image.getImage());
            } catch (URISyntaxException e) {
                return null;
            }
        }
        return null;
    }

    public void setImages(List<ItemImage> images) {
        this.images = images;
    }

    public int getPs() {
        return ps;
    }

    public void setPs(int ps) {
        this.ps = ps;
    }

    public String getImdbRating() {
        if (imdbRating != null && imdbRating.equals("0.0")) {
            return null;
        }
        return imdbRating;
    }

    public void setImdbRating(String imdbRating) {
        this.imdbRating = imdbRating;
    }

}

package sgtmelon.kudagofilmstv.app.model.item;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import sgtmelon.kudagofilmstv.office.annot.DefApi;

import java.net.URI;
import java.net.URISyntaxException;

/**
 * Модель фильма
 */
public class ItemFilm implements Parcelable {

    @SerializedName(DefApi.field_f_id)
    @Expose
    private long id;
    @SerializedName(DefApi.field_f_siteUrl)
    @Expose
    private String siteUrl;

    @SerializedName(DefApi.field_f_title)
    @Expose
    private String title;
    @SerializedName(DefApi.field_f_bodyText)
    @Expose
    private String bodyText;

    @SerializedName(DefApi.field_f_genres)
    @Expose
    private ItemGenre[] genres;

    @SerializedName(DefApi.field_f_country)
    @Expose
    private String country;
    @SerializedName(DefApi.field_f_year)
    @Expose
    private String year;
    @SerializedName(DefApi.field_f_runningTime)
    @Expose
    private String runningTime;

    @SerializedName(DefApi.field_f_ageRestriction)
    @Expose
    private String ageRestriction;

    @SerializedName(DefApi.field_f_stars)
    @Expose
    private String stars;
    @SerializedName(DefApi.field_f_director)
    @Expose
    private String director;
    @SerializedName(DefApi.field_f_writer)
    @Expose
    private String writer;

    @SerializedName(DefApi.field_f_trailer)
    @Expose
    private String trailer;
    @SerializedName(DefApi.field_f_poster)
    @Expose
    private ItemImage poster;
    @SerializedName(DefApi.field_f_images)
    @Expose
    private ItemImage[] images;
    private int ps;

    @SerializedName(DefApi.field_f_imdbRating)
    @Expose
    private String rating;

    public static final Creator<ItemFilm> CREATOR = new Creator<ItemFilm>() {
        @Override
        public ItemFilm createFromParcel(Parcel in) {
            return new ItemFilm(in);
        }

        @Override
        public ItemFilm[] newArray(int size) {
            return new ItemFilm[size];
        }
    };

    private ItemFilm(Parcel in) {
        id = in.readLong();
        siteUrl = in.readString();

        title = in.readString();
        bodyText = in.readString();

        Parcelable[] temp = in.readParcelableArray(ItemFilm.class.getClassLoader());
        if (temp != null) {
            genres = new ItemGenre[temp.length];
            for (int i = 0; i < temp.length; i++) {
                genres[i] = (ItemGenre) temp[i];
            }
        } else genres = null;

        country = in.readString();
        year = in.readString();
        runningTime = in.readString();
        ageRestriction = in.readString();

        stars = in.readString();
        director = in.readString();
        writer = in.readString();

        trailer = in.readString();

        temp = in.readParcelableArray(ItemFilm.class.getClassLoader());
        if (temp != null) {
            images = new ItemImage[temp.length];
            for (int i = 0; i < temp.length; i++) {
                images[i] = (ItemImage) temp[i];
            }
        } else images = null;
        ps = in.readInt();

        poster = in.readParcelable(ItemFilm.class.getClassLoader());

        rating = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeLong(id);
        parcel.writeString(siteUrl);

        parcel.writeString(title);
        parcel.writeString(bodyText);

        parcel.writeParcelableArray(genres, i);

        parcel.writeString(country);
        parcel.writeString(year);
        parcel.writeString(runningTime);
        parcel.writeString(ageRestriction);

        parcel.writeString(stars);
        parcel.writeString(director);
        parcel.writeString(writer);

        parcel.writeString(trailer);

        parcel.writeParcelableArray(images, i);
        parcel.writeInt(ps);

        parcel.writeParcelable(poster, i);

        parcel.writeString(rating);
    }

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
        if (genres != null && genres.length != 0) {
            gen = new StringBuilder();
            for (ItemGenre itemGenre : genres) {
                gen.append(itemGenre.getName());
                gen.append(", ");
            }
            gen.delete(gen.length() - 2, gen.length());
        }
        return gen != null ? gen.toString() : null;
    }

    public void setGenres(ItemGenre[] genres) {
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
        if (images != null && images.length != 0) {
            ItemImage image = images[ps];
            ps = ++ps % images.length;

            try {
                return new URI(image.getImage());
            } catch (URISyntaxException e) {
                return null;
            }
        }
        return null;
    }

    public void setImages(ItemImage[] images) {
        this.images = images;
    }

    public int getPs() {
        return ps;
    }

    public void setPs(int ps) {
        this.ps = ps;
    }

    public String getRating() {
        if (rating != null && rating.equals("0.0")) {
            return null;
        }
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

}

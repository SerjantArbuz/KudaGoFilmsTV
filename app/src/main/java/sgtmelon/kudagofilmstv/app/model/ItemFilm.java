package sgtmelon.kudagofilmstv.app.model;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import sgtmelon.kudagofilmstv.office.annot.DefServer;

import java.net.URI;
import java.net.URISyntaxException;

public class ItemFilm implements Parcelable {

    @SerializedName(DefServer.field_id)
    @Expose
    private long id;
    @SerializedName(DefServer.field_siteUrl)
    @Expose
    private String siteUrl;

    @SerializedName(DefServer.field_title)
    @Expose
    private String title;
    @SerializedName(DefServer.field_bodyText)
    @Expose
    private String bodyText;

    @SerializedName(DefServer.field_country)
    @Expose
    private String country;
    @SerializedName(DefServer.field_year)
    @Expose
    private String year;
    @SerializedName(DefServer.field_runningTime)
    @Expose
    private String runningTime;
    @SerializedName(DefServer.field_ageRestriction)
    @Expose
    private String ageRestriction;

    @SerializedName(DefServer.field_trailer)
    @Expose
    private String trailer;
    @SerializedName(DefServer.field_poster)
    @Expose
    private ItemImage poster;
    @SerializedName(DefServer.field_images)
    @Expose
    private ItemImage[] images;
    private int ps = 0;

    public ItemFilm() {
        id = 3385;

        title = "Остров собак";
        bodyText = "<p>Мультипликационный фильм Уэса Андерсона («Королевство полной луны», «Гранд Отель Будапешт»). Для собак города Мегасаки настали не самые дружелюбные времена: согласно указу мэра-тирана, все они должны отправиться на свалку. Потерявший в этой политической борьбе своего пса Слотса мальчик Атари отправляется искать питомца и вскоре находит — в месте, где всё будет по-другому. Приз Берлинского кинофестиваля за лучшую режиссуру. </p>";

        year = "2018";
        country = "США, Германия";
        runningTime = "101";
        ageRestriction = "12+";

        trailer = "https://www.youtube.com/watch?v=brbm6vKW7IA";
        poster = new ItemImage("https://kudago.com/media/images/movie/poster/97/28/97282e7a39837b263497dfd317ec10ce.jpg");
        images = new ItemImage[]{
            new ItemImage("https://kudago.com/media/images/movie/15/1f/151ffc3b53200fac9bc8c7b9d5f9d272.jpg"),
            new ItemImage("https://kudago.com/media/images/movie/8d/e2/8de265506b2a0f76c6fe225df80da2ca.jpg"),
            new ItemImage("https://kudago.com/media/images/movie/cf/b6/cfb6a382c234b626c1ba4c950c5697d7.jpg")
        } ;
    }

    private ItemFilm(Parcel in) {
        id = in.readLong();
        siteUrl = in.readString();

        title = in.readString();
        bodyText = in.readString();

        country = in.readString();
        year = in.readString();
        runningTime = in.readString();
        ageRestriction = in.readString();

        poster = in.readParcelable(ItemImage.class.getClassLoader());
        trailer = in.readString();

        images = (ItemImage[]) in.readParcelableArray(ItemImage.class.getClassLoader());

        ps = in.readInt();
    }

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

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getRunningTime() {
        return runningTime;
    }

    public void setRunningTime(String runningTime) {
        this.runningTime = runningTime;
    }

    public String getAgeRestriction() {
        return ageRestriction;
    }

    public void setAgeRestriction(String ageRestriction) {
        this.ageRestriction = ageRestriction;
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
        try {
            return new URI(poster.getImage());
        } catch (URISyntaxException e) {
            return null;
        }
    }

    public void setPoster(ItemImage poster) {
        this.poster = poster;
    }

    public URI getImages() {
        ItemImage image = images[ps];
        ps = ++ps % images.length;

        try {
            return new URI(image.getImage());
        } catch (URISyntaxException e) {
            return null;
        }
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

        parcel.writeString(country);
        parcel.writeString(year);
        parcel.writeString(runningTime);
        parcel.writeString(ageRestriction);

        parcel.writeString(trailer);
        parcel.writeString(poster.getImage());

        parcel.writeParcelableArray(images, 0);
        parcel.writeInt(ps);
    }

}

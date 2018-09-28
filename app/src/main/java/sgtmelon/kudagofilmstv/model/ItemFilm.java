package sgtmelon.kudagofilmstv.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

public class ItemFilm implements Parcelable {

    private long id;

    private String title;
    private String age;

    private String year;
    private String country;
    private String time;

    private String details;
    private String poster;

    private List<String> images;
    private int ps = 0;

    public ItemFilm() {
        id = 3385;

        title = "Остров собак";
        age = "12+";

        year = "2018";
        country = "США, Германия";
        time = "101";

        details = "<p>Мультипликационный фильм Уэса Андерсона («Королевство полной луны», «Гранд Отель Будапешт»). Для собак города Мегасаки настали не самые дружелюбные времена: согласно указу мэра-тирана, все они должны отправиться на свалку. Потерявший в этой политической борьбе своего пса Слотса мальчик Атари отправляется искать питомца и вскоре находит — в месте, где всё будет по-другому. Приз Берлинского кинофестиваля за лучшую режиссуру. </p>";

        poster = "https://kudago.com/media/images/movie/poster/97/28/97282e7a39837b263497dfd317ec10ce.jpg";

        images = new ArrayList<String>() {{
            add("https://kudago.com/media/images/movie/15/1f/151ffc3b53200fac9bc8c7b9d5f9d272.jpg");
            add("https://kudago.com/media/images/movie/8d/e2/8de265506b2a0f76c6fe225df80da2ca.jpg");
            add("https://kudago.com/media/images/movie/cf/b6/cfb6a382c234b626c1ba4c950c5697d7.jpg");
        }};
    }

    private ItemFilm(Parcel in) {
        id = in.readLong();
        title = in.readString();
        age = in.readString();
        year = in.readString();
        country = in.readString();
        time = in.readString();
        details = in.readString();
        poster = in.readString();

        ps = in.readInt();

        images = new ArrayList<>();
        in.readStringList(images);
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

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
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

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public URI getPoster() {
        try {
            return new URI(poster);
        } catch (URISyntaxException e) {
            return null;
        }
    }

    public void setPoster(String poster) {
        this.poster = poster;
    }

    public int getPs() {
        return ps;
    }

    public void setPs(int ps) {
        this.ps = ps;
    }

    public URI getImages() {
        String image = images.get(ps);
        ps = ++ps % images.size();

        try {
            return new URI(image);
        } catch (URISyntaxException e) {
            return null;
        }
    }

    public void setImages(List<String> images) {
        this.images = images;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeLong(id);
        parcel.writeString(title);
        parcel.writeString(age);
        parcel.writeString(year);
        parcel.writeString(country);
        parcel.writeString(time);
        parcel.writeString(details);
        parcel.writeString(poster);

        parcel.writeInt(ps);
        parcel.writeStringList(images);
    }

}

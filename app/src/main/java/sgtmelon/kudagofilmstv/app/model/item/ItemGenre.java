package sgtmelon.kudagofilmstv.app.model.item;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import sgtmelon.kudagofilmstv.office.annot.DefApi;

/**
 * Модель жанра
 */
public class ItemGenre implements Parcelable {

    @SerializedName(DefApi.field_name)
    @Expose
    private String name;

    private ItemGenre(Parcel in) {
        name = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public String getName() {
        return name.toLowerCase();
    }

    public void setName(String name) {
        this.name = name;
    }

    public static final Creator<ItemGenre> CREATOR = new Creator<ItemGenre>() {
        @Override
        public ItemGenre createFromParcel(Parcel in) {
            return new ItemGenre(in);
        }

        @Override
        public ItemGenre[] newArray(int size) {
            return new ItemGenre[size];
        }
    };

}

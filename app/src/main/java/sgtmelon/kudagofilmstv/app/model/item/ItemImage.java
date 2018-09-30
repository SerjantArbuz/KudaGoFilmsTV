package sgtmelon.kudagofilmstv.app.model.item;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import sgtmelon.kudagofilmstv.office.annot.DefApi;

/**
 * Модель изображения
 */
public class ItemImage implements Parcelable {

    public static final Creator<ItemImage> CREATOR = new Creator<ItemImage>() {
        @Override
        public ItemImage createFromParcel(Parcel in) {
            return new ItemImage(in);
        }

        @Override
        public ItemImage[] newArray(int size) {
            return new ItemImage[size];
        }
    };

    @SerializedName(DefApi.field_image)
    @Expose
    private String image;

    private ItemImage(Parcel in) {
        image = in.readString();
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(image);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public String getImage() {
        return image;
    }

}

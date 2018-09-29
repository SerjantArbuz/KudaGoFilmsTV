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

    @SerializedName(DefApi.field_image)
    @Expose
    private String image;

    public ItemImage() {

    }

    public ItemImage(String image) {
        this.image = image;
    }

    private ItemImage(Parcel in) {
        image = in.readString();
    }

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

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(image);
    }
}

package sgtmelon.kudagofilmstv.app.model.item;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import sgtmelon.kudagofilmstv.office.def.DefApi;

/**
 * Модель изображения
 */
public final class ItemImage {

    @SerializedName(DefApi.field_image)
    @Expose
    private String image;

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

}

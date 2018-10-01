package sgtmelon.kudagofilmstv.app.model.item;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import sgtmelon.kudagofilmstv.office.def.DefApi;

/**
 * Модель жанра
 */
public final class ItemGenre {

    @SerializedName(DefApi.field_name)
    @Expose
    private String name;

    public String getName() {
        return name.toLowerCase();
    }

    public void setName(String name) {
        this.name = name;
    }

}

package sgtmelon.kudagofilmstv.app.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import sgtmelon.kudagofilmstv.app.model.item.ItemFilm;
import sgtmelon.kudagofilmstv.office.def.DefApi;

import java.util.List;

/**
 * Модель фильма
 */
public final class ModelFilm {

    @SerializedName(DefApi.field_count)
    @Expose
    private int count;
    @SerializedName(DefApi.field_next)
    @Expose
    private String next;
    @SerializedName(DefApi.field_results)
    @Expose
    private List<ItemFilm> result;

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public String getNext() {
        return next;
    }

    public void setNext(String next) {
        this.next = next;
    }

    public List<ItemFilm> getResult() {
        return result;
    }

    public void setResult(List<ItemFilm> result) {
        this.result = result;
    }

}

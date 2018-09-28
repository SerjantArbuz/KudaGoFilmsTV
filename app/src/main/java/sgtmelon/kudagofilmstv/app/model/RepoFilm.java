package sgtmelon.kudagofilmstv.app.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import sgtmelon.kudagofilmstv.office.annot.DefApi;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class RepoFilm {

    @SerializedName(DefApi.count)
    @Expose
    private int count;
    @SerializedName(DefApi.next)
    @Expose
    private String next;
    @SerializedName(DefApi.previous)
    @Expose
    private String previous;
    @SerializedName(DefApi.results)
    @Expose
    private List<ItemFilm> listFilm;

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

    public String getPrevious() {
        return previous;
    }

    public void setPrevious(String previous) {
        this.previous = previous;
    }

    public List<ItemFilm> getListFilm() {
        return listFilm;
    }

    public List<ItemFilm> getListFilmReverse() {
        List<ItemFilm> listFilm = new ArrayList<>(this.listFilm);
        Collections.reverse(listFilm);
        return listFilm;
    }

    public void setListFilm(List<ItemFilm> listFilm) {
        this.listFilm = listFilm;
    }

}

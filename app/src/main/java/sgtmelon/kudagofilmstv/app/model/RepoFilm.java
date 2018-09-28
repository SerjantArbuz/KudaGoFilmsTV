package sgtmelon.kudagofilmstv.app.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import sgtmelon.kudagofilmstv.office.annot.DefServer;

import java.util.List;

public class RepoFilm {

    @SerializedName(DefServer.count)
    @Expose
    private int count;
    @SerializedName(DefServer.next)
    @Expose
    private String next;
    @SerializedName(DefServer.results)
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

    public List<ItemFilm> getListFilm() {
        return listFilm;
    }

    public void setListFilm(List<ItemFilm> listFilm) {
        this.listFilm = listFilm;
    }
}

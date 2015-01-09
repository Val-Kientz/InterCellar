package p54.intercellar.model;

import java.util.List;

/**
 * Created by Simon on 09/01/2015.
 */
public class Cellar {
    private long id;
    private String name;
    private List<Shelf> shelfList;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Shelf> getShelfList() {
        return shelfList;
    }

    public void setShelfList(List<Shelf> shelfList) {
        this.shelfList = shelfList;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
}

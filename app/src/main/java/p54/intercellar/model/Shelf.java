package p54.intercellar.model;

import java.util.List;

/**
 * Created by Simon on 09/01/2015.
 */
public class Shelf {
    private long id;
    private int capacity;
    private int width;
    private int height;
    private List<Bottle> bottleList;
    private long cellar_id;

    public long getCellar_id()
    {
        return cellar_id;
    }
    public void setCellar_id(long id)
    {
        cellar_id=id;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public List<Bottle> getBottleList() {
        return bottleList;
    }

    public void setBottleList(List<Bottle> bottleList) {
        this.bottleList = bottleList;
    }
}

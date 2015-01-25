package p54.intercellar.model;

import java.util.List;

/**
 * Created by Simon on 09/01/2015.
 */
public class Bottle {
    private long id;
    private String year;
    private String name;
    private double price;
    private String picture;
    private String description;
    private String type;
    private String market;
    private int coordinates = -1;

    private Shelf shelf;
    private Chateau chateau;
    // A user can rate several times the same bottle.
    private List<Rating> ratingList;

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getMarket() {
        return market;
    }

    public void setMarket(String market) {
        this.market = market;
    }

    public int getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(int coordinates) {
        this.coordinates = coordinates;
    }

    public Chateau getChateau() {
        return chateau;
    }

    public void setChateau(Chateau chateau) {
        this.chateau = chateau;
    }

    public Shelf getShelf(){return shelf;}

    public void setShelf(Shelf shelf) {this.shelf = shelf;}

    public List<Rating> getRatingList() {
        return ratingList;
    }

    public void setRatingList(List<Rating> rating) {
        this.ratingList = rating;
    }

    public void addRating(Rating rating) {
        this.ratingList.add(rating);
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return name;
    }
}

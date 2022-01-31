package com.honor.volleyfullproject.Model;

public class DataMain {

    private int id ;
    private String name;
    private int year;
    private String color;
    private String pantone_value;

    public DataMain() {
    }

    public DataMain(String name, int year, String color, String pantone_value) {
        this.name = name;
        this.year = year;
        this.color = color;
        this.pantone_value = pantone_value;
    }

//    @Override
//    public String toString() {
//        return "DataMain{" +
//                "id=" + id +
//                ", name='" + name + '\'' +
//                ", year=" + year +
//                ", color='" + color + '\'' +
//                ", pantone_value='" + pantone_value + '\'' +
//                '}';
//    }

    public DataMain(int id, String name, int year, String color, String pantone_value) {
        this.id = id;
        this.name = name;
        this.year = year;
        this.color = color;
        this.pantone_value = pantone_value;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getPantone_value() {
        return pantone_value;
    }

    public void setPantone_value(String pantone_value) {
        this.pantone_value = pantone_value;
    }
}

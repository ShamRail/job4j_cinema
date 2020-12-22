package ru.job4j.cinema.model;

import java.util.Objects;

public class Place {

    private int id;
    private int row;
    private int col;
    private int price;
    private Account account;

    public Place() { }

    public Place(int id, int row, int col, int price, Account account) {
        this.id = id;
        this.row = row;
        this.col = col;
        this.price = price;
        this.account = account;
    }

    public Place(int row, int col, int price, Account account) {
        this.row = row;
        this.col = col;
        this.price = price;
        this.account = account;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getRow() {
        return row;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public int getCol() {
        return col;
    }

    public void setCol(int col) {
        this.col = col;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Place place = (Place) o;
        return id == place.id
                && row == place.row
                && col == place.col;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, row, col);
    }

    @Override
    public String toString() {
        return "Place{"
                + "id=" + id
                + ", row=" + row
                + ", col=" + col
                + ", price=" + price
                + ", account=" + account
                + '}';
    }
}

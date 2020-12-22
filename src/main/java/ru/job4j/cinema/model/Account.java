package ru.job4j.cinema.model;

import java.util.Objects;

public class Account {

    private int id;
    private String username;
    private String phoneNumber;

    public Account() { }

    public Account(int id, String username, String phoneNumber) {
        this.username = username;
        this.phoneNumber = phoneNumber;
        this.id = id;
    }

    public Account(String username, String phoneNumber) {
        this.username = username;
        this.phoneNumber = phoneNumber;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Account account = (Account) o;
        return id == account.id
                && Objects.equals(username, account.username)
                && Objects.equals(phoneNumber, account.phoneNumber);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, username, phoneNumber);
    }

    @Override
    public String toString() {
        return "Account{"
                + "id=" + id
                + ", username='" + username + '\''
                + ", phoneNumber='" + phoneNumber + '\''
                + '}';
    }
}

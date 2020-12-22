package ru.job4j.cinema.service;

import org.apache.commons.dbcp2.BasicDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.job4j.cinema.model.Account;
import ru.job4j.cinema.model.Place;

import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class CinemaServiceImpl implements CinemaService {

    private final BasicDataSource pool = new BasicDataSource();

    private static final Logger LOGGER = LoggerFactory.getLogger(CinemaServiceImpl.class);

    public CinemaServiceImpl() {
        initPool();
    }

    private void initPool() {
        LOGGER.debug("Init pool");
        Properties properties = new Properties();
        try (InputStream inputStream = getClass().getClassLoader().getResourceAsStream("app.properties")) {
            properties.load(inputStream);
        } catch (IOException e) {
            LOGGER.error("Properties is not read cause {0}", e.getCause());
            throw new RuntimeException(e);
        }
        pool.setDriverClassName(properties.getProperty("jdbc.driver"));
        pool.setUrl(properties.getProperty("jdbc.url"));
        pool.setUsername(properties.getProperty("jdbc.username"));
        pool.setPassword(properties.getProperty("jdbc.password"));
        pool.setMinIdle(5);
        pool.setMaxIdle(10);
        pool.setMaxOpenPreparedStatements(100);
        LOGGER.debug("Properties successfully read");
    }

    @Override
    public void buy(Account account, Place place) {
        try (Connection connection = pool.getConnection()) {
            try (PreparedStatement query = connection.prepareStatement("insert into accounts(username, phone_number) values (?, ?)",
                    PreparedStatement.RETURN_GENERATED_KEYS)) {
                query.setString(1, account.getUsername());
                query.setString(2, account.getPhoneNumber());
                query.executeUpdate();
                ResultSet keys = query.getGeneratedKeys();
                if (keys.next()) {
                    try (PreparedStatement updatePlace = connection.prepareStatement(
                            "update halls set account_id = ? where id = ?"
                    )) {
                        updatePlace.setInt(1, keys.getInt(1));
                        updatePlace.setInt(2, place.getId());
                        updatePlace.executeUpdate();
                    }
                }
            } catch (SQLException throwables) {
                LOGGER.error("Error to save account. Cause: {0}", throwables.getCause());
            }
        } catch (SQLException throwables) {
            LOGGER.error("Error to connect! Cause: {0}", throwables.getCause());
        }

    }

    @Override
    public List<Place> findAllPlaces() {

        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        List<Place> places = new ArrayList<>();
        try (Connection connection = DriverManager.getConnection(
                "jdbc:postgresql://127.0.0.1:5432/cinema", "postgres", "password"
        );
             PreparedStatement query = connection.prepareStatement(
                     "select h.id as \"h.id\", h.row, h.col, h.price, a.id as \"a.id\", a.username, a.phone_number "
                             + "from halls as h left join accounts as a on h.account_id = a.id"
             )) {
            ResultSet resultSet = query.executeQuery();
            while (resultSet.next()) {
                String username = resultSet.getString("username");
                String phoneNumber = resultSet.getString("phone_number");
                Account account = null;
                if (username != null && phoneNumber != null) {
                    account = new Account(resultSet.getInt("a.id"), username, phoneNumber);
                }
                Place place = new Place(
                        resultSet.getInt("h.id"),
                        resultSet.getInt("row"),
                        resultSet.getInt("col"),
                        resultSet.getInt("price"),
                        account
                );
                places.add(place);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return places;
    }



}

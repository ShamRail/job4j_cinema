package ru.job4j.cinema.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import ru.job4j.cinema.model.Account;
import ru.job4j.cinema.model.Place;
import ru.job4j.cinema.service.CinemaService;
import ru.job4j.cinema.service.CinemaServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.util.stream.Collectors;

public class HallController extends HttpServlet {

    private CinemaService cinemaService;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public void init() throws ServletException {
        super.init();
        this.cinemaService = new CinemaServiceImpl();
    }

    @Override
    public void destroy() {
        super.destroy();
        try {
            this.cinemaService.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        OutputStream outputStream = resp.getOutputStream();
        objectMapper.writeValue(outputStream, cinemaService.findAllPlaces());
        outputStream.flush();
        outputStream.close();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Account account = new Account(
                req.getParameter("username"),
                req.getParameter("phoneNumber")
        );
        Place place = new Place();
        place.setId(Integer.parseInt(req.getParameter("id")));
        cinemaService.buy(account, place);
    }
}

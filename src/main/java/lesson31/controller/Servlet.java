package lesson31.controller;

import lesson31.dto.GetRequestInfoDto;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/ips")
public class Servlet extends HttpServlet {

    private final List<GetRequestInfoDto> requestsList = new ArrayList<>();
    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("MM-dd-yyyy HH:mm:ss");

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        GetRequestInfoDto getRequestInfoDto = new GetRequestInfoDto(req.getRemoteAddr(), req.getHeader("User-Agent"), DATE_TIME_FORMATTER.format(LocalDateTime.now()));
        req.setAttribute("currentRequest", getRequestInfoDto);
        req.setAttribute("requestsList", new ArrayList<>(requestsList));
        requestsList.add(getRequestInfoDto);
        req.getRequestDispatcher("index.jsp").forward(req, resp);
    }
}

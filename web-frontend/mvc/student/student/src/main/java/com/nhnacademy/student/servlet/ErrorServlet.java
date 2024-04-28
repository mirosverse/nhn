package com.nhnacademy.student.servlet;

import lombok.extern.slf4j.Slf4j;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;

import static javax.servlet.RequestDispatcher.*;

@Slf4j
@WebServlet(name = "errorServlet", urlPatterns = "/error")
public class ErrorServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        log.info("[error] doGet");
        req.setAttribute("status_code",  req.getAttribute(ERROR_STATUS_CODE));
        req.setAttribute("exception_type",  req.getAttribute(ERROR_EXCEPTION_TYPE));
        req.setAttribute("message",  req.getAttribute(ERROR_MESSAGE));
        req.setAttribute("exception",  req.getAttribute(ERROR_EXCEPTION));
        req.setAttribute("request_uri", req.getAttribute(FORWARD_REQUEST_URI));
        req.getRequestDispatcher("/error.jsp").forward(req, resp);
    }

}
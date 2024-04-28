package com.nhnacademy.student.servlet;

import com.nhnacademy.student.domain.Gender;
import com.nhnacademy.student.domain.Student;
import com.nhnacademy.student.respository.StudentRepository;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;

@Slf4j
@WebServlet(name = "studentRegisterServlet", urlPatterns = "/student/register")
public class StudentRegisterServlet extends HttpServlet {

    private StudentRepository studentRepository;

    @Override
    public void init(ServletConfig config) throws ServletException {
        // init studentRepository
        studentRepository = (StudentRepository) config.getServletContext().getAttribute("studentRepository");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // /student/register.jsp forward 합니다.
        if (req.getParameter("id") != null && !req.getParameter("id").isEmpty()) {
            req.setAttribute("action", "/student/update");
        } else {
            req.setAttribute("action", "/student/register");
        }
        req.getRequestDispatcher("/student/register.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        log.debug("[RegisterServlet] do Post");

        Student student = new Student();
        student.setId(req.getParameter("id"));
        student.setName(req.getParameter("name"));
        student.setAge(Integer.parseInt(req.getParameter("age")));
        student.setGender(Gender.valueOf(req.getParameter("gender")));
        student.setCreatedAt(LocalDateTime.now());

        log.info(student.toString());
        if (!student.isValid()) {
            log.error("[RegisterServlet] Invalid student");
            throw new RuntimeException("학생 정보가 유효하지 않는 값입니다.");
        }
        studentRepository.save(student);
        req.setAttribute("student", student);
        resp.sendRedirect("/student/view?id=" + student.getId());
    }

}
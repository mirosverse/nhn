package com.nhnacademy.student.servlet;

import com.nhnacademy.student.domain.Gender;
import com.nhnacademy.student.domain.Student;
import com.nhnacademy.student.respository.StudentRepository;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "studentUpdateServlet", urlPatterns = "/student/update")
public class StudentUpdateServlet extends HttpServlet {
    private StudentRepository studentRepository;

    @Override
    public void init(ServletConfig config) throws ServletException {
        studentRepository = (StudentRepository) config.getServletContext().getAttribute("studentRepository");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Student student = studentRepository.getStudentById(req.getParameter("id"));
        req.setAttribute("student", student);
        req.getRequestDispatcher("/student/register").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Student student = studentRepository.getStudentById(req.getParameter("id"));
        student.setName(req.getParameter("name"));
        student.setAge(Integer.parseInt(req.getParameter("age")));

        if (!student.isValid()) {
            return;
        }

        req.setAttribute("student", student);
        studentRepository.update(student);
        resp.sendRedirect("/student/view?id=" + student.getId());
    }
}
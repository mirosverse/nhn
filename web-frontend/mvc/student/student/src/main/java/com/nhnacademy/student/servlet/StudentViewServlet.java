package com.nhnacademy.student.servlet;

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

@Slf4j
@WebServlet(name = "studentViewServlet", urlPatterns = "/student/view")
public class StudentViewServlet extends HttpServlet {
    private StudentRepository studentRepository;

    @Override
    public void init(ServletConfig config) throws ServletException {
        studentRepository = (StudentRepository) config.getServletContext().getAttribute("studentRepository");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // id null check
        String id = req.getParameter("id");
        if (!studentRepository.existById(id)) {
            return;
        }
        // student 조회
        Student student = studentRepository.getStudentById(id);
        req.setAttribute("student", student);

        //todo /student/view.jsp <-- forward
        req.getRequestDispatcher("/student/view.jsp").forward(req, resp);
    }

}
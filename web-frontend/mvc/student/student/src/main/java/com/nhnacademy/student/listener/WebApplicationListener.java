package com.nhnacademy.student.listener;

import com.nhnacademy.student.domain.Gender;
import com.nhnacademy.student.domain.Student;
import com.nhnacademy.student.respository.MapStudentRepository;
import com.nhnacademy.student.respository.StudentRepository;
import org.apache.commons.math3.random.RandomGenerator;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import java.util.Random;

@WebListener
public class WebApplicationListener implements ServletContextListener {

    private static Random random = new Random();

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        ServletContext context = sce.getServletContext();
        StudentRepository studentRepository = new MapStudentRepository();

        for (int i = 1; i <= 10; i++) {
            Student student = new Student("id" + i, "name" + i, Gender.F, random.nextInt(10) + 20);
            studentRepository.save(student);
        }
        // ... application scope에서 studentRepository 객체에 접근할 수 있도록 구현하기
        context.setAttribute("studentRepository", studentRepository);

    }
}
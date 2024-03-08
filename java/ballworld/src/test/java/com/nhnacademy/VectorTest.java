package com.nhnacademy;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import org.junit.jupiter.api.Test;

public class VectorTest {

    @Test
    void testConstructor() {
        assertDoesNotThrow(()-> {
            int dx = 100;
            int dy = -100;
        });
    }

    @Test
    void testSet(){
        assertDoesNotThrow(()->{
            Vector target = new Vector(100, -100);
            Vector other = new Vector(0, 0);

        });
    }
    
}

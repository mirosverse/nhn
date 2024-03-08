package com.nhnacademy;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import java.util.stream.Stream;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class BallTest {

    @ParameterizedTest
    @MethodSource("setXProvider")
    void testSetX_updateX(int changeX) {
        assertDoesNotThrow(() -> {
            Ball ball = new Ball(10, 10, 4);
            ball.setX(changeX);
            assertEquals((int) ball.getRegion().getX(), changeX);
        });
    }

    static Stream<Arguments> setXProvider() {
        return Stream.of(
                Arguments.arguments(10),
                Arguments.arguments(20),
                Arguments.arguments(30));
    }

    @ParameterizedTest
    @MethodSource("setYProvider")
    void testSetY_updateY(int changeY) {
        assertDoesNotThrow(() -> {
            Ball ball = new Ball(10, 10, 4);
            ball.setY(changeY);
            assertEquals((int) ball.getRegion().getY(), changeY);
        });
    }

    static Stream<Arguments> setYProvider() {
        return Stream.of(
                Arguments.arguments(100),
                Arguments.arguments(200),
                Arguments.arguments(300));
    }


}

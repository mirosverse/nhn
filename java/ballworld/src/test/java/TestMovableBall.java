import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import java.awt.Color;
import java.util.stream.Stream;


import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import com.nhnacademy.MovableBall;

/**
 * TestMovableBall
 */
class TestMovableBall {
    static final int FRAME_WIDTH = 500;
    static final int FRAME_HEIGHT = 400;
    static final int INIT_X = 300;
    static final int INIT_Y = 300;

    private MovableBall ball;

    @Test
    void testConstructor() {
        assertDoesNotThrow(() -> {
            MovableBall ball = new MovableBall(INIT_X, INIT_Y, 10, Color.BLACK);
            assertEquals(INIT_X, ball.getX());
            assertEquals(INIT_Y, ball.getY());
        });
    }

    @ParameterizedTest
    @MethodSource("deltaProvide")
    void testDeltaXY(int x, int y, int radius, int dx, int dy) {
        assertDoesNotThrow(() -> {
            MovableBall ball = new MovableBall(x, y, radius, Color.RED);

            ball.setDX(dx);
            ball.setDY(dy);
            assertEquals(dx, ball.getDX());
            assertEquals(dy, ball.getDY());
        });
    }

    static Stream<Arguments> deltaProvide() {
        return Stream.of(
            Arguments.arguments(0,0,10,0,0),
            Arguments.arguments(0,0,10,1,-1),
            Arguments.arguments(0,0,10,1,1),
            Arguments.arguments(0,0,10,-1,1),
            Arguments.arguments(0,0,10,0,Integer.MAX_VALUE),
            Arguments.arguments(0,0,10,0,Integer.MIN_VALUE)
        );
    }

    @ParameterizedTest
    @MethodSource("moveProvider")
    void testMove(int x, int y, int radius, int dx, int dy, int count) {
        assertDoesNotThrow(() -> {
            MovableBall ball = new MovableBall(x, y, radius, Color.BLUE);

            ball.setDX(dx);
            ball.setDY(dy);

            for (int i = 0; i < count; i++) {
                ball.move();
                assertEquals(dx, ball.getDX());
                assertEquals(dy, ball.getDY());
            }

        });
    }

    static Stream<Arguments> moveProvider(){
        return Stream.of(
            Arguments.arguments(10,20,10,5,10,10),
            Arguments.arguments(10,20,10,-5,10,100)
        );
    }

    @RepeatedTest(10)
    void testRepeatedMove(){
        assertEquals(FRAME_WIDTH, FRAME_HEIGHT);
    }


    @BeforeEach
    void setUp() {
        ball = new MovableBall(INIT_X, INIT_Y, 10, Color.BLACK);
    }

    @Test
    void test_move() {
        ball.move();
        Assertions.assertEquals(INIT_X + ball.getDX(), ball.getX());
        Assertions.assertEquals(INIT_Y + ball.getDX(), ball.getX());
    }

    @RepeatedTest(value = 10)
    void RepeatedTestMove() {
        ball.move();
        System.out.println(ball.toString());

    }

}
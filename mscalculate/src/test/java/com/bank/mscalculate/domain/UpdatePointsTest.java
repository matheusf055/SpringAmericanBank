package com.bank.mscalculate.domain;

import com.bank.mscalculate.entity.UpdatePoints;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

public class UpdatePointsTest {

    @Test
    public void testConstructorAndGetters() {
        UpdatePoints updatePoints = new UpdatePoints(1L, 100);

        assertEquals(1L, updatePoints.getCustomerId());
        assertEquals(100, updatePoints.getPoints());
    }

    @Test
    public void testEqualsAndHashCode() {
        UpdatePoints updatePoints1 = new UpdatePoints(1L, 100);
        UpdatePoints updatePoints2 = new UpdatePoints(1L, 100);
        UpdatePoints updatePoints3 = new UpdatePoints(2L, 200);

        assertEquals(updatePoints1, updatePoints2);
        assertNotEquals(updatePoints1, updatePoints3);
        assertEquals(updatePoints1.hashCode(), updatePoints2.hashCode());
        assertNotEquals(updatePoints1.hashCode(), updatePoints3.hashCode());
    }

    @Test
    public void testToString() {
        UpdatePoints updatePoints = new UpdatePoints(1L, 100);
        String expected = "UpdatePoints(customerId=1, points=100)";
        assertEquals(expected, updatePoints.toString());
    }

    @Test
    public void testSetters() {
        UpdatePoints updatePoints = new UpdatePoints();
        updatePoints.setCustomerId(2L);
        updatePoints.setPoints(200);

        assertEquals(2L, updatePoints.getCustomerId());
        assertEquals(200, updatePoints.getPoints());
    }

    @Test
    public void testCanEqual() {
        UpdatePoints updatePoints1 = new UpdatePoints(1L, 100);
        UpdatePoints updatePoints2 = new UpdatePoints(1L, 100);

        assertEquals(updatePoints1, updatePoints2);
    }
}

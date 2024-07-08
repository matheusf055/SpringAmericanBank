package com.bank.mscalculate.domain;

import com.bank.mscalculate.entity.Protocol;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ProtocolTest {

    @Test
    public void testProtocolConstructorAndGetters() {
        String protocolo = "ABC123";
        Protocol protocol = new Protocol(protocolo);

        assertEquals(protocolo, protocol.getProtocolo());
    }

    @Test
    public void testEqualsAndHashCode() {
        Protocol protocol1 = new Protocol("ABC123");
        Protocol protocol2 = new Protocol("ABC123");
        Protocol protocol3 = new Protocol("XYZ789");

        assertEquals(protocol1, protocol2);
        assertNotEquals(protocol1, protocol3);
        assertEquals(protocol1.hashCode(), protocol2.hashCode());
        assertNotEquals(protocol1.hashCode(), protocol3.hashCode());
    }

    @Test
    public void testToString() {
        Protocol protocol = new Protocol("ABC123");
        String expected = "Protocol(protocolo=ABC123)";
        assertEquals(expected, protocol.toString());
    }

    @Test
    public void testCanEqual() {
        Protocol protocol1 = new Protocol("ABC123");
        Protocol protocol2 = new Protocol("ABC123");

        assertEquals(protocol1, protocol2);
    }

    @Test
    public void testNotEqualsNull() {
        Protocol protocol1 = new Protocol("ABC123");
        assertFalse(protocol1.equals(null));
    }

    @Test
    public void testNotEqualsDifferentClass() {
        Protocol protocol = new Protocol("ABC123");
        String differentClass = "ABC123";
        assertFalse(protocol.equals(differentClass));
    }

    @Test
    public void testEqualsSelf() {
        Protocol protocol = new Protocol("ABC123");
        assertTrue(protocol.equals(protocol));
    }

    @Test
    public void testEqualsDifferentProtocolo() {
        Protocol protocol1 = new Protocol("ABC123");
        Protocol protocol2 = new Protocol("XYZ789");
        assertFalse(protocol1.equals(protocol2));
    }

    @Test
    public void testHashCode() {
        Protocol protocol1 = new Protocol("ABC123");
        Protocol protocol2 = new Protocol("ABC123");
        assertEquals(protocol1.hashCode(), protocol2.hashCode());
    }

    @Test
    public void testToStringFormat() {
        Protocol protocol = new Protocol("ABC123");
        String toStringResult = protocol.toString();
        assertTrue(toStringResult.contains("Protocol(protocolo=ABC123)"));
    }
}

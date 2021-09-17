package org.challenge.controllers;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PingControllerTest {

    @Test
    void testPing(){
        PingController test = new PingController();
        assertEquals("pong", test.handlePing());
    }
}
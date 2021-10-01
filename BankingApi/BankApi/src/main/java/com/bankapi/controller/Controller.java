package com.bankapi.controller;

import io.javalin.Javalin;

public interface Controller {

    void mapEndPoints(Javalin app);
}

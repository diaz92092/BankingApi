package com.bankapi;

import com.bankapi.controller.Controller;
import com.bankapi.controller.SecondAccountController;
import io.javalin.Javalin;

import com.bankapi.controller.AccountController;
import com.bankapi.controller.ClientController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class App {

    private static Javalin app;

    private static final Logger logger = LoggerFactory.getLogger(App.class);

    public static void main(String[] args) {

        app = Javalin.create();

        app.before(ctx -> {
            String URI = ctx.req.getRequestURI();
            String httpMethod = ctx.req.getMethod();
            logger.info(httpMethod + " request to endpoint " + URI + " received");
        });

        mapControllers(new ClientController(), new AccountController(), new SecondAccountController());

        app.start(7000);

    }

    public static void mapControllers(Controller... controllers){
        for (Controller c: controllers){
            c.mapEndPoints(app);
        }
    }


}

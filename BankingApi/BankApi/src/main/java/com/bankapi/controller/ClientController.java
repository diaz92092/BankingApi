package com.bankapi.controller;

import com.bankapi.model.Client;
import com.bankapi.service.ClientService;
import io.javalin.Javalin;
import io.javalin.http.Handler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class ClientController implements Controller{

    private Logger logger = LoggerFactory.getLogger(ClientController.class);
    private ClientService clientService;

    public ClientController() {
        this.clientService = new ClientService();
    }

    private Handler getAllClients = ctx -> {
        List<Client> listOfClients = clientService.getAllClients();

        ctx.json(listOfClients);
    };

    private Handler getClientById = ctx -> {
        String id = ctx.pathParam("id");

        Client client = clientService.getClientById(Integer.parseInt(id));

        ctx.json(client);
    };

    private Handler addClient = ctx -> {
        Client client = ctx.bodyAsClass(Client.class);

        Client insertedClient = this.clientService.addClient(client);

        ctx.json(insertedClient);
        ctx.status(201);
    };

    private Handler deleteClientById = ctx -> {
        String id = ctx.pathParam("id");

        Client client = new Client();
        clientService.deleteClientById(Integer.parseInt(id), client);

        ctx.status(201);
    };

    private Handler updateClientById = ctx -> {

        String id = ctx.pathParam("id");

        Client client = ctx.bodyAsClass(Client.class);

        Client clientUpdated = clientService.updateClientById(Integer.parseInt(id), client);

        Client clientCheck = clientService.getClientById(Integer.parseInt(id));

        if (clientCheck.getId() == Integer.parseInt(id) ) {

            logger.info("Account successfully updated.");
            ctx.json(clientUpdated);

        } else {

            ctx.result("No such account for client.");
        }

    };



    @Override
    public void mapEndPoints(Javalin app) {
        app.post("/clients", addClient);
        app.get("/clients", getAllClients);
        app.get("/clients/:id", getClientById);
        app.delete("/clients/:id", deleteClientById);
        app.put("/clients/:id", updateClientById);

    }
}



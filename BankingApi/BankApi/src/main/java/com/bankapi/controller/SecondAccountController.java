package com.bankapi.controller;

import com.bankapi.dao.SecondAccountRepository;
import io.javalin.Javalin;
import io.javalin.http.Handler;
import com.bankapi.model.SecondAccount;
import com.bankapi.service.ClientService;
import com.bankapi.service.SecondAccountService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SecondAccountController implements Controller{
    private ClientService clientService;

    private Logger logger = LoggerFactory.getLogger(AccountController.class);

    private SecondAccountService secondAccountService;

    public SecondAccountController() {
        this.secondAccountService = new SecondAccountService();
    }

//    private Handler addSecondAccountByClientId = ctx -> {
//        String id = ctx.pathParam("id");
//        Account account = ctx.bodyAsClass(Account.class);
//
//        SecondAccount newSecondAccountAdded = secondAccountService.addSecondAccountByClientId(Integer.parseInt(id), account);
//        if (newSecondAccountAdded.getId() != 0) {
//            ctx.json(newSecondAccountAdded);
//        } else {
//            logger.info("Client with id: " + Integer.parseInt(id) + " does not exist.");
//            ctx.result("Client does not exist.");
//        }
//    };


//     else {
//
//            ctx.result("No such account for client.");
//        }

    private Handler updateAccountByIdForClientIdTransfer = ctx -> {
        String acctId = ctx.pathParam("acctId");
        String clientId = ctx.pathParam("clientId");
        String secondAcctId = ctx.pathParam("secondAcctId");


        SecondAccount secondAccount = ctx.bodyAsClass(SecondAccount.class);

        SecondAccount secondAccountUpdated = secondAccountService.updateAccountByIdForClientIdTransfer(Integer.parseInt(acctId),
                Integer.parseInt(clientId), Integer.parseInt(secondAcctId), secondAccount);

        SecondAccountRepository secondAccountService = new SecondAccountRepository();
        //SecondAccount secondAccountCheck = secondAccountService.getSecondAccountById(Integer.parseInt(acctId));

        logger.info("Account successfully updated.");
        ctx.json(secondAccountUpdated);

    };

    @Override
    public void mapEndPoints(Javalin app) {
        app.patch("/clients/:clientId/accounts/:acctId/transfer/:secondAcctId", updateAccountByIdForClientIdTransfer);
    }
};





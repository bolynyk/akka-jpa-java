package com.olynyk.accounts;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;

import java.io.IOException;

public class AccountSystem {
    public static void main(String[] args) {
        final ActorSystem system = ActorSystem.create("accounts-akka");
        try {
            final ActorRef accountManagerActor = system.actorOf(AccountManagerActor.props(), "accountManagerActor");
            final AccountManagerActor.CreateAccountCommand createAccountCommand = new AccountManagerActor.CreateAccountCommand(Account.EType.ADMINISTRATOR, "blair.olynyk@gmail.com", "Password123!");
            accountManagerActor.tell(createAccountCommand, ActorRef.noSender());
            System.out.println(">>> Press ENTER to exit <<<");
            System.in.read();
        } catch (IOException ex) {
            System.err.println(ex.getMessage());
        } finally {
            system.terminate();
        }
    }

}

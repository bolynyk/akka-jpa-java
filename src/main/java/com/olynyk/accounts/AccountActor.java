package com.olynyk.accounts;

import akka.actor.AbstractActor;
import akka.actor.AbstractLoggingActor;
import akka.actor.Props;

public class AccountActor extends AbstractLoggingActor {

    private final Account account;

    public AccountActor(Account account) {
        this.account = account;
    }

    @Override
    public Receive createReceive() {
        return receiveBuilder().matchAny(this::unhandled).build();
    }

    public static Props props(final Account account) {
        return Props.create(AccountActor.class, () -> new AccountActor(account));
    }
}

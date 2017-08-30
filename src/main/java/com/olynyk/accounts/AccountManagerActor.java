package com.olynyk.accounts;

import akka.actor.AbstractLoggingActor;
import akka.actor.ActorRef;
import akka.actor.Props;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import static com.google.common.base.Preconditions.checkNotNull;

public class AccountManagerActor extends AbstractLoggingActor {

    private final AccountRepository accountRepository;
    private final Map<UUID, ActorRef> accountActorRefs;

    public AccountManagerActor() {
        accountRepository = new AccountRepository();
        accountActorRefs = new HashMap<>();
    }

    public static Props props() {
        return Props.create(AccountManagerActor.class, AccountManagerActor::new);
    }

    @Override
    public Receive createReceive() {
        return receiveBuilder()
                .match(CreateAccountCommand.class, createAccountCommand -> handleCreateAccountCommand(createAccountCommand))
                .matchAny(this::unhandled).build();
    }

    private void handleCreateAccountCommand(CreateAccountCommand createAccountCommand) {
        final Account account = new Account(createAccountCommand.type, createAccountCommand.email, createAccountCommand.password);
        accountRepository.create(account);
        final ActorRef accountActor = context().actorOf(AccountActor.props(account), account.getUuid().toString());
        accountActorRefs.put(account.getUuid(), accountActor);
        context().system().eventStream().publish(new CreateAccountEvent(accountActor));
    }

    public static final class CreateAccountCommand {
        public final Account.EType type;
        public final String email;
        public final String password;

        public CreateAccountCommand(final Account.EType type, final String email, final String password) {
            checkNotNull(type, "Account.EType type, cannot be null");
            checkNotNull(email, "String email, cannot be null");
            checkNotNull(password, "String password, cannot be null");
            this.type = type;
            this.email = email;
            this.password = password;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            CreateAccountCommand that = (CreateAccountCommand) o;

            if (type != that.type) return false;
            if (!email.equals(that.email)) return false;
            return password.equals(that.password);
        }

        @Override
        public int hashCode() {
            int result = type.hashCode();
            result = 31 * result + email.hashCode();
            result = 31 * result + password.hashCode();
            return result;
        }
    }

    public static final class CreateAccountEvent {
        public final ActorRef account;

        public CreateAccountEvent(final ActorRef account) {
            checkNotNull(account, "ActorRef account, cannot be null");
            this.account = account;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            CreateAccountEvent that = (CreateAccountEvent) o;

            return account.equals(that.account);
        }

        @Override
        public int hashCode() {
            return account.hashCode();
        }
    }
}

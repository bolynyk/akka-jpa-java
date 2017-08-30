package com.olynyk.accounts;

import org.hibernate.annotations.ColumnTransformer;
import org.hibernate.annotations.NaturalId;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Table(name = "accounts")
public class Account {

    @Id
    @Type(type="uuid-char")
    @GeneratedValue
    private UUID uuid;

    @Enumerated(EnumType.STRING)
    private Account.EType type;

    @Enumerated(EnumType.STRING)
    private Account.EState state;

    @NaturalId
    private String email;

    @ColumnTransformer(
            write = "SHA2(?, '256')"
    )
    private String password;

    @Version
    private Integer version;

    protected Account() {
        this.state = EState.PREACTIVATED;
    }

    protected Account(Account.EType type, String email, String password) {
        this();
        this.type = type;
        this.email = email;
        this.password = password;
    }

    public UUID getUuid() {
        return uuid;
    }

    public EType getType() {
        return type;
    }

    public void setType(EType type) {
        this.type = type;
    }

    public EState getState() {
        return state;
    }

    public void setState(EState state) {
        this.state = state;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public enum EType {
        ADMINISTRATOR,
        PROGRAM,
        USER
    }

    public enum EState {
        PREACTIVATED,
        ACTIVATED,
        DEACTIVATED
    }
}

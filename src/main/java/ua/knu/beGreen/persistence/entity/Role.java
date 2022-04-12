package ua.knu.beGreen.persistence.entity;

import org.springframework.security.core.GrantedAuthority;

public enum Role implements GrantedAuthority {
    USER, SUPER_ADMIN, ADMIN, COMPANY, CUSTOMER;

    @Override
    public String getAuthority() {
        return name();
    }
}

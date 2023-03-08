package com.postnord.ndm.base.jwt_handler.model;

import java.util.Objects;

public record AccountInfo(String id, boolean superUser) {

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof AccountInfo)) {
            return false;
        }
        final AccountInfo that = (AccountInfo) o;
        return superUser == that.superUser && Objects.equals(id, that.id);
    }

    @Override
    public String toString() {
        return "AccountInfo{" +
                "id=" + id +
                ", superUser=" + superUser +
                '}';
    }
}

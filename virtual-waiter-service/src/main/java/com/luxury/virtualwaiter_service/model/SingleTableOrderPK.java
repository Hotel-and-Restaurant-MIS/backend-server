package com.luxury.virtualwaiter_service.model;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Objects;

@NoArgsConstructor
@Getter
public class SingleTableOrderPK implements Serializable {
    private Long orderId;
    private Integer tableId;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof SingleTableOrderPK that)) return false;
        return Objects.equals(orderId, that.orderId) && Objects.equals(tableId, that.tableId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(orderId, tableId);
    }
}

package com.postnord.ndm.base.jpa_utils.utils;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.Order;
import jakarta.persistence.criteria.Root;

public final class CommonJPAUtils {
    private static final int MIN_ORDER_STRING_LENGTH = 2;
    private static final char ASCENDING_PREFIX = '+';

    /**
     * Create order object for JPA criteria query
     *
     * @param criteriaBuilder The criteria builder
     * @param root            Entity root
     * @param order           The order, '+' for ascending and '-' for descending
     * @return The created order object
     */
    public static Order createOrderCriteria(final CriteriaBuilder criteriaBuilder, final Root<?> root, final String order) {
        if (order == null || order.length() < MIN_ORDER_STRING_LENGTH) {
            return criteriaBuilder.asc(root.get("id"));
        }
        final String field = order.substring(1);
        if (order.charAt(0) == ASCENDING_PREFIX) {
            return criteriaBuilder.asc(root.get(field));
        }
        return criteriaBuilder.desc(root.get(field));
    }

    private CommonJPAUtils() {
    }
}

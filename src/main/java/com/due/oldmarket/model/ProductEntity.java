package com.due.oldmarket.model;

import javax.persistence.Column;

public interface ProductEntity {
    Long getIdProduct();
    String getProductName();
    Long getPrice();
    String getDescription();
    String getCategoryName();
}

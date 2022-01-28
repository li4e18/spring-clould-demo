package com.shitu;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 *
 * @author li4e
 */
@Data
public class Product implements Serializable {

    private String name;

    private BigDecimal price;
}

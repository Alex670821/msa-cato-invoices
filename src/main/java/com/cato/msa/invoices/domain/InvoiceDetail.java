package com.cato.msa.invoices.domain;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class InvoiceDetail {

        private Long id;
        private String productName;
        private Integer quatity;
        private BigDecimal unitPrice;
        private BigDecimal subTotal;
        public void calculateSubTotal(){

                subTotal = unitPrice.multiply(new BigDecimal(quatity));
        }

}

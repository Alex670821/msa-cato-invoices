package com.cato.msa.invoices.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@Entity
@Table(name= "T_INVOICE_DETAILS")
public class InvoiceDetail {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        @Column(name = "IND_ID", nullable = false)
        private Long id;

        @Column(name = "IND_PROD_NAME", nullable = false)
        private String productName;

        @Column(name = "IND_QUANTITY", nullable = false)
        private Integer quantity;

        @Column(name = "IND_UNIT_PRICE", nullable = false)
        private BigDecimal unitPrice;

        @Column(name = "IND_SUB_TOTAL", nullable = false)
        private BigDecimal subTotal;

        @ManyToOne(fetch = FetchType.LAZY)
        @JoinColumn(name = "IND_INH_ID", nullable = false)
        @JsonIgnore
        private InvoiceHeader invoiceHeader;

        public void calculateSubTotal(){
                subTotal = unitPrice.multiply(new BigDecimal(quantity));
        }

}

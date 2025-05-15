package com.cato.msa.invoices.repository;

import com.cato.msa.invoices.domain.InvoiceHeader;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InvoiceHeaderRepository extends JpaRepository<InvoiceHeader, Long> {

}

package com.cato.msa.invoices.repository;

import com.cato.msa.invoices.domain.InvoiceHeader;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface InvoiceHeaderRepository extends JpaRepository<InvoiceHeader, Long> {
    @Query("SELECT ih FROM InvoiceHeader ih WHERE ih.number = :number")
    Optional<InvoiceHeader> findByNumber(@Param("number") String number);
}

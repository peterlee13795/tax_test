package com.tax.test.database.repository;

import com.tax.test.database.entity.TaxScrapEntity;
import org.springframework.data.jpa.repository.JpaRepository;


public interface TaxScrapRepository extends JpaRepository<TaxScrapEntity, String> {


}

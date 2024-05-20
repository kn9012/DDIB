package com.ddib.payment.payment.repository;

import com.ddib.payment.payment.domain.Tid;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TidRepository extends CrudRepository<Tid, String> {
}

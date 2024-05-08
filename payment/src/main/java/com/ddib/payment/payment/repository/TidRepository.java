package com.ddib.payment.payment.repository;

import com.ddib.payment.payment.domain.Tid;
import org.springframework.data.repository.CrudRepository;

public interface TidRepository extends CrudRepository<Tid, String> {
}

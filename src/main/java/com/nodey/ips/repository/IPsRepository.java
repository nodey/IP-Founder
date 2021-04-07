package com.nodey.ips.repository;

import com.nodey.ips.model.IP;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

//Simple repository for interaction with database
@Repository
public interface IPsRepository extends JpaRepository<IP, Long> {
}

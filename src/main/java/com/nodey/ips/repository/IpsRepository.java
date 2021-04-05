package com.nodey.ips.repository;

import com.nodey.ips.model.IP;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IpsRepository extends JpaRepository<IP, Long> {
}

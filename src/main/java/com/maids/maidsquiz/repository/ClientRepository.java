package com.maids.maidsquiz.repository;

import com.maids.maidsquiz.data.Client;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClientRepository extends JpaRepository<Client, Long> {
}

package com.szemereadam.cma.repository;

import com.szemereadam.cma.model.Client;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClientRepository extends JpaRepository<Client, Long> {

}

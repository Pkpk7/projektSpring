package com.projekt.projekt;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

public interface KlientRepository extends JpaRepository<Klient, Integer> {
}

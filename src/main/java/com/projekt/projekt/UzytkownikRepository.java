package com.projekt.projekt;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

public interface UzytkownikRepository extends JpaRepository<Uzytkownik, Integer> {

    Uzytkownik findBylogin(String username);
}

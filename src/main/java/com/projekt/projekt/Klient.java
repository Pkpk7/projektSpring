package com.projekt.projekt;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Entity
public class Klient {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    private int users;
    private int lodowki;
    @Autowired
    @JsonIgnore
    @ManyToMany(mappedBy = "klienci", fetch = FetchType.EAGER, cascade = {CascadeType.ALL})
    private List<Uzytkownik> uzytkownicy = new ArrayList<>();

    protected Klient() {}

    public Klient(int users, int lodowki, List<Uzytkownik> uzytkownicy)
    {
        this.setUsers(users);
        this.setLodowki(lodowki);
        this.setUzytkownicy(uzytkownicy);
    }

    public int getUsers() {
        return users;
    }

    public void setUsers(int users) {
        this.users = users;
    }

    public int getLodowki() {
        return lodowki;
    }

    public void setLodowki(int lodowki) {
        this.lodowki = lodowki;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void addUser(Uzytkownik user)
    {
        uzytkownicy.add(user);
    }

    public List<Uzytkownik> getUzytkownicy() {
        return uzytkownicy;
    }

    public void setUzytkownicy(List<Uzytkownik> uzytkownicy) {
        this.uzytkownicy = uzytkownicy;
    }
}

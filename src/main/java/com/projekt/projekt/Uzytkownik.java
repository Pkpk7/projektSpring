package com.projekt.projekt;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.*;
import java.util.*;

@Entity
public class Uzytkownik {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    private String login;
    private String haslo;
    private String uprawnienia;
    @Autowired
    @ManyToMany(cascade = {CascadeType.MERGE,CascadeType.DETACH}, fetch = FetchType.EAGER)
    @JoinTable(
            name = "laczanie",
            joinColumns = @JoinColumn(name = "Uzytkownik_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "Kleint_id", referencedColumnName = "id")
    )
    private List<Klient> klienci = new ArrayList<>();

    public Uzytkownik(){}

    public Uzytkownik(String login, String haslo, List<Klient> klienci, String uprawnienia)
    {
        this.login = login;
        this.haslo = haslo;
        if(klienci!=null)
        for (Klient klient: klienci
             ) {
            this.klienci.add(klient);
        }
        this.setUprawnienia(uprawnienia);
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }
    public void addKlient(Klient klient)
    {
        this.klienci.add(klient);
    }
    public void deleteKlient(int id){
        for (Klient klient : klienci
             ) {
            if(klient.getId() == id) {
                klienci.remove(klient);
                break;
            }
        }
    };
    public Klient getKlientById(int id)
    {
        return this.getKlienci().get(id);
    }
    public void save()
    {

    }
    public String getHaslo() {
        return haslo;
    }

    public void setHaslo(String haslo) {
        this.haslo = haslo;
    }

    public List<Klient> getKlienci() {
        return klienci;
    }

    public void setKlienci(List<Klient> klienci) {
        this.klienci = klienci;
    }

    public String getUprawnienia() {
        return uprawnienia;
    }

    public void setUprawnienia(String uprawnienia) {
        this.uprawnienia = uprawnienia;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }




}

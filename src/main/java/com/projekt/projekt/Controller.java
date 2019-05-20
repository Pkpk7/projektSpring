package com.projekt.projekt;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import sun.plugin.liveconnect.SecurityContextHelper;

import javax.swing.text.html.parser.Entity;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

@RestController
public class Controller {
    @Autowired
    KlientRepository repozytorium1;
    @Autowired
    UzytkownikRepository repozytorium2;

    @GetMapping("/")
    public ModelAndView zaloguj(Principal principal)
    {
        Uzytkownik us = repozytorium2.findBylogin(principal.getName());
        if(us.getUprawnienia().equals("ADMIN"))
        {
            ModelAndView model = new ModelAndView("AdminPlik");
            //model.addObject("uz", new Uzytkownik());
            model.addObject("lista", repozytorium2.findAll());
            model.addObject("user", new Uzytkownik());
            model.addObject("co", "");
            return model;
        }else {
            ModelAndView model = new ModelAndView("user");
            model.addObject("user", repozytorium2.findBylogin(principal.getName()));
            List<Klient> klienci = repozytorium2.findBylogin(principal.getName()).getKlienci();
            model.addObject("lista", klienci);
            model.addObject("co","");
            int cena = 0;
            for (Klient kl: repozytorium2.findBylogin(principal.getName()).getKlienci()
            ) {
                cena = cena + 1000 + kl.getLodowki()*500 + kl.getUsers()*100;

            }
            model.addObject("cena", cena);
            return model;
        }
    }

    @GetMapping("/Admin")
    public ModelAndView wypiszAdmin()
    {
        ModelAndView model = new ModelAndView("AdminPlik");
        //model.addObject("uz", new Uzytkownik());
        model.addObject("lista", repozytorium2.findAll());
        model.addObject("user", new Uzytkownik());
        model.addObject("co", "");
        return model;
    }

    @RequestMapping(value="/Admin", method = RequestMethod.POST)
    public ModelAndView registerUser(@ModelAttribute Uzytkownik user)
    {
        ModelAndView modelAndView = new ModelAndView("AdminPlik");
        Uzytkownik uz = repozytorium2.findBylogin(user.getLogin());
        if(uz!=null)
        {
            modelAndView.addObject("co","Istnieje juz konto o takim loginie");
        }else
        {
            repozytorium2.save(user);
            modelAndView.addObject("co", "Konto zostalo pomyslnie stworzone");
        }
        modelAndView.addObject("lista", repozytorium2.findAll());
        modelAndView.addObject("user", new Uzytkownik());
        return modelAndView;
    }
    @RequestMapping(value="/Admin/edycja", method = RequestMethod.POST)
    public ModelAndView editUser(@ModelAttribute Uzytkownik uz, @ModelAttribute ArrayList<Klient> listaK)
    {
        ModelAndView mode = new ModelAndView("edytowanie");
        Uzytkownik ad = repozytorium2.findBylogin(uz.getLogin());
        mode.addObject("uz", ad);
        listaK = new ArrayList<Klient>();

        mode.addObject("lista", listaK);
        mode.addObject("kl", new Klient());
        return mode;
    }

    @RequestMapping(value="/Admin/wyedytowane", method = RequestMethod.POST)
    public ModelAndView editedUser(@ModelAttribute Uzytkownik uz, @ModelAttribute ArrayList<Klient> listaK)
    {
        System.out.println("WSZEDŁEM ---------------");
        ModelAndView mode = new ModelAndView("edytowanie");
        Uzytkownik ad = repozytorium2.findBylogin(uz.getLogin());
        if(ad!=null && ad!=repozytorium2.findById(uz.getId()).orElse(null))
        {
            mode.addObject("co", "Nie udalo sie zedytowac");
        }
        else
        {
            repozytorium2.save(uz);
            mode.addObject("co", "Edytowanie zakonczone sukcesem ");
        }
        listaK = new ArrayList<Klient>();

        mode.addObject("lista", listaK);
        mode.addObject("uz",uz);
        mode.addObject("kl", new Klient());
        return mode;
    }

    @RequestMapping(value="/Admin/stworz", method = RequestMethod.POST)
    public ModelAndView addKlient(@ModelAttribute Klient kl, @ModelAttribute Uzytkownik us)
    {

        Uzytkownik al = repozytorium2.findBylogin(us.getLogin());
        ModelAndView mode = new ModelAndView("edytowanie");
        repozytorium1.save(kl);
        al.addKlient(kl);
        repozytorium2.save(al);
        mode.addObject("uz", al);
        mode.addObject("kl", new Klient());
        return mode;
    }
    @RequestMapping(value="/Admin/usun", method = RequestMethod.POST)
    public ModelAndView deleteKlient(@ModelAttribute Klient kl, @ModelAttribute Uzytkownik us)
    {


        Uzytkownik al = repozytorium2.findBylogin(us.getLogin());
        ModelAndView mode = new ModelAndView("edytowanie");
        al.deleteKlient(kl.getId());
        repozytorium1.deleteById(kl.getId());
        repozytorium2.save(al);
        mode.addObject("uz", al);
        mode.addObject("kl", new Klient());
        for (Klient k: repozytorium1.findAll()
             ) {
            System.out.println(k.getId());
        }
        return mode;
    }
    @RequestMapping(value="/Admin/usunUser", method = RequestMethod.POST)
    public ModelAndView deleteUser(@ModelAttribute Uzytkownik uza)
    {
        ModelAndView model = new ModelAndView("AdminPlik");
        Uzytkownik user = repozytorium2.findBylogin(uza.getLogin());
        repozytorium2.delete(user);
        model.addObject("lista", repozytorium2.findAll());
        model.addObject("user", new Uzytkownik());
        return model;
    }

    @RequestMapping(value="/user", method = RequestMethod.POST)
    public ModelAndView stronaUser(@ModelAttribute Uzytkownik uza)
    {
        ModelAndView model = new ModelAndView("AdminPlik");
        Uzytkownik user = repozytorium2.findBylogin(uza.getLogin());
        repozytorium2.delete(user);
        model.addObject("lista", repozytorium2.findAll());
        model.addObject("user", new Uzytkownik());
        return model;
    }


    @GetMapping("/user")
    @ResponseBody
    public ModelAndView wypiszUser(Principal principal, Uzytkownik user)
    {
        ModelAndView model = new ModelAndView("user");
        model.addObject("user", repozytorium2.findBylogin(principal.getName()));
        List<Klient> klienci = repozytorium2.findBylogin(principal.getName()).getKlienci();
        model.addObject("lista", klienci);
        model.addObject("co","");
        int cena = 0;
        for (Klient kl: repozytorium2.findBylogin(principal.getName()).getKlienci()
        ) {
            cena = cena + 1000 + kl.getLodowki()*500 + kl.getUsers()*100;

        }
        model.addObject("cena", cena);
        return model;

    }

    @RequestMapping(value="/user/zmienHaslo", method = RequestMethod.POST)
    public ModelAndView zmienHaslo(@RequestParam("stareHaslo") String stareHaslo, @RequestParam("noweHaslo") String noweHaslo, Principal principal)
    {
        ModelAndView model = new ModelAndView("user");
        Uzytkownik user = repozytorium2.findBylogin(principal.getName());
        if(stareHaslo.equals(user.getHaslo())) {
            user.setHaslo(noweHaslo);
            repozytorium2.save(user);
            model.addObject("co", "Zmieniono haslo");
        }else
        {
            model.addObject("co", "nie udalo sie zmienic hasla");
        }

        List<Klient> klienci = repozytorium2.findBylogin(principal.getName()).getKlienci();
        model.addObject("lista", klienci);
        model.addObject("user", repozytorium2.findBylogin(principal.getName()));
        int cena = 0;
        for (Klient kl: user.getKlienci()
             ) {
            cena = cena + 1000 + kl.getLodowki()*500 + kl.getUsers()*100;

        }
        model.addObject("cena", cena);
        return model;
    }


}

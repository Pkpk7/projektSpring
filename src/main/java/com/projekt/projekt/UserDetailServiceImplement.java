package com.projekt.projekt;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class UserDetailServiceImplement implements UserDetailsService {

    @Autowired
    private UzytkownikRepository repozyytorium;

    @Override
    public UserDetails loadUserByUsername(String login)
    {
        Uzytkownik user = repozyytorium.findBylogin(login);
        if(user==null) throw new UsernameNotFoundException(login);
        return new MyUserPrincipal(user);
    }

}

package com.maciejp.postsapp.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserDataAccessObject userDataAccessObject;

    @Override
    public UserDetails loadUserByUsername(String name) throws UsernameNotFoundException {
        User user = userDataAccessObject.selectUserByName(name);
        if (user == null) {
            throw new UsernameNotFoundException("user " + name + " does not exist");
        }
        return new CustomUserDetails(user);
    }
}

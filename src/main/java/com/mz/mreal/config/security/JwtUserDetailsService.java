package com.mz.mreal.config.security;

import com.mz.mreal.model.RealityKeeper;
import com.mz.mreal.model.RealityKeeperRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


@Service
public class JwtUserDetailsService implements UserDetailsService {

    private final RealityKeeperRepository repository;

    @Autowired
    public JwtUserDetailsService(RealityKeeperRepository repository) {this.repository = repository;}

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        RealityKeeper realityKeeper = repository.findByUsername(username);

        if (realityKeeper == null) {
            throw new UsernameNotFoundException(String.format("No user found with username '%s'.", username));
        } else {
            return new JwtUser(realityKeeper);
        }
    }
}

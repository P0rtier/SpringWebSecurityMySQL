package pl.uzi.psspringbootstarttutorial.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import pl.uzi.psspringbootstarttutorial.configuration.UziSecurityDetails;
import pl.uzi.psspringbootstarttutorial.models.User;

@Service
public class UziSecurityDetailsService implements UserDetailsService {

    private final UserManager userManager;

    @Autowired
    public UziSecurityDetailsService(UserManager userManager) {
        this.userManager = userManager;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
            User user = userManager.findByUsername(username);
            if(user == null)
                throw new UsernameNotFoundException("No such element found!");
            return new UziSecurityDetails(user);
    }
}

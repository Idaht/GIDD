package idatt2106.group3.backend.Service;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import idatt2106.group3.backend.Model.User;
import idatt2106.group3.backend.Model.UserSecurityDetails;
import idatt2106.group3.backend.Repository.UserRepository;

/**
 * Service class needed for getting User from database and creating UserDetails object needed for Security Principal
 */
@Service
public class UserSecurityDetailsService implements UserDetailsService{

    private static final Logger LOGGER = LoggerFactory.getLogger(UserSecurityDetailsService.class);

    @Autowired
    UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        LOGGER.info("loadUserByUsername(String username) was called with email: {}", username);

        Optional<User> userOptional = userRepository.findUserByEmail(username);
        if(userOptional.isPresent()){
            User user = userOptional.get();
            List<GrantedAuthority> grantedAuthorities = List.of(new SimpleGrantedAuthority(user.getRole()));
            return new UserSecurityDetails(user.getHash(), user.getEmail(), user.getUserId(), grantedAuthorities);
        }
        else{
            throw new UsernameNotFoundException("Email: " + username + "was not found");
        }
    }

}
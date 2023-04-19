package shop.mtcoding.metablog.core.auth;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import shop.mtcoding.metablog.model.user.User;
import shop.mtcoding.metablog.model.user.UserRepository;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class MyUserDetailsService implements UserDetailsService {
    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> userOP = userRepository.findByUsername(username);
        if (userOP.isPresent()) {
            return new MyUserDetails(userOP.get());
        } else {
            return null;
        }
    }
}

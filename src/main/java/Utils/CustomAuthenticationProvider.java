package Utils;

import Services.LibraryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by aldm on 08.06.2016.
 */
@Service
public class CustomAuthenticationProvider implements AuthenticationProvider {

    @Autowired
    private LibraryService libraryService;

    @Override
    public Authentication authenticate(Authentication authentication) {
        final String username = authentication.getPrincipal().toString();
        final String password = authentication.getCredentials().toString();
        final boolean userExists = libraryService.isRegistered(username,password);

        if (userExists) {
            List<GrantedAuthority> roles = new ArrayList<>();
            roles.add(new SimpleGrantedAuthority("ROLE_USER"));
            return new UsernamePasswordAuthenticationToken(username, password, roles);
        }
        else {
            throw new BadCredentialsException("Bad Credentials");
        }
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return aClass.equals(UsernamePasswordAuthenticationToken.class);
    }
}

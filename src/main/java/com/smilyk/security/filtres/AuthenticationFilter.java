package com.smilyk.security.filtres;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.smilyk.constants.SecurityConstants;
import com.smilyk.repo.UserEntityRepository;
import com.smilyk.security.modelAndDto.LoginRequestDto;
import com.smilyk.security.modelAndDto.UserPrincipal;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

/**
 * class that check user and if everything id ok - create JWT token
 */
public class AuthenticationFilter extends UsernamePasswordAuthenticationFilter {
    private final AuthenticationManager authenticationManager;
    private final UserEntityRepository userRepository;

    public AuthenticationFilter(AuthenticationManager authenticationManager, UserEntityRepository userRepository) {
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
    }

    /**
     * method that check user
     * start all time when user send request to {url}/login
     *
     * @param request  {@link HttpServletRequest}
     * @param response {@link HttpServletResponse}
     * @return {@link AuthenticationManager} - user with all his credentials
     * @throws {@link AuthenticationException} - exception
     */
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
        throws AuthenticationException {
        try {
            LoginRequestDto creds = new ObjectMapper()
                .readValue(request.getInputStream(), LoginRequestDto.class);
            return authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                    creds.getUserEmail(),
                    creds.getPassword(),
                    new ArrayList<>()
                )
            );
        } catch (IOException e) {
            throw new RuntimeException();
        }
    }

    /**
     * methods that create {@link Jwts} token if user is relevant
     *
     * @param request    {@link HttpServletRequest}
     * @param response   {@link HttpServletResponse}
     * @param chain      {@link FilterChain}
     * @param authResult {@link Authentication}
     * @throws {@link  IOException} exception
     * @throws {@link ServletException} exception
     */
    @Override
    protected void successfulAuthentication(HttpServletRequest request,
                                            HttpServletResponse response,
                                            FilterChain chain,
                                            Authentication authResult) throws IOException, ServletException {
        UserPrincipal userPrincipal = (UserPrincipal) authResult.getPrincipal();
        String userName = userPrincipal.getUsername();
        String token = Jwts.builder()
            .setSubject(userName)
            .setExpiration(new Date(System.currentTimeMillis() + SecurityConstants.EXPIRATION_TIME))
            .signWith(SignatureAlgorithm.HS512, SecurityConstants.getTokenSecret())
            .compact();
        response.addHeader(SecurityConstants.HEADER_STRING, SecurityConstants.TOKEN_PREFIX + token
        );
    }


}

package com.smilyk.security.filtres;

import com.smilyk.constants.SecurityConstants;
import com.smilyk.enums.Roles;
import com.smilyk.model.UserEntity;
import com.smilyk.repo.UserEntityRepository;
import com.smilyk.security.modelAndDto.UserPrincipal;
import io.jsonwebtoken.Jwts;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Filter that check at authorization (check {@link Roles})
 */
public class AuthorizationFilter extends BasicAuthenticationFilter {

    final UserEntityRepository userRepository;
    public AuthorizationFilter(AuthenticationManager authenticationManager, UserEntityRepository userRepository) {
        super(authenticationManager);
        this.userRepository = userRepository;
    }


    /**
     * method thsat works every time when server got request to API (any API)
     * This method has access to headers and he check token
     * @param request {@link HttpServletRequest}
     * @param response {@link HttpServletResponse}
     * @param chain {@link FilterChain}
     * @throws IOException
     * @throws ServletException
     */
    //    Каждый раз, когда происходит запрос к любой конечной точке –
//    вызывается этот фильтр. В нем есть доступ
//    к headers, то есть мы можем достать токен и проверить его.
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                                    FilterChain chain) throws IOException, ServletException {
        String header = request.getHeader(SecurityConstants.HEADER_STRING);
        if (header == null || !header.startsWith(SecurityConstants.TOKEN_PREFIX)) {
            chain.doFilter(request, response);
            return;
        }
        UsernamePasswordAuthenticationToken authentication = getAuthentication(request);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        chain.doFilter(request, response);
    }

    private UsernamePasswordAuthenticationToken getAuthentication(HttpServletRequest request) {
        String token = request.getHeader(SecurityConstants.HEADER_STRING);

        if (token != null) {
            token = token.replace(SecurityConstants.TOKEN_PREFIX, "");
            String userEmail = Jwts.parser()
                .setSigningKey(SecurityConstants.getTokenSecret())
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
            if (userEmail != null) {
                UserEntity userEntity = userRepository.getByUserEmail(userEmail).get();
                UserPrincipal userPrincipal = new UserPrincipal(userEntity);
                return new UsernamePasswordAuthenticationToken(userPrincipal,
                    null, userPrincipal.getAuthorities());
            }
            return null;
        }
        return null;
    }



}

package com.mz.mreal.config.security;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.mz.mreal.util.jwt.JwtTokenUtil;
import io.jsonwebtoken.ExpiredJwtException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

public class JwtAuthorizationTokenFilter extends OncePerRequestFilter {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private UserDetailsService userDetailsService;
    private JwtTokenUtil jwtTokenUtil;
    private String tokenHeader;

    public JwtAuthorizationTokenFilter(UserDetailsService userDetailsService, JwtTokenUtil jwtTokenUtil, String tokenHeader) {
        this.userDetailsService = userDetailsService;
        this.jwtTokenUtil = jwtTokenUtil;
        this.tokenHeader = tokenHeader;
    }

    /* TENER EN CUENTA QUE ESTE METODO SIEMPRE SERA INVOCADO A PESAR SI EL REQUEST ESTA COMO permitAll o como authenticated */
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws ServletException, IOException {
        logger.debug("processing authentication for '{}'", request.getRequestURL());

        final String requestHeader = request.getHeader(this.tokenHeader);

        String username = null;
        String authToken = null;
        if (requestHeader != null && requestHeader.startsWith("Bearer ")) {
            authToken = requestHeader.substring("Bearer ".length());
            try {
                username = jwtTokenUtil.getUsernameFromToken(authToken);
            } catch (IllegalArgumentException e) {
                logger.error("an error occured during getting username from token", e);
            } catch (ExpiredJwtException e) {
                logger.warn("El token ya no es valido", e);
            }
        } else {
            logger.warn("No se encontraron tokens en el encabezado de autenticacion");
        }

        Cookie[] cookiesArr = request.getCookies() == null ? new Cookie[]{} : request.getCookies();
        List<Cookie> cookies = Arrays.asList(cookiesArr);
        Optional<Cookie> tokenCookie = cookies.stream().filter(cookie -> "token".equalsIgnoreCase(cookie.getName())).findFirst();
        if (tokenCookie.isPresent()) {
            authToken = tokenCookie.get().getValue();
            try {
                username = jwtTokenUtil.getUsernameFromToken(authToken);
            } catch (IllegalArgumentException e) {
                logger.error("Ocurrio un error al verificar el token a traves del cookie", e);
            } catch (ExpiredJwtException e) {
                logger.warn("El token ya no es valido", e);
            }
        } else {
            logger.warn("No se encontraron tokens en los cookies");
        }


        logger.debug("checking authentication for user '{}'", username);
        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            logger.debug("security context was null, so authorizating user");
            authorizeUser(request, username, authToken);
        }

        chain.doFilter(request, response);
    }

    private void authorizeUser(HttpServletRequest request, String username, String authToken) {
        // It is not compelling necessary to load the use details from the database. You could also store the information
        // in the token and read it from it. It's up to you ;)
        UserDetails userDetails = this.userDetailsService.loadUserByUsername(username);

        // For simple validation it is completely sufficient to just check the token integrity. You don't have to call
        // the database compellingly. Again it's up to you ;)
        if (jwtTokenUtil.validateToken(authToken, userDetails)) {
            UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
            authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
            logger.info("authorizated user '{}', setting security context", username);
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }
    }
}

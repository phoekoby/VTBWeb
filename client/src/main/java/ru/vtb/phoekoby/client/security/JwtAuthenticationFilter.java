package ru.vtb.phoekoby.client.security;

import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.GenericFilterBean;
import ru.vtb.phoekoby.client.dto.response.ResponseUserDTO;
import ru.vtb.phoekoby.client.service.UserService;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Component
@Slf4j
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends GenericFilterBean {
    private final UserService userService;
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        final String token = getTokenFromRequest((HttpServletRequest) servletRequest);
        if (token != null) {
            try {
                userService.authenticate(token);
                Authentication authentication = getAuthentication(token);
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }catch (Exception e){
                log.error("Authentication error with Token: {}" , token);
            }
        }
        filterChain.doFilter(servletRequest, servletResponse);
    }
    private String getTokenFromRequest(HttpServletRequest httpServletRequest) {
        String HEADER_STRING = "Authorization";
        String bearer = httpServletRequest.getHeader(HEADER_STRING);
        String TOKEN_PREFIX = "Bearer ";
        if (bearer != null && bearer.contains(TOKEN_PREFIX) && bearer.startsWith(TOKEN_PREFIX)) {
            return bearer.replace(TOKEN_PREFIX, "");
        }
        return null;
    }
    private Authentication getAuthentication(String token){
        ResponseUserDTO user = userService.authenticate(token);
        if (user == null) {
            return null;
        }
        List<GrantedAuthority> authorities = AuthorityUtils.commaSeparatedStringToAuthorityList(String.join(",", user.getPrivileges()));
        return new UsernamePasswordAuthenticationToken(user, null, authorities);
    }
}

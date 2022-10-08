package ru.vtb.serverrpcmicroservice.security;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.GenericFilterBean;
import ru.vtb.serverrpcmicroservice.dto.UserDTO;
import ru.vtb.serverrpcmicroservice.service.GatewayFeignClient;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends GenericFilterBean {
    private final GatewayFeignClient gatewayFeignClient;
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws ServletException, IOException {

        final String token = getTokenFromRequest((HttpServletRequest) servletRequest);
        if (token != null) {
            try {
                UserDTO userDto = gatewayFeignClient.authenticate(token);
                setUserToAuthenticate(userDto);
            }catch (Throwable e){
                log.info("Error while authenticate. Token = {}", token);
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

    private void setUserToAuthenticate(UserDTO user){
        List<GrantedAuthority> grantedAuthorityList = AuthorityUtils.createAuthorityList(String.join(",", user.getPrivileges()));
        SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken(user, null, grantedAuthorityList));
    }
}

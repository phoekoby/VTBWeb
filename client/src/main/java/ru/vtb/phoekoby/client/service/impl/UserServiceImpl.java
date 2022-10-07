package ru.vtb.phoekoby.client.service.impl;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.SignatureException;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.bouncycastle.jcajce.provider.digest.MD5;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import ru.vtb.phoekoby.client.domain.Privilege;
import ru.vtb.phoekoby.client.domain.Role;
import ru.vtb.phoekoby.client.domain.RolesConstants;
import ru.vtb.phoekoby.client.domain.User;
import ru.vtb.phoekoby.client.dto.AbstractUserDTO;
import ru.vtb.phoekoby.client.dto.create.CreateUserDTO;
import ru.vtb.phoekoby.client.dto.response.ResponseUserDTO;
import ru.vtb.phoekoby.client.mapper.CreateUserMapper;
import ru.vtb.phoekoby.client.mapper.ResponseUserMapper;
import ru.vtb.phoekoby.client.repo.RoleRepository;
import ru.vtb.phoekoby.client.repo.UserRepository;
import ru.vtb.phoekoby.client.service.UserService;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    private final CreateUserMapper createUserMapper;

    private final ResponseUserMapper responseUserMapper;

    private final RoleRepository roleRepository;

    private final String SECRET = "dGhpc2lzbXlmaXJzdHNlY3JldGZvcmp3dHRva2Vuc2liZWxpZXZlaW53aWxsYmV2ZXJ5dXNlZnVsbA";

    @Override
    public ResponseUserDTO createUser(@NonNull CreateUserDTO createUserDTO) {
        User user = createUserMapper.toEntity(createUserDTO);
        user.setRoles(List.of(roleRepository.getRoleByName(RolesConstants.ROLE_USER)));
        user.setPassword(getHashedString(createUserDTO.getPassword()));
        user = userRepository.save(user);
        return responseUserMapper.toDto(user);
    }

    @Override
    public boolean exitingUser(Long id) {
        return userRepository.existsById(id);
    }

    @Override
    public boolean exitingUserByLogin(String login) {
        return userRepository.existsUserByLogin(login);
    }

    @Override
    public String authorize(String login, String password) {
        String encryptedPassword = getHashedString(password);
        User user = userRepository.findByLoginAndPassword(login, encryptedPassword)
                .orElseThrow(()-> new HttpClientErrorException(HttpStatus.NOT_FOUND, "User login or password are incorrect"));
        return Jwts
                .builder()
                .setIssuer(user.getLogin())
                .setSubject("JWT")
                .claim("id", user.getId())
                .claim("login", user.getLogin())
                .claim("email", user.getEmail())
                .claim("firstName", user.getFirstName())
                .claim("lastName", user.getLastName())
                .claim("roles", user.getRoles().stream().map(Role::getName).collect(Collectors.joining(",")))
                .claim("privileges", user.getRoles()
                        .stream()
                        .map(Role::getPrivileges)
                        .map(privileges -> privileges
                                .stream()
                                .map(Privilege::getName)
                                .collect(Collectors.joining(","))))
                .signWith(
                        SignatureAlgorithm.HS256,
                        SECRET
                )
                .compact();
    }

    @Override
    public ResponseUserDTO   authenticate(String jwt) {
        Claims claims;
        try {
            claims = getClaimsFromToken(jwt);
        } catch (ExpiredJwtException expEx) {
            log.error("Token expired", expEx);
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST,"Invalid token");
        } catch (UnsupportedJwtException unsEx) {
            log.error("Unsupported jwt", unsEx);
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST,"Invalid token");
        } catch (MalformedJwtException mjEx) {
            log.error("Malformed jwt", mjEx);
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST,"Invalid token");
        } catch (SignatureException sEx) {
            log.error("Invalid signature", sEx);
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST,"Invalid token");
        } catch (Exception e) {
            log.error("invalid token", e);
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST,"Invalid token");
        }
        return parseClaimsToUser(claims);
    }

    private Claims getClaimsFromToken(@NonNull String jwt) {
        return Jwts.parserBuilder()
                .setSigningKey(SECRET)
                .build()
                .parseClaimsJws(jwt)
                .getBody();
    }

    private ResponseUserDTO parseClaimsToUser(Claims claims) {
        ResponseUserDTO responseUserDTO;
        try {
        responseUserDTO = (ResponseUserDTO) AbstractUserDTO
                .builder()
                .firstName((String) claims.get("firstName"))
                .lastName((String) claims.get("lastName"))
                .login((String) claims.get("login"))
                .email((String) claims.get("email"))
                .build();
        responseUserDTO.setId((Long) claims.get("id"));
        responseUserDTO.setPrivileges(Arrays.asList(((String) claims.get("privileges")).split(",")));
        responseUserDTO.setRoles(Arrays.asList(((String) claims.get("roles")).split(",")));
        } catch (Exception e) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST,"Invalid token");
        }
        return responseUserDTO;
    }

    public String getHashedString(String string) {
        MessageDigest md = null;
        try {
            md = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            throw new HttpServerErrorException(HttpStatus.CONFLICT);
        }
        md.update(string.getBytes());
        byte[] digest = md.digest();

        return bytesToHex(digest);
    }

    private static String bytesToHex(byte[] bytes) {
        StringBuilder sb = new StringBuilder();
        for (byte b : bytes) {
            sb.append(String.format("%02x", b));
        }
        return sb.toString();
    }
}

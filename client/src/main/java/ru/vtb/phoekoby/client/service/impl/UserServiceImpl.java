package ru.vtb.phoekoby.client.service.impl;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.SignatureException;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.bouncycastle.jcajce.provider.digest.MD5;
import org.bouncycastle.util.encoders.Base64;
import org.springframework.data.domain.Pageable;
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

import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;
import java.security.Key;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Collection;
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
    byte[] apiKeySecretBytes = DatatypeConverter.parseBase64Binary(SECRET);
    SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;
    Key signingKey = new SecretKeySpec(apiKeySecretBytes, signatureAlgorithm.getJcaName());


    @Override
    public ResponseUserDTO createUser(@NonNull CreateUserDTO createUserDTO) {
        User user = createUserMapper.toEntity(createUserDTO);
        user.setRoles(List.of(roleRepository.getRoleByName(RolesConstants.ROLE_USER).get()));
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
                .orElseThrow(() -> new HttpClientErrorException(HttpStatus.NOT_FOUND, "User login or password are incorrect"));
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
                        signingKey
                )
                .compact();
    }

    @Override
    public ResponseUserDTO addRoleToUser(Long id, String role) {
        log.debug("Try to add role to User: {} {}", role, id);
        User user = getUserById(id);
        Role roleEntity = roleRepository.getRoleByName(role).orElseThrow(() -> new HttpClientErrorException(HttpStatus.NOT_FOUND, "Such Role not Found"));
        Collection<Role> userRoles = user.getRoles();
        userRoles.add(roleEntity);
        user = userRepository.save(user);
        return responseUserMapper.toDto(user);
    }

    @Override
    public List<ResponseUserDTO> getAllPublicUsers(Pageable pageable) {
        List<User> users = userRepository.findAll();
        return users.stream().map(responseUserMapper::toDto).collect(Collectors.toList());
    }

    private User getUserById(Long id){
        return userRepository.findById(id).orElseThrow(() -> new HttpClientErrorException(HttpStatus.NOT_FOUND, "User with such id not found"));
    }
    @Override
    public ResponseUserDTO getPublicUser(Long id) {
        User user = getUserById(id);
        return responseUserMapper.toDto(user);
    }

    @Override
    public ResponseUserDTO authenticate(String jwt) {
        Claims claims;
        try {
            claims = getClaimsFromToken(jwt);
        } catch (ExpiredJwtException expEx) {
            log.error("Token expired", expEx);
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Invalid token");
        } catch (UnsupportedJwtException unsEx) {
            log.error("Unsupported jwt", unsEx);
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Invalid token");
        } catch (MalformedJwtException mjEx) {
            log.error("Malformed jwt", mjEx);
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Invalid token");
        } catch (SignatureException sEx) {
            log.error("Invalid signature", sEx);
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Invalid token");
        } catch (Exception e) {
            log.error("invalid token", e);
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Invalid token");
        }
        return parseClaimsToUser(claims);
    }

    private Claims getClaimsFromToken(@NonNull String jwt) {
        return Jwts.parserBuilder()
                .setSigningKey(signingKey)
                .build()
                .parseClaimsJws(jwt)
                .getBody();
    }

    private ResponseUserDTO parseClaimsToUser(Claims claims) {
        ResponseUserDTO responseUserDTO;
        try {
            responseUserDTO = new ResponseUserDTO(Long.parseLong(String.valueOf(claims.get("id"))),
                    Arrays.asList(((String) claims.get("privileges")).split(",")),
                    Arrays.asList(((String) claims.get("roles")).split(",")));
            responseUserDTO.setFirstName((String) claims.get("firstName"));
            responseUserDTO.setLastName((String) claims.get("lastName"));
            responseUserDTO.setLogin((String) claims.get("login"));
            responseUserDTO.setEmail((String) claims.get("email"));
        } catch (Exception e) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Invalid token");
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

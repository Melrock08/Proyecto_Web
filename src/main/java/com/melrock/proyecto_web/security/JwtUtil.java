package com.melrock.proyecto_web.security;

import java.nio.charset.StandardCharsets;

import java.security.MessageDigest;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import java.util.Date;
import java.util.Base64;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import org.springframework.stereotype.Component;
import org.springframework.security.core.Authentication;

import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.Jwts.SIG;

import com.melrock.proyecto_web.exception.InvalidTokenException;



import com.melrock.proyecto_web.dto.*;

@Component
public class JwtUtil {
    private final SecretKey llaveSecreta = generateSecretKey();
    private final long tiempoExpiracion = 1000 * 60 * 60 * 3;

    private static SecretKey generateSecretKey() {
        // Lógica para generar una clave secreta segura
        String secreto = System.getenv("JWT_SECRET_KEY");

        if(secreto == null || secreto.trim().isEmpty()) {
            secreto = "founders-2025-tesis-tesos-jwt-secret-key-minimum-256-bits-for-hs256-algorithm";
        }  
        
        //clave con almenos 256 bits- 32 bytes
        byte[] bytesLlave = secreto.getBytes(StandardCharsets.UTF_8);

        if(bytesLlave.length < 32){
            try{
                MessageDigest digestor = MessageDigest.getInstance("SHA-256");
                bytesLlave = digestor.digest(bytesLlave);
            } catch (NoSuchAlgorithmException e){

                StringBuilder constructorSecreto = new StringBuilder(secreto);

                while (constructorSecreto.length() < 32){
                    constructorSecreto.append(secreto);
                } 
                    
                bytesLlave = constructorSecreto.substring(0, 32).getBytes(StandardCharsets.UTF_8);

            }  
        } else if(bytesLlave.length > 32){

            //si mayor de 32 bytes, truncar
            byte[] truncado = new byte[32];
            System.arraycopy(bytesLlave, 0, truncado, 0, 32);
            bytesLlave = truncado;
        }

        return new SecretKeySpec(bytesLlave, SIG.HS256.key().build().getAlgorithm());
    }

    public String generarToken(String jusuario, String rol){
        return Jwts.builder()
        .setSubject(jusuario)
        .claim("rol", rol)
        .issuedAt(new Date())
        .expiration(new Date(System.currentTimeMillis() + tiempoExpiracion))
        .signWith(this.llaveSecreta)
        .compact();
    }

    public String extraerContrasenia(String token){
        return getReclamaciones(token).getSubject();
    }

    public Claims getReclamaciones(String token){
        return Jwts.parser()
        .verifyWith(this.llaveSecreta)
        .build()
        .parseSignedClaims(token)
        .getPayload();
    }

    public boolean validarToken(String token){
        try{
            Claims reclamaciones = getReclamaciones(token);
            return !reclamaciones.getExpiration().before(new Date());
        } catch (Exception e){
            return false;
        }
    }

    public String extraerRol(String token) {
        return (String) getReclamaciones(token).get("rol");
        
    }

    //@throws JsonProcessingException
    //@throws JsonMappingException
    public AuthorizedDTO renovarToken(Authentication autenticacion) throws JsonMappingException, JsonProcessingException{

        if(autenticacion == null || autenticacion.getPrincipal() == null){
            throw new IllegalArgumentException("Autenticación o principal nulo");
        }

        String token = autenticacion.getCredentials().toString();

        if(!validarToken(token)){
            throw new IllegalArgumentException("Token inválido o expirado");
        }

        return getAutorizado(token);
    }

    public AuthorizedDTO getAutorizado(String token) throws JsonMappingException, JsonProcessingException{

        String jusuario = getReclamaciones(token).getSubject();

        ObjectMapper mapper = new ObjectMapper();

        UserExtendedDTO usuario = mapper.readValue(jusuario, UserExtendedDTO.class);

        String nuevoToken = generarToken(jusuario, "APP_USER");

        return new AuthorizedDTO(usuario, nuevoToken, "Bearer");
    }

    public AuthorizedDTO appAutorizado(String token) throws InvalidKeyException, NoSuchAlgorithmException, NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException, JsonMappingException, JsonProcessingException{
        
        String llaveSecreta = System.getenv("JWT_SECRET_APP");
        SecretKey llaveSecretaApp = generarLlaveDesdeTexto(llaveSecreta);
        String susuario = desencriptar(token, llaveSecretaApp);

        ObjectMapper mapper = new ObjectMapper();

        UserExtendedDTO usuario = mapper.readValue(susuario, UserExtendedDTO.class);

        String nuevoToken = generarToken(susuario, "APP_USER");

        return new AuthorizedDTO(usuario, nuevoToken, "Bearer");
    }

    public SecretKey generarLlaveDesdeTexto(String texto){
        byte[] llaveBytes = texto.getBytes(StandardCharsets.UTF_8);

        if(llaveBytes.length < 32){
            try{
                MessageDigest digestor = MessageDigest.getInstance("SHA-256");
                llaveBytes = digestor.digest(llaveBytes);

            }catch(NoSuchAlgorithmException e){
                throw new RuntimeException("Error al generar la llave secreta", e);
            }
        } else if(llaveBytes.length > 32){
            byte[] truncado = new byte[32];
            System.arraycopy(llaveBytes, 0, truncado, 0, 32);
            llaveBytes = truncado;
        }

        return new SecretKeySpec(llaveBytes, "AES");
    }

    public String desencriptar(String textoCifrado, SecretKey llaveSecreta) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException{

        Cipher cifrador = Cipher.getInstance("AES");
        cifrador.init(Cipher.DECRYPT_MODE, llaveSecreta);

        //Decodificar en base 64
        byte[] bytesDecodificados = Base64.getDecoder().decode(textoCifrado);
        byte[] bytesDecriptados = cifrador.doFinal(bytesDecodificados);

        return new String(bytesDecriptados, StandardCharsets.UTF_8);
    }

    public String encriptar(String text, SecretKey llaveSecreta) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException{
        
        Cipher cifrador = Cipher.getInstance("AES");
        cifrador.init(Cipher.ENCRYPT_MODE, llaveSecreta);

        byte[] bytesCifrados = cifrador.doFinal(text.getBytes(StandardCharsets.UTF_8));

        return Base64.getUrlEncoder().withoutPadding().encodeToString(bytesCifrados);
    }

    public UsuarioDTO getUsuario(Authentication autenticacion) throws JsonMappingException, JsonProcessingException, InvalidTokenException {
        String token = autenticacion.getCredentials().toString();

        if(!validarToken(token)){
            throw new InvalidTokenException("Token inválido o expirado");
        }

        return getAutorizado(token).getUserExtended();
    }

    public String hashMD5(String data){
        try{
            MessageDigest digestor = MessageDigest.getInstance("MD5");
            byte[] bytesHash = digestor.digest(data.getBytes(StandardCharsets.UTF_8));
            return bytesHaciaHexadecimal(bytesHash);
        }catch(NoSuchAlgorithmException e){
            throw new RuntimeException("Error al generar el hash MD5", e);
        }
    }

    public String bytesHaciaHexadecimal(byte[] bytes){
        StringBuilder strBuilder = new StringBuilder();

        for(byte b : bytes){
            strBuilder.append(String.format("%02x", b));
        }

        return strBuilder.toString();
    }

    public SecretKey generarLlaveSecreta() {
        String miLlave = "EstaNocheOscuraTeTorturaLaLocuraProcuraEstarAMiAlturaAunqueBajaEsTuEstaturaTartamurasAnteElMiedoQueGeneroEnElMomentoQueAparexcoEntreLasSombrasYEnTuMenteMeConecto";
        byte[] llaveBytes = miLlave.getBytes(StandardCharsets.UTF_8);
        
        byte[] llaveAjustada = new byte[16];

        System.arraycopy(llaveBytes, 0, llaveAjustada, 0, Math.min(llaveBytes.length, 16));

        return new SecretKeySpec(llaveAjustada, "AES");
     }
}



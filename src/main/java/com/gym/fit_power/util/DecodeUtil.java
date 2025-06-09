package com.gym.fit_power.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.interfaces.DecodedJWT;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class DecodeUtil {

    private static final String BEARER_PREFIX = "Bearer ";

    /**
     * Decodifica un token JWT (opcionalmente con prefijo "Bearer ") y extrae el claim "cuit".
     *
     * @param authorizationHeader El token JWT, posiblemente con el prefijo "Bearer ".
     * @return El valor del claim "cuit" como String, o null si el token es inválido,
     *         no contiene el claim, o el claim no es un String.
     */
    public static String extractCuitFromToken(String authorizationHeader) {
        if (authorizationHeader == null || authorizationHeader.trim().isEmpty()) {
            log.warn("El encabezado de autorización proporcionado es nulo o vacío.");
            return null;
        }

        String token;
        // Verificamos si el token viene con el prefijo "Bearer " y lo removemos
        if (authorizationHeader.startsWith(BEARER_PREFIX)) {
            token = authorizationHeader.substring(BEARER_PREFIX.length());
        } else {
            token = authorizationHeader;
        }

        if (token.trim().isEmpty()) {
            log.warn("El token extraído (después de remover 'Bearer ' si existía) está vacío.");
            return null;
        }

        try {
            DecodedJWT decodedJWT = JWT.decode(token);
            // Asumimos que el claim "cuit" es un String.
            // Si pudiera ser de otro tipo, necesitarías un manejo más robusto.
            if (decodedJWT.getClaim("cuit").isMissing() || decodedJWT.getClaim("cuit").isNull()) {
                log.warn("El claim 'cuit' no fue encontrado en el token o es nulo.");
                return null;
            }
            return decodedJWT.getClaim("cuit").asString();
        } catch (JWTDecodeException exception){
            // Esto ocurre si el token no es un JWT válido o está malformado.
            log.error("Error al decodificar el token JWT: {}", exception.getMessage());
            return null;
        }
        // El NullPointerException catch anterior es menos probable con la forma en que
        // java-jwt maneja los claims ausentes (devuelve un Claim especial que .asString() convierte a null).
        // Sin embargo, lo mantenemos por si acaso o para otras operaciones con claims.
        // La comprobación explícita con .isMissing() o .isNull() es más clara.
    }

}

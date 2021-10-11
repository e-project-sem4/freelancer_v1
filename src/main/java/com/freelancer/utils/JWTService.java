package com.freelancer.utils;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.nimbusds.jose.JWSVerifier;
import com.nimbusds.jose.crypto.MACVerifier;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;

@Service
public class JWTService {

	@Value("${security.jwt.token.secret-key:secret-key}")
	private String secretKey;

	public String getUsernameFromToken(String token) {
		String username = null;
		try {
			JWTClaimsSet claims = getClaimsFromToken(token);
			if (claims != null)
				username = claims.getStringClaim("username").toLowerCase();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return username;
	}

	private JWTClaimsSet getClaimsFromToken(String token) {
		JWTClaimsSet claims = null;
		try {
			SignedJWT signedJWT = SignedJWT.parse(token);
			JWSVerifier verifier = new MACVerifier(generateShareSecret());
			if (signedJWT.verify(verifier)) {
				claims = signedJWT.getJWTClaimsSet();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return claims;
	}

	private byte[] generateShareSecret() {
		// Generate 256-bit (32-byte) shared secret
		byte[] sharedSecret = new byte[32];
		sharedSecret = "secret-key".getBytes();
		return sharedSecret;
	}
}

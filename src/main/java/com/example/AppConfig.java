package com.example;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.util.Base64;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.enterprise.context.ApplicationScoped;
import javax.security.enterprise.authentication.mechanism.http.CustomFormAuthenticationMechanismDefinition;
import javax.security.enterprise.authentication.mechanism.http.LoginToContinue;
import javax.security.enterprise.identitystore.DatabaseIdentityStoreDefinition;

@DatabaseIdentityStoreDefinition(
		dataSourceLookup = "jdbc/DefaultDataSource",
		callerQuery = "select user_password from security_user where email_address = ?",
		groupsQuery = "select r.role_name from security_user u join group_role g on u.fk_group_id = g.fk_group_id join security_role r on g.fk_role_id = r.role_id where email_address = ?",
		priority=30)
@CustomFormAuthenticationMechanismDefinition(
		loginToContinue = @LoginToContinue(loginPage = "/login.xhtml", errorPage = "/login.xhtml?error=true"))
@ApplicationScoped
public class AppConfig {

	public static void main(String[] args) throws NoSuchAlgorithmException, InvalidKeySpecException {
		SecureRandom random = new SecureRandom();
		byte[] salt = new byte[32];
		random.nextBytes(salt);
		KeySpec spec = new PBEKeySpec("password123".toCharArray(), salt, 2048, 32);
		SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");
		byte[] hash = factory.generateSecret(spec).getEncoded();
		String base64Hash = Base64.getEncoder().encodeToString(hash);
		String base64Salt = Base64.getEncoder().encodeToString(salt);

		System.out.println("PBKDF2WithHmacSHA256:2048:" + base64Salt + ":" + base64Hash);
	}
}

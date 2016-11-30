package com.duayres.config.init;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class PasswordGenerator {
	public static void main(String[] args) {
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		// salt do ShaPass... é "after string" e circundado com chaves ({}) :)
		System.out.println(encoder.encode("admin"));
	}
}

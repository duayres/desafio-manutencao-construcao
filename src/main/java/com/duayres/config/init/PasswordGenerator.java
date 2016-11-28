package com.duayres.config.init;

import org.springframework.security.authentication.encoding.ShaPasswordEncoder;

public class PasswordGenerator {
	public static void main(String[] args) {
		ShaPasswordEncoder sha = new ShaPasswordEncoder();
		// descobri que o salt é "after string" e circundado com chaves ({}) :)
		System.out.println(sha.encodePassword("admin", "palavrasecreta"));
	}
}

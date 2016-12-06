package com.duayres.mailer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import com.duayres.model.Usuario;
/**
 * Classe responsavel pelo envio de email
 * @author duayres
 */
@Component
public class Mailer{
	
	@Autowired(required=false)
	private JavaMailSender mailSender;
	/**
	 * Metodo que efetua o envio de uma mensagem de email, chamado assincronamente
	 * @param usuario
	 */
	@Async
	public void enviar(Usuario usuario){
		SimpleMailMessage mensagem = new SimpleMailMessage();
		
		mensagem.setFrom("duzao7667@gmail.com");
		mensagem.setTo(usuario.getEmail());
		mensagem.setSubject("Seu cadastro está completo");
		mensagem.setText("Seu cadastro está completo. Seus dados de acesso são: Usuario: "+usuario.getEmail()+" Senha: "+usuario.getConfSenha());
		
		mailSender.send(mensagem);
	}
}

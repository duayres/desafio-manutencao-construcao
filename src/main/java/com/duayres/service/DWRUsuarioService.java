package com.duayres.service;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Optional;

import org.directwebremoting.annotations.RemoteProxy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

import com.duayres.mailer.Mailer;
import com.duayres.model.TipoUsuario;
import com.duayres.model.Usuario;
import com.duayres.repository.IUsuarioRepository;
import com.duayres.service.exception.EmailJaExistenteException;

@RemoteProxy
public class DWRUsuarioService {
	
	private BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
	
	@Autowired
	private IUsuarioRepository usuarioRepository;
	
	@Autowired
	private Mailer mailSender;
	
	@Transactional
	@PreAuthorize("hasRole('ROLE_ADMINISTRADOR')")
	public Usuario save(Usuario usuario){
		if (usuario.isNew()){
			if (findByEmailIgnoreCaseAndStatusTrue(usuario.getEmail()).isPresent()){
				throw new EmailJaExistenteException("Email já existente.");
			}
			if (!usuario.getSenha().isEmpty()) usuario.setSenha(encoder.encode(usuario.getSenha()));
			
		}
		usuario = this.usuarioRepository.saveAndFlush(usuario);
		mailSender.enviar(usuario);
		return usuario;
	}
	
	public Usuario findUserById( Long id )
	{
		Optional<Usuario> userOpt= this.usuarioRepository.findByIdUsuario(id);
		
		Usuario user = userOpt.orElseThrow(() -> new UsernameNotFoundException("User not found"));
		return user;
	}
	
	/*public Usuario findUserById( Long id )
	{
		return this.usuarioRepository.findOne( id );
	}*/

	public List<Usuario> listAll(){
		return usuarioRepository.findAll();
	}
	
	public TipoUsuario[] listTiposUsuario(){
		return TipoUsuario.values();
	}
	
	public List<Usuario> find(Usuario usuario) {
		if(usuario.getNome() == null){
			usuario.setNome("");
		}
		
		if(usuario.getEmail() == null){
			usuario.setEmail("");
		}
		
		return usuarioRepository.findByNomeIgnoreCaseContainingAndEmailIgnoreCaseContaining(usuario.getNome(), usuario.getEmail());
	}

	public Optional<Usuario> findByEmailIgnoreCaseAndStatusTrue(String username) {
		return usuarioRepository.findByEmailIgnoreCaseAndStatusTrue(username);
	}
	
	public Usuario login(Usuario usuario) {
		System.out.println(encoder.encode(usuario.getSenha()));
		Optional<Usuario> userOpt = usuarioRepository.findByEmailIgnoreCaseAndStatusTrue(usuario.getEmail());
		
		Usuario user = userOpt.orElseThrow(() -> new UsernameNotFoundException("Usuario e/ou senha não encontrados"));
		
		if (!encoder.matches(usuario.getSenha(), user.getSenha())){
			throw new UsernameNotFoundException("Usuario e/ou senha não encontrados");
		}
		
		return user;
	}
	
	public List<Usuario> listUsuariosLivresNoPeriodo(Calendar dtInicio, Calendar dtFinal){
		SimpleDateFormat formatter = new SimpleDateFormat("ddd MMM yy HH:mm:ss");
		//dtInicio=Calendar.getInstance();
		//dtFinal=Calendar.getInstance();
		System.out.println(formatter.format(dtFinal.getTime()));
		//dtInicio.set(Calendar.DAY_OF_YEAR, 5);
		//dtInicio.set(Calendar.DAY_OF_YEAR, 5);
		
		
		return usuarioRepository.listUsuariosLivresNoPeriodo(dtInicio, dtFinal);
	}
	
}

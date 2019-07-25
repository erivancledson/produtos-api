package com.erivan.produtos.security;

import java.io.IOException;


import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;

import org.springframework.web.filter.OncePerRequestFilter;

import com.erivan.produtos.model.Usuario;
import com.erivan.produtos.repository.UsuarioRepository;

public class AutenticacaoViaTokenFilter extends OncePerRequestFilter{
	
	private TokenService tokenService;
	private UsuarioRepository repository;
	
	public AutenticacaoViaTokenFilter(TokenService tokenService, UsuarioRepository repository) {
		this.tokenService = tokenService;
		this.repository = repository;
	}
	
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		
			String token = recuperarToken(request);
			boolean valido = tokenService.isTokenValido(token);
			if(valido) {
				//so autentica se tiver valido
				autenticarCliente(token);
			}
			
			filterChain.doFilter(request, response);
		
	}
	private void autenticarCliente(String token) {
	
		Long idUsuario = tokenService.getIdUsuario(token);
		Usuario usuario = repository.findById(idUsuario).get();
		
		UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(usuario, null, usuario.getAuthorities());
		SecurityContextHolder.getContext().setAuthentication(authentication);
		
	}

	//pega o token criado
	private String recuperarToken(HttpServletRequest request) {
		String token = request.getHeader("Authorization");
		//se atender algumas dessas condições retorna nulo
		if(token == null || token.isEmpty() || !token.startsWith("Bearer ")) {
			return null;
		}
		//começa pegar o token depois do nome Bearer, por isso conta 7 posicoes
		return token.substring(7, token.length());
	}

}

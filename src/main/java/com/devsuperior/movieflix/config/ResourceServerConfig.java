package com.devsuperior.movieflix.config;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;


// ############ RESOURCE SERVER

@Configuration
@EnableResourceServer
public class ResourceServerConfig extends ResourceServerConfigurerAdapter {

	@Autowired
	private Environment env; // VARIÁVEL DO AMBIENTE, UTILIZAREMOS PARA LIBERAR O H2-CONSOLE LOCALHOST:8080
	
	@Autowired
	private JwtTokenStore tokenStore;
	
	// ENDPOINTS PUBLICOS DA APLICAÇÃO -> O CATÁLOGO, QUE É PÚBLICO, SÓ FAZ REQUISIÇÕES 'GET' PARA MOSTRAR OS PRODUTOS AO CLIENTE
	private static final String[] PUBLIC = { "/oauth/token", "/h2-console/**" };
	
	
	@Override
	public void configure(ResourceServerSecurityConfigurer resources) throws Exception {
		resources.tokenStore(tokenStore); // ELE DECODIFICA O TOKEN E ANALISA SE O TOKEN RECEBIDO ESTÁ BATENDO COM O SECRET, SE ESTÁ EXPIRADO, ETC. VER SE O TOKEN É VÁLIDO
	}

	@Override
	public void configure(HttpSecurity http) throws Exception {
		
		// LIBERAR O LOCALHOST:8080 H2
		if (Arrays.asList(env.getActiveProfiles()).contains("test")) { //SE NOS PERFIS ATIVOS, CONTIVER O "TESTE"
			// LIBERAR O LOCALHOST H2
			http.headers().frameOptions().disable();
			
		}
		
		// CONFIGURAR AS ROTAS DE QUEM PODE ACESSAR O QUE
		http.authorizeRequests()
		.antMatchers(PUBLIC).permitAll() // TUDO LIBERADO, NÃO PRECISA LOGIN
		.anyRequest().authenticated(); // QUALQUER OUTRA ROTA, TEM QUE ESTAR LOGADO, NÃO IMPORTANDO O PERFIL DO USUÁRIO
		
	}

	
}

package com.tevo.frutas.auth;

import java.util.Arrays;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

@Configuration
@EnableResourceServer
public class ResourceServerConfig extends ResourceServerConfigurerAdapter {

	@Override
	public void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests().antMatchers(HttpMethod.GET, "/api/fruta/**",			
				"/api/cliente/**",
				"/api/almacen/**",
				"/api/variedad_fruta/**",
				"/api/sub_tipo_empaque/**",
				"/api/orden_venta/**",
				"/api/uploads/img/**",
				"/api/tabla_auxiliar_detalle/**",
				"/api/ubigeo/**",
				"/api/orden_compra/img/*",
				"/images/**").hasAnyRole("ADMIN")
		.antMatchers(HttpMethod.POST, "/api/proveedor", "/api/orden_compra", "/api/orden_venta", "/api/upload").hasAnyRole("ADMIN")
		.antMatchers(HttpMethod.PUT, "/api/proveedor/{id}", "/api/orden_compra/{id}", "/api/orden_venta/{id}").hasAnyRole("ADMIN")
		.antMatchers("/api/orden_compra/subscription", "/api/orden_compra/notification","/api/proveedor/**").permitAll()
//		.anyRequest().authenticated()
		.and()
		.cors().configurationSource(corsConfigurationSource());
	}
	
	@Bean
	public CorsConfigurationSource corsConfigurationSource() {
		CorsConfiguration config = new CorsConfiguration();
		config.setAllowedOrigins(Arrays.asList("https://frutas.cpysd1.com", "*"));
		config.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS"));
		config.setAllowCredentials(true);
		config.setAllowedHeaders(Arrays.asList("Content-Type", "Authorization"));
		
		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		source.registerCorsConfiguration("/**", config);
		
		return source;		
	}
	
	@Bean
	public FilterRegistrationBean<CorsFilter> corsFilter() {
		FilterRegistrationBean<CorsFilter> bean = new FilterRegistrationBean<CorsFilter>(new CorsFilter(corsConfigurationSource()));
		bean.setOrder(Ordered.HIGHEST_PRECEDENCE);
		
		return bean;
	}
}

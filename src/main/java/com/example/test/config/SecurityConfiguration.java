//package com.example.test.config;
//
//import javax.sql.DataSource;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.security.provisioning.JdbcUserDetailsManager;
//
//
//@Configuration
//@EnableWebSecurity
//public class SecurityConfiguration extends WebSecurityConfigurerAdapter {
//	@Autowired
//	DataSource myds;
//	@Bean
//    @Override
//    protected UserDetailsService userDetailsService(){
//        JdbcUserDetailsManager manager = new JdbcUserDetailsManager();
//        manager.setDataSource(myds);
//        //Only for initiating empty database
//        //PasswordEncoder encoder = passwordEncoder();
//        //manager.createUser(User.withUsername("admin").password(encoder.encode("123456")).authorities("ADMIN").build());
//        //manager.createUser(User.withUsername("user").password(encoder.encode("123456")).authorities("USER").build());
//        return manager;
//    }
//
//	@Bean
//	public PasswordEncoder passwordEncoder() { // 配置密碼加密元件
//		return new BCryptPasswordEncoder();
//	}
//
//	@Bean
//	public DaoAuthenticationProvider authProvider() {
//		DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
//		authProvider.setUserDetailsService(userDetailsService());
//		authProvider.setPasswordEncoder(passwordEncoder()); // 在Auth物件上設定加密元件
//		return authProvider;
//	}
//
//	@Override
//	protected void configure(HttpSecurity http) throws Exception {
//		http.authorizeRequests()
//			.antMatchers("/bill/**").authenticated()
//			.antMatchers("/product/**").permitAll()
//			.and()
//			.httpBasic();
//	}
//}

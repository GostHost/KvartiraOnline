package com.example.KvartiraOnline.config;


import com.example.KvartiraOnline.repository.UserRepository;
import com.example.KvartiraOnline.service.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.hierarchicalroles.RoleHierarchy;
import org.springframework.security.access.hierarchicalroles.RoleHierarchyImpl;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.session.SessionRegistryImpl;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.expression.DefaultWebSecurityExpressionHandler;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.security.web.session.HttpSessionEventPublisher;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private AuthenticationSuccessHandler myAuthenticationSuccessHandler;

    @Autowired
    private LogoutSuccessHandler myLogoutSuccessHandler;

    @Autowired
    private AuthenticationFailureHandler authenticationFailureHandler;

//    @Autowired
//    private CustomWebAuthenticationDetailsSource authenticationDetailsSource;

    @Autowired
    private UserRepository userRepository;


//    @Bean
//    public AuthenticationManager authManager(HttpSecurity http) throws Exception {
//        return http.getSharedObject(AuthenticationManagerBuilder.class)
//                .authenticationProvider(authProvider())
//                .build();
//    }

//    @Bean
//    public DaoAuthenticationProvider authProvider() {
//        final CustomAuthenticationProvider authProvider = new CustomAuthenticationProvider();
//        authProvider.setUserDetailsService(userDetailsService);
//        authProvider.setPasswordEncoder(encoder());
//        authProvider.setPostAuthenticationChecks(differentLocationChecker());
//        return authProvider;
//    }

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return (web) -> web.ignoring()
                .requestMatchers("/resources/**", "/h2/**");
    }

    @Bean
    public PasswordEncoder encoder() {
        return new BCryptPasswordEncoder(11);
    }


    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf()
                .disable()
                .authorizeRequests()
                .requestMatchers("/login*", "/logout*", "/signin/**", "/signup/**", "/customLogin", "/user/registration*", "/registrationConfirm*", "/expiredAccount*", "/registration*", "/badUser*", "/user/resendRegistrationToken*", "/forgetPassword*",
                        "/user/resetPassword*", "/user/savePassword*", "/updatePassword*", "/user/changePassword*", "/emailError*", "/resources/**", "/old/user/registration*", "/successRegister*", "/qrcode*", "/user/enableNewLoc*")
                .permitAll()
                .requestMatchers("/invalidSession*")
                .anonymous()
                .requestMatchers("/user/updatePassword*")
                .hasAuthority("CHANGE_PASSWORD_PRIVILEGE")
                .anyRequest()
                .hasAuthority("READ_PRIVILEGE")
                .and()
                .formLogin()
                .loginPage("/login")
                .defaultSuccessUrl("/homepage.html")
                .failureUrl("/login?error=true")
                .successHandler(myAuthenticationSuccessHandler)
                .failureHandler(authenticationFailureHandler)
                //.authenticationDetailsSource(authenticationDetailsSource)
                .permitAll()
                .and()
                .sessionManagement()
                .invalidSessionUrl("/invalidSession.html")
                .maximumSessions(1)
                .sessionRegistry(sessionRegistry())
                .and()
                .sessionFixation()
                .none()
                .and()
                .logout()
                .logoutSuccessHandler(myLogoutSuccessHandler)
                .invalidateHttpSession(true)
                .logoutSuccessUrl("/logout.html?logSucc=true")
                .deleteCookies("JSESSIONID")
                .permitAll()
                .and();
                //.key("theKey");
        return http.build();
    }

//    @Bean
//    public UserDetailsService userDetailsService() {
//        UserDetails user =
//                User.withDefaultPasswordEncoder()
//                        .username("user")
//                        .password("password")
//                        .roles("USER")
//                        .build();
//
//        return new InMemoryUserDetailsManager(user);
//    }

    @Bean
    public RoleHierarchy roleHierarchy() {
        RoleHierarchyImpl roleHierarchy = new RoleHierarchyImpl();
        String hierarchy = "ROLE_ADMIN > ROLE_STAFF \n ROLE_STAFF > ROLE_USER";
        roleHierarchy.setHierarchy(hierarchy);
        return roleHierarchy;
    }

    @Bean
    public DefaultWebSecurityExpressionHandler webSecurityExpressionHandler() {
        DefaultWebSecurityExpressionHandler expressionHandler = new DefaultWebSecurityExpressionHandler();
        expressionHandler.setRoleHierarchy(roleHierarchy());
        return expressionHandler;
    }

    @Bean
    public HttpSessionEventPublisher httpSessionEventPublisher() {
        return new HttpSessionEventPublisher();
    }

    @Bean
    public SessionRegistry sessionRegistry() {
        return new SessionRegistryImpl();
    }



}

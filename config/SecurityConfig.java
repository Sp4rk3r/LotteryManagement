package be.thibaulthelsmoortel.lotterymanagement.config;

import be.thibaulthelsmoortel.lotterymanagement.AppConstants;
import be.thibaulthelsmoortel.lotterymanagement.util.authentication.AppAuthenticationProvider;
import be.thibaulthelsmoortel.lotterymanagement.web.views.authentication.login.LoginViewImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.ServletListenerRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.session.SessionRegistryImpl;
import org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint;
import org.springframework.security.web.session.HttpSessionEventPublisher;

/**
 * Spring Security configuration.
 *
 * @author Thibault Helsmoortel
 */
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final AppAuthenticationProvider appAuthenticationProvider;

    @Autowired
    public SecurityConfig(AppAuthenticationProvider appAuthenticationProvider) {
        this.appAuthenticationProvider = appAuthenticationProvider;
    }

    @Bean
    public static ServletListenerRegistrationBean<HttpSessionEventPublisher> httpSessionEventPublisher() {
        return new ServletListenerRegistrationBean<>(new HttpSessionEventPublisher());
    }

        @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(appAuthenticationProvider);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // Configure what needs authentication
        http.authorizeRequests()// Define requests
                .antMatchers("/updateStates/**").permitAll()
                .antMatchers("/", "/**").permitAll() // Allow home page
                //.antMatchers(AppUI.UI_NAME, AppUI.UI_NAME + "/**").fullyAuthenticated()
                // TODO: Add line to allow registration page | use .not().authenticated()
                .antMatchers("/vaadinServlet/UIDL/**").permitAll() // Allow Vaadin
                .antMatchers("/vaadinServlet/HEARTBEAT/**").permitAll() // Allow heartbeat
                .antMatchers("/vaadinServlet/PUSH").permitAll()// Allow push
                .anyRequest().authenticated();

        // Configure login url
        http.exceptionHandling().authenticationEntryPoint(new LoginUrlAuthenticationEntryPoint("/#!" + LoginViewImpl.VIEW_NAME));

        // Configure logout url
        http.logout().logoutUrl(AppConstants.LOGOUT_URL).logoutSuccessUrl("/").permitAll();

        // TODO add an invalid session page - url
        // http.sessionManagement().invalidSessionUrl("/invalidSession.html");

        http.sessionManagement()
                .maximumSessions(100)
                .maxSessionsPreventsLogin(false)
                .expiredUrl("/#!logout")
                .sessionRegistry(sessionRegistry());

        // Disable cross-side
        http.csrf().disable();

        // The iframe origin must be set in order to allow file downloads
        http.headers().frameOptions().sameOrigin();
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/VAADIN/**");
        web.ignoring().antMatchers("/css/*"); // Static resources are ignored
    }

    @Override
    @Bean(name = "authenticationManager")
    protected AuthenticationManager authenticationManager() throws Exception {
        return super.authenticationManager();
    }

    @Bean
    public SessionRegistry sessionRegistry() {
        SessionRegistry sessionRegistry = new SessionRegistryImpl();
        appAuthenticationProvider.setSessionRegistry(sessionRegistry);
        return sessionRegistry;
    }
}

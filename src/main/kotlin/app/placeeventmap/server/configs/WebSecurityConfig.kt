package app.placeeventmap.server.configs

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.builders.WebSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter

@Configuration
@EnableWebSecurity
class WebSecurityConfig : WebSecurityConfigurerAdapter() {
    @Autowired
    private val jwtAuthenticationEntryPoint: JwtAuthenticationEntryPoint? = null

    @Autowired
    private val jwtUserDetailsService: UserDetailsService? = null

    @Autowired
    private val jwtRequestFilter: JwtRequestFilter? = null

    @Autowired
    override fun configure(auth: AuthenticationManagerBuilder) {
        auth.userDetailsService(jwtUserDetailsService)
    }

    @Bean
    fun passwordEncoder(): PasswordEncoder {
        return BCryptPasswordEncoder()
    }

    @Bean
    override fun authenticationManagerBean(): AuthenticationManager {
        return super.authenticationManagerBean()
    }

    override fun configure(httpSecurity: HttpSecurity) {
        httpSecurity.cors().and()
            .csrf().disable() // Don't authenticate this particular request
            .authorizeRequests().antMatchers("/auth", "/profile/info", "/register", "/swagger-ui.html/**", "/v2/api-docs/**").permitAll().anyRequest()
            .authenticated().and().exceptionHandling()
            .authenticationEntryPoint(jwtAuthenticationEntryPoint).and().sessionManagement()
            .sessionCreationPolicy(SessionCreationPolicy.STATELESS)

        httpSecurity.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter::class.java)
    }

    override fun configure(web: WebSecurity) {
        web.ignoring().antMatchers("/v2/api-docs", "/configuration/ui", "/swagger-resources/**", "/configuration/security",
            "/swagger-ui.html", "/webjars/**")
    }

//    @Bean
//    fun corsConfigurationSource(): CorsConfigurationSource {
//        val configuration = CorsConfiguration()
//        configuration.allowedOrigins = Arrays.asList("http://localhost:3000", "http://localhost:4200")
//        configuration.allowedMethods = Arrays.asList("GET", "POST", "PUT", "DELETE")
//        configuration.addAllowedHeader("*")
//        val source = UrlBasedCorsConfigurationSource()
//        source.registerCorsConfiguration("/**", configuration)
//        return source
//    }
}
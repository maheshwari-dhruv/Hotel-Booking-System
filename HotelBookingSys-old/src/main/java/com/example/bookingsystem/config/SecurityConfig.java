package com.example.bookingsystem.config;

import com.example.bookingsystem.filter.JwtRequestFilter;
import com.example.bookingsystem.service.impl.CustomUserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    private static final String STAFF = "staff";
    private static final String CUSTOMER = "customer";

    @Autowired
    private JwtAuthEntryPoint jwtAuthEntryPoint;

    @Autowired
    private CustomUserDetailsServiceImpl customUserDetailsService;

    @Autowired
    private JwtRequestFilter jwtRequestFilter;

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(customUserDetailsService).passwordEncoder(passwordEncoder());
    }

    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.csrf().disable()
                .cors().disable()
                .authorizeRequests().antMatchers("/api/v2/user/register", "/api/v2/user/register/staff", "/api/v2/user/login").permitAll()
                .antMatchers("/api/v2/user/view/all").hasAuthority(STAFF)
                .antMatchers("/api/v2/room/create", "/api/v2/room/view/all/reserved", "/api/v2/room/update/{roomId}", "/api/v2/room/delete/{roomId}").hasAuthority(STAFF)
                .antMatchers("/api/v2/booking/view/all", "/api/v2/booking/view/all/date={checkInDate}", "/api/v2/booking/view/all/dates={checkInDate}&{checkOutDate}", "/api/v2/booking/view/all/active").hasAuthority(STAFF)
                .antMatchers("/api/v2/meal/create", "/api/v2/meal/update/{mealName}", "/api/v2/meal/delete/{mealName}").hasAuthority(STAFF)
                .antMatchers("/api/v2/facility/create", "/api/v2/facility/update/{mealName}", "/api/v2/facility/delete/{mealName}").hasAuthority(STAFF)
                .antMatchers("/api/v2/user/view/{username}", "/api/v2/user/update/{username}", "/api/v2/user/delete/{username}").hasAnyAuthority(STAFF, CUSTOMER)
                .antMatchers("/api/v2/room/view/all", "/api/v2/room/view/id/{roomId}", "/api/v2/room/view/type/{roomType}", "/api/v2/room/view/occupancy/adults/{adults}/children/{children}", "/api/v2/room/view/all/available").hasAnyAuthority(STAFF, CUSTOMER)
                .antMatchers("/api/v2/booking/create/rooms={roomId}/user={username}/guests={guestId}/facilities={facilities}/meals={meals}", "/api/v2/booking/view/{bookingId}", "/api/v2/booking/check/room={roomId}/availability/dates={checkInDate}&{checkOutDate}", "/api/v2/booking/view/all/{username}", "/api/v2/booking/delete/{bookingId}").hasAnyAuthority(STAFF, CUSTOMER)
                .antMatchers("/api/v2/guest/create", "/api/v2/guest/view/all", "/api/v2/guest/view/{guestId}", "/api/v2/guest/update/{guestId}", "/api/v2/guest/delete/{guestId}").hasAnyAuthority(STAFF, CUSTOMER)
                .antMatchers("/api/v2/meal/view/all", "/api/v2/meal/view/{mealName}").hasAnyAuthority(STAFF, CUSTOMER)
                .antMatchers("/api/v2/facility/view/all", "/api/v2/facility/view/{mealName}").hasAnyAuthority(STAFF, CUSTOMER)
                .antMatchers("/api/v2/catalog/save/facilities={facilityNames}/meals={mealsName}", "/api/v2/catalog/view/all").hasAnyAuthority(STAFF, CUSTOMER)
                .anyRequest().authenticated().and()
                .exceptionHandling().authenticationEntryPoint(jwtAuthEntryPoint).and().sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        httpSecurity.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);
    }
}

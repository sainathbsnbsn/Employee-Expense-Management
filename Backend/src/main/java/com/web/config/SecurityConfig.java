package com.web.config;

import com.web.filter.JwtAuthFilter;
import com.web.service.UserInfoUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;


@Configuration
@EnableWebSecurity
@EnableWebMvc
@EnableMethodSecurity
public class SecurityConfig {
	
	@Autowired
	private JwtAuthFilter authFilter;
	
	
	//authentication
	@Bean
	public UserDetailsService userDetailsService() {
			return new UserInfoUserDetailsService();
    }
	
	
	@Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http.csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(auth->{
                	auth.requestMatchers("expense/authenticate","expense/new","category/add","category/update/{id}","category/delete/{id}","category/getall","category/get/{id}","category/status/{status}","expense/report/getreceipt/{id}","/swagger-ui.html", "/swagger-ui/**", "/v3/api-docs", "/v3/api-docs/**").permitAll()
                	.requestMatchers("/", "/error", "/csrf", "/swagger-ui.html", "/swagger-ui/**", "/v3/api-docs", "/v3/api-docs/**").permitAll()
                    .requestMatchers("expense/welcome","expense/welcomeemployee",
                    		"expense/report/add",
                    		"expense/employee/update/{associateId}",
                    		"expense/report/addexpensereceipt/{id}",
                    		"expense/report/allexpenses",
                    		"expense/report/get/{expenseId}",
                    		"expense/report/delete/{expenseId}",
                    		"expense/report/reject/{expenseId}",
                    		"expense/report/accept/{expenseId}",
                    		"expense/report/getExpenses/{empid}",
                    		"expense/report/update/{expenseId}",
                    		"expense/report/getExpensesbycategory/{categoryId}",
                    		"expense/report/getExpensesByStatus/{status}",
                    		"expense/report/getExpensesByStatusByEmpId/{status}/{associateId}",
                    		"expense/report/bystatusbycategoryId/{status}/{categoryId}",
                    		"expense/report/getAmountByCategory/{categoryId}",
                    		"expense/report/getTotalAmountByCategoryInBetween","expense/report/getAmountByStatus/{status}",
                    		"expense/report/getTotalAmountByCategoryByStatusInBetween",
                    		"expense/report/getAmountByCategoryByStatusMonthWise/{year}",
                    		"expense/employee/getworkerId/{id}",
                    		"expense/employee/getEmployeebymngId/{managerId}",
                    		"expense/employee/getall",
                    		"expense/employee/get/{empId}",
                    		"expense/report/getAmountBystatusByAssIdPresentYear/{status}/{associateId}",                   		
                    		"expense/report/get/{expenseId}/{empid}",        
                    		"expense/report/getAmountByStatusByEmpId/{status}/{associateId}",
                    		"expense/report/getExpensesByCategoryIdPagenation/{status}/{pageno}/{rows}",
                    		"expense/report/getAmountByStatusLastFiveYearWise/{empId}"
                    		).authenticated();
               })
                .sessionManagement(session->session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authenticationProvider(authenticationProvider())
                .addFilterBefore(authFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }
	
	
	@Bean
    public AuthenticationProvider authenticationProvider(){
        DaoAuthenticationProvider authenticationProvider=new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(userDetailsService());
        authenticationProvider.setPasswordEncoder(passwordEncoder());
        return authenticationProvider;
    }
	
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }
   
	
}

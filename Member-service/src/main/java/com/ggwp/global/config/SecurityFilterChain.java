package com.ggwp.global.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ggwp.global.filter.CustomJsonUsernamePasswordAuthenticationFilter;
import com.ggwp.global.handler.LoginFailureHandler;
import com.ggwp.global.handler.LoginSuccessHandler;
import com.ggwp.global.handler.TokenAccessDeniedHandler;
import com.ggwp.global.jwt.JwtAuthenticationProcessingFilter;
import com.ggwp.global.jwt.JwtTokenProvider;
import com.ggwp.global.jwt.service.CustomUserDetailService;
import com.ggwp.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.access.expression.method.DefaultMethodSecurityExpressionHandler;
import org.springframework.security.access.expression.method.MethodSecurityExpressionHandler;
import org.springframework.security.access.hierarchicalroles.RoleHierarchy;
import org.springframework.security.access.hierarchicalroles.RoleHierarchyImpl;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.logout.LogoutFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

/**
 * 인증은 CustomJsonUsernamePasswordAuthenticationFilter에서 authenticate()로 인증된 사용자로 처리
 * JwtAuthenticationProcessingFilter는 AccessToken, RefreshToken 재발급
 */
@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityFilterChain {

    private final CustomUserDetailService customUserDetailService;
    private final JwtTokenProvider jwtTokenProvider;
    private  final MemberRepository memberRepository;
    private final ObjectMapper objectMapper;
    private final TokenAccessDeniedHandler tokenAccessDeniedHandler;


    @Bean
    public org.springframework.security.web.SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http
                .cors(cors -> cors.configurationSource(corsConfigurationSource()))
                .sessionManagement(sessionManagement -> sessionManagement.sessionCreationPolicy(
                        SessionCreationPolicy.STATELESS))
                .csrf(AbstractHttpConfigurer::disable)
                .formLogin(AbstractHttpConfigurer::disable)
                .httpBasic(AbstractHttpConfigurer::disable)
                .headers(headers -> headers.frameOptions(frameOptions -> headers.disable()))
                .exceptionHandling(exceptionHandling -> exceptionHandling
//                        .authenticationEntryPoint(new RestAuthenticationEntryPoint())
                        .accessDeniedHandler(tokenAccessDeniedHandler))
                //== URL별 권한 관리 옵션 ==//
//        .authorizeHttpRequests(m -> methodSecurityExpressionHandler(new RoleHierarchy()))
                .authorizeHttpRequests(authorizationHttpRequests -> authorizationHttpRequests
                        .requestMatchers(
                               "/v1/auth/**",
                                "/v1/member/signup",//회원가입
                                "/v1/member/login",//로그인
                                "/v1/member/findpw",//비밀번호 찾기 (비밀번호 재발급)
                                "/v1/member/findid",//아이디 찾기
                                "/v1/member/verify/**" //이메일 인증
                        ).permitAll()
                        .anyRequest().authenticated()) // 위의 경로 이외에는 모두 인증된 사용자만 접근 가능
                .logout(logout -> logout.logoutSuccessUrl("/"))


                 //순서 : LogoutFilter -> JwtAuthenticationProcessingFilter -> CustomJsonUsernamePasswordAuthenticationFilter
                .addFilterAfter(customJsonUsernamePasswordAuthenticationFilter(), LogoutFilter.class)
                .addFilterBefore(jwtAuthenticationProcessingFilter(),
                        CustomJsonUsernamePasswordAuthenticationFilter.class);

        return http.build();
    }


    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
    @Bean
    public AuthenticationManager authenticationManager() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setPasswordEncoder(passwordEncoder());
        provider.setUserDetailsService(customUserDetailService);
        return new ProviderManager(provider);
    }
    @Bean
    public LoginSuccessHandler loginSuccessHandler() {
        return new LoginSuccessHandler(jwtTokenProvider, memberRepository);
    }

    @Bean
    public LoginFailureHandler loginFailureHandler() {
        return new LoginFailureHandler();
    }

    //커스텀 필터 및 성공 실패 핸들러
    @Bean
    public CustomJsonUsernamePasswordAuthenticationFilter customJsonUsernamePasswordAuthenticationFilter() {
        CustomJsonUsernamePasswordAuthenticationFilter customJsonUsernamePasswordLoginFilter
                = new CustomJsonUsernamePasswordAuthenticationFilter(objectMapper);
        customJsonUsernamePasswordLoginFilter.setAuthenticationManager(authenticationManager());
        customJsonUsernamePasswordLoginFilter.setAuthenticationSuccessHandler(
                loginSuccessHandler());
        customJsonUsernamePasswordLoginFilter.setAuthenticationFailureHandler(
                loginFailureHandler());
        return customJsonUsernamePasswordLoginFilter;
    }
    @Bean
    public JwtAuthenticationProcessingFilter jwtAuthenticationProcessingFilter() {
        return new JwtAuthenticationProcessingFilter(
                jwtTokenProvider, memberRepository); //f블랙ㅅ리스트 추가예정
    }
    //cors 설정
    @Bean
    public UrlBasedCorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.addAllowedOriginPattern("*");
        configuration.addAllowedMethod("*");
        configuration.addAllowedHeader("*");
        configuration.setAllowCredentials(true);
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }


    @Bean
    public RoleHierarchy roleHierarchy() {
        RoleHierarchyImpl roleHierarchy = new RoleHierarchyImpl();
        roleHierarchy.setHierarchy("ROLE_ADMIN > ROLE_USER");
        return roleHierarchy;
    }

    @Bean
    public MethodSecurityExpressionHandler methodSecurityExpressionHandler(
            RoleHierarchy roleHierarchy) {
        DefaultMethodSecurityExpressionHandler expressionHandler = new DefaultMethodSecurityExpressionHandler();
        expressionHandler.setRoleHierarchy(roleHierarchy);
        return expressionHandler;
    }
}

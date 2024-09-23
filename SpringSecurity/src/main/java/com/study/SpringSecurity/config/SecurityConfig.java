package com.study.SpringSecurity.config;

import com.study.SpringSecurity.security.filter.JwtAccessTokenFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

// 김언!!
/**
 * 김언은 들어라
 * 갈출 키보드 좋나
 * 피자타임 맛있나
 * 분홍색 물병, 대야 컵, 펄럭개.
 * 케이블카, 김언의 갈변한 케이스
 * 김언의 몸속엔.. 5마리의 사막여우가.. 힘을 숨기고 있다..
 * 갈축 키보드 좋네;;
 */


/**
 * - 오버라이드시 매개변수의 이름은 바꿀 수 있다.(타입못 바꿈)
 */

@EnableWebSecurity  // 우리가 만든 SecurityConfig를 적용시키겠다.
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private JwtAccessTokenFilter jwtAccessTokenFilter;

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override                   // 시큐리티 초기 세팅
    protected void configure(HttpSecurity http) throws Exception {
        http.formLogin().disable();
        http.httpBasic().disable();
        http.csrf().disable();
        // 위조 방지 스티커(토큰)

        // http.sessionManagement().disable();
        // 스프링 시큐리티가 세션을 생성하지도 않고 기존의 세션을 사용하지도 않겠다.
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        // 스프링 시큐리티가 세션을 생성하지 않겠다. 기존의 센션을 완전히 사용하지 않겠다는 뜻은 아님.
        // JWT 등의 토큰 인증방식을 사용할 때 설정하는 것.
        http.cors();    // crossOrigin을 가능하게 하는 것, 서로 다른 서버 연결(특정 조건일 때 연결 가능하게 하겠다같은 것)
        http.authorizeRequests()
                .antMatchers("/auth/**", "/h2-console/**")
                .permitAll()        // 위의 것을 가능하게 하는것(위의 괄호안의 것은 인증할 필요가 없다)
                .anyRequest()       // 1. 모든요청은
                .authenticated()    // 2. 인증이 없으면 나머지 다른 요청들을 막는다.
                // h2콘솔 쓰려면 있어야 됨
                .and()
                .headers()
                .frameOptions()
                .disable();

        // 뒷 필터가 실행되기전에 앞 필터가 실행 되라
        http.addFilterBefore(jwtAccessTokenFilter, UsernamePasswordAuthenticationFilter.class);
    }

}









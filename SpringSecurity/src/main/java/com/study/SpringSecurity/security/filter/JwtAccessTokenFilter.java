package com.study.SpringSecurity.security.filter;

import com.study.SpringSecurity.domain.entity.User;
import com.study.SpringSecurity.repository.UserRepository;
import com.study.SpringSecurity.security.jwt.JwtProvider;
import com.study.SpringSecurity.security.principal.PrincsipalUser;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.objenesis.instantiator.perc.PercInstantiator;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.security.Principal;
import java.util.Optional;

@Component
public class JwtAccessTokenFilter extends GenericFilter {

    @Autowired
    private JwtProvider jwtProvider;

    @Autowired
    private UserRepository userRepository;

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;

        String bearerAccessToken = request.getHeader("Authorization");

        Claims claims = null;
        if (bearerAccessToken != null) {
            String accessToken = jwtProvider.removeBearer(bearerAccessToken);
            claims = null;
            try {
                claims = jwtProvider.parseToken(accessToken);
            } catch (Exception e) {

                // 다음 필터로 넘어간다. 응답을 하면 다시 이 필터로 넘어온다. 이거 밑이 후처리
                filterChain.doFilter(servletRequest, servletResponse);
                // 리턴을 안달면 루프돈다.
                return;
            }
            Long userId = ((Integer)claims.get("userId")).longValue();
            Optional<User> optionalUser = userRepository.findById(userId);
            if(optionalUser.isEmpty()) {
                filterChain.doFilter(servletRequest, servletResponse);
                return;
            }

            PrincsipalUser princsipalUser = optionalUser.get().toPrincipaluser();
            Authentication authentication = new UsernamePasswordAuthenticationToken(princsipalUser, princsipalUser.getPassword(), princsipalUser.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }

        filterChain.doFilter(servletRequest, servletResponse); // 전처리, 후처리
    }


}


/**
 * AOP와 필터의 차이
 * AOP : 메소드 호출에 대한 공통 로직들(컨트롤러도 메소드임)
 * Filter : 톰캣에서 컨트롤러로 가기 전, 요청과 응답에 대한 공통 로직들
 * 시큐리티 : 서버로 들어가기전 거처야 됨
 */

/**
 * 시큐리티 라이브러리 : 수 많은 필터들의 집합
 * config : 컴포넌트 등록
 * inable : 기존것은 안쓰고 우리가 만든것을 쓰겠다.
 * 클라이언트 : 요청, 응답 날리는 대상 -> tomcat(여기서 req, resp 만들어짐) -> (서블릿으로 가기전 걸린다) 필터 : 여기서 JWT검사한 후
 * 403등으로 응답, 여기서 Auth를 날리면 서블릿으로 갈 수 있다. Auth으로 안준얘들은 무조건 토큰 들고있어야 됨 / 전역상태 SecuratyController에
 * Auth 객체를 넣음, SecuratyController가 요청, 응답되고나서 다시 클라이언트쪽으로 돌아가면 들어있던 객체는 없어짐 / SecuratyController에 user_id등
 * 정보들이 다 들어있음 /매 번 토큰을 들고와서 서버한테 검사받고 서버가 허용해주면 들어갈 수 있음(임시출입증같은 것) -> 서블릿 -> 컨트롤러
 *
 * 토큰 : 서버가 종료되도 사라지지않는다. 기간(시간)이 만료되면 사라짐
 *
 *
 */
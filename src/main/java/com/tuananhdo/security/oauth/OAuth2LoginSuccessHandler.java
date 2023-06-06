package com.tuananhdo.security.oauth;

import com.tuananhdo.entity.User;
import com.tuananhdo.service.UserAuthenticationService;
import com.tuananhdo.util.AuthenticationType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class OAuth2LoginSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {

    private final UserAuthenticationService userAuthenticationService;

    @Autowired
    public OAuth2LoginSuccessHandler(UserAuthenticationService userAuthenticationService) {
        this.userAuthenticationService = userAuthenticationService;
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws ServletException, IOException {
        OAuth2BlogUser oAuth2User = (OAuth2BlogUser) authentication.getPrincipal();
        String name = oAuth2User.getName();
        String email = oAuth2User.getEmail();
        User user = userAuthenticationService.findByEmail(email);
        if (user == null) {
            userAuthenticationService.createUserOAuthLogin(name, email);
        } else {
            userAuthenticationService.updateAuthentication(user, AuthenticationType.GOOGLE);
        }
        super.onAuthenticationSuccess(request, response, authentication);
    }
}

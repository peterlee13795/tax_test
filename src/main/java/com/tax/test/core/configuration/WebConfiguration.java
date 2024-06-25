package com.tax.test.core.configuration;

import com.tax.test.core.resolver.MemberPrincipalArgumentResolver;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

@Configuration
@EnableJpaAuditing
@RequiredArgsConstructor
public class WebConfiguration implements WebMvcConfigurer {

    private final MemberPrincipalArgumentResolver memberPrincipalArgumentResolver;

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(memberPrincipalArgumentResolver);
    }
}

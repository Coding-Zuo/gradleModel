package com.zuo.model.modules.security.core.social.config;
import java.util.List;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.boot.autoconfigure.thymeleaf.ThymeleafAutoConfiguration;
import org.springframework.boot.autoconfigure.web.servlet.WebMvcAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.social.UserIdSource;
import org.springframework.social.config.annotation.EnableSocial;
import org.springframework.social.config.annotation.SocialConfigurerAdapter;
import org.springframework.social.connect.ConnectionFactoryLocator;
import org.springframework.social.connect.ConnectionRepository;
import org.springframework.social.connect.UsersConnectionRepository;
import org.springframework.social.connect.web.ConnectController;
import org.springframework.social.connect.web.ConnectInterceptor;
import org.springframework.social.connect.web.DisconnectInterceptor;
import org.springframework.social.connect.web.ProviderSignInController;
import org.springframework.social.connect.web.ProviderSignInInterceptor;
import org.springframework.social.connect.web.SignInAdapter;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;
import org.springframework.web.servlet.view.BeanNameViewResolver;

/**
 * {@link EnableAutoConfiguration Auto-configuration} for Spring Social's web connection
 * support.
 * springboot2缺失
 * @author Craig Walls
 * @since 1.1.0
 */
@Configuration
@ConditionalOnClass({ ConnectController.class, SocialConfigurerAdapter.class })
@ConditionalOnBean({ ConnectionFactoryLocator.class, UsersConnectionRepository.class })
@AutoConfigureBefore(ThymeleafAutoConfiguration.class)
@AutoConfigureAfter(WebMvcAutoConfiguration.class)
public class SocialWebAutoConfiguration {

    @Configuration
    @EnableSocial
    @ConditionalOnWebApplication
    protected static class SocialAutoConfigurationAdapter
            extends SocialConfigurerAdapter {

        private final List<ConnectInterceptor<?>> connectInterceptors;

        private final List<DisconnectInterceptor<?>> disconnectInterceptors;

        private final List<ProviderSignInInterceptor<?>> signInInterceptors;

        public SocialAutoConfigurationAdapter(
                ObjectProvider<List<ConnectInterceptor<?>>> connectInterceptorsProvider,
                ObjectProvider<List<DisconnectInterceptor<?>>> disconnectInterceptorsProvider,
                ObjectProvider<List<ProviderSignInInterceptor<?>>> signInInterceptorsProvider) {
            this.connectInterceptors = connectInterceptorsProvider.getIfAvailable();
            this.disconnectInterceptors = disconnectInterceptorsProvider.getIfAvailable();
            this.signInInterceptors = signInInterceptorsProvider.getIfAvailable();
        }

        @Bean
        @ConditionalOnMissingBean(ConnectController.class)
        public ConnectController connectController(
                ConnectionFactoryLocator factoryLocator,
                ConnectionRepository repository) {
            ConnectController controller = new ConnectController(factoryLocator,
                    repository);
            if (!CollectionUtils.isEmpty(this.connectInterceptors)) {
                controller.setConnectInterceptors(this.connectInterceptors);
            }
            if (!CollectionUtils.isEmpty(this.disconnectInterceptors)) {
                controller.setDisconnectInterceptors(this.disconnectInterceptors);
            }
            return controller;
        }

        @Bean
        @ConditionalOnMissingBean
        @ConditionalOnProperty(prefix = "spring.social", name = "auto-connection-views")
        public BeanNameViewResolver beanNameViewResolver() {
            BeanNameViewResolver viewResolver = new BeanNameViewResolver();
            viewResolver.setOrder(Ordered.HIGHEST_PRECEDENCE);
            return viewResolver;
        }

        @Bean
        @ConditionalOnBean(SignInAdapter.class)
        @ConditionalOnMissingBean
        public ProviderSignInController signInController(
                ConnectionFactoryLocator factoryLocator,
                UsersConnectionRepository usersRepository, SignInAdapter signInAdapter) {
            ProviderSignInController controller = new ProviderSignInController(
                    factoryLocator, usersRepository, signInAdapter);
            if (!CollectionUtils.isEmpty(this.signInInterceptors)) {
                controller.setSignInInterceptors(this.signInInterceptors);
            }
            return controller;
        }

    }

    @Configuration
    @EnableSocial
    @ConditionalOnWebApplication
    @ConditionalOnMissingClass("org.springframework.security.core.context.SecurityContextHolder")
    protected static class AnonymousUserIdSourceConfig extends SocialConfigurerAdapter {

        @Override
        public UserIdSource getUserIdSource() {
            return new UserIdSource() {
                @Override
                public String getUserId() {
                    return "anonymous";
                }
            };
        }

    }

    @Configuration
    @EnableSocial
    @ConditionalOnWebApplication
    @ConditionalOnClass(SecurityContextHolder.class)
    protected static class AuthenticationUserIdSourceConfig
            extends SocialConfigurerAdapter {

        @Override
        public UserIdSource getUserIdSource() {
            return new SecurityContextUserIdSource();
        }

    }

//    @Configuration
//    @ConditionalOnClass(SpringResourceResourceResolver.class)
//    protected static class SpringSocialThymeleafConfig {
//
//        @Bean
//        @ConditionalOnMissingBean
//        public SpringSocialDialect springSocialDialect() {
//            return new SpringSocialDialect();
//        }
//
//    }

    private static class SecurityContextUserIdSource implements UserIdSource {

        @Override
        public String getUserId() {
            SecurityContext context = SecurityContextHolder.getContext();
            Authentication authentication = context.getAuthentication();
            Assert.state(authentication != null,
                    "Unable to get a " + "ConnectionRepository: no user signed in");
            return authentication.getName();
        }
    }
}

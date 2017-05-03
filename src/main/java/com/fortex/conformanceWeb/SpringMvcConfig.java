/**<p>Description</p>
 * @author Ivan Huo
 */
package com.fortex.conformanceWeb;

import java.util.Locale;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;

/**
 * @author Administrator
 *
 */
@Configuration
public class SpringMvcConfig {
	@Bean(name="localeResolver")
    public LocaleResolver localeResolverBean() {
       // return new SessionLocaleResolver();
      SessionLocaleResolver localeResolver=new SessionLocaleResolver();      
      //localeResolver.setDefaultLocale(Locale.ENGLISH);
      return localeResolver;
    }
}

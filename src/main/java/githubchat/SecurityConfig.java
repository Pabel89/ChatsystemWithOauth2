package githubchat;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import  org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.config.annotation.CorsRegistry;

@Configuration
@EnableWebSecurity
public class SecurityConfig implements WebMvcConfigurer {

        /*
         * Bean, die Festlegt was freigeben wird. Ohne diese wird
         * durch die automatische OAuth Security alles gesperrt. Hier wird die Einstiegsseite freigegeben
         */
        @Bean
        public WebSecurityCustomizer webSecurityCustomizer() {
          /*
          //H2 Konsole Sperren, wenn Anwendung produktiv läuft. Nur statische Ressourcen freigeben z.B. CSS & JS von Einstiegsseite und Einstiegsseite 
           return (web) -> web.ignoring().requestMatchers("/css/style.css","/js/index.js","index.html");

          */

          //H2 Konsole auch Freigeben während der Entwicklung
          return (web) -> web.ignoring().requestMatchers("/css/style.css","/js/index.js","index.html","/h2/**");
          
        }

        
        @Override
        public void addCorsMappings(CorsRegistry registry) {
          registry.addMapping("/**")
              .allowedOrigins("*")
              .allowedMethods("GET", "POST", "PUT", "DELETE")
              .allowedHeaders("*");
        }

}
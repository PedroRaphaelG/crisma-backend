package br.edu.uniesp.crisma.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Sistema de Gerenciamento de Crisma - API")
                        .version("1.0.0")
                        .description("API REST para gerenciamento completo de programas de crisma, " +
                                    "incluindo cadastro de crismandos, catequistas, eventos, " +
                                    "controle de frequência e gestão financeira.")
                        .contact(new Contact()
                                .name("Equipe Crisma - UNIESP")
                                .email("crismajp@uniesp.edu.br"))
                        .license(new License()
                                .name("MIT License")
                                .url("https://opensource.org/licenses/MIT")));
    }
}

// конфигуратор бинов для передачи в контекст спринга

package org.example;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.*;


@Configuration
@PropertySource("classpath:application.properties")
@ComponentScan(basePackages = "org.example")
public class AppConfig {

    @Value("${contacts.file-path}")
    private String contactsFilePath;

    @Bean
    public ContactService contactService() {
        return new ContactService(contactsFilePath);
    }
    @Value("${contacts.storage-file}")
    private String storageFile;

}

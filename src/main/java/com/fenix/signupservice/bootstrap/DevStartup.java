package com.fenix.signupservice.bootstrap;

import com.fenix.signupservice.model.AppUser;
import com.fenix.signupservice.model.AppUserRole;
import com.fenix.signupservice.repository.AppUserRepository;
import lombok.AllArgsConstructor;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class DevStartup implements ApplicationListener<ContextRefreshedEvent> {

    private final AppUserRepository appUserRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        initData();
    }

    private void initData() {
        AppUser ali = new AppUser("Ali", "Majeed", "alimajeed@live.com", bCryptPasswordEncoder.encode("password"), AppUserRole.USER);
        appUserRepository.save(ali);
    }
}

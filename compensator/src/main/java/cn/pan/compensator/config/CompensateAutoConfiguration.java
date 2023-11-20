package cn.pan.compensator.config;

import cn.pan.compensator.context.CompensateContextHolder;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;

import org.springframework.boot.context.properties.EnableConfigurationProperties;

import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;


@Configuration
@EnableConfigurationProperties(CompensateConfigProperties.class)
@Slf4j
public class CompensateAutoConfiguration implements CommandLineRunner {

    @Autowired
    private CompensateConfigProperties properties;

    @Override
    public void run(String... strings) throws Exception {
        log.info("compensateConfigProperties properties:{}", properties);
    }

    @PostConstruct
    public void init() {
        String targetPSM = properties.getTargetPSM();
        if (StringUtils.isBlank(targetPSM)) {
            targetPSM = System.getenv("TCE_PSM");
        }
        CompensateContextHolder.setTargetPSM(targetPSM);
    }
    /*
    xxxx
     */
}

package cn.pan.compensator.job;


import cn.pan.compensator.utils.RedisUtils;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.StopWatch;
import org.springframework.data.redis.core.RedisTemplate;

import java.net.InetAddress;
import java.util.concurrent.TimeUnit;


@Slf4j
public class CompensateRunnable implements Runnable {

    private final CompensateManager compensateManager;

    private final int waitMillis;

    private final int compensateSize;

    private RedisTemplate redisTemplate;

    private String redisPrefixKey;

    public CompensateRunnable(CompensateManager compensateManager, int waitSeconds, int compensateSize,
                              RedisTemplate redisTemplate, String redisPrefixKey) {
        this.compensateManager = compensateManager;
        this.waitMillis = waitSeconds <= 0 ? 60 * 1000 : waitSeconds * 1000;
        this.redisTemplate = redisTemplate;
        this.compensateSize = compensateSize;
        this.redisPrefixKey = StringUtils.isNotBlank(redisPrefixKey) ? redisPrefixKey : System.getenv("TCE_PSM");
    }

    @SneakyThrows
    @Override
    public void run() {
        while (true) {
            try {
                StopWatch stopWatch = new StopWatch();
                stopWatch.start();
                //是否开启独占
                if (redisTemplate != null && !new RedisUtils<>(redisTemplate).lock(redisPrefixKey + "compensateJob-run",
                    InetAddress.getLocalHost().toString(), waitMillis, TimeUnit.MILLISECONDS)) {
                    log.info("compensateRunnable skip. {}ms", stopWatch.getTime());
                    Thread.sleep(waitMillis);
                    continue;
                }
                //开始获取数据并补偿
                log.info("compensateRunnable run start. {}ms", stopWatch.getTime());
//                compensateManager.getRepository().findAllFail(compensateSize).forEach(compensateManager::doCompensate);
                log.info("compensateRunnable run finish. {}ms", stopWatch.getTime());
                Thread.sleep(waitMillis);
            } catch (Exception ex) {
                log.error("compensateRunnable run error.", ex);
                Thread.sleep(waitMillis);
            }
        }
    }

}

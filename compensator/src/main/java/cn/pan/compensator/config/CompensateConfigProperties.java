package cn.pan.compensator.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.NestedConfigurationProperty;

@ConfigurationProperties(prefix = "compensate")
@Data
public class CompensateConfigProperties {

    /**
     * 补偿开关
     */
    public Boolean enabled = Boolean.FALSE;

    /**
     * 补偿表名称
     */
    private String tableName;

    /**
     * 序列化方式
     */
    private String serializeType;

    /**
     * 一次拉取补偿个数
     */
    private Integer compensateSize = 1000;

    /**
     * 处理悬挂超时时间
     */
    private Integer suspendTimeOut = 600;

    /**
     * 补偿的目标PSM
     */
    private String targetPSM;

    /**
     * 内置补偿任务
     */
    @NestedConfigurationProperty
    private ScheduledJob scheduledJob = new ScheduledJob();

    @Data
    public static class ScheduledJob {
        /**
         * 内置补偿任务
         */
        public Boolean enabled = Boolean.FALSE;

        /**
         * 任务间隔时间
         */
        public Integer waitSeconds = 10;

        /**
         * redis template
         */
        public String redisTemplate;

        /**
         * redis lock prefix key
         */
        public String redisPrefixKey;
    }
}

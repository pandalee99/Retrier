package cn.pan.compensator.pojo;


import cn.pan.compensator.enums.CompensateStatus;
import lombok.Data;

import java.io.Serializable;


@Data
public class Compensate implements Serializable {

    /**
     * id
     */
    private Long id;

    /**
     * 链路id
     */
    private String traceId;

    /**
     * 补偿状态
     */
    private CompensateStatus compensateStatus;

    /**
     * 补偿方法信息（这个字段会持久化）
     */
    private InvocationContext compensateContext;

    /**
     * 当前方法信息（这个字段不会持久化）
     */
    private InvocationContext currentInvocationContext;

    /**
     * 重试次数
     */
    private Integer retryCount;

    /**
     * 最大重试次数
     */
    private Integer maxRetryCount;

    /**
     * 创建时间
     */
    private Long createTime;

    /**
     * 修改时间
     */
    private Long modifyTime;

    /**
     * 是否异步
     */
    private boolean async;

    /**
     * 下次补偿时间
     */
    private Long nextRetryTime;
}

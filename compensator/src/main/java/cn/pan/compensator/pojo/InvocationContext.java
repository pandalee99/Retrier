package cn.pan.compensator.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class InvocationContext implements Serializable {
    /**
     * 目标class
     */
    private Class<?> targetClass;

    /**
     * 方法
     */
    private String methodName;

    /**
     * 参数类型
     */
    private Class<?>[] parameterTypes;

    /**
     * 参数
     */
    private Object[] args;

    /**
     * 补偿信息
     */
    private BackOff backOff;

    /**
     * psm
     */
    private String targetPSM;

    /**
     * 扩展字段
     */
    private ExtrasMap extras;

}

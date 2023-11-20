package cn.pan.compensator.annotation;

import org.apache.commons.lang3.StringUtils;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.METHOD })
public @interface Compensable {

    /**
     * 补偿次数
     *
     * @return
     */
    int retryCount() default Integer.MAX_VALUE;

    /**
     * 补偿方法
     *
     * @return
     */
    String compensateMethod() default StringUtils.EMPTY;

    /**
     * 是否异步
     *
     * @return
     */
    boolean async() default false;

    /**
     * 重试延迟时间
     *
     * @return
     */
    long delay() default 0;

    /**
     * 乘数，用于获取下一次延迟时间
     *
     * @return
     */
    double multiplier() default 1;

    /**
     * 最大延迟时间，如果获取的延迟时间大于maxDelay，则取maxDelay为延迟时间
     *
     * @return
     */
    long maxDelay() default Long.MAX_VALUE;

    /**
     * 初始化补偿信息时同步执行
     *
     * @return
     */
    String initHandle() default StringUtils.EMPTY;

    /**
     * 补偿前调用
     *
     * @return
     */
    String preHandle() default StringUtils.EMPTY;

    /**
     * 补偿后调用
     *
     * @return
     */
    String postHandle() default StringUtils.EMPTY;

    /**
     * 异常后调用
     *
     * @return
     */
    String afterThrow() default StringUtils.EMPTY;
}

package cn.pan.compensator.interceptor;

public interface CompensateInterceptor {

    /**
     * 初始化补偿信息时同步执行
     */
    default void initHandle(CompensateContext context){}

    /**
     * 补偿开始前
     */
    default void preHandle(CompensateContext context){}

    /**
     * 补偿完成后
     */
    default void postHandle(CompensateContext context){}

    /**
     * 补偿执行异常
     */
    default void afterThrow(CompensateContext context, Throwable throwable){}
}

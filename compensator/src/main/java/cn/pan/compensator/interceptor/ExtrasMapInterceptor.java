package cn.pan.compensator.interceptor;

import cn.pan.compensator.context.CompensateContext;

public interface ExtrasMapInterceptor extends CompensateInterceptor{


    @Override
    default void initHandle(CompensateContext context){
        initHandle(context.getCompensate().getCompensateContext().getExtras());
    }


    @Override
    default void preHandle(CompensateContext context){
        preHandle(context.getCompensate().getCompensateContext().getExtras());
    }


    @Override
    default void postHandle(CompensateContext context){
        postHandle(context.getCompensate().getCompensateContext().getExtras());
    }


    @Override
    default void afterThrow(CompensateContext context, Throwable throwable){
        afterThrow(context.getCompensate().getCompensateContext().getExtras(), throwable);
    }

    /**
     * 初始化补偿信息时同步执行
     */
    default void initHandle(ExtrasMap extrasMap){}

    /**
     * 补偿开始前
     */
    default void preHandle(ExtrasMap extrasMap){}

    /**
     * 补偿完成后
     */
    default void postHandle(ExtrasMap extrasMap){}

    /**
     * 补偿执行异常
     */
    default void afterThrow(ExtrasMap extrasMap, Throwable throwable){}
}

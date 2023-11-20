package cn.pan.compensator.context;



import cn.pan.compensator.pojo.Compensate;

import java.util.ArrayList;
import java.util.List;


public class CompensateContextHolder {
    private static final ThreadLocal<List<Compensate>> currentCompensateList = new ThreadLocal<>();

    private static final ThreadLocal<Boolean> compensableFlag = new ThreadLocal<>();

    private static final ThreadLocal<Integer> compensateTimes = new ThreadLocal<>();

    private static String targetPSM;

    /**
     * 注册补偿信息
     * 
     * @param compensate
     */
    public static void register(Compensate compensate) {
        if (currentCompensateList.get() == null) {
            currentCompensateList.set(new ArrayList<>());
        }
        currentCompensateList.get().add(compensate);
    }

    /**
     * 获取补偿信息列表
     * 
     * @return
     */
    public static List<Compensate> getCurrentCompensateList() {
        return currentCompensateList.get();
    }

    /**
     * 清除补偿信息列表
     */
    public static void clearCurrentCompensateList() {
        currentCompensateList.remove();
    }

    /**
     * 激活补偿标记
     */
    public static void activeCompensble() {
        compensableFlag.set(Boolean.TRUE);
    }

    /**
     * 激活补偿标记，携带重试次数
     */
    public static void activeCompensble(Integer retryTimes) {
        compensableFlag.set(Boolean.TRUE);
        compensateTimes.set(retryTimes);
    }

    /**
     * 获取补偿次数<br/>
     * 0代表第一次执行，不是重试
     * 
     * @return
     */
    public static Integer getCompensateTimes() {
        return compensateTimes.get() == null ? 0 : compensateTimes.get();
    }

    /**
     * 判断当前是否处于重试 第一次默认非重试
     * 
     * @return
     */
    public static boolean isRetry() {
        return compensateTimes.get() != null && compensateTimes.get() > 0;
    }

    /**
     * 判断当时是否处于补偿
     * 
     * @return
     */
    public static boolean isCompensable() {
        return compensableFlag.get() != null && compensableFlag.get();
    }

    /**
     * 清除补偿标记
     */
    public static void clearCompensableFlag() {
        compensableFlag.remove();
        compensateTimes.remove();
    }

    public static void setTargetPSM(String psm){
        targetPSM = psm;
    }

    public static String getTargetPSM(){
        return targetPSM;
    }
}

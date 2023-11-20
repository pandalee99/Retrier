package cn.pan.compensator.enums;


public enum CompensateStatus implements IEnum<Integer, String> {

    INIT(0, "初始化"),

    DOING(1, "补偿中"),

    FAIL(2, "补偿失败，将重新补偿");

    private final Integer code;

    private final String desc;

    CompensateStatus(Integer code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public static CompensateStatus getByCode(Integer code) {
        for (CompensateStatus status: CompensateStatus.values()) {
            if (status.getCode().equals(code)) {
                return status;
            }
        }
        return null;
    }

    @Override
    public Integer getCode() {
        return code;
    }

    @Override
    public String getDesc() {
        return desc;
    }
}

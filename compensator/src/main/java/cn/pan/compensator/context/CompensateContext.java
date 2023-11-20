package cn.pan.compensator.context;


import cn.pan.compensator.pojo.Compensate;
import lombok.AllArgsConstructor;
import lombok.Getter;


@Getter
@AllArgsConstructor
public class CompensateContext {

    private Compensate compensate;

    private Object target;

}

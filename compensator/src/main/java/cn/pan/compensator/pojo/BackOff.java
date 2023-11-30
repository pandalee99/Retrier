package cn.pan.compensator.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BackOff implements Serializable {

    /**
     * 补偿延迟时间
     */
    private Long delay;

    /**
     * 乘数，用于获取下一次延迟时间
     */
    private Double multiplier;

    /**
     * 最大延迟时间，如果获取的延迟时间大于maxDelay，则取maxDelay为延迟时间
     */
    private Long maxDelay;

}

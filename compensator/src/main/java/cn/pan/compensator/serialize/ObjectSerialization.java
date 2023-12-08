package cn.pan.compensator.serialize;

/**
 * 序列化接口
 */
public interface ObjectSerialization {

    /**
     * 序列化
     * 
     * @param obj
     * @return
     */
    byte[] serialize(Object obj);

    /**
     * 反序列化
     * 
     * @param bytes
     * @param clazz
     * @param <T>
     * @return
     */
    <T> T deserialize(byte[] bytes, Class<T> clazz);

}

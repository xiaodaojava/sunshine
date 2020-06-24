package red.lixiang.tools.common.mybatis;

/**
 * @author lixiang
 * @date 2020/6/23
 **/
public class ClassHolder {
    Class<?> clazz ;

    public Class<?> getClazz() {
        return clazz;
    }

    public ClassHolder setClazz(Class<?> clazz) {
        this.clazz = clazz;
        return this;
    }
}

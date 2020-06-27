package red.lixiang.tools.spring.processor;

import red.lixiang.tools.common.mybatis.model.BaseQC;

/**
 * 这个类主要是为了给CURD默认的方法添加前置或者后置的逻辑
 * @author lixiang
 * @date 2020/6/27
 **/
public interface SimpleProcessor {

    /**
     * 在保存之前的操作
     * @param target
     */
    default  void beforeSimpleSave(Object target){

    }

    /**
     * 在保存之后的操作
     * @param target
     * @return
     */
    default  Object afterSimpleSave(Object target){
        return target;
    }

    /**
     * 在findByQuery之前的操作
     * @param query
     */
    default void beforeSimpleQuery(BaseQC query){

    }



}

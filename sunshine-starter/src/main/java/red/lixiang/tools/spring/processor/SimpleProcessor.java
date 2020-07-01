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
    default  Object beforeSimpleSave(Object target){
        return target;
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
    default Object beforeSimpleQuery(BaseQC query){
        return query;
    }

    /**
     * 在findByQuery之后的操作
     * @param object
     * @return
     */
    default Object afterSimpleQuery(Object object){
        return object;
    }

    default Object beforeSimpleGet(Object object){
        return object;
    }

    default Object afterSimpleGet(Object object){
        return object;
    }

    default Object beforeSimpleRemove(Object obj){
        return obj;
    }

    default Object afterSimpleRemove(Object obj){
        return obj;
    }

    default Object beforeSimpleRemoveByQuery(Object obj){
        return obj;
    }

    default Object afterSimpleRemoveByQuery(Object obj){
        return obj;
    }



}

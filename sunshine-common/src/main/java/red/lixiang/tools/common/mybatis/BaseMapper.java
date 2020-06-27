package red.lixiang.tools.common.mybatis;

import org.apache.ibatis.annotations.*;
import red.lixiang.tools.common.mybatis.model.BaseQC;

import java.util.List;
import java.util.Map;

/**
 * @author lixiang
 * @date 2020/6/23
 **/
public interface BaseMapper<T> {

    /**
     * 需要子类重写的返回,返回当前的Mapper.class
     *
     * @return
     */
     Class<?> getMapperClass();

    /**
     * 查找全部
     *
     * @see BaseProvider#findAll(Class)
     */
    @SelectProvider(type = BaseProvider.class)
    List<T> findAll(Class<?> tClass);

    default List<T> findAll() {
        return this.findAll(getMapperClass());
    }


    /**
     * 通过ID查找
     * @see BaseProvider#findById(Long, Class)
     * @param id
     * @return
     */
    @SelectProvider(type = BaseProvider.class)
    T findById(@Param("id") Long id, Class<?> tClass);

    default T findById(Long id) {
        return this.findById(id, getMapperClass());
    }


    /**
     * 通过条件查询
     * @see BaseProvider#findByQuery(BaseQC)
     * @param qc
     * @return
     */
    @SelectProvider(type = BaseProvider.class)
    List<T> findByQuery(BaseQC qc);

    /**
     * 通过条件查询数量
     * @see BaseProvider#countByQuery(BaseQC)
     * @param qc
     * @return
     */
    @SelectProvider(type = BaseProvider.class)
    Long countByQuery(BaseQC qc);


    /**
     * 插入
     * @see BaseProvider#insert(Object)
     * @param t
     * @return
     */
    @InsertProvider(type = BaseProvider.class)
    int insert(T t);

    /**
     * 更新,只根据id更新
     * @see BaseProvider#update(Object)
     * @param t
     * @return
     */
    @UpdateProvider(type = BaseProvider.class)
    int update(T t);

    /**
     * 通过ID进行删除
     * @see BaseProvider#removeById(long, Class)
     * @param id
     * @return
     */
    @DeleteProvider(type = BaseProvider.class)
    int removeById(@Param("id")Long id, Class<?> tClass);

    default int removeById(Long id){
        return removeById(id,getMapperClass());
    }

    /**
     * 通过查询条件删除
     *
     * @param qc
     * @return
     */
    @DeleteProvider(type = BaseProvider.class)
    int removeByQuery(BaseQC qc);

    /**
     * 任意sql查询一个
     * @see BaseProvider#selectOne(String)
     * @param sql
     * @return
     */
    @SelectProvider(type =  BaseProvider.class)
    T selectOne(String sql);

    /**
     * 任意sql查询多个
     * @see BaseProvider#selectList (String)
     * @param sql
     * @return
     */
    @SelectProvider(type =  BaseProvider.class)
    List<T> selectList(String sql);

}

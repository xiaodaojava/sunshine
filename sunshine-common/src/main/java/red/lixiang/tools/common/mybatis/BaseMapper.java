package red.lixiang.tools.common.mybatis;

import red.lixiang.tools.common.mybatis.model.BaseQC;

import java.util.List;

/**
 * @author lixiang
 * @date 2020/6/23
 **/
public interface BaseMapper<T> {
    /**
     * 查找全部
     */
    List<T> findAll();

    /**
     * 通过ID查找
     * @param id
     * @return
     */
    T findById(Long id);

    /**
     * 通过条件查询
     * @param qc
     * @return
     */
    T findByQuery(BaseQC qc);

    /**
     * 保存,如果ID存在则更新,不存在则新增
     * @param t
     * @return
     */
    T save(T t);

    /**
     * 通过ID进行删除
     * @param id
     * @return
     */
    int removeById(Long id);

    /**
     * 通过查询条件删除
     * @param qc
     * @return
     */
    int removeByQuery(BaseQC qc);
}

package red.lixiang.tools.common.mybatis.model;

import java.io.Serializable;
import java.util.Date;

/**
 * Query Condition 查询条件， 用于数据库条件查询
 * @Author lixiang
 * @CreateTime 01/04/2018
 **/
public class BaseQC implements Serializable {

    @QC(skipRich = true)
    private static final long serialVersionUID = 3128244594458652839L;

    /** 排序条件 */
    private Sort sort;

    /** 分页条件 */
    private Page page;

    /** 创建时间 */
    private Date createTime;

    /** 更新时间 */
    private Date updateTime;

    public Sort getSort() {
        return sort;
    }

    public BaseQC setSort(Sort sort) {
        this.sort = sort;
        return this;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public BaseQC setCreateTime(Date createTime) {
        this.createTime = createTime;
        return this;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public BaseQC setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
        return this;
    }


    public Page getPage() {
        return page;
    }

    public BaseQC setPage(Page page) {
        this.page = page;
        return this;
    }
}

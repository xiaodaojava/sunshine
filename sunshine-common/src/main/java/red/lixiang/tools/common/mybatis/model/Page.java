package red.lixiang.tools.common.mybatis.model;

import java.io.Serializable;

/**
 * 用于QC中对数据库的查询
 * 如果需要分页查询，那么就让QC继承这个类
 * @Author lixiang
 * @CreateTime 01/04/2018
 **/
public class Page implements Serializable {

    private static final long serialVersionUID = 1887192532333759387L;


    /** 只查询一个的 */
    private static final Page ONE = new Page(1,1);

    /**
     * 页码，第几页，从1起 【page】
     */
    private Integer pageIndex;

    /**
     * 每页显示条数【rows】
     */
    private Integer pageSize;

    /**
     * 起始行号 limit startIndex,pageSize
     */
    private Integer startIndex = 0;

    public Page() {
    }

    public Page(Integer pageIndex, Integer pageSize) {
        this.pageIndex = pageIndex;
        this.pageSize = pageSize;
    }

    public Integer getPageIndex() {
        return pageIndex;
    }

    public void setPageIndex(Integer pageIndex) {
        this.pageIndex = pageIndex;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public void setStartIndex(Integer startIndex) {
        this.startIndex = startIndex;
    }

    public Integer getStartIndex() {
        if (this.pageIndex != null && this.pageSize != null) {
            if(this.pageIndex <1){
                this.pageIndex = 1;
            }
            return (this.pageIndex - 1) * this.pageSize;
        }
        return startIndex;
    }

    public static Page getOne(){
        return ONE;
    }

    public static void main(String[] args) {
        Page page = new Page(2,10);
        System.out.println(page.getStartIndex());
    }
}

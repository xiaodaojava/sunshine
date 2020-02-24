package red.lixiang.tools.base;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * 用于返回值中，如果返回值是个带分页的list，用这个类包装之后再从Controller中返回
 *
 * @author lixiang
 * @date 2018/2/28
 */
public class PageData<E> implements Serializable {
    
    private static final long serialVersionUID = -3068829537767185296L;

    public static final int PAGE_INDEX = 1;

    public static final int PAGE_SIZE = 20;


    /**
     * 页码，第几页，从1起 【page】
     */
    private Integer pageIndex;
    /**
     * 每页显示条数【rows】
     */
    private Integer pageSize ;

    /**
     * 总记录数【total】
     */
    private Long totalCount;

    /**
     * key:数据库排序字段名
     * value:页面grid表头文案
     * eg：{"update_time":"更新时间","stock_qty":"库存数量"}
     */
    private Map<String, String> sortByMap;

    private List<E> dataList;


    public PageData() {
    }

    public PageData(Integer pageIndex, Integer pageSize, Long totalCount, List<E> dataList) {
        this.pageIndex = pageIndex;
        this.pageSize = pageSize;
        this.totalCount = totalCount;
        this.dataList = dataList;
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

    public Long getTotalCount() {
        return totalCount;
    }

    public PageData<E> setTotalCount(Long totalCount) {
        this.totalCount = totalCount;
        return this;
    }

    public Map<String, String> getSortByMap() {
        return sortByMap;
    }

    public void setSortByMap(Map<String, String> sortByMap) {
        this.sortByMap = sortByMap;
    }

    public List<E> getDataList() {
        return dataList;
    }

    public void setDataList(List<E> dataList) {
        this.dataList = dataList;
    }
}

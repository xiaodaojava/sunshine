package red.lixiang.tools.common.mybatis.model;


import red.lixiang.tools.jdk.StringTools;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 *
 * 数据库中用于描述排序的对象
 *
 * @Author lixiang
 * @CreateTime 2019/8/31
 **/
public class Sort implements Serializable {

    private static final long serialVersionUID = 1074959734171416323L;

    /** 考虑到controller入参是QC, 可以从Controller里面直接传排序的操作 */
    private String initSort;

    /**  考虑到多种排序的可能  */
    private List<String> sorts = new ArrayList<>(5);

    public String[] getSortAll(){
       return sorts.toArray(new String[0]);
    }

    /**
     * 插入的示例: update_time desc,id asc
     * 中间使用,分隔
     * @param sort
     * @return
     */
    public Sort addSort(String sort) {
        sort = StringTools.camel2UnderScope(sort);
        sorts.addAll(Arrays.asList(sort.split(",")));
        return this;
    }

    public String getInitSort() {
        return initSort;
    }

    public Sort setInitSort(String initSort) {
        this.initSort = initSort;
        sorts.add(initSort);
        return this;
    }
}

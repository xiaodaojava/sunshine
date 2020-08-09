package red.lixiang.tools.spring.controller;

/**
 * @author lixiang
 * @date 2020/7/3
 **/
public class TableColumn {

    /** java实体类中的字段名 */
    private String field;

    /** 表格的表头 */
    private String title;

    /** 是不是可编辑的 */
    private Boolean editable ;

    private String cellStyle;




    public TableColumn() {
    }

    public String getCellStyle() {
        return cellStyle;
    }

    public TableColumn setCellStyle(String cellStyle) {
        this.cellStyle = cellStyle;
        return this;
    }

    public TableColumn(String field, String title) {
        this.field = field;
        this.title = title;
    }

    public Boolean getEditable() {
        return editable;
    }

    public TableColumn setEditable(Boolean editable) {
        this.editable = editable;
        return this;
    }

    public String getField() {
        return field;
    }

    public TableColumn setField(String field) {
        this.field = field;
        return this;
    }

    public String getTitle() {
        return title;
    }

    public TableColumn setTitle(String title) {
        this.title = title;
        return this;
    }
}

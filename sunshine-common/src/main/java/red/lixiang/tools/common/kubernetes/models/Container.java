package red.lixiang.tools.common.kubernetes.models;

/**
 * @author lixiang
 * @date 2020/6/10
 **/
public class Container {
    private String image;
    private String name;
    private boolean selected;

//    private List<>


    public boolean isSelected() {
        return selected;
    }

    public Container setSelected(boolean selected) {
        this.selected = selected;
        return this;
    }

    public String getImage() {
        return image;
    }

    public Container setImage(String image) {
        this.image = image;
        return this;
    }

    public String getName() {
        return name;
    }

    public Container setName(String name) {
        this.name = name;
        return this;
    }
}

package red.lixiang.tools.base;

/**
 * 这个适用于关联关系的那种
 * @author lixiang
 * @date 2020/8/29
 **/
public class Pair {

    private Long id;
    private Long sourceId;
    private String sourceName;
    private Long targetId;
    private String targetName;

    public Pair() {
    }

    public Pair(Long sourceId, Long targetId) {
        this.sourceId = sourceId;
        this.targetId = targetId;
    }

    public Long getId() {
        return id;
    }

    public Pair setId(Long id) {
        this.id = id;
        return this;
    }

    public Long getSourceId() {
        return sourceId;
    }

    public Pair setSourceId(Long sourceId) {
        this.sourceId = sourceId;
        return this;
    }

    public String getSourceName() {
        return sourceName;
    }

    public Pair setSourceName(String sourceName) {
        this.sourceName = sourceName;
        return this;
    }

    public Long getTargetId() {
        return targetId;
    }

    public Pair setTargetId(Long targetId) {
        this.targetId = targetId;
        return this;
    }

    public String getTargetName() {
        return targetName;
    }

    public Pair setTargetName(String targetName) {
        this.targetName = targetName;
        return this;
    }
}

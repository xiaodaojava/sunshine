package red.lixiang.tools.base;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

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
        System.out.println();
        System.err.println();
        System.out.println("Pair.Pair");
        System.out.println();
        System.out.println("id = " + id);
//        System.out.printf("")
//        System.err.print();
        try {
            System.in.read("aaa".getBytes(StandardCharsets.UTF_8));
        } catch (IOException e) {
            e.printStackTrace();
        }
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

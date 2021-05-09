package red.lixiang.tools.common.jianyingpro.models;

import com.alibaba.fastjson.annotation.JSONField;

import java.util.List;

public class DraftContent {

    @JSONField(name = "materials")
    private Material material;

    private List<Track> tracks;

    public Material getMaterial() {
        return material;
    }

    public DraftContent setMaterial(Material material) {
        this.material = material;
        return this;
    }

    public List<Track> getTracks() {
        return tracks;
    }

    public DraftContent setTracks(List<Track> tracks) {
        this.tracks = tracks;
        return this;
    }
}

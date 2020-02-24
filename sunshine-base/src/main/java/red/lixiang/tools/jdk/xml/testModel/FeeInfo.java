package red.lixiang.tools.jdk.xml.testModel;

import red.lixiang.tools.jdk.xml.XmlField;

/**
 * @author lixiang
 * @date 2020/2/3
 **/
public class FeeInfo {

    @XmlField(xmlEle = "ake005")
    private String ake1;
    @XmlField(xmlEle = "ake006")
    private String ake2;
    @XmlField(xmlEle = "aaz213")
    private String aaz2;

    public String getAke1() {
        return ake1;
    }

    public FeeInfo setAke1(String ake1) {
        this.ake1 = ake1;
        return this;
    }

    public String getAke2() {
        return ake2;
    }

    public FeeInfo setAke2(String ake2) {
        this.ake2 = ake2;
        return this;
    }

    public String getAaz2() {
        return aaz2;
    }

    public FeeInfo setAaz2(String aaz2) {
        this.aaz2 = aaz2;
        return this;
    }
}

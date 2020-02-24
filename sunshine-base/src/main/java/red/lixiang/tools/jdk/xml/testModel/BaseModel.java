package red.lixiang.tools.jdk.xml.testModel;

import red.lixiang.tools.jdk.xml.XmlField;

/**
 * @author lixiang
 * @date 2020/2/3
 **/
@XmlField(xmlRoot = "program")
public class BaseModel {
    @XmlField(xmlEle = "session_id")
    private String sessionId;
    @XmlField(xmlEle = "function_id")
    private String functionId;
    @XmlField(xmlEle = "akb020")
    private String akb;
    @XmlField(xmlEle = "aaz218")
    private String aaz;
    @XmlField(xmlEle = "bka014")
    private String bka;
    @XmlField(xmlEle = "bka015")
    private String bka2;
    @XmlField(xmlEle = "bka893")
    private String bka3;

    @XmlField(xmlEle = "feeinfo",row = "row")
    private FeeInfo feeInfo;

    public String getSessionId() {
        return sessionId;
    }

    public BaseModel setSessionId(String sessionId) {
        this.sessionId = sessionId;
        return this;
    }

    public String getFunctionId() {
        return functionId;
    }

    public BaseModel setFunctionId(String functionId) {
        this.functionId = functionId;
        return this;
    }

    public String getAkb() {
        return akb;
    }

    public BaseModel setAkb(String akb) {
        this.akb = akb;
        return this;
    }

    public String getAaz() {
        return aaz;
    }

    public BaseModel setAaz(String aaz) {
        this.aaz = aaz;
        return this;
    }

    public String getBka() {
        return bka;
    }

    public BaseModel setBka(String bka) {
        this.bka = bka;
        return this;
    }

    public String getBka2() {
        return bka2;
    }

    public BaseModel setBka2(String bka2) {
        this.bka2 = bka2;
        return this;
    }

    public String getBka3() {
        return bka3;
    }

    public BaseModel setBka3(String bka3) {
        this.bka3 = bka3;
        return this;
    }

    public FeeInfo getFeeInfo() {
        return feeInfo;
    }

    public BaseModel setFeeInfo(FeeInfo feeInfo) {
        this.feeInfo = feeInfo;
        return this;
    }
}

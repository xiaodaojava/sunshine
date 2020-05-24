package red.lixiang.tools.jdk.file;

import java.io.Serializable;

/**
 * 准备拆分文件传输用
 * @author lixiang
 * @date 2020/5/22
 **/
public class FilePart implements Serializable {


    private static final long serialVersionUID = -4493314497035488285L;

    public static final int ONE_KB = 1024;
    public static final int ONE_MB = 1024*ONE_KB;
    public static final int TEN_MB = 10*ONE_MB;
    public static final int FIFTY_MB = 5*TEN_MB;
    public static final int SIXTY_MB = 6*TEN_MB;

    /** 文件名称 */
    private String fileName;

    /** 文件的总大小 */
    private Long totalLength;

    /** 文件总分块数 */
    private Integer totalPart;

    /** 文件的当前的块数 */
    private Integer currentPart;

    /** 当前文件的大小数 */
    private Integer currentLength;


    /** 当前文件的所带的真正的内容 */
    private byte[] contents;

    public Integer getCurrentPart() {
        return currentPart;
    }

    public FilePart setCurrentPart(Integer currentPart) {
        this.currentPart = currentPart;
        return this;
    }

    public Integer getCurrentLength() {
        return currentLength;
    }

    public String getFileName() {
        return fileName;
    }

    public FilePart setFileName(String fileName) {
        this.fileName = fileName;
        return this;
    }

    public Long getTotalLength() {
        return totalLength;
    }

    public FilePart setTotalLength(Long totalLength) {
        this.totalLength = totalLength;
        return this;
    }

    public Integer getTotalPart() {
        return totalPart;
    }

    public FilePart setTotalPart(Integer totalPart) {
        this.totalPart = totalPart;
        return this;
    }


    public FilePart setCurrentLength(Integer currentLength) {
        this.currentLength = currentLength;
        return this;
    }

    public byte[] getContents() {
        return contents;
    }

    public FilePart setContents(byte[] contents) {
        this.contents = contents;
        return this;
    }

}

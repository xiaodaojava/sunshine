package red.lixiang.tools.jdk.file;

import red.lixiang.tools.base.exception.BusinessException;
import red.lixiang.tools.jdk.ByteTools;
import red.lixiang.tools.jdk.io.IOTools;
import red.lixiang.tools.jdk.os.OSTools;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.channels.SeekableByteChannel;
import java.nio.file.*;
import java.util.Base64;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static red.lixiang.tools.jdk.file.FilePart.FIFTY_MB;

/**
 * @Author lixiang
 * @CreateTime 2019-08-02
 **/
public class FileTools {

    /**
     * 获取文件的后缀名,包含 '.'
     * @param fileName
     * @return
     */
    public  static String getSuffixName(String fileName){
        return fileName.substring(fileName.lastIndexOf("."));
    }

    /**
     * 从文件绝对路径中获取文件名
     * @param path
     * @return
     */
    public static String getFileNameFromPath(String path){
        String separator = OSTools.fileSeparator();
        if(!path.contains(separator)){
            return path;
        }else {
            int i = path.lastIndexOf(separator);
            path = path.substring(i+1);
            return path;
        }
    }

    public static String getDirFromPath(String path){
        String separator = OSTools.fileSeparator();
        if(!path.contains(separator)){
            return ".";
        }else {
            int i = path.lastIndexOf(separator);
            path = path.substring(0,i+1);
            return path;
        }
    }



    public  static String getSuffixNameNoDot(String fileName){
        return fileName.substring(fileName.lastIndexOf(".")+1);
    }

    /**
     * 获取文件名
     * @param fileName
     * @return
     */
    public  static String getNameWithoutSuffix(String fileName){
        return fileName.substring(0,fileName.lastIndexOf("."));
    }

    public static String fileBase64Content(File file){
        try {
            FileInputStream inputStream = new FileInputStream(file);
            byte[] bytes = IOTools.readByte(inputStream);
            inputStream.close();
            return Base64.getEncoder().encodeToString(bytes);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    public static String fileBase64Content(String path){
        try {
            byte[] bytes = Files.readAllBytes(Paths.get(path));
            return Base64.getEncoder().encodeToString(bytes);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void deleteDirFiles(String path){
        try {
            Stream<Path> list = Files.list(Paths.get(path));
            list.forEach(x->{
                try {
                    Files.deleteIfExists(x);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public static FilePart partInfoFromFile(File file,Integer readSize){
        FilePart filePart = new FilePart();
        String fileName = file.getName();
        // 文件的总长度
        long totalLength = file.length();
        // 总共有多少段
        int totalPart = (int) (totalLength/readSize)+1;

        filePart.setTotalLength(totalLength)
                .setCurrentLength(readSize)
                .setTotalPart(totalPart)
                .setCurrentPart(0)
                .setFileName(fileName);
        return filePart;
    }

    /**
     *
     * @param filePath  文件路径
     * @param startPart  从第几个分段开始读,初始是1
     * @param readSize  每次读多少个大小
     */
    public static FilePart readToFilePart(String filePath,Integer startPart,Integer readSize){
        FilePart filePart = new FilePart();
        File file = new File(filePath);
        return readToFilePart(file, startPart, readSize);
    }

    public static FilePart readToFilePart(File file,Integer startPart,Integer readSize){
        FilePart filePart = new FilePart();

        String fileName = file.getName();
        // 文件的总长度
        long totalLength = file.length();
        // 总共有多少段
        int totalPart = (int) (totalLength/readSize)+1;
        if(startPart>totalPart){
            return null;
        }
        // 开始的位置
        long startIndex = (startPart-1)*readSize;
        filePart.setTotalLength(totalLength)
                .setTotalPart(totalPart)
                .setCurrentPart(startPart)
                .setFileName(fileName);

        byte[] bytes;
        if(startPart == totalPart){
            bytes = new byte[(int) (totalLength-startIndex)];
        }else {
            bytes = new byte[readSize];
        }
        filePart.setCurrentLength(bytes.length);
        try (RandomAccessFile randomFile = new RandomAccessFile(file, "r")) {
            randomFile.seek(startIndex);
            randomFile.read(bytes);
            filePart.setContents(bytes);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return filePart;
    }

    public static void mergeFilePart(String workDir){
        String descFile = workDir+"desc.hbb";
        if(Files.notExists(Paths.get(descFile))){
            throw new BusinessException("缺少描述文件:desc.hbb");
        }
        try {
            byte[] bytes = Files.readAllBytes(Paths.get(descFile));
            FilePart filePart = ByteTools.byteToObject(bytes, FilePart.class);
            String fileName = filePart.getFileName();
            String filePath = workDir+fileName;
//            File file = new File(workDir+fileName);
//            RandomAccessFile randomFile = new RandomAccessFile(file,"rw");

            SeekableByteChannel byteChannel = Files.newByteChannel(Paths.get(filePath), StandardOpenOption.CREATE,StandardOpenOption.WRITE,StandardOpenOption.APPEND);
            //获取当前目录下的所有x.hbb文件,并排好序
            long count = Files.list(Paths.get(workDir)).filter(x ->
                    x.getFileName().toString().endsWith(".hbb")
                            &&!(x.getFileName().toString().contains("desc")))
                    .sorted().map(x -> {
                        try {
                            byte[] allBytes = Files.readAllBytes(x);
                            FilePart part = ByteTools.byteToObject(allBytes, FilePart.class);
                            byte[] contents = part.getContents();
                            byteChannel.write(ByteBuffer.wrap(contents));
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        return null;
                    }).count();
            System.out.println(count);
            byteChannel.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        mergeFilePart("/Users/lixiang/Desktop/split/");
    }

    /**
     *
     * 把一个文件拆分成多个
     *
     * @param filePath
     */
    public static void splitFile(String filePath) {
        //String filePath = "/Users/lixiang/Desktop/testPDF.pdf";
        File file = new File(filePath);
        String dirFromPath = FileTools.getDirFromPath(filePath);
        String workDir = dirFromPath+"/split/";
        FilePart filePart = partInfoFromFile(file, FIFTY_MB);
        try {
            Files.write(Paths.get(workDir+"desc.hbb"),ByteTools.objectToByte(filePart));
        } catch (IOException e) {
            e.printStackTrace();
        }
        Integer totalPart = filePart.getTotalPart();
        for (int i = 0; i < totalPart; i++) {
            FilePart part = readToFilePart(file, i+1, FIFTY_MB);
            if (part==null){
                continue;
            }
            //先单独输出
            byte[] bytes = ByteTools.objectToByte(part);
            try {
                Files.createDirectories(Paths.get(workDir));
                Files.write(Paths.get(workDir + part.getCurrentPart() + ".hbb"),bytes,StandardOpenOption.CREATE );
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}

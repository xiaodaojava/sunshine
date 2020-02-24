package red.lixiang.tools.base;

import java.io.Serializable;
import java.util.List;

/**
 * @author lixiang
 * @date 2018/04/02
 */
public class BaseResponse<T> implements Serializable {


    private static final long serialVersionUID = -6707681616003589614L;
    private boolean result = true;

    private String msg;
    private String code;
    /**
     *  data返回list时T需要继承 PageData
     *  data为单个vo时，T不需要继承 PageData
     */
    private T data;

    public static  <T> BaseResponse<T> assembleResponse(T data, String message, boolean success) {
        BaseResponse<T> baseResponse = new BaseResponse<T>();
        baseResponse.setData(data);
        baseResponse.setMsg(message);
        baseResponse.setResult(success);
        return baseResponse;
    }
    public static  <T> BaseResponse<T> assembleResponse(T data, String message, String messageCode, boolean success) {
        BaseResponse<T> baseResponse = new BaseResponse<T>();
        baseResponse.setData(data);
        baseResponse.setMsg(message);
        baseResponse.setResult(success);
        baseResponse.setCode(messageCode);
        return baseResponse;
    }


    public static  <T> BaseResponse<T> assembleResponse(boolean success, String messageCode, String message) {
        BaseResponse<T> baseResponse = new BaseResponse<T>();
        baseResponse.setMsg(message);
        baseResponse.setResult(success);
        baseResponse.setCode(messageCode);
        return baseResponse;
    }




    public static  <T> BaseResponse<T> assembleResponse(T data, boolean success) {
        return assembleResponse(data, "", success);
    }

    public static  <T> BaseResponse<T> assembleResponse(T data) {
        return assembleResponse(data, true);
    }

    public static  <T> BaseResponse<PageData<T>> assemblePageResponse(List<T> data, Long totalCount, Integer pageIndex, Integer pageSize) {
        PageData<T> pageData = new PageData<T>(pageIndex, pageSize, totalCount, data);
        return assembleResponse(pageData);
    }



    public static  <T> BaseResponse<T> success(T data) {
        return assembleResponse(data, "", true);
    }
    public static  <T> BaseResponse<T> fail(String message) {
        return new BaseResponse<T>(false, message);
    }

    public static  <T> BaseResponse<T> fail(String code,String message) {
        return new BaseResponse<T>(false, message,code);
    }



    public BaseResponse() {
    }

    public BaseResponse(boolean result, T data) {
        this.result = result;
        this.data = data;
    }


   public BaseResponse(boolean result, String msg) {
        this.result = result;
        this.msg = msg;
    }

    public BaseResponse(boolean result, String msg, String code) {
        this.result = result;
        this.msg = msg;
        this.code = code;
    }

    public BaseResponse(boolean result, String msg, T data) {
        this.result = result;
        this.msg = msg;
        this.data = data;
    }

    public boolean isResult() {
        return result;
    }

    public void setResult(boolean result) {
        this.result = result;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public String getCode() {
        return code;
    }

    public BaseResponse<T> setCode(String code) {
        this.code = code;
        return this;
    }
}

package io.github.nunumao.global.response;
// +----------------------------------------------------------------------
// | 官方网站: https://github.com/N7MCoder/n7m-boot
// +----------------------------------------------------------------------
// | 功能描述:
// +----------------------------------------------------------------------
// | 时　　间: 2023-04-10
// +----------------------------------------------------------------------
// | 代码创建: numao <n7mcoder@gmail.com>
// +----------------------------------------------------------------------
// | 版本信息: V 0.0.1
// +----------------------------------------------------------------------
// | 代码修改:（修改人 - 修改时间）
// +----------------------------------------------------------------------

import cn.hutool.json.JSONUtil;

import java.io.Serializable;

public class ResultFormat<T> implements Serializable {

    public final static Integer SUCCESS_CODE = 200;
    public final static Integer ERROR_CODE = 300;

    private Integer code;
    private String message;
    private T result;


    public static <T> ResultFormat<T> success(String message, T data) {
        return ResultFormat.response(ResultFormat.SUCCESS_CODE, message, data);
    }

    public static <T> ResultFormat<T> success(String message) {
        return ResultFormat.success(message, null);
    }

    public static <T> ResultFormat<T> error(String message) {
        return ResultFormat.response(ResultFormat.ERROR_CODE, message, null);
    }

    public static <T> ResultFormat<T> error() {
        return ResultFormat.error("error");
    }

    public static <T> ResultFormat<T> response(int code, String message, T data) {
        ResultFormat<T> result = new ResultFormat<T>();
        result.setCode(code);
        result.setMessage(message);
        if (data == null) {
            data = (T) "";
        }
        result.setResult(data);
        return result;
    }

    public String toJson() {
        return JSONUtil.toJsonStr(ResultFormat.response(code, message, result));
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getResult() {
        return result;
    }

    public void setResult(T result) {
        this.result = result;
    }

}

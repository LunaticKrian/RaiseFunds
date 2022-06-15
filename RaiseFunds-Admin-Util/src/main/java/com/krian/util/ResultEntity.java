package com.krian.util;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * 封装的响应内容
 *
 * @param <T>
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ResultEntity<T> {

    public static final String SUCCESS = "SUCCESS";
    public static final String FAILED = "FAILED";
    public static final String NO_MESSAGE = "NO_MESSAGE";
    public static final String NO_DATA = "NO_DATA";

    /**
     * 返回操作结果为成功，不带数据
     *
     * @return ResultEntity
     */
    public static <E> ResultEntity<E> successWithoutData() {
        return new ResultEntity<E>(SUCCESS, NO_MESSAGE, null);
    }

    /**
     * 返回操作结果为成功，携带数据
     *
     * @param data * @return
     */
    public static <E> ResultEntity<E> successWithData(E data) {
        return new ResultEntity<E>(SUCCESS, NO_MESSAGE, data);
    }

    /**
     * 返回操作结果为失败，不带数据
     *
     * @param message * @return
     */
    public static <E> ResultEntity<E> failed(String message) {
        return new ResultEntity<E>(FAILED, message, null);
    }

    // 用来封装当前请求处理的结果是成功还是失败：
    private String result;

    // 处理请求失败是返回的错误信息：
    private String message;

    // 封装返回响应前端数据：
    private T data;
}

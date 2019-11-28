package com.zuulserver.model.enums;

/**
 * 结果枚举
 *
 * @author f
 * @date 2018-04-23
 */
public enum ResultEnum {
    /**
     * 成功
     */
    SUCCESS(200, "成功！"),
    /**
     * 服务器错误
     */
    ERROR(-1, "服务器错误！"),
    /**
     * 没有相关数据
     */
    NO_DATA(401, "数据不存在！"),
    /**
     * token验证失败，请尝试重新登录
     */
    NO_TOKEN(402, "token验证失败，请尝试重新登录！"),
    /**
     * redis key null
     */
    KEY_NONE(403, "key 不能为空！"),
    /**
     * 熔断
     */
    FALL_BACK(404, "服务异常，触发熔断！"),
    /**
     * 用户名或密码不正确！
     */
    MEMBER_NAME_PASSWORD_ERROR(405, "用户名或密码不正确！"),
    /**
     * 用户未登陆！
     */
    MEMBER_LOGIN_ERROR(406, "用户未登陆！"),
    /**
     * 校验失败
     */
    VALIDATE_ERROR(400, "校验失败！");

    private int code;
    private String msg;

    ResultEnum(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }
}

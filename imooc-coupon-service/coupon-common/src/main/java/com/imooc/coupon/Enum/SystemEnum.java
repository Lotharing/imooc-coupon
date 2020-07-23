package com.imooc.coupon.Enum;


/**
 * @author : LuTong.Zhao
 * @date : 19:48 2020/7/23
 */
public enum SystemEnum {

    SYS_ERROR(-1, "系统错误"),
    SC_UNAUTHORIZED(401, "授权失败"),
    RATE_LIMIT(402, "请求限流"),
    SC_FORBIDDEN(403, "禁止访问"),
    SUCCESS(200, "成功");

    private int code;
    private String desc;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    SystemEnum(int code, String desc) {
        this.code = code;
        this.desc = desc;
    }
}
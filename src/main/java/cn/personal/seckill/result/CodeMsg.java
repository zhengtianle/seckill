package cn.personal.seckill.result;

import lombok.Getter;

/**
 * Created with IntelliJ IDEA.
 *
 * @Time 19-8-5
 * @Author ZhengTianle
 * Description:
 * code
 * msg
 */
@Getter
public class CodeMsg {
    private int code;
    private String msg;

    //通用异常
    public static final CodeMsg SUCCESS=new CodeMsg(0,"success");
    public static final CodeMsg SERVER_ERROR=new CodeMsg(500100,"服务端异常!");
    public static final CodeMsg BIND_ERROR=new CodeMsg(500101,"参数校验异常:%s");
    public static final CodeMsg REQUEST_ILLEAGAL=new CodeMsg(500102,"非法请求!");
    public static final CodeMsg MIAOSHA_FAIL=new CodeMsg(500103,"秒杀失败!");
    public static final CodeMsg ACCESS_LIMIT=new CodeMsg(500104,"达到访问限制次数，访问太频繁!");
    //登录模块异常
    public static final CodeMsg SESSION_ERROR=new CodeMsg(500210,"session失效!");
    public static final CodeMsg PASSWORD_EMPTY=new CodeMsg(500211,"密码不能为空!");
    public static final CodeMsg MOBILE_EMPTY=new CodeMsg(500212,"手机号不能为空!");
    public static final CodeMsg MOBILE_ERROR=new CodeMsg(500213,"手机号格式错误!");
    public static final CodeMsg MOBILE_NOTEXIST=new CodeMsg(500214,"手机号号码不存在!");
    public static final CodeMsg PASSWORD_ERROR=new CodeMsg(500215,"密码错误!");

    //订单模块异常
    public static final CodeMsg ORDER_NOT_EXIST=new CodeMsg(500410,"订单不存在!");
    //秒杀模块异常
    public static final CodeMsg MIAOSHA_OVER_ERROR=new CodeMsg(500500,"商品秒杀完毕，库存不足!");
    public static final CodeMsg REPEATE_MIAOSHA=new CodeMsg(500500,"不能重复秒杀!");

    private CodeMsg(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public CodeMsg fillArgs(Object... args) {
        int code = this.code;
        String message = String.format(this.msg, args);
        return new CodeMsg(code, message);
    }
}

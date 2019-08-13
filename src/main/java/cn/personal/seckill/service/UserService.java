package cn.personal.seckill.service;

import cn.personal.seckill.dao.UserDao;
import cn.personal.seckill.entity.User;
import cn.personal.seckill.exception.GlobalException;
import cn.personal.seckill.result.CodeMsg;
import cn.personal.seckill.util.MD5Util;
import cn.personal.seckill.vo.LoginVO;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.thymeleaf.util.StringUtils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * Created with IntelliJ IDEA.
 *
 * @Time 19-8-6
 * @Author ZhengTianle
 * Description:
 */
@Service
@RequiredArgsConstructor
public class UserService {
    public static final String COOKIE_NAME_TOKEN = "token";
    public static final int TOKEN_EXPIRE = 3600 * 24 * 2;

    private final UserDao userDao;
    private final RedisTemplate<String, Object> redisTemplate;

    public boolean login(HttpServletResponse response, LoginVO loginVO) {
        if(loginVO == null) {
            throw new GlobalException(CodeMsg.SERVER_ERROR);
        }
        String mobile = loginVO.getMobile();
        String formPass =  loginVO.getPassword();
        //判断手机号是否存在
        User user = userDao.getById(Long.parseLong(mobile));
        if(user == null) {
            throw new GlobalException(CodeMsg.MOBILE_NOTEXIST);
        }
        // 验证密码
        String dbPass = user.getPassword();
        String dbSalt = user.getSalt();
        String calcPass = MD5Util.formPassToDBPass(formPass, dbSalt);
        if(!calcPass.equals(dbPass)) {
            throw new GlobalException(CodeMsg.PASSWORD_ERROR);
        }
        // 生成 cookie
        String token = UUID.randomUUID().toString().replace("-", "");
        addCookie(response, user, token);
        return true;
    }

    /**
     * 添加或者说更新一个 cookie
     */
    private void addCookie(HttpServletResponse response, User user, String token) {
        // token -> user 放入缓存中
        redisTemplate.opsForValue().set("token:" + token, user, TOKEN_EXPIRE, TimeUnit.SECONDS);
        // token 放入 cookie 中
        Cookie cookie = new Cookie(COOKIE_NAME_TOKEN, token);
        // cookie 设置同样的 2 天过期有效时间，与缓存中充当 session 的有效期一致
        cookie.setMaxAge(TOKEN_EXPIRE);
        cookie.setPath("/");
        response.addCookie(cookie);
    }

    /**
     * 从缓存中取出 token 对应的 user 对象
     */
    public User getByToken(HttpServletResponse response, String token) {
        if(StringUtils.isEmpty(token)) return null;

        User user = (User) redisTemplate.opsForValue().get("token:" + token);
        // 缓存中存在此 token，则这次请求顺便延长 token 的有效期
        if(user != null) {
            addCookie(response, user, token);
        }
        return user;
    }
}

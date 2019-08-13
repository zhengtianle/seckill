package cn.personal.seckill.controller;

import cn.personal.seckill.entity.User;
import cn.personal.seckill.service.GoodsService;
import cn.personal.seckill.vo.GoodsVO;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.thymeleaf.context.WebContext;
import org.thymeleaf.spring5.view.ThymeleafViewResolver;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Created with IntelliJ IDEA.
 *
 * @Time 19-8-10
 * @Author ZhengTianle
 * Description:
 */
@Controller
@RequestMapping("/goods")
@RequiredArgsConstructor
public class GoodsController {
    private static final String GOODS_LIST_KEY = "goodsList";
    private static final String GOODS_DETAIL_KEY = "goodsDetail";

    private final GoodsService goodsService;
    private final RedisTemplate<String, Object> redisTemplate;
    // html解析器
    private final ThymeleafViewResolver thymeleafViewResolver;

    @RequestMapping(value = "/to_list", produces = "text/html")
    @ResponseBody
    public String toList(HttpServletRequest request, HttpServletResponse response, Model model, User user) {
        model.addAttribute("user",user);
        //查询商品列表
        List<GoodsVO> goodsList = goodsService.listGoodsVO();
        model.addAttribute("goodsList", goodsList);
        //return "goods_list";

        // 从缓存中获取goods_list静态页面的源代码
        String html = (String) redisTemplate.opsForValue().get(GOODS_LIST_KEY);
        if(!StringUtils.isEmpty(html)) {
            return html;
        }
        // 缓存中没有取到↓↓↓
        // 手动渲染页面
        WebContext context = new WebContext(request,
                response,
                request.getServletContext(),
                request.getLocale(),
                model.asMap());
        html = thymeleafViewResolver.getTemplateEngine().process("goods_list", context);
        if(!StringUtils.isEmpty(html)) {
            // 页面缓存有效期一般比较短，这里设置1分钟
            redisTemplate.opsForValue().set(GOODS_LIST_KEY, html, 1, TimeUnit.MINUTES);
        }
        return html;
    }

    @RequestMapping(value = "/to_detail/{goodsId}", produces = "text/html")
    @ResponseBody
    public String toDetail(HttpServletRequest request, HttpServletResponse response, Model model, User user, @PathVariable long goodsId) {

        // 从缓存中获取goods_detail静态页面的源代码
        String html = (String) redisTemplate.opsForValue().get(GOODS_DETAIL_KEY + goodsId);
        if(!StringUtils.isEmpty(html)) {
            return html;
        }

        //缓存中静态页面没有取到↓↓↓

        model.addAttribute("user",user);
        //商品详情页
        GoodsVO goods = goodsService.getGoodsVOByGoodsId(goodsId);
        model.addAttribute("goods", goods);

        //秒杀开始结束时间
        long startAt = goods.getStartDate().getTime();
        long endAt = goods.getEndDate().getTime();
        long now = System.currentTimeMillis();

        int status = 0;
        int remainSeconds = 0;
        if(now < startAt) {
            // 秒杀还没开始，进行倒计时
            status = 0;
            remainSeconds = (int) ((startAt - now) / 1000);
        } else if(now > endAt) {
            // 秒杀已经结束
            status = 2;
            remainSeconds = -1;
        } else {
            // 秒杀进行中
            status = 1;
            remainSeconds = 0;
        }
        model.addAttribute("status", status);
        model.addAttribute("remainSeconds", remainSeconds);

        //return "goods_detail";

        WebContext context = new WebContext(request,
                response,
                request.getServletContext(),
                request.getLocale(),
                model.asMap());
        html = thymeleafViewResolver.getTemplateEngine().process("goods_detail", context);
        if (!StringUtils.isEmpty(html)) {
            redisTemplate.opsForValue().set(GOODS_DETAIL_KEY + goodsId, html, 1, TimeUnit.MINUTES);
        }
        return html;

    }

}

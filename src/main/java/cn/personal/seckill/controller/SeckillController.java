package cn.personal.seckill.controller;

import cn.personal.seckill.entity.MiaoshaOrder;
import cn.personal.seckill.entity.OrderInfo;
import cn.personal.seckill.entity.User;
import cn.personal.seckill.result.CodeMsg;
import cn.personal.seckill.service.GoodsService;
import cn.personal.seckill.service.SeckillService;
import cn.personal.seckill.service.OrderService;
import cn.personal.seckill.vo.GoodsVO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Created with IntelliJ IDEA.
 *
 * @Time 19-8-11
 * @Author ZhengTianle
 * Description:
 */
@Controller
@RequestMapping("/miaosha")
@RequiredArgsConstructor
public class SeckillController {
    private final GoodsService goodsService;
    private final OrderService orderService;
    private final SeckillService seckillService;


    @RequestMapping("/do_miaosha")
    public String doMiaosha(Model model, User user, @RequestParam long goodsId) {
        model.addAttribute("user", user);
        if(user == null) return "login";
        // 判断库存
        GoodsVO goods = goodsService.getGoodsVOByGoodsId(goodsId);
        int stock =  goods.getStockCount();
        if(stock <= 0) {
            model.addAttribute("errmsg", CodeMsg.MIAOSHA_OVER_ERROR.getMsg());
            return "miaosha_fail";
        }
        // 有库存的话，需要检测当前是否之前已经秒杀过一次了（限制一个用户一件商品只能秒杀一次）
        MiaoshaOrder order = orderService.getMiaoshaOrderByUserIdGoodsId(user.getId(), goodsId);
        if(order != null) {
            model.addAttribute("errmsg", CodeMsg.REPEATE_MIAOSHA.getMsg());
        }
        // 可以秒杀，减库存->下订单->写入秒杀订单
        OrderInfo orderInfo = seckillService.miaosha(user, goods);
        // 可以显示订单详情页面
        model.addAttribute("orderInfo", orderInfo);
        model.addAttribute("goods", goods);
        return "order_detail";
    }
}

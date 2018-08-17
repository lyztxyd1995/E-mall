package com.o2o.web.shopadmin;

import com.o2o.entity.Shop;
import com.o2o.service.ShopService;
import com.o2o.util.HttpServletRequestUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping(value="shopadmin",method = {RequestMethod.GET})
public class ShopAdminController {
    @Autowired
    ShopService shopService;
    @RequestMapping(value="/shopoperation")
    public String shopOperation(){
        return "shop/shopoperation";
    }

    @RequestMapping(value="/shoplist")
    public String shopList(){
        return "shop/shoplist";
    }
    @RequestMapping(value="/shopmanagement")
    public String shopManagement(HttpServletRequest request){
        long shopId = HttpServletRequestUtil.getLong(request,"shopId");
        Shop shop = shopService.getByShopId(shopId);
        request.getSession().setAttribute("currentShop",shop);
        return "shop/shopmanagement";
    }
    @RequestMapping(value="/productcategorymanagement")
    public String productCategoryManagement(){
        return "shop/productcategorymanagement";
    }

    @RequestMapping(value="/productoperation")
    public String productOperation(){
        return "shop/productoperation";
    }

    @RequestMapping(value = "/productmanagement")
    public String productManagement(){
        return "shop/productmanagement";
    }

}

package com.o2o.web.frontend;

import com.o2o.dto.ShopExecution;
import com.o2o.entity.Area;
import com.o2o.entity.Shop;
import com.o2o.entity.ShopCategory;
import com.o2o.service.AreaService;
import com.o2o.service.ShopCategoryService;
import com.o2o.service.ShopService;
import com.o2o.util.HttpServletRequestUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/frontend")
public class ShopListController {
    @Autowired
    private AreaService areaService;

    @Autowired
    private ShopCategoryService shopCategoryService;

    @Autowired
    private ShopService shopService;

    @RequestMapping(value="/listshopspageinfo", method = RequestMethod.GET)
    @ResponseBody
    private Map<String,Object> listShopPageInfo(HttpServletRequest request){
        Map<String,Object>modelMap = new HashMap<>();
        long parentId = HttpServletRequestUtil.getLong(request, "parentId");
        List<ShopCategory>shopCategoryList = null;
        if(parentId != -1){
            try{
                ShopCategory child = new ShopCategory();
                ShopCategory parent = new ShopCategory();
                parent.setShopCategoryId(parentId);
                child.setParent(parent);
                shopCategoryList = shopCategoryService.getShopCategoryList(child);
            } catch (Exception e){
                modelMap.put("success", false);
                modelMap.put("errMsg",e.getMessage());
                return modelMap;
            }
        } else {
            try{
                shopCategoryList = shopCategoryService.getShopCategoryList(null);
            } catch (Exception e){
                modelMap.put("success", false);
                modelMap.put("errMsg",e.getMessage());
                return modelMap;
            }
        }
        modelMap.put("shopCategoryList", shopCategoryList);
        List<Area>areaList = null;
        try{
            areaList = areaService.getAreaList();
            modelMap.put("areaList", areaList);
        } catch (Exception e){
            modelMap.put("success", false);
            modelMap.put("errMsg",e.getMessage());
            return modelMap;
        }
        modelMap.put("success", true);
        return modelMap;
    }

    @RequestMapping(value = "/listshops", method = RequestMethod.GET)
    @ResponseBody
    private Map<String,Object>listShops(HttpServletRequest request){
        Map<String,Object>modelMap = new HashMap<>();
        int pageIndex = HttpServletRequestUtil.getInt(request, "pageIndex");
        int pageSize = HttpServletRequestUtil.getInt(request,"pageSize");
        if(pageIndex > -1 && pageSize > -1){
            long parentId = HttpServletRequestUtil.getLong(request, "parentId");
            long shopCategoryId = HttpServletRequestUtil.getLong(request, "shopCategoryId");
            int areaId = HttpServletRequestUtil.getInt(request,"areaId");
            String shopName = HttpServletRequestUtil.getString(request, "shopName");
            Shop shopCondition = compactShopCondition(parentId,shopCategoryId,areaId,shopName);
            ShopExecution se = shopService.getShopList(shopCondition, pageIndex, pageSize);
            modelMap.put("success",true);
            modelMap.put("count", se.getCount());
            modelMap.put("shopList",se.getShopList());
        } else {
            modelMap.put("success", false);
            modelMap.put("errMsg", "empty page index or page size");
        }
        return modelMap;
    }
    private Shop compactShopCondition(long parentId, long shopCategoryId, int areaId, String shopName){
        Shop shopCondtion = new Shop();
        if(parentId != -1L){
            ShopCategory child = new ShopCategory();
            ShopCategory parent = new ShopCategory();
            parent.setShopCategoryId(parentId);
            child.setParent(parent);
            shopCondtion.setShopCategory(child);
        }
        if(shopCategoryId != -1L){
            ShopCategory shopCategory = new ShopCategory();
            shopCategory.setShopCategoryId(shopCategoryId);
            shopCondtion.setShopCategory(shopCategory);
        }
        if(areaId != -1L){
            Area area = new Area();
            area.setAreaId(areaId);
            shopCondtion.setArea(area);
        }
        if(shopName != null && !shopName.trim().equals("")){
            shopCondtion.setShopName(shopName);
        }
        shopCondtion.setEnableStatus(1);
        return shopCondtion;
    }
}

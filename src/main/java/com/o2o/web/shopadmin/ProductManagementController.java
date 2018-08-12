package com.o2o.web.shopadmin;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.o2o.dto.ImageHolder;
import com.o2o.dto.ProductExecution;
import com.o2o.entity.Product;
import com.o2o.entity.ProductCategory;
import com.o2o.entity.Shop;
import com.o2o.enums.ProductSateEnum;
import com.o2o.service.ProductCategoryService;
import com.o2o.service.ProductService;
import com.o2o.util.CodeUtil;
import com.o2o.util.HttpServletRequestUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import javax.servlet.http.HttpServletRequest;
import java.awt.*;
import java.awt.image.ImagingOpException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/shopadmin")
public class ProductManagementController {
    @Autowired
    private ProductService productService;

    @Autowired
    private ProductCategoryService productCategoryService;

    private static final int IMAGEMAXCOUNT  = 6;

    @RequestMapping(value = "/addproduct", method = RequestMethod.POST)
    @ResponseBody
    private Map<String,Object> addProduct(HttpServletRequest request) throws IOException {
        Map<String,Object>modelMap = new HashMap<>();
        if(!CodeUtil.checkVerifyCode(request)){
            modelMap.put("success",false);
            modelMap.put("errMsg","Validation code wrong");
            return modelMap;
        }
        ObjectMapper mapper = new ObjectMapper();
        Product product = null;
        String productStr = HttpServletRequestUtil.getString(request,"productStr");
        MultipartHttpServletRequest multipartRequest = null;
        ImageHolder thumbnail = null;
        List<ImageHolder> productImgList = new ArrayList<>();
        CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver(request.getSession().getServletContext());
        try{
            if(multipartResolver.isMultipart(request)){
                multipartRequest = (MultipartHttpServletRequest)request;
                CommonsMultipartFile thumbnailFile = (CommonsMultipartFile)multipartRequest.getFile("thumbnail");
                thumbnail = new ImageHolder(thumbnailFile.getOriginalFilename(), thumbnailFile.getInputStream());
                for(int i = 0; i < IMAGEMAXCOUNT; i++){
                    CommonsMultipartFile productImgFile = (CommonsMultipartFile)multipartRequest.getFile("productImg" + i);
                    if(productImgFile != null){
                        ImageHolder productImg = new ImageHolder(productImgFile.getOriginalFilename(),productImgFile.getInputStream());
                        productImgList.add(productImg);
                    } else {
                        break;
                    }
                }
            } else{
                modelMap.put("success", false);
                modelMap.put("errMsg", "uploaded image could not by empty");
                return modelMap;
            }
        } catch (Exception e){
            modelMap.put("success", false);
            modelMap.put("errMsg", e.getMessage());
            return modelMap;
        }
        try{
            product = mapper.readValue(productStr, Product.class);
        } catch (Exception e){
            modelMap.put("success", false);
            modelMap.put("errMsg", e.toString());
            return modelMap;
        }
        if(product != null && thumbnail != null && productImgList.size() > 0){
            try{
                Shop currentShop = (Shop)request.getSession().getAttribute("currentShop");
                Shop shop = new Shop();
                shop.setShopId(currentShop.getShopId());
                product.setShop(shop);
                ProductExecution pe = productService.addProduct(product, thumbnail, productImgList);
                if(pe.getState() == ProductSateEnum.SUCCESS.getState()){
                    modelMap.put("success", true);
                } else {
                    modelMap.put("success",false);
                    modelMap.put("errMsg", pe.getStateInfo());
                }
            } catch (Exception e){
                modelMap.put("success",false);
                modelMap.put("errMsg", e.toString());
                return modelMap;
            }
        } else {
            modelMap.put("success",false);
            modelMap.put("errMsg", "please complete the product information");
        }
        return modelMap;
    }

    @RequestMapping(value="/getproductbyid", method = RequestMethod.GET)
    @ResponseBody
    private Map<String,Object> getProductById(@RequestParam Long productId){
        Map<String,Object>modelMap = new HashMap<>();
        if(productId != null && productId > -1){
            Product product = productService.getProductById(productId);
            List<ProductCategory>productCategoryList = productCategoryService.getProductCategory(product.getShop().getShopId());
            modelMap.put("product", product);
            modelMap.put("productCategoryList", productCategoryList);
            modelMap.put("success",true);
        } else {
            modelMap.put("success", false);
            modelMap.put("errMsg", "empty product id");
        }
        return modelMap;
    }

    @RequestMapping(value="/modifyproduct", method = RequestMethod.POST)
    @ResponseBody
    private Map<String,Object>modifyProduct(HttpServletRequest request) throws IOException {
        Map<String,Object>modelMap = new HashMap<>();
        boolean statusChange = HttpServletRequestUtil.getBolean(request, "statusChange");
        if(!statusChange && !CodeUtil.checkVerifyCode(request)){
            modelMap.put("success", false);
            modelMap.put("errMsg", "please enter the right auto code");
            return modelMap;
        }
        System.out.println("start");
        ObjectMapper mapper = new ObjectMapper();
        Product product = new Product();
        ImageHolder thumbnail = null;
        List<ImageHolder> productImgList = new ArrayList<>();
        CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver(request.getSession().getServletContext());
        try{
            if(multipartResolver.isMultipart(request)){
                MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest)request;
                CommonsMultipartFile thumbnailFile = (CommonsMultipartFile)multipartRequest.getFile("thumbnail");
                if(thumbnailFile != null){
                    thumbnail = new ImageHolder(thumbnailFile.getOriginalFilename(), thumbnailFile.getInputStream());
                }
                for(int i = 0; i < IMAGEMAXCOUNT; i++){
                    CommonsMultipartFile productImgFile = (CommonsMultipartFile)multipartRequest.getFile("productImg" + i);
                    if(productImgFile != null){
                        ImageHolder productImg = new ImageHolder(productImgFile.getOriginalFilename(),productImgFile.getInputStream());
                        productImgList.add(productImg);
                    } else {
                        break;
                    }
                }
                System.out.println("finish adding productImgList");
            }
        } catch (Exception e){
            modelMap.put("success",false);
            System.out.println(e.toString());
            modelMap.put("errMsg", e.toString());
            return modelMap;
        }
        try{
            String productStr = HttpServletRequestUtil.getString(request,"productStr");
            product = mapper.readValue(productStr, Product.class);
        } catch (Exception e){
            modelMap.put("success", false);
            modelMap.put("errMsg", e.toString());
            return modelMap;
        }
        if(product != null){
            try{
                Shop currentShop = (Shop)request.getSession().getAttribute("currentShop");
                product.setShop(currentShop);
                ProductExecution pe = productService.modifyProduct(product, thumbnail, productImgList);
                if(pe.getState() == ProductSateEnum.SUCCESS.getState()){
                    modelMap.put("success",true);
                } else {
                    modelMap.put("success",false);
                    modelMap.put("errMsg", pe.getStateInfo());
                }
            } catch (Exception e){
                modelMap.put("success",false);
                modelMap.put("errMsg", e.toString());
                return modelMap;
            }
        } else {
            modelMap.put("success",false);
            modelMap.put("errMsg", ProductSateEnum.EMPTY_LIST.getStateInfo());
        }
        return modelMap;
    }

    @RequestMapping(value = "/getproductlistbyshop", method = RequestMethod.GET)
    @ResponseBody
    private Map<String,Object>getProductListByShop(HttpServletRequest request){
        Map<String,Object> modelMap = new HashMap<>();
        int pageIndex = HttpServletRequestUtil.getInt(request,"pageIndex");
        int pageSize = HttpServletRequestUtil.getInt(request, "pageSize");
        Shop currentShop = (Shop)request.getSession().getAttribute("currentShop");
        System.out.println(currentShop.getShopId());
        if((pageIndex > -1) && (pageSize > -1) && (currentShop!= null) && (currentShop.getShopId() != null)){
            Long productCategoryId = HttpServletRequestUtil.getLong(request, "productCategoryId");
            String productName = HttpServletRequestUtil.getString(request, "productName");
            Product productCondition = compactProductCondition(currentShop.getShopId(), productCategoryId, productName);
            ProductExecution pe = productService.getProductList(productCondition, pageIndex, pageSize);
            modelMap.put("success",true);
            modelMap.put("count", pe.getCount());
            modelMap.put("productList", pe.getProductList());
        } else {
            modelMap.put("success",false);
            modelMap.put("errMsg", "empty pagesize, pageindex or shopId");
        }
        return modelMap;
    }

    private Product compactProductCondition(long shopId, Long productCategoryId, String productName){
        Product productCondition = new Product();
        Shop shop = new Shop();
        shop.setShopId(shopId);
        productCondition.setShop(shop);
        if(productCategoryId != null && productCategoryId >= 0){
            ProductCategory productCategory = new ProductCategory();
            productCategory.setProductCategoryId(productCategoryId);
            productCondition.setProductCategory(productCategory);
        }
        if(productName != null){
            productCondition.setProductName(productName);
        }
        return productCondition;
    }
}

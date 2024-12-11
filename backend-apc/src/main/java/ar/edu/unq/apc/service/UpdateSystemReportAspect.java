package ar.edu.unq.apc.service;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import ar.edu.unq.apc.model.CartState;
import ar.edu.unq.apc.model.ExceptionHandlerAspectCustomPointcut;
import ar.edu.unq.apc.model.HttpException;
import ar.edu.unq.apc.model.MercadoLibreProduct;
import ar.edu.unq.apc.model.ProductCounter;
import ar.edu.unq.apc.model.ProductInCart;
import ar.edu.unq.apc.model.ShoppingCart;
import ar.edu.unq.apc.model.UserCounter;

@Aspect
@Order(1)
@Component
public class UpdateSystemReportAspect {

    @Autowired
    private SystemReportService systemReportService;

    @Autowired
    private MercadoLibreProxyService mlService;

    @Autowired
    private PurchasesMetrics purchasesMetrics;

    private Logger log = LoggerFactory.getLogger(ExceptionHandlerAspectCustomPointcut.class);

    @After("execution(* ar.edu.unq.apc.service.ShoppingCartService.updateShoppingCart(..)) && args(shoppingCart,..)")
    public void updatedReports(JoinPoint joinPoint, ShoppingCart shoppingCart){

        log.info("////////////////// Update system report purchases Aspect - AFTER START ////////////////////");

        if(shoppingCart.getCartState() == CartState.SOLD){
            //Updates the number of purchased products by the user
            getAndUpdateUserCounter(shoppingCart);
            for (ProductInCart product : shoppingCart.getCart()) {
                //Updates the number of times the product was purchased
                updatePurchasedProductCounter(product);
            }

            purchasesMetrics.countedCall();
            String methodName = joinPoint.getSignature().getName();
            log.info("Executed method: " + methodName + " and updated reports"); 
        }
    }


    @After("execution(* ar.edu.unq.apc.service.UserService.updateMoreFavorites(..)) && args(productId,..)")
    public void updateFavoriteReportForAdding(JoinPoint joinPoint, String productId){
        log.info("////////////////// Update system report add favorite Aspect - AFTER START ////////////////////");

        addFavoriteProductCounter(productId);

        String methodName = joinPoint.getSignature().getName();
        log.info("Executed method: " + methodName + " and updated reports");
    }

    @After("execution(* ar.edu.unq.apc.service.UserService.updateLessFavorites(..)) && args(productId,..)")
    public void updateFavoriteReportForSubtracting(JoinPoint joinPoint, String productId){
        
        log.info("////////////////// Update system report subtract favorite Aspect - AFTER START ////////////////////");

        subtractFavoriteProductCounter(productId);

        String methodName = joinPoint.getSignature().getName(); 
        log.info("Executed method: " + methodName + " and updated reports");
    }


    private void subtractFavoriteProductCounter(String productId){
        ProductCounter counter = new ProductCounter();
        try {
            counter = systemReportService.getProductCounter(productId);
            if(counter.getFavoriteCounter() > 0){
                counter.addAmountOfFavorites(-1);
            }
            systemReportService.saveProductCounter(counter);
        } catch (HttpException e) {
            //Nothing
        }
        
    }


    private void addFavoriteProductCounter(String productId){
        ProductCounter counter = new ProductCounter();
        try {
            counter = systemReportService.getProductCounter(productId);
            counter.addAmountOfFavorites(1);
        } catch (HttpException e) {
            MercadoLibreProduct product = mlService.getProductById(productId);
            counter = new ProductCounter(0,
                                         productId,
                                         product.getTitle(),
                                         product.getPictures().get(0),
                                         1);
        }
        systemReportService.saveProductCounter(counter);
    }


    private Integer getProductsAmountFromShoppingCart(ShoppingCart shoppingCart){
        Integer amount = 0;
        for (ProductInCart product : shoppingCart.getCart() ) {
            amount = amount + product.getAmount();
        }
        return amount;
    }


    private void getAndUpdateUserCounter(ShoppingCart shoppingCart){
        Integer amount = getProductsAmountFromShoppingCart(shoppingCart);
        UserCounter counter = new UserCounter();
        try {
            counter = systemReportService.getUserCounter(shoppingCart.getBuyer().getId()); 
            counter.addAmountOfPurchases(amount);   
        } catch ( HttpException e) {
            counter = new UserCounter(amount, 
                                      shoppingCart.getBuyer().getId(),
                                      shoppingCart.getBuyer().getUserName(),
                                      shoppingCart.getBuyer().getEmail());
        }
        systemReportService.saveUserCounter(counter);
    }


    private void updatePurchasedProductCounter(ProductInCart product){
        ProductCounter counter = new ProductCounter();
        try {
            counter = systemReportService.getProductCounter(product.getMercadoLibreId());
            counter.addAmountOfPurchases(product.getAmount());
        } catch (HttpException e) {
            counter = new ProductCounter(product.getAmount(),
                                         product.getMercadoLibreId(),
                                         product.getTitle(),
                                         product.getPicture(),
                                         0);
        }
        systemReportService.saveProductCounter(counter);
    }
    

   

}

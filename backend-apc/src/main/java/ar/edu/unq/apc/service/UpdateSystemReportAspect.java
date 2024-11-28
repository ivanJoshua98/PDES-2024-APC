package ar.edu.unq.apc.service;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import ar.edu.unq.apc.model.CartState;
import ar.edu.unq.apc.model.ExceptionHandlerAspectCustomPointcut;
import ar.edu.unq.apc.model.ProductInCart;
import ar.edu.unq.apc.model.ShoppingCart;

@Aspect
@Component
public class UpdateSystemReportAspect {

    @Autowired
    private SystemReportService systemReportService;
    
    private Logger log = LoggerFactory.getLogger(ExceptionHandlerAspectCustomPointcut.class);

    @After("execution(* ar.edu.unq.apc.service.ShoppingCartService.updateShoppingCart(..)) && args(shoppingCart,..)")
    public void updatedReports(JoinPoint joinPoint, ShoppingCart shoppingCart){
        log.info("////////////////// Update system report Aspect - AFTER START ////////////////////");
        if(shoppingCart.getCartState() == CartState.SOLD){
            this.systemReportService.saveProductsPurchasedByUserCounter(shoppingCart.getBuyer(), getProductsAmounFromShoppingCart(shoppingCart));
            String methodName = joinPoint.getSignature().getName();
            log.info("Executing method: " + methodName + " and update reports");
        }
    }

    private Integer getProductsAmounFromShoppingCart(ShoppingCart shoppingCart){
        Integer amount = 0;
        for (ProductInCart product : shoppingCart.getCart() ) {
            amount = amount + product.getAmount();
        }
        return amount;
    }

}

package ar.edu.unq.apc.webService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ar.edu.unq.apc.model.UserWithMostPurchases;
import ar.edu.unq.apc.service.SystemReportService;
import ar.edu.unq.apc.webService.dto.UserWithMostPurchasesDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "System reports services", description = "Manage reports of the application")
@RestController
@RequestMapping("/apc")
public class SystemReportController {

    @Autowired
    private SystemReportService systemReportService;

    
    @Operation(summary = "Get top five users with most purchases")
    @GetMapping("/reports/users-with-most-purchases")
    public ResponseEntity<List<UserWithMostPurchasesDTO>> getTopFiveUsersWithMostPurchases(){
        List<UserWithMostPurchases> topFive = this.systemReportService.getUsersWithMostPurchases();

        return ResponseEntity.ok().body(topFive.stream().map(this::convertUserEntityToUserDTO).toList());
    }

    private UserWithMostPurchasesDTO convertUserEntityToUserDTO(UserWithMostPurchases user){
        return new UserWithMostPurchasesDTO(user.getId(), 
                                            user.getUserName(), 
                                            user.getEmail(), 
                                            user.getPurchasesCount());
    }
    
}

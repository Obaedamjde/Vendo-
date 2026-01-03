package com.aabu.finalproject.controller.seller;


import com.aabu.finalproject.model.dto.SellerProfileDto;
import com.aabu.finalproject.model.dto.request.SellerReq;
import com.aabu.finalproject.model.exception.UnauthorizedException;
import com.aabu.finalproject.model.repository.UserRepository;
import com.aabu.finalproject.service.seller.impl.SellerServiceImpl;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/sellers")
@RequiredArgsConstructor
public class SellerController {

    private final SellerServiceImpl sellerService;

    @PreAuthorize("hasAnyRole('BUYER','ADMIN')")
    @PostMapping("/createShop")
    public ResponseEntity<SellerProfileDto> createShop(
                                                      @Valid @RequestBody SellerReq req ){



        SellerProfileDto dto= sellerService.CreateShop(req);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(dto);

    }

    @PreAuthorize("hasRole('ADMIN')")
    // updated added it to admin role
    @PatchMapping("/acceptCreateShop/{userId}")
    public ResponseEntity<?> AcceptCreateShop( @Valid @PathVariable Long userId){

        boolean updated = sellerService.AcceptCreateShop(userId);

        return updated ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/rejectCreateShop/{userId}")
    public ResponseEntity<?> RejectCreateShop( @Valid @PathVariable Long userId){

        boolean updated = sellerService.RejectCreateShop(userId);

        return updated ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/getAllSellers")
    public List<SellerProfileDto> getAllSellers(){
        return sellerService.getAllSellers();
    }

}

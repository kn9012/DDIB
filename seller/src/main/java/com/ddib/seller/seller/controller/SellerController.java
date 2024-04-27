package com.ddib.seller.seller.controller;

import com.ddib.seller.seller.dto.request.SellerRequestDto;
import com.ddib.seller.seller.service.seller.SellerService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
@RequestMapping(value = "/api/seller", produces = "application/json")
@RequiredArgsConstructor
@Tag(name = "Seller Controller", description = "판매회원 API")
public class SellerController {
    private final SellerService sellerService;
    @PostMapping("/apply")
    @Operation(summary = "판매회원 신청 API")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "판매회원 신청 성공"),
            @ApiResponse(responseCode = "400", description = "판매회원 신청 실패")
    })
    public ResponseEntity<?> sellerApply(@RequestBody SellerRequestDto requestDto, Principal principal) {
        try {
            sellerService.applySeller(requestDto, principal);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<Void>(HttpStatus.BAD_REQUEST);
        }
    }
}

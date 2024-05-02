package com.ddib.seller.seller.controller;

import com.ddib.seller.seller.dto.request.SellerModifyRequestDto;
import com.ddib.seller.seller.dto.request.SellerRequestDto;
import com.ddib.seller.seller.service.seller.SellerService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping(value = "/api/seller", produces = "application/json")
@RequiredArgsConstructor
@Tag(name = "Seller Controller", description = "판매회원 API")
public class SellerController {

    private final SellerService sellerService;

    @PutMapping("/apply")
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

    @GetMapping
    @Operation(summary = "판매회원 정보 조회 API")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "판매회원 정보 조회 성공"),
            @ApiResponse(responseCode = "400", description = "판매회원 정보 조회 실패")
    })
    public ResponseEntity<?> sellerDetails(Principal principal) {
        try {
            return new ResponseEntity<>(sellerService.findSeller(principal), HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<Void>(HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping
    @Operation(summary = "판매회원 정보 수정 API")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "판매회원 정보 수정 성공"),
            @ApiResponse(responseCode = "400", description = "판매회원 정보 수정 실패")
    })
    public ResponseEntity<?> sellerModify(@RequestBody SellerModifyRequestDto requestDto, Principal principal) {
        try {
            sellerService.modifySeller(requestDto, principal);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<Void>(HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping
    @Operation(summary = "판매회원 탈퇴 API")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "판매회원 탈퇴 성공"),
            @ApiResponse(responseCode = "400", description = "판매회원 탈퇴 실패")
    })
    public ResponseEntity<?> sellerDelete(Principal principal) {
        try {
            sellerService.deleteSeller(principal);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<Void>(HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/logout")
    @Operation(summary = "판매회원 로그아웃 API")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "판매회원 로그아웃 성공"),
            @ApiResponse(responseCode = "400", description = "판매회원 로그아웃 실패")
    })
    public ResponseEntity<?> logout(HttpServletResponse response) {
        try {
            Cookie refresh = new Cookie("refresh", null);
            Cookie access = new Cookie("Authorization", null);

            refresh.setMaxAge(0);
            access.setMaxAge(0);

            response.addCookie(refresh);
            response.addCookie(access);

            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<Void>(HttpStatus.BAD_REQUEST);
        }
    }
}

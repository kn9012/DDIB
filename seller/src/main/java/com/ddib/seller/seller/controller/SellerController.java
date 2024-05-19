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
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/api/seller", produces = "application/json")
@RequiredArgsConstructor
@Slf4j
@Tag(name = "Seller Controller", description = "판매회원 API")
public class SellerController {

    private final SellerService sellerService;

    @PutMapping("/apply/{sellerId}")
    @Operation(summary = "판매회원 신청 API")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "판매회원 신청 성공"),
            @ApiResponse(responseCode = "400", description = "판매회원 신청 실패")
    })
    public ResponseEntity<?> sellerApply(@RequestBody SellerRequestDto requestDto, @PathVariable Integer sellerId) {
        try {
            sellerService.applySeller(requestDto, sellerId);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<Void>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/{sellerId}")
    @Operation(summary = "판매회원 정보 조회 API")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "판매회원 정보 조회 성공"),
            @ApiResponse(responseCode = "400", description = "판매회원 정보 조회 실패")
    })
    public ResponseEntity<?> sellerDetails(@PathVariable Integer sellerId) {
        try {
            return new ResponseEntity<>(sellerService.findSeller(sellerId), HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<Void>(HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/{sellerId}")
    @Operation(summary = "판매회원 정보 수정 API")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "판매회원 정보 수정 성공"),
            @ApiResponse(responseCode = "400", description = "판매회원 정보 수정 실패")
    })
    public ResponseEntity<?> sellerModify(@RequestBody SellerModifyRequestDto requestDto, @PathVariable Integer sellerId) {
        try {
            sellerService.modifySeller(requestDto, sellerId);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<Void>(HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/{sellerId}")
    @Operation(summary = "판매회원 탈퇴 API")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "판매회원 탈퇴 성공"),
            @ApiResponse(responseCode = "400", description = "판매회원 탈퇴 실패")
    })
    public ResponseEntity<?> sellerDelete(@PathVariable Integer sellerId) {
        try {
            sellerService.deleteSeller(sellerId);
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
            Cookie num = new Cookie("num", null);
            Cookie JSESSIONID = new Cookie("JSESSIONID", null);

            refresh.setMaxAge(0);
            access.setMaxAge(0);
            num.setMaxAge(0);
            JSESSIONID.setMaxAge(0);

            refresh.setPath("/");
            access.setPath("/");
            num.setPath("/");
            JSESSIONID.setPath("/");

            response.addCookie(refresh);
            response.addCookie(access);
            response.addCookie(num);
            response.addCookie(JSESSIONID);

            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<Void>(HttpStatus.BAD_REQUEST);
        }
    }
}

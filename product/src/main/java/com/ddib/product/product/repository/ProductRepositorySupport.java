package com.ddib.product.product.repository;

import com.ddib.product.product.domain.Product;
import com.ddib.product.product.domain.ProductCategory;
import com.ddib.product.product.domain.QProduct;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.List;

@Repository
public class ProductRepositorySupport {

    @Autowired
    private JPAQueryFactory jpaQueryFactory;

    QProduct qProduct = QProduct.product;

//    @Deprecated
//    public List<Product> getTodayList() {
//        LocalDate today = LocalDate.now();
//        LocalDate tomorrow = today.plusDays(1);
//
//        return jpaQueryFactory
//                .selectFrom(qProduct)
//                .where(
//                        qProduct.eventDate.goe(Timestamp.valueOf(today.atStartOfDay())),
//                        qProduct.eventDate.lt(Timestamp.valueOf(tomorrow.atStartOfDay()))
//                )
//                .orderBy(qProduct.isOver.asc(), qProduct.eventDate.asc(), qProduct.eventStartTime.asc())
//                .fetch();
//    }

    public List<Product> getTodayList() {
        LocalDate today = LocalDate.now();
        LocalDate tomorrow = today.plusDays(1);

        return jpaQueryFactory
                .selectFrom(qProduct)
                .where(
                        qProduct.eventDate.goe(Timestamp.valueOf(today.atStartOfDay())),
                        qProduct.eventDate.lt(Timestamp.valueOf(tomorrow.atStartOfDay())),
                        qProduct.isOver.eq(false)
                )
                .orderBy(qProduct.eventStartTime.asc())
                .fetch();
    }

    public List<Product> getWeekList() {
        LocalDate startDate = LocalDate.now();
        LocalDate endDate = startDate.plusDays(7);

        return jpaQueryFactory
                .selectFrom(qProduct)
                .where(qProduct.eventDate.between(
                        Timestamp.valueOf(startDate.atStartOfDay()),
                        Timestamp.valueOf(endDate.atStartOfDay())))
                .orderBy(qProduct.eventDate.asc(), qProduct.eventStartTime.asc())
                .fetch();
    }

    public List<Product> getTodayListNotOver() {
        LocalDate today = LocalDate.now();
        LocalDate tomorrow = today.plusDays(1);

        return jpaQueryFactory
                .selectFrom(qProduct)
                .where(
                        qProduct.eventDate.goe(Timestamp.valueOf(today.atStartOfDay())),
                        qProduct.eventDate.lt(Timestamp.valueOf(tomorrow.atStartOfDay())),
                        qProduct.isOver.eq(false)
                )
                .fetch();
    }

    public List<Product> findByConditions(String keyword, String category) {
        BooleanBuilder booleanBuilder = new BooleanBuilder();

        if(keyword != null){
            booleanBuilder.or(qProduct.name.like(keyword));

            booleanBuilder.or(qProduct.category.eq(ProductCategory.searchCategoryByKeyword(keyword)));
        }

        if(category != null){
            booleanBuilder.or(qProduct.category.eq(ProductCategory.valueOf(category)));
        }

        return jpaQueryFactory
                .selectFrom(qProduct)
                .distinct()
                .where(booleanBuilder)
                .fetch();
    }
}

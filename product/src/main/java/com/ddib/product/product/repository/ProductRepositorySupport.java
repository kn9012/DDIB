package com.ddib.product.product.repository;

import com.ddib.product.product.domain.Product;
import com.ddib.product.product.domain.ProductCategory;
import com.ddib.product.product.domain.QProduct;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public class ProductRepositorySupport {

    @Autowired
    private JPAQueryFactory jpaQueryFactory;

    private static final int WEEK_DAYS = 7;

    private static final int ONE_DAY = 1;

    QProduct qProduct = QProduct.product;

    public List<Product> getTodayListAll() {
        LocalDate today = LocalDate.now();
        LocalDate tomorrow = today.plusDays(1);

        return jpaQueryFactory
                .selectFrom(qProduct)
                .where(
                        qProduct.eventStartDate.goe(Timestamp.valueOf(today.atStartOfDay())),
                        qProduct.eventStartDate.lt(Timestamp.valueOf(tomorrow.atStartOfDay()))
                )
                .orderBy(qProduct.eventStartTime.asc())
                .fetch();
    }

    public List<Product> getTodayListOver() {
        LocalDate today = LocalDate.now();
        LocalDate tomorrow = today.plusDays(ONE_DAY);

        return jpaQueryFactory
                .selectFrom(qProduct)
                .where(
                        qProduct.eventStartDate.goe(Timestamp.valueOf(today.atStartOfDay())),
                        qProduct.eventStartDate.lt(Timestamp.valueOf(tomorrow.atStartOfDay())),
                        qProduct.isOver.eq(true)
                )
                .orderBy(qProduct.eventStartTime.asc())
                .fetch();
    }

    public List<Product> getWeekList() {
        LocalDate startDate = LocalDate.now();
        LocalDate endDate = startDate.plusDays(WEEK_DAYS);

        return jpaQueryFactory
                .selectFrom(qProduct)
                .where(qProduct.eventStartDate.between(
                        Timestamp.valueOf(startDate.atStartOfDay()),
                        Timestamp.valueOf(endDate.atStartOfDay())))
                .orderBy(qProduct.eventStartDate.asc(), qProduct.eventStartTime.asc())
                .fetch();
    }

    public List<Product> getTodayListNotOver() {
        LocalDate today = LocalDate.now();
        LocalDate tomorrow = today.plusDays(ONE_DAY);

        return jpaQueryFactory
                .selectFrom(qProduct)
                .where(
                        qProduct.eventStartDate.goe(Timestamp.valueOf(today.atStartOfDay())),
                        qProduct.eventStartDate.lt(Timestamp.valueOf(tomorrow.atStartOfDay())),
                        qProduct.isOver.eq(false)
                )
                .limit(3)
                .fetch();
    }

    public List<Product> findByConditions(String keyword, String category, boolean isOver) {
        BooleanBuilder booleanBuilder = new BooleanBuilder();

        if (keyword != null && !keyword.isEmpty()) {
            booleanBuilder.or(qProduct.name.contains(keyword));

            booleanBuilder.or(qProduct.category.eq(ProductCategory.searchCategoryByKeyword(keyword.toUpperCase())));
        }

        if (category != null && !category.isEmpty()) {
            booleanBuilder.or(qProduct.category.eq(ProductCategory.valueOf(category.toUpperCase())));
        }
        booleanBuilder.and(qProduct.isOver.eq(isOver)); // 종료되지 않은 것에 대한 추가

        return jpaQueryFactory
                .selectFrom(qProduct)
                .distinct()
                .where(booleanBuilder)
                .fetch();
    }

    public boolean isAvailableTime(LocalDate date, int start, int end) {
        LocalDate startOfDay = date.atStartOfDay().toLocalDate();
        LocalDate endOfDay = startOfDay.plusDays(ONE_DAY);

        BooleanExpression isOverlappingTime = qProduct.eventEndTime.gt(start)
                .and(qProduct.eventStartTime.lt(end))
                .and(qProduct.eventStartDate.goe(Timestamp.valueOf(startOfDay.atStartOfDay())))
                .and(qProduct.eventStartDate.lt(Timestamp.valueOf(endOfDay.atStartOfDay())));

        List<Product> products = jpaQueryFactory
                .selectFrom(qProduct)
                .where(isOverlappingTime)
                .fetch();

        return products.isEmpty();
    }

    public boolean[] getAvailableTime(LocalDate date) {
        LocalDate startOfDay = date.atStartOfDay().toLocalDate();
        LocalDate endOfDay = startOfDay.plusDays(ONE_DAY);

        BooleanExpression isEqualDate = qProduct.eventStartDate.goe(Timestamp.valueOf(startOfDay.atStartOfDay()))
                .and(qProduct.eventStartDate.lt(Timestamp.valueOf(endOfDay.atStartOfDay())));
        // 지정된 날짜로 시작하는 상품 검색
        List<Product> products = jpaQueryFactory
                .selectFrom(qProduct)
                .where(isEqualDate)
                .fetch();

        // 시간별 상품의 가용 여부를 나타내는 배열 초기화
        boolean[] times = new boolean[24];

        // 각 상품의 시작 시간과 종료 시간을 이용하여 times 배열 갱신
        for (Product product : products) {
            int startTime = product.getEventStartTime();
            int endTime = product.getEventEndTime();
            for (int time = startTime; time < endTime; time++) {
                times[time] = true;
            }
        }

        return times;
    }

    public void updateTimeOverProduct() {
        LocalDate startOfDay = LocalDate.now().atStartOfDay().toLocalDate();
        LocalDate endOfDay = startOfDay.plusDays(ONE_DAY);

        int presentHour = LocalDateTime.now().getHour();

        BooleanExpression isTodayAndOverEndTime = qProduct.eventStartDate.goe(Timestamp.valueOf(startOfDay.atStartOfDay()))
                .and(qProduct.eventStartDate.lt(Timestamp.valueOf(endOfDay.atStartOfDay())))
                .and(qProduct.eventEndTime.loe(presentHour));

        List<Product> products = jpaQueryFactory
                .selectFrom(qProduct)
                .where(isTodayAndOverEndTime)
                .fetch();

        for (Product product : products) {
            product.updateIsOver(true);
        }
    }
}

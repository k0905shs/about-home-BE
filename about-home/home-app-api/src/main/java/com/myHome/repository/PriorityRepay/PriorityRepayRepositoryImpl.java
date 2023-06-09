package com.myHome.repository.PriorityRepay;

import com.myHome.collection.PriorityRepay;
import com.myHome.model.homeCheck.HomeCheckDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

@RequiredArgsConstructor
@Repository
public class PriorityRepayRepositoryImpl implements PriorityRepayCustomRepository<PriorityRepay>{

    private final MongoTemplate mongoTemplate;

    @Override
    public PriorityRepay findPolicyByTargetDate(HomeCheckDto.checkPriorityRepayParam checkPriorityRepayParam) {
        String targetDate = checkPriorityRepayParam.getTargetDate().toString();
        System.out.println(targetDate);
        System.out.println(targetDate);
        Query query = new Query();
        query.addCriteria(
                new Criteria().andOperator(
                        Criteria.where("startDate").lte(targetDate),
                        Criteria.where("endDate").gt(targetDate)
                )
        );

        return  mongoTemplate.findOne(query, PriorityRepay.class);
    }

}

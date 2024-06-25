package com.tax.test.api.member;

import com.tax.test.core.exception.LogicException;
import com.tax.test.database.entity.MemberEntity;
import com.tax.test.database.repository.MemberRepository;
import com.tax.test.type.StatusType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class MemberService {

    private final MemberRepository memberRepository;


    public MemberEntity findMemberBy(String userId) {
        return this.memberRepository.findById(userId)
                .orElseThrow(() -> new LogicException(StatusType.MEMBER_NOT_FOUND));
    }

}

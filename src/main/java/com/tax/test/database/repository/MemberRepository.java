package com.tax.test.database.repository;

import com.tax.test.database.entity.MemberEntity;
import org.springframework.data.jpa.repository.JpaRepository;


public interface MemberRepository extends JpaRepository<MemberEntity, String> {


}

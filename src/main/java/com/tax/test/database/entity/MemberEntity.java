package com.tax.test.database.entity;

import com.tax.test.type.MemberType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.*;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MemberEntity {

	@Id
	private String userId;

	@Column(nullable = false)
	private String password;

	@Column(nullable = false)
	private String name;

	@Column(nullable = false)
	private String regNo;

	@Column(nullable = false)
	private MemberType memberType;

}

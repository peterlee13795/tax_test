package com.tax.test.database.entity;

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
public class TaxScrapEntity {

	@Id
	private String userId;

	@Column(nullable = false, columnDefinition = "TEXT")
	private String json;
}

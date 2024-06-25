package com.tax.test.type;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum StatusType {

	OK(0, ""),
	// 1xx:
	INVALID_PARAMETER(101, "parameter invalid."),
	DATABASE_ENTITY_NOT_FOUND(102, "data can't be found in database."),
	EXTERNAL_API_ERROR(103, "external api error."),
	// 2xx: 회원 관련
	MEMBER_NOT_FOUND(201, "can't find a member."),
	MEMBER_NOT_ALLOWED(202, "not allowed member."),
	MEMBER_REGISTERED(203, "member is already registered."),
	MEMBER_NOT_LOGIN_ABLE(204, "user-id or password is not correct."),
	// 3xx: Tax

	// etc..
	UNKNOWN(-1, "internal error")
	;

	private final int code;
	private final String message;

}

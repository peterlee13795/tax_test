package com.tax.test.domain;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.tax.test.type.StatusType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.StringUtils;


@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
@NoArgsConstructor
public class BaseResponse<T> {
	private Header header;

	private T body;

	public static final BaseResponse<Void> OK = new BaseResponse<>(StatusType.OK);

	public BaseResponse(StatusType statusType) {
		this(statusType, StringUtils.EMPTY);
	}

	public BaseResponse(StatusType statusType, String viewMessage) {
		this.header = new Header(statusType.getCode(), statusType.getMessage(), viewMessage);
	}

	public BaseResponse(T body) {
		this(StatusType.OK);
		this.body = body;
	}

	@Getter
	@NoArgsConstructor
	@AllArgsConstructor
	public static class Header {
		private int code;
		private String message;
		private String viewMessage;
	}
}

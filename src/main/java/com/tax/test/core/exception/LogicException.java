package com.tax.test.core.exception;

import com.tax.test.type.StatusType;
import lombok.Getter;
import org.apache.commons.lang3.StringUtils;

@Getter
public class LogicException extends RuntimeException {

    private final StatusType statusType;

    private final String viewMessage;

    public LogicException(StatusType statusType) {
        this(statusType, StringUtils.EMPTY);

    }

    public LogicException(StatusType statusType, String viewMessage) {
        super();
        this.statusType = statusType;
        this.viewMessage = viewMessage;
    }


    @Override
    public String getMessage() {
        if(statusType != null) {
            return statusType.getMessage();
        }
        return super.getMessage();
    }

    public String getStatusMessage() {
        if(statusType != null) {
            return statusType.getMessage();
        }
        return StringUtils.EMPTY;
    }

}

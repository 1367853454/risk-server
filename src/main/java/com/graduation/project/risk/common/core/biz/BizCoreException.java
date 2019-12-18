package com.graduation.project.risk.common.core.biz;

public class BizCoreException extends RuntimeException {

    private static final long serialVersionUID = -5586792174208604173L;

    private ErrorCode code = ErrorCode.SYSTEM_EXCEPTION;

    public BizCoreException(String message) {
        super(message);
        this.code = null;
    }

    public BizCoreException(String message, ErrorCode code) {
        super(message);
        this.code = code;
    }

    public BizCoreException(ErrorCode code) {
        super(code.getDesc());
        this.code = code;
    }

    public BizCoreException(String message, Throwable e) {
        super(message, e);
    }

    public BizCoreException(String message, ErrorCode code, Throwable e) {
        super(message, e);
        this.code = code;
    }

    public BizCoreException(Throwable e) {
        super(e);
    }

    public BizCoreException(ErrorCode code, Throwable e) {
        super(e);
        this.code = code;
    }

    public ErrorCode getCode() {
        return code;
    }

    public void setCode(ErrorCode code) {
        this.code = code;
    }

}

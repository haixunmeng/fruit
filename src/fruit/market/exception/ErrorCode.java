package fruit.market.exception;

public class ErrorCode {
	
	public static final ErrorCode UNSUPPORTED_ENCODING = new ErrorCode("000001", "��֧�ֵı���");
	public static final ErrorCode READ_PARAMETER_EXCEPTION = new ErrorCode("000002", "��ȡ�����쳣");
	

	public String error_code;

	public String error_msg;

	ErrorCode(String error_code, String error_msg){
		this.error_code = error_code;
		this.error_msg = error_msg;
	}
}

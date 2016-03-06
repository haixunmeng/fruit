package fruit.market.utils;


public class ErrorMeg {

	public int errorCode;
	
	public String errorMeg;

	public static final ErrorMeg DB_OPERATE_ERROR = new ErrorMeg(100001, "数据库操作异常");
	public static final ErrorMeg CALL_SUCCESS = new ErrorMeg(100002, "操作成功");
	public static final ErrorMeg DATA_NOT_EXIST = new ErrorMeg(100003, "数据不存在");
	
	
	public ErrorMeg(int errorCode, String errorMeg){
		this.errorCode = errorCode;
		this.errorMeg = errorMeg;
	}
}

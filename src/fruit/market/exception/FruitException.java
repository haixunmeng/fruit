package fruit.market.exception;

public class FruitException extends RuntimeException {
	
	private static final long serialVersionUID = -4003339585141193046L;
	
	public static final FruitException UNSUPPORTED_ENCODING = new FruitException("000001", "不支持的编码格式");
	public static final FruitException RW_PARAMETER_EXCEPTION = new FruitException("000002", "读写参数时异常");
	public static final FruitException DB_OPTION_EXCEPTION = new FruitException("000003", "数据库操作异常");
	public static final FruitException OPTIONS_SUCCESS = new FruitException("000004", "操作成功");
	public static final FruitException LOAD_PROPERTIES_EXCEPTION = new FruitException("000005", "加载配置文件时异常");
	public static final FruitException SEARCH_CONDITION_EXCEPTION = new FruitException("000006", "查询条件异常");
	public static final FruitException PHONE_HAS_BEEN_REGISTED_EXCEPTION = new FruitException("000007", "该号码已经被注册");
	public static final FruitException USER_NOT_EXISTS_EXCEPTION = new FruitException("000008", "用户不存在");
	public static final FruitException PASSWORD_NOT_CORRECT_EXCEPTION = new FruitException("000009", "密码错误");
	public static final FruitException PASSCODE_ERROR_EXCEPTION = new FruitException("000010", "验证码错误");
	public static final FruitException NO_AUTH_EXCEPTION = new FruitException("000011", "无授权操作");


	public String errorCode;

	public String errorMsg;

	FruitException(String error_code, String error_msg){
		this.errorCode = error_code;
		this.errorMsg = error_msg;
	}

	@Override
	public String toString() {
		return "错误编码：" + this.errorCode + " 错误信息：" + this.errorMsg;
	}
	
	
}

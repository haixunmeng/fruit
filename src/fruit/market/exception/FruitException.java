package fruit.market.exception;

public class FruitException extends RuntimeException {
	
	private static final long serialVersionUID = -4003339585141193046L;
	
	public static final FruitException UNSUPPORTED_ENCODING = new FruitException("000001", "不支持的编码");
	public static final FruitException RW_PARAMETER_EXCEPTION = new FruitException("000002", "读取参数异常");
	public static final FruitException DB_OPTION_EXCEPTION = new FruitException("000003", "数据库操作异常");
	public static final FruitException OPTIONS_SUCCESS = new FruitException("000004", "操作成功");


	public String error_code;

	public String error_msg;

	FruitException(String error_code, String error_msg){
		this.error_code = error_code;
		this.error_msg = error_msg;
	}

	@Override
	public String toString() {
		return "错误编码 ：" + this.error_code + " 错误信息：" + this.error_msg;
	}
	
	
}

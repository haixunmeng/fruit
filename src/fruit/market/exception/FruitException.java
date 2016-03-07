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


	public String error_code;

	public String error_msg;

	FruitException(String error_code, String error_msg){
		this.error_code = error_code;
		this.error_msg = error_msg;
	}

	@Override
	public String toString() {
		return "错误编码：" + this.error_code + " 错误信息：" + this.error_msg;
	}
	
	
}

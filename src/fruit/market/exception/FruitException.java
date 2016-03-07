package fruit.market.exception;

public class FruitException extends RuntimeException {
	
	private static final long serialVersionUID = -4003339585141193046L;
	
	public static final FruitException UNSUPPORTED_ENCODING = new FruitException("000001", "��֧�ֵı���");
	public static final FruitException RW_PARAMETER_EXCEPTION = new FruitException("000002", "��ȡ�����쳣");
	public static final FruitException DB_OPTION_EXCEPTION = new FruitException("000003", "���ݿ�����쳣");
	public static final FruitException OPTIONS_SUCCESS = new FruitException("000004", "�����ɹ�");
	public static final FruitException LOAD_PROPERTIES_EXCEPTION = new FruitException("000005", "加载配置文件时异常");


	public String error_code;

	public String error_msg;

	FruitException(String error_code, String error_msg){
		this.error_code = error_code;
		this.error_msg = error_msg;
	}

	@Override
	public String toString() {
		return "������� ��" + this.error_code + " ������Ϣ��" + this.error_msg;
	}
	
	
}

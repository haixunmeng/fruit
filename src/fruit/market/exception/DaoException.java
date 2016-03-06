package fruit.market.exception;

import fruit.market.utils.ErrorMeg;


public class DaoException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	
	private ErrorMeg errorMeg;
	
	public DaoException(ErrorMeg errorMeg){
		this.errorMeg = errorMeg;
	}

	public ErrorMeg getErrorMeg() {
		return errorMeg;
	}

	public void setErrorMeg(ErrorMeg errorMeg) {
		this.errorMeg = errorMeg;
	}

}

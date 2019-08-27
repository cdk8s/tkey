package com.cdk8s.tkey.server.util.response;

import com.cdk8s.tkey.server.constant.GlobalVariable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class R {


	//=====================================正常返回 start=====================================


	//=====================================正常返回  end=====================================

	//=====================================异常返回 start=====================================

	public static ResponseEntity<ResponseErrorObject> failure() {
		return failure(HttpStatus.INTERNAL_SERVER_ERROR);
	}

	public static ResponseEntity<ResponseErrorObject> failure(HttpStatus httpStatus) {
		ResponseErrorObject responseErrorObject = new ResponseErrorObject();
		responseErrorObject.setError(GlobalVariable.OAUTH_ERROR_MSG);
		responseErrorObject.setErrorDescription(GlobalVariable.OAUTH_ERROR_MSG);
		responseErrorObject.setErrorUriMsg(GlobalVariable.OAUTH_ERROR_URI_MSG);
		return ResponseEntity.status(httpStatus).body(responseErrorObject);
	}

	public static ResponseEntity<ResponseErrorObject> failure(HttpStatus httpStatus, String message) {
		ResponseErrorObject responseErrorObject = new ResponseErrorObject();
		responseErrorObject.setError(GlobalVariable.OAUTH_ERROR_MSG);
		responseErrorObject.setErrorDescription(message);
		responseErrorObject.setErrorUriMsg(GlobalVariable.OAUTH_ERROR_URI_MSG);
		return ResponseEntity.status(httpStatus).body(responseErrorObject);
	}

	//=====================================异常返回  end=====================================
}

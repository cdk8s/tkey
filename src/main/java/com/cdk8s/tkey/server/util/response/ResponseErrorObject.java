package com.cdk8s.tkey.server.util.response;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.*;

import java.io.Serializable;


@Setter
@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class ResponseErrorObject implements Serializable {

	private static final long serialVersionUID = 9010786825517645779L;

	private String error;
	private String errorDescription;
	private String errorUriMsg;

}

package we.base.security;

import org.springframework.security.oauth2.common.exceptions.ClientAuthenticationException;

public class QywxOpenIdNotBindException extends ClientAuthenticationException {

	public QywxOpenIdNotBindException(String msg) {
		super(msg);
	}

	@Override
	public String getOAuth2ErrorCode() {
		return "J2_E02";
	}
}

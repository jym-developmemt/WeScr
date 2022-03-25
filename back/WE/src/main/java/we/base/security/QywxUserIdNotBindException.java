package we.base.security;

import org.springframework.security.oauth2.common.exceptions.ClientAuthenticationException;

public class QywxUserIdNotBindException extends ClientAuthenticationException {

	public QywxUserIdNotBindException(String msg) {
		super(msg);
	}

	@Override
	public String getOAuth2ErrorCode() {
		return "J2_E01";
	}
}

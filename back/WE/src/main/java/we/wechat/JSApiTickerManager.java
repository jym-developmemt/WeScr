package we.wechat;

import java.util.Date;
import java.util.concurrent.Semaphore;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.stereotype.Component;

import we.base.util.CommonUtil;

@Component
public class JSApiTickerManager {

	private static final String TICKET_URL = "https://qyapi.weixin.qq.com/cgi-bin/get_jsapi_ticket";

	private static final String PARAM_ACCESS_TOKEN = "access_token";

	private static Semaphore semaphore = new Semaphore(1);

	private static String ticket = null;

	private static Date expiresDate = null;

	@Autowired
	private AccessTokenManager accessTokenManager;

	/**
	 * 获取企业的jsapi_ticket
	 *
	 * @return
	 */
	public String getTicker() throws Exception {
		semaphore.acquire();
		try {

			// 获取新的Token
			if (isExpires()) {
				refresh();
			}

			return ticket;

		} finally {
			semaphore.release();
		}
	}

	private boolean isExpires() {
		if (ticket == null) {
			return true;
		}

		Date sysdate = new Date();
		if (expiresDate.before(sysdate)) {
			return true;
		}

		return false;
	}

	private void refresh() throws Exception {
		StringBuffer strUrl = new StringBuffer();
		strUrl.append(TICKET_URL);
		strUrl.append("?");
		strUrl.append(PARAM_ACCESS_TOKEN);
		strUrl.append("=");
		strUrl.append(accessTokenManager.getAccessToken());

		JSONObject result = CommonUtil.sendGetRequest(strUrl.toString());
		int errcode = result.getInt("errcode");
		if (errcode == 0) {
			ticket = result.getString("ticket");
			int expires = result.getInt("expires_in");
			expiresDate = new Date(new Date().getTime() + (expires - 60) * 1000);
		} else {
			throw new Exception(result.getString("errmsg"));
		}
	}
}

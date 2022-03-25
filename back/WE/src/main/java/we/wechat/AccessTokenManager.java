package we.wechat;

import java.util.Date;
import java.util.concurrent.Semaphore;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.stereotype.Component;

import we.base.util.CommonUtil;
import we.core.CoreProperties;

@Component
public class AccessTokenManager {

	private static final String TOKEN_URL = "https://qyapi.weixin.qq.com/cgi-bin/gettoken";

	private static final String PARAM_CORPID = "corpid";

	private static final String PARAM_CORPSECRET = "corpsecret";

	private static Semaphore semaphore = new Semaphore(1);

	private static String accessToken = null;

	private static Date expiresDate = null;

	@Autowired
	private CoreProperties websiteProp;

	/**
	 * 获取API登录凭证
	 *
	 * @return
	 */
	public String getAccessToken() throws Exception {
		semaphore.acquire();
		try {

			// 获取新的Token
			if (isExpires()) {
				refresh();
			}

			return accessToken;

		} finally {
			semaphore.release();
		}
	}

	private boolean isExpires() {
		if (accessToken == null) {
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
		strUrl.append(TOKEN_URL);
		strUrl.append("?");
		strUrl.append(PARAM_CORPID);
		strUrl.append("=");
		strUrl.append(websiteProp.getQyapiCorpid());
		strUrl.append("&");
		strUrl.append(PARAM_CORPSECRET);
		strUrl.append("=");
		strUrl.append(websiteProp.getQyapiCorpsecret());
		JSONObject result = CommonUtil.sendGetRequest(strUrl.toString());
		int errcode = result.getInt("errcode");
		if (errcode == 0) {
			accessToken = result.getString("access_token");
			int expires = result.getInt("expires_in");
			expiresDate = new Date(new Date().getTime() + (expires - 60) * 1000);
		} else {
			throw new Exception(result.getString("errmsg"));
		}
	}
}

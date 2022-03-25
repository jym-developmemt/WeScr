package we.wechat;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.stereotype.Component;

import we.base.util.CommonUtil;

@Component
public class WxUserManager {

	private static final String WX_USERINFO_URL = "https://qyapi.weixin.qq.com/cgi-bin/user/getuserinfo";

	private static final String CONVERT_TO_OPENID_URL = "https://qyapi.weixin.qq.com/cgi-bin/user/convert_to_openid";

	private static final String PARAM_ACCESS_TOKEN = "access_token";

	private static final String PARAM_CODE = "code";

	private static final String PARAM_USERID = "userid";

	@Autowired
	private AccessTokenManager accessTokenManager;

	public JSONObject getUserInfo(String userCode) throws Exception {
		String accessToken = accessTokenManager.getAccessToken();

		StringBuffer strUrl = new StringBuffer(WX_USERINFO_URL);
		strUrl.append("?");
		strUrl.append(PARAM_ACCESS_TOKEN);
		strUrl.append("=");
		strUrl.append(accessToken);
		strUrl.append("&");
		strUrl.append(PARAM_CODE);
		strUrl.append("=");
		strUrl.append(userCode);

		return CommonUtil.sendGetRequest(strUrl.toString());
	}

	public String convertToOpenid(String qywxUserId) throws Exception {
		// API登录凭证
		String accessToken = accessTokenManager.getAccessToken();

		StringBuffer strUrl = new StringBuffer(CONVERT_TO_OPENID_URL);
		strUrl.append("?");
		strUrl.append(PARAM_ACCESS_TOKEN);
		strUrl.append("=");
		strUrl.append(accessToken);

		JSONObject params = new JSONObject();
		params.put(PARAM_USERID, qywxUserId);

		JSONObject sendResult = CommonUtil.sendPostRequest(strUrl.toString(), params);
		int errcode = sendResult.getInt("errcode");
		if (errcode != 0) {
			return null;
		}
		return sendResult.getString("openid");
	}
}

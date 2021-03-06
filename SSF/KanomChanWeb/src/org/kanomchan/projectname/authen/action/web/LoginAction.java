package org.kanomchan.projectname.authen.action.web;

import javax.servlet.http.Cookie;

import org.kanomchan.core.common.constant.CommonConstant;
import org.kanomchan.core.common.cookie.bean.CookieOrm;
import org.kanomchan.core.common.io.LoginIO;
import org.kanomchan.core.common.processhandler.CookieFilter;
import org.kanomchan.core.common.processhandler.ServiceResult;
import org.kanomchan.core.common.web.struts.action.BaseAction;
import org.kanomchan.core.security.authen.service.LoginService;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("prototype")
public class LoginAction extends BaseAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2275327612858235569L;
	private String user;
	private String password;
	
	private String identifier;
//	@Autowired
//	private LoginSSOService loginSSOService;
	private LoginService loginService;
	@Override
	public String init() throws Exception {
		
		return "loginPage";
	}
	
	public String login() throws Exception{
		Cookie[] cookies = httpServletRequest.getCookies();
		CookieOrm cookieBean = new CookieOrm();
		for (Cookie cookie : cookies) {
			if(CookieFilter.KEY_MID.equals(cookie.getName())){
				cookieBean.setMachineId(cookie.getValue());
			}else if(CookieFilter.KEY_UID.equals(cookie.getName())){
				cookieBean.setUserId(cookie.getValue());
			}else if(CookieFilter.KEY_KID.equals(cookie.getName())){
				cookieBean.setKeyId(cookie.getValue());
			}else if(CookieFilter.KEY_TID.equals(cookie.getName())){
				cookieBean.setTokenId(cookie.getValue());
			}
		}
		ServiceResult<LoginIO> serviceResult = loginService.performLoginAndPutDataSession(user,password,cookieBean);
		 
		if(serviceResult.isSuccess()){
			session.put(CommonConstant.SESSION.USER_BEAN_KEY, serviceResult.getResult().getUserBean());
			session.put(CommonConstant.SESSION.MENU_BEAN_KEY, serviceResult.getResult().getMenuVO().getMenuBeans());
			session.put(CommonConstant.SESSION.MENU_BEAN_MAP_KEY, serviceResult.getResult().getMenuVO().getLookupMap());
			
			session.putAll(serviceResult.getResult().getSession());
			if(session.get(CommonConstant.SESSION.NEXT_URL_KEY)!=null){
				nextUrl = (String) session.get(CommonConstant.SESSION.NEXT_URL_KEY);
				session.remove(CommonConstant.SESSION.NEXT_URL_KEY);
				return CommonConstant.FORCE_TO_NEXT_URL;
			}else{
				return CommonConstant.FORCE_TO_LOGGEDIN_WELCOME_PAGE;
			}
		}else{
			messageList = serviceResult.getMessages();
			return "loginPage";
		}
	}
	
	
//	public String loginOpenId() throws Exception{
//		
//		if (!Strings.isNullOrEmpty(httpServletRequest.getParameter("error"))) {
//
//			// there's an error coming back from the server, need to handle this
////			handleError(request, response);
//			return null; // no auth, response is sent to display page or something
//
//		} else if (!Strings.isNullOrEmpty(httpServletRequest.getParameter("code"))) {
//
//			// we got back the code, need to process this to get our tokens
////			ServiceResult<LoginIO> serviceResult = loginService.performLoginAndPutDataSession(httpServletRequest.getParameter("code"),httpServletRequest.getParameter("state"),session);
////			if(serviceResult.isSuccess()){
////				session.put(CommonConstant.SESSION.USER_BEAN_KEY, serviceResult.getResult().getUserBean());
////				session.put(CommonConstant.SESSION.MENU_BEAN_KEY, serviceResult.getResult().getMenuVO().getMenuBeans());
////				session.put(CommonConstant.SESSION.MENU_BEAN_MAP_KEY, serviceResult.getResult().getMenuVO().getLookupMap());
////				
////				session.putAll(serviceResult.getResult().getSession());
////				if(session.get(CommonConstant.SESSION.NEXT_URL_KEY)!=null){
////					nextUrl = (String) session.get(CommonConstant.SESSION.NEXT_URL_KEY);
////					session.remove(CommonConstant.SESSION.NEXT_URL_KEY);
////					return CommonConstant.FORCE_TO_NEXT_URL;
////				}else{
////					return CommonConstant.FORCE_TO_LOGGEDIN_WELCOME_PAGE;
////				}
////			}else{
////				messageList = serviceResult.getMessages();
////				return "loginPage";
////			}
//			
//		} else {
////			String url = httpServletRequest.getRequestURL().toString()+((httpServletRequesthttpServletRequest.getQueryString()==null||"null".equals(request.getQueryString())||"".equals(request.getQueryString()))?"":"?"+request.getQueryString());
////			session.put(CommonConstant.SESSION.NEXT_URL_KEY, url);
//			String redirectUri = httpServletRequest.getRequestURL().toString();
//			// not an error, not a code, must be an initial login of some type
////			ServiceResult<AuthRequestBean> result = loginService.handleAuthorizationRequest(redirectUri, identifier);
////			if(result.isSuccess()){
////				AuthRequestBean authRequestBean = result.getResult();
////				httpServletResponse.sendRedirect(authRequestBean.getRedirectUrl());
////				session.putAll(authRequestBean.getSession());
////				return null;
////			}else{
////				
////			}
//			
//			return null; // no auth, response redirected to the server's Auth Endpoint (or possibly to the account chooser)
//		}
////		return null;
//	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	public String getIdentifier() {
		return identifier;
	}
	public void setIdentifier(String identifier) {
		this.identifier = identifier;
	}

}


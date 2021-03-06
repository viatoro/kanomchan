package org.kanomchan.core.security.authen.service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.kanomchan.core.common.bean.UserBean;
import org.kanomchan.core.common.cookie.bean.CookieOrm;
import org.kanomchan.core.common.exception.NonRollBackException;
import org.kanomchan.core.common.exception.RollBackException;
import org.kanomchan.core.common.io.LoginIO;
import org.kanomchan.core.common.processhandler.ServiceResult;
import org.kanomchan.core.security.authen.bean.IUserDefault;

public interface LoginService {

	public ServiceResult<UserBean> performLogin(String username, String password)throws NonRollBackException,RollBackException;
	public ServiceResult<LoginIO> performLoginAndPutDataSession(String username, String password,CookieOrm cookieOrm)throws NonRollBackException,RollBackException;
//	public ServiceResult<LoginIO> performLoginAndPutDataSession(String authorizationCode,String state,Map<String, Object> session)throws NonRollBackException,RollBackException;
//	public ServiceResult<AuthRequestBean> startSSO()throws NonRollBackException,RollBackException;
//	public ServiceResult<AuthRequestBean> startSSO(String identifier)throws NonRollBackException,RollBackException;
//	public ServiceResult<AuthRequestBean> startSSO(String identifier,String redirectUri)throws NonRollBackException,RollBackException;
	public ServiceResult<LoginIO> performLoginWithOutPasswordAndPutDataSession(String username,CookieOrm cookieOrm) throws NonRollBackException, RollBackException;
	public ServiceResult<LoginIO> performLoginWithOutPasswordAndPutDataSession(Long userId,CookieOrm cookieOrm) throws NonRollBackException, RollBackException;
	public ServiceResult<LoginIO> performLoginAndPutDataSession(UserBean userBean,IUserDefault userDefault) throws NonRollBackException,RollBackException;
	public ServiceResult<LoginIO> attemptAuthenticationAndPutDataSession(HttpServletRequest httpServletRequest,HttpServletResponse httpServletResponse) throws NonRollBackException,RollBackException;

}

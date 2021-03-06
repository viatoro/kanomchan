package org.kanomchan.core.common.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.log4j.Logger;
import org.kanomchan.core.common.bean.ActionBean;
import org.kanomchan.core.common.bean.Config;
import org.kanomchan.core.common.bean.ConfigByCountry;
import org.kanomchan.core.common.bean.ConfigByDate;
import org.kanomchan.core.common.bean.ConfigDefault;
import org.kanomchan.core.common.bean.FieldValidatorBean;
import org.kanomchan.core.common.bean.FieldValidatorDefault;
import org.kanomchan.core.common.bean.Label;
import org.kanomchan.core.common.bean.LabelDefault;
import org.kanomchan.core.common.bean.Message;
import org.kanomchan.core.common.bean.MessageDefault;
import org.kanomchan.core.common.exception.NonRollBackException;
import org.kanomchan.core.common.exception.RollBackException;
import org.springframework.jdbc.core.RowMapper;

import com.googlecode.ehcache.annotations.Cacheable;
import com.googlecode.ehcache.annotations.TriggersRemove;

public class ConfigDaoImpl extends JdbcCommonDaoImpl implements ConfigDao {
	
	private static final Logger logger = Logger.getLogger(ConfigDaoImpl.class);
	
	
	private static final String SQL_QUERY_CONFIG = "SELECT CONFIG_KEY, CONFIG_VALUE FROM SYS_M_CONFIG WHERE STATUS = 'A' ";
	@Override
	public Map<String, String> getConfigMap() throws RollBackException ,NonRollBackException {
		Map<String, String> configMap = new ConcurrentHashMap<String, String>();
		
		List<Config> configList = nativeQuery(SQL_QUERY_CONFIG, CONFIG_MAPPER);//(SQL_QUERY_CONFIG, new configMapper());
		
		for (Config systemConfig : configList) {
			if (logger.isInfoEnabled()) {
				logger.info("Config loading... " + systemConfig);
			}			
			configMap.put(systemConfig.getKey(), systemConfig.getValue());
		}
		return configMap;
	}
	
	private static final ConfigMapper<Config> CONFIG_MAPPER = new ConfigMapper<Config>();
	public static final class ConfigMapper<T extends Config> implements RowMapper<Config> {

	    public Config mapRow(ResultSet rs, int num)throws SQLException {
	    	ConfigDefault configDefault = new ConfigDefault();
	    	configDefault.setKey( rs.getString("CONFIG_KEY"));
	    	configDefault.setValue( rs.getString("CONFIG_VALUE"));
	        return configDefault;
	    }
    }
	
	private static final String SQL_QUERY_CONFIG_BY_COUNTRY = "SELECT CONFIG_KEY, CONFIG_VALUE, CONFIG_CODE FROM SYS_M_CONFIG_BY_CODE WHERE STATUS = 'A' ";
	@Override
	public Map<Long,Map<String, String>> getConfigCountryMap() throws RollBackException ,NonRollBackException {
		Map<Long,Map<String, String>> configMap = new ConcurrentHashMap<Long,Map<String, String>>();
		Map<String, String> configByCountryMap = new ConcurrentHashMap<String, String>();
		
		List<ConfigByCountry> configList = nativeQuery(SQL_QUERY_CONFIG_BY_COUNTRY, CONFIG_BY_COUNTRY_MAPPER);//(SQL_QUERY_CONFIG, new configMapper());
		
		for (ConfigByCountry systemConfig : configList) {
			if (logger.isInfoEnabled()) {
				logger.info("Config loading... " + systemConfig);
			}	
			configByCountryMap.put(systemConfig.getKey(), systemConfig.getValue());
			configMap.put(systemConfig.getIdCountry(), configByCountryMap);
		}
		return configMap;
	}
	
	private static final ConfigByCountryMapper<ConfigByCountry> CONFIG_BY_COUNTRY_MAPPER = new ConfigByCountryMapper<ConfigByCountry>();
	public static final class ConfigByCountryMapper<T extends ConfigByCountry> implements RowMapper<ConfigByCountry> {

	    public ConfigByCountry mapRow(ResultSet rs, int num)throws SQLException {
	    	ConfigByCountry configByCountry = new ConfigByCountry();
	    	configByCountry.setKey( rs.getString("CONFIG_KEY"));
	    	configByCountry.setValue( rs.getString("CONFIG_VALUE"));
	    	configByCountry.setIdCountry(Long.parseLong(rs.getString("CONFIG_CODE")));
	        return configByCountry;
	    }
    }

	public static final String SQL_QUERY_MESSAGE = 
			" SELECT MESSAGE_CODE, MESSAGE_LANG, DISPLAY_TEXT, MESSAGE_DESC, " +
			" MESSAGE_TYPE, SOLUTION " +
			" FROM SYS_M_MESSAGE WHERE STATUS = 'A' ";
	@Override
	@Cacheable(cacheName = "getMessageMap")
	public Map<String, Message> getMessageMap() {
		Map<String, Message> messageMap = new ConcurrentHashMap<String, Message>();
		
		List<Message> messageList;
		try {
			messageList = nativeQuery(SQL_QUERY_MESSAGE, MESSAGE_MAPPER);
		} catch (RollBackException | NonRollBackException e) {
			messageList = new ArrayList<Message>();
			logger.error("getMessageMap()", e);
		}//(SQL_QUERY_CONFIG, new configMapper());
		
		for (Message message : messageList) {
			if (logger.isInfoEnabled()) {
				logger.info("Message loading... " + message);
			}			
			messageMap.put(message.getMessageCode()+"_"+message.getMessageLang(), message);
		}
		return messageMap;
	}
	
	private static final MessageMapper<Message> MESSAGE_MAPPER = new MessageMapper<Message>();
	public static final class MessageMapper<T extends Message> implements RowMapper<Message> {

	    public Message mapRow(ResultSet rs, int num)throws SQLException {
	    	MessageDefault message = new MessageDefault(); 
	    	message.setMessageCode(rs.getString("MESSAGE_CODE"));
	    	message.setMessageLang(rs.getString("MESSAGE_LANG"));
	    	message.setDisplayText(rs.getString("DISPLAY_TEXT"));
	    	message.setMessageDesc(rs.getString("MESSAGE_DESC"));
	    	message.setMessageType(rs.getString("MESSAGE_TYPE"));
	    	message.setSolution(rs.getString("SOLUTION"));
	        return message;
	    }
    }
	
	private static final LabelMapper<Label> LABEL_MAPPER = new LabelMapper<Label>();
	public static final class LabelMapper<T extends Label> implements RowMapper<Label> {

	    public Label mapRow(ResultSet rs, int num)throws SQLException {
	    	LabelDefault label = new LabelDefault(); 
	    	label.setLabel(rs.getString("LABEL"));
	    	label.setLanguage(rs.getString("LANGUAGE"));
	    	label.setPage(rs.getString("PAGE"));
	    	label.setDisplayText(rs.getString("DISPLAY_TEXT"));
	        return label;
	    }
    }
	@Override
	public List<Message> getMessageList(String messageType, String messageLang)throws RollBackException ,NonRollBackException {
		
		StringBuilder whereClause = new StringBuilder();
		Map<String,Object> params = new ConcurrentHashMap<String, Object>();
		
		if( messageType != null && messageType.length() > 0 ){
			whereClause.append(" AND MESSAGE_TYPE LIKE :messageType ");
			params.put("messageType", "%"+messageType+"%");
		}
		if( messageLang != null && messageLang.length() > 0 ){
			whereClause.append(" AND MESSAGE_LANG = :messageLang ");
			params.put("messageLang", messageLang);
		}
		return nativeQuery(SQL_QUERY_MESSAGE+whereClause.toString(), MESSAGE_MAPPER, params);
	}

	
	public static final String SQL_QUERY_LABEL = 
			" SELECT LABEL, PAGE, DISPLAY_TEXT, LANGUAGE " +
			" FROM SYS_M_LABEL  WHERE STATUS = 'A' ";
	
	@Override
	public List<Label> getLabelList() throws RollBackException ,NonRollBackException{
		
		List<Label> labelList = nativeQuery(SQL_QUERY_LABEL, LABEL_MAPPER);//(SQL_QUERY_CONFIG, new configMapper());
		
		return labelList;
	}

	@Override
	public Map<String, List<Label>> getLabelMap()throws RollBackException ,NonRollBackException {
		Map<String, List<Label>> messageMap = new ConcurrentHashMap<String, List<Label>>();
		List<Label> labelList = nativeQuery(SQL_QUERY_LABEL, LABEL_MAPPER);//(SQL_QUERY_CONFIG, new configMapper());
		
		for (Label label : labelList) {
			List<Label> labels = messageMap.get(label.getLanguage());
			if(labels==null){
				labels = new ArrayList<Label>();
				
			}
			labels.add(label);
			messageMap.put(label.getLanguage(), labels);
		}
		return messageMap;
	}
	
	@Override
	public Map<String, Map<String, String>> getLabelStrMap() throws RollBackException ,NonRollBackException{
		
		Map<String, Map<String, String>> messageStringMap = new ConcurrentHashMap<String, Map<String, String>>();

		Map<String, List<Label>> messageMap   = getLabelMap();
		Map<String, String> labelMapDefault = new ConcurrentHashMap<String, String>();
		for (Label label : messageMap.get(Label.DEFAULT_LANG)) {
			labelMapDefault.put(label.getPage()+"_"+label.getLabel(), label.getDisplayText());
		}
		Set<String> langSet = messageMap.keySet();
		for (String lang : langSet) {
			if(lang != Label.DEFAULT_LANG){
				Map<String, String> labelMap = new ConcurrentHashMap<String, String>(labelMapDefault);
				for (Label label : messageMap.get(lang)) {
					labelMap.put(label.getPage()+"_"+label.getLabel(), label.getDisplayText());
				}
				messageStringMap.put(lang, labelMap );
			}
			
		}
		return messageStringMap;
	}
	
	@Override
	@TriggersRemove(cacheName="getActionByActionId", removeAll=true)
	public void clearConfigCache() throws RollBackException ,NonRollBackException{
		
	}

	@Override
	@TriggersRemove(cacheName="getMessageMap", removeAll=true)
	public void clearMessageCache() throws RollBackException ,NonRollBackException{
		
	}
	
//	public static final String SQL_QUERY_DISPLAY_FIELD = 
////			" ID_DISPLAY_FIELD ,ID_REGION ,ID_COUNTRY ,ID_ZONE ,ID_PROVINE ,ID_CITY ,MODULE ,PAGE ,FIELD ,IS_MANDATORY ,IS_MATCH ,IS_WEIGHT ,WEIGHT_PERCENT ,IS_DISPLAY ,DESCRIPTION ,STATUS " +
//			" ID_DISPLAY_FIELD ,IS_MANDATORY ,IS_MATCH ,IS_WEIGHT ,WEIGHT_PERCENT ,IS_DISPLAY ,DESCRIPTION ,STATUS " +
//			" FROM JOB_N_DISPLAY_FIELD  WHERE STATUS = 'A' ";
//	private static final DisplayFieldMapper<DisplayField> DISPLAY_FIELD_MAPPER = new DisplayFieldMapper<DisplayField>();
//	public static final class DisplayFieldMapper<T extends DisplayField> implements RowMapper<DisplayField> {
//		/**
//		 * Logger for this class
//		 */
//		private static final Logger logger = Logger.getLogger(DisplayFieldMapper.class);
//
//	    public DisplayField mapRow(ResultSet rs, int num)throws SQLException {
//	    	DisplayFieldDefault displayFiled = new DisplayFieldDefault(); 
//	    	displayFiled.setIdCountry(Long.parseLong(rs.getString("ID_COUNTRY")));
//	    	displayFiled.setPage(rs.getString("PAGE"));
//	    	displayFiled.setIsDisplay(rs.getString("DISPLAY_TEXT"));
//	        return displayFiled;
//	    }
//    }
	
	public static final String SQL_QUERY_PAGE_FIELD_VALIDATORS = 
			" SELECT * " +
			" FROM SYS_M_FIELD_VALIDATOR  WHERE STATUS = 'A' ";
	
	
	private static final FieldValidatorBeanMapper<FieldValidatorBean> DISPLAY_FIELD_VALIDATOR = new FieldValidatorBeanMapper<FieldValidatorBean>();
	public static final class FieldValidatorBeanMapper<T extends FieldValidatorBean> implements RowMapper<FieldValidatorBean> {

	    public FieldValidatorBean mapRow(ResultSet rs, int num)throws SQLException {
	    	FieldValidatorDefault displayFiled = new FieldValidatorDefault();
	    	displayFiled.setId(rs.getLong("ID_FIELD_VALIDATOR"));
	    	displayFiled.setPage(rs.getString("PAGE"));
	    	displayFiled.setField(rs.getString("FIELD"));
	    	displayFiled.setPreCon(rs.getLong("PRE_CON"));
	    	displayFiled.setType(rs.getString("TYPE"));
	    	displayFiled.setParameter(rs.getString("PARAMETER"));
	    	displayFiled.setMessage(rs.getString("MESSAGE"));
	    	displayFiled.setMessageKey(rs.getString("MESSAGE_KEY"));
	    	displayFiled.setMessageParameter(rs.getString("MESSAGE_PARAMETER"));
	    	displayFiled.setSeq(rs.getInt("SEQ"));
	        return displayFiled;
	    }
    }
	@Override
	public Map<String, Map<String, List<FieldValidatorBean>>> getPageFieldValidators()throws RollBackException ,NonRollBackException {
		Map<String, Map<String, List<FieldValidatorBean>>> page = new ConcurrentHashMap<String, Map<String, List<FieldValidatorBean>>>();
		List<FieldValidatorBean> fieldValidatorsqu = nativeQuery(SQL_QUERY_PAGE_FIELD_VALIDATORS, DISPLAY_FIELD_VALIDATOR);//(SQL_QUERY_CONFIG, new configMapper());
		
		for (FieldValidatorBean fieldValidator : fieldValidatorsqu) {
			Map<String, List<FieldValidatorBean>> field = page.get(fieldValidator.getPage());
			if(field == null){
				field = new ConcurrentHashMap<String, List<FieldValidatorBean>>();
				page.put(fieldValidator.getPage(), field);
			}
			if(fieldValidator.getField() != null){
				List<FieldValidatorBean> fieldValidators = field.get(fieldValidator.getField());
				if(fieldValidators==null){
					fieldValidators = new LinkedList<FieldValidatorBean>();
					field.put(fieldValidator.getField(), fieldValidators);
				}
				fieldValidators.add(fieldValidator);
			}
			
		}
		return page;
	}
	
	@Override
	public Map<String, List<FieldValidatorBean>> getPageValidators()throws RollBackException ,NonRollBackException {
		Map<String, List<FieldValidatorBean>> page = new ConcurrentHashMap<String,  List<FieldValidatorBean>>();
		List<FieldValidatorBean> fieldValidatorsqu = nativeQuery(SQL_QUERY_PAGE_FIELD_VALIDATORS, DISPLAY_FIELD_VALIDATOR);//(SQL_QUERY_CONFIG, new configMapper());
		
		for (FieldValidatorBean fieldValidator : fieldValidatorsqu) {
			List<FieldValidatorBean> fieldValidators = page.get(fieldValidator.getPage());
			if(fieldValidators==null){
				fieldValidators = new LinkedList<FieldValidatorBean>();
				page.put(fieldValidator.getPage(), fieldValidators);
			}
			fieldValidators.add(fieldValidator);
			
			Collections.sort(fieldValidators, new Comparator<FieldValidatorBean>() {

				@Override
				public int compare(FieldValidatorBean o1, FieldValidatorBean o2) {
					return o1.getSeq().compareTo(o2.getSeq() );
				}

			});
		}
		return page;
	}

	@Override
	public Map<String, String> getActionInputResult() throws RollBackException ,NonRollBackException{
		Map<String, String> actionInputResult = new ConcurrentHashMap<String, String>();
		List<ActionBean> actionBeans = nativeQuery(SQL_QUERY_ACTION_INPUT_RESULT,ACTION_MAPPER);//(SQL_QUERY_CONFIG, new configMapper());
		
		for (ActionBean actionBean : actionBeans) {
			if(actionBean.getInputResultName()!=null&&(actionBean.getActionName()!=null||actionBean.getNameSpace()!=null))
			actionInputResult.put(actionBean.getNameSpace()+"/"+actionBean.getActionName(), actionBean.getInputResultName());
		}
		return actionInputResult;
	}
	public static final String SQL_QUERY_ACTION_INPUT_RESULT = "SELECT * FROM SYS_M_ACTION  WHERE STATUS = 'A' ";
	
	private static final ActionMapper<ActionBean> ACTION_MAPPER = new ActionMapper<ActionBean>();
	public static final class ActionMapper<T extends ActionBean> implements RowMapper<ActionBean> {

	    public ActionBean mapRow(ResultSet rs, int num)throws SQLException {
	    	ActionBean displayFiled = new ActionBean();
	    	displayFiled.setActionName(rs.getString("ACTION_NAME"));
	    	displayFiled.setNameSpace(rs.getString("NAME_SPACE"));
	    	displayFiled.setInputResultName(rs.getString("INPUT_RESULT_NAME"));
//	    	displayFiled.setParameter(rs.getString("PARAMETER"));
//	    	displayFiled.setMessage(rs.getString("MESSAGE"));
//	    	displayFiled.setMessageParameter(rs.getString("MESSAGE_PARAMETER"));
	        return displayFiled;
	    }
    }
	
	private static final String SQL_QUERY_CONFIG_BY_DATE = "SELECT ID_REF_DATA, CONFIG_KEY, CONFIG_VALUE FROM SYS_M_CONFIG_BY_DATE WHERE STATUS = 'A' ";
	
	@Override
	public Map<String, String> getConfigDateMap() throws RollBackException, NonRollBackException {
		Map<String, String> configMap = new ConcurrentHashMap<String, String>();
		List<ConfigByDate> configList = nativeQuery(SQL_QUERY_CONFIG_BY_DATE, CONFIG_BY_DATE_MAPPER);//(SQL_QUERY_CONFIG, new configMapper());
		for (ConfigByDate systemConfig : configList) {
			if (logger.isInfoEnabled()) {
				logger.info("Config loading... " + systemConfig);
			}	
			configMap.put(systemConfig.getKey(), systemConfig.getValue());
		}
		return configMap;
	}
	
	private static final ConfigByDateMapper<ConfigByDate> CONFIG_BY_DATE_MAPPER = new ConfigByDateMapper<ConfigByDate>();
	public static final class ConfigByDateMapper<T extends ConfigByDate> implements RowMapper<ConfigByDate> {

	    public ConfigByDate mapRow(ResultSet rs, int num)throws SQLException {
	    	ConfigByDate configByDate = new ConfigByDate();
	    	configByDate.setKey( rs.getString("ID_REF_DATA")+ "_"+rs.getString("CONFIG_KEY"));
	    	configByDate.setValue( rs.getString("CONFIG_VALUE"));
	    	configByDate.setIdRefData(Long.parseLong(rs.getString("ID_REF_DATA")));
	    	
	        return configByDate;
	    }
    } 
}

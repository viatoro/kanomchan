package org.kanomchan.core.common.service;

import java.io.IOException;

import javax.annotation.PostConstruct;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

import org.kanomchan.core.common.bean.LocationBean;
import org.kanomchan.core.common.context.ApplicationContextUtil;
import org.kanomchan.core.common.dao.LocationDao;
import org.kanomchan.core.common.exception.NonRollBackException;
import org.kanomchan.core.common.exception.RollBackException;
import org.kanomchan.core.common.processhandler.ServiceResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.context.ResourceLoaderAware;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.transaction.annotation.Transactional;

import com.googlecode.ehcache.annotations.Cacheable;
import com.googlecode.ehcache.annotations.TriggersRemove;
import com.maxmind.geoip.Location;
import com.maxmind.geoip.LookupService;

public class LocationServiceImpl implements LocationService {

	private LookupService lookupService;
	private LocationDao locationDao;
	@Autowired
	@Required
	public void setLocationDao(LocationDao locationDao) {
		this.locationDao = locationDao;
	}
//	private ResourceLoader resourceLoader;
//	 
//	public void setResourceLoader(ResourceLoader resourceLoader) {
//		this.resourceLoader = resourceLoader;
//	}

	@Override
	@Transactional
	@Cacheable(cacheName = "locationServiceImpl.getLocation")
	public ServiceResult<LocationBean> getLocation(String ipAddress) throws RollBackException,NonRollBackException {
//		if(lookupService==null)
//			init();
		LocationBean locationBean = null;
		
		if(lookupService!=null){
			Location location = lookupService.getLocation(ipAddress);
			if(location == null)
				location = lookupService.getLocationV6(ipAddress);
			if(location != null)
				locationDao.getLocation(location.countryCode, location.countryName, location.region, location.city, location.postalCode);
			else
				locationBean = new LocationBean();
		}else{
			locationBean = new LocationBean();
		}
		return new ServiceResult<LocationBean>(locationBean );
	}

	@PostConstruct
	public void init() {
		try {
			lookupService = new LookupService(getClass().getResource("//GeoLiteCity.dat").getFile(),LookupService.GEOIP_MEMORY_CACHE );
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public void destroy() {
		lookupService.close();
	}
	
	@Override
	@TriggersRemove(cacheName="locationServiceImpl.getLocation", removeAll=true)
	public void refresh() {
		locationDao.refresh();
	}

}

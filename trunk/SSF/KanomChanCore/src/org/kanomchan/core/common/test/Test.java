package org.kanomchan.core.common.test;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.kanomchan.core.common.bean.PagingBean.ORDER_MODE;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class Test {

	public static void main(String[] args) {
//		// TODO Auto-generated method stub
////		Set<String> key = new HashSet<String>();
////		
////		key.add("222");
////		key.add("223");
////		key.add("444");
////		key.add("555");
////		Set<String> codes = new HashSet<String>();
////		
////		codes.add("555");
////		System.out.println(key.containsAll(codes));
//		Calendar curTime = Calendar.getInstance();
//		
//		
//		curTime = Calendar.getInstance();
//		System.out.println(curTime.getTimeInMillis());
//		curTime = Calendar.getInstance();
//		System.out.println(curTime.getTimeInMillis());
//		curTime = Calendar.getInstance();
//		System.out.println(curTime.getTimeInMillis());
//		curTime = Calendar.getInstance();
//		System.out.println(curTime.getTimeInMillis());
//		curTime = Calendar.getInstance();
//		System.out.println(curTime.getTimeInMillis());
//		curTime = Calendar.getInstance();
//		System.out.println(curTime.getTimeInMillis());
//		
//		curTime = Calendar.getInstance();
//		System.out.println(curTime.getTime().getTime());
//		curTime = Calendar.getInstance();
//		System.out.println(curTime.getTime().getTime());
//		curTime = Calendar.getInstance();
//		System.out.println(curTime.getTime().getTime());
//		curTime = Calendar.getInstance();
//		System.out.println(curTime.getTime().getTime());
//		curTime = Calendar.getInstance();
//		System.out.println(curTime.getTime().getTime());
//		curTime = Calendar.getInstance();
//		System.out.println(curTime.getTime().getTime());
//		curTime = Calendar.getInstance();
//		System.out.println(curTime.getTime().getTime());
//
//		try {
//			LookupService lookupService = new LookupService("/GeoLiteCity.dat",LookupService.GEOIP_MEMORY_CACHE );
//			Location location = lookupService.getLocation("127.0.0.1");
//			 System.out.println(" countryCode: " + location.countryCode +
//                   "\n countryName: " + location.countryName +
//                   "\n region: " + location.region +
//                   "\n regionName: " + regionName.regionNameByCode(location.countryCode, location.region) +
//                   "\n city: " + location.city +
//                   "\n postalCode: " + location.postalCode +
//                   "\n latitude: " + location.latitude +
//                   "\n longitude: " + location.longitude +
////                   "\n distance: " + location.distance(l1) +
////                   "\n distance: " + l1.distance(location) +
//	       "\n metro code: " + location.metro_code +
//	       "\n area code: " + location.area_code +
//                   "\n timezone: " + timeZone.timeZoneByCountryAndRegion(location.countryCode, location.region));
//			Gson gson = new Gson();
//			Type typeOfSrc = new TypeToken<List<String>>(){}.getType();
//			List<String> list = new ArrayList<String>();
//			
//			list.add("ww");
//			list.add("ww");
//			String s = gson.toJson(list, typeOfSrc);
//			System.out.println(s);
		
		System.out.println(ORDER_MODE.ASC.equals(ORDER_MODE.ASC));
//			List<String> extraParams = gson.fromJson(fieldValidatorBean.getParameter(), typeOfSrc);
			 
//			 
//			 System.out.println(gson.toJson(location));
//			 lookupService.close();
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
	}

}

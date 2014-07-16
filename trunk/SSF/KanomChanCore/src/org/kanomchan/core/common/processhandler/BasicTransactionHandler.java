package org.kanomchan.core.common.processhandler;

import org.apache.log4j.Logger;

import java.lang.reflect.Method;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.DefaultTransactionDefinition;

public class BasicTransactionHandler  implements TransactionHandler{
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(BasicTransactionHandler.class);
	private PlatformTransactionManager platformTransactionManager;
	@Autowired
	@Required
	public void setPlatformTransactionManager(PlatformTransactionManager platformTransactionManager) {
		this.platformTransactionManager = platformTransactionManager;
	}
	@Override
	public boolean isTxnProcess(ProceedingJoinPoint joinPoint){
		
		
		//== STEP 1 : Check is Class declare @Transactional 
		Class<?> targetInterface = joinPoint.getSignature().getDeclaringType();
		if( targetInterface.isAnnotationPresent(Transactional.class) ){
			return true;
		}
		
		Class<? extends Object> targetImplClass = joinPoint.getTarget().getClass();
		if( targetImplClass.isAnnotationPresent(Transactional.class) ){
			return true;
		}

		//== STEP 2 : Check is Method declare @Transactional
		MethodSignature methodSignature = (MethodSignature)joinPoint.getSignature();
		Method targetInterfaceMethod = methodSignature.getMethod();
				
		if( targetInterfaceMethod != null && targetInterfaceMethod.isAnnotationPresent(Transactional.class) ){
			return true;
		}

		try {
			Method targetImplMethod = targetImplClass.getMethod(targetInterfaceMethod.getName(), targetInterfaceMethod.getParameterTypes());
			if( targetImplMethod != null && targetImplMethod.isAnnotationPresent(Transactional.class) ){
				return true;
			}
		} 
		catch (SecurityException e) {
			e.printStackTrace();
		} 
		catch (NoSuchMethodException e) {
			e.printStackTrace();
		}
		
		//if targetClass and targetMethod have no @Transactional -> return false	
		return false;
	}

	@Override
	public void beginTxn(ProcessContext processContext) {
		try {
			if( processContext.txnStatus == null ){
				System.out.println("beginTxn");
				TransactionDefinition txnDefinition = new DefaultTransactionDefinition(DefaultTransactionDefinition.PROPAGATION_REQUIRED);
				TransactionStatus txnStatus = platformTransactionManager.getTransaction(txnDefinition);	
				logger.debug("TransactionStatus.isCompleted "+txnStatus.isCompleted());
				processContext.txnStatus = txnStatus;
			}
		} 
		catch( Exception e ) {
			logger.error("beginTxn(ServiceContext)", e); //$NON-NLS-1$
		}
		
	}
	@Override
	public void commitTxn(ProcessContext processContext){
		TransactionStatus txnStatus = processContext.txnStatus;
		if( txnStatus != null ){
			processContext.txnStatus = (null);
			System.out.println("commitTxn");
			platformTransactionManager.commit(txnStatus);
			
		}
		
		
	}
	
	@Override
	public  <T> T unProxy(T returnValue, boolean fristProcess){
		if(returnValue!=null &&returnValue instanceof ServiceResult){
			
			if(fristProcess){
				ServiceResult serviceResult = (ServiceResult) returnValue;
				serviceResult.setResult(clearUnproxy(serviceResult.getResult()));
			}
			
		}else{
			
			if(fristProcess){
				returnValue = clearUnproxy(returnValue);
			}
		}
		return returnValue;
	}
	
	private <T> T clearUnproxy(T entity)  {
//	    if (entity == null) {
//	    	return entity;
//	    }
//
//	    if (entity instanceof HibernateProxy) {
//	    	HibernateProxy hibernateProxy =((HibernateProxy) entity);
//	    	
//	    	LazyInitializer lazyInitializer = hibernateProxy.getHibernateLazyInitializer();
//	    	if(lazyInitializer.isUninitialized()){
//	    		T out = null;
//	    		try {
//	    			Class cls = Class.forName(lazyInitializer.getEntityName());
//					out = (T) cls.newInstance();
//					Field[] fs = cls.getDeclaredFields();
//					for (Field field : fs) {
//						if(field.isAnnotationPresent(Id.class)){
//							String nameset = "set"+field.getName().substring(0, 1).toUpperCase()+field.getName().substring(1);
//							Method methodSet = out.getClass().getMethod(nameset,lazyInitializer.getIdentifier().getClass());
//							methodSet.invoke(out, lazyInitializer.getIdentifier());
//							break;
//						}else{
//							String nameset = "set"+field.getName().substring(0, 1).toUpperCase()+field.getName().substring(1);
//							Method methodSet = cls.getMethod(nameset,lazyInitializer.getIdentifier().getClass());
//							if(methodSet.isAnnotationPresent(Id.class)){
//								methodSet.invoke(out, lazyInitializer.getIdentifier());
//								break;
//							}else{
//								String nameget = "get"+field.getName().substring(0, 1).toUpperCase()+field.getName().substring(1);
//								Method methodGet = cls.getMethod(nameget);
//								if(methodGet.isAnnotationPresent(Id.class)){
//									methodSet.invoke(out, lazyInitializer.getIdentifier());
//									break;
//								}
//							}
//							
//						}
//					}
//				} catch (ClassNotFoundException e) {
//					e.printStackTrace();
//				} catch (NoSuchMethodException e) {
//					e.printStackTrace();
//				} catch (SecurityException e) {
//					e.printStackTrace();
//				} catch (InstantiationException e) {
//					e.printStackTrace();
//				} catch (IllegalAccessException e) {
//					e.printStackTrace();
//				} catch (IllegalArgumentException e) {
//					e.printStackTrace();
//				} catch (InvocationTargetException e) {
//					e.printStackTrace();
//				}
//	    		entity =out;
//	    	}else{
//	    		entity = (T) lazyInitializer.getImplementation();
//	    	}
//	    }
//	    
//	    Class<? extends Object> class1   = entity.getClass();
//		Field[] fields = entity.getClass().getDeclaredFields();
//		if(fields!=null){
//			for (Field field : fields) {
//				String nameget = "get"+field.getName().substring(0, 1).toUpperCase()+field.getName().substring(1);
//				String nameset = "set"+field.getName().substring(0, 1).toUpperCase()+field.getName().substring(1);
//				try {
//					if(!field.isAccessible()&&java.lang.reflect.Modifier.isStatic(field.getModifiers())){
//						Method methodGet = class1.getMethod(nameget);
//						Method methodSet = class1.getMethod(nameset,methodGet.getReturnType());
//						Object o = methodGet.invoke(entity);
//						if(o instanceof HibernateProxy){
//							methodSet.invoke(entity, clearUnproxy(o));
//						}
//					}
//				} catch (NoSuchMethodException e) {
//					e.printStackTrace();
//				} catch (SecurityException e) {
//					e.printStackTrace();
//				} catch (IllegalAccessException e) {
//					e.printStackTrace();
//				} catch (IllegalArgumentException e) {
//					e.printStackTrace();
//				} catch (InvocationTargetException e) {
//					e.printStackTrace();
//				}
//				
//				
//			}
//		}
//	    
//	    return entity;
		return entity;
	}
	@Override
	public void rollbackTxn(ProcessContext processContext){
		TransactionStatus txnStatus = processContext.txnStatus;
		if( txnStatus != null ){
			processContext.txnStatus = (null);
			System.out.println("rollback");
			platformTransactionManager.rollback(txnStatus);
		}
		
	}

}
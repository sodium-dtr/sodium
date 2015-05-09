package space.sodium.builder;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.ParameterizedType;
import java.util.Collection;
import java.util.HashMap;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang3.ArrayUtils;

import space.sodium.entity.Representation;
import space.sodium.entity.Represents;
import space.sodium.exception.RepresentationException;

public class Builder {
	
	private HashMap<Class<?>, Class<?>> register = new HashMap<Class<?>, Class<?>>();
	
	public void register(Class<?> modelClass, Class<?> representationClass) {
		register.put(modelClass, representationClass);
	}
	
	
	public Object getRep(Object sourceEntity) throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException {
		Class<?> repClass = register.get(sourceEntity.getClass());
		return getRep(sourceEntity, repClass);
	}
	
	public Object getRep(Object sourceEntity, Class<?> repClass) throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException {
		Object rep = repClass.getConstructor(new Class<?>[0]).newInstance(new Object[0]);
		
		for (Field targetField : repClass.getDeclaredFields()) {
			setValue(rep, targetField, sourceEntity);
		}
		return rep;
	}
	
	private void setValue(Object rep, Field targetField, Object sourceEntity) {
		
		Represents represents = targetField.getAnnotation(Represents.class);
		
		try {
			Object value = getValue(represents, sourceEntity, targetField);
			
			Representation representation = getRepresentation(value, targetField);
			
			if (value != null && representation != null) {
				if (value instanceof Collection<?>) {
//					ParameterizedType parameterizedType = (ParameterizedType) targetField.getGenericType();
//					Class<?> listClass = (Class<?>) parameterizedType.getActualTypeArguments()[0];
//					IRepBuilder<?, ?> repBuilder = builderContext.getBuilder(listClass);
//					TODO: fix this
//					PropertyUtils.setProperty(rep, targetField.getName(), buildList(value, repBuilder));
					
				} else {
					PropertyUtils.setProperty(rep, targetField.getName(), getRep(value));
				}
				
			} else if (value != null && value.getClass().isEnum()) {
				PropertyUtils.setProperty(rep, targetField.getName(), value.toString());
				
			} else {
				PropertyUtils.setProperty(rep, targetField.getName(), value);
			}
		} catch (NoSuchMethodException e) {
			// Do nothing
			
		} catch (Exception e) {
			throw new RepresentationException("Couldn't access " + targetField.getName() + " from source object: " + sourceEntity.getClass().getName(), e);
		}
	}
	
	private Object getValue(Represents represents, Object sourceEntity, Field targetField) throws IllegalAccessException, InvocationTargetException, NoSuchMethodException {
		if (represents == null || "".equals(represents.value())) {
			return PropertyUtils.getProperty(sourceEntity, targetField.getName());
		} else {
			return getSourceValue(sourceEntity, represents.value().split("\\."));
		}
	}
	
	private Representation getRepresentation(Object value, Field targetField) {
		if (value instanceof Collection<?>) {
			ParameterizedType parameterizedType = (ParameterizedType) targetField.getGenericType();
			Class<?> listClass = (Class<?>) parameterizedType.getActualTypeArguments()[0];
			return listClass.getAnnotation(Representation.class);
		} else {
			return targetField.getType().getAnnotation(Representation.class);
		}
	}
	
//	private List<Object> buildList(Object values, IRepBuilder<?, ?> repBuilder) {
//		
//		List<Object> repList = new ArrayList<Object>();
//		for (Object value : (Collection<?>) values) {
//			repList.add(repBuilder.get(value));
//		}
//		return repList;
//	}
	
	private Object getSourceValue(Object sourceEntity, String[] pathParts) throws IllegalAccessException, InvocationTargetException, NoSuchMethodException {
		
		if (pathParts.length == 1) {
			return PropertyUtils.getProperty(sourceEntity, pathParts[0]);
		} else {
			Object nextEntity = PropertyUtils.getProperty(sourceEntity, pathParts[0]);
			pathParts = ArrayUtils.remove(pathParts, 0);
			
			return getSourceValue(nextEntity, pathParts);
		}
	}
	
//	private Object getSourceEntity(Class<?> sourceClass, Object[] sourceEntities) throws RepresentationException {
//		
//		for (Object se : sourceEntities) {
//			if (se.getClass().equals(sourceClass)) {
//				return se;
//			}
//		}
//		throw new RepresentationException(sourceClass.getName() + " not found in source entity array.");
//	}
//	
//	private Class<?> getSourceClass(Class<?> repClass, Represents represents) throws RepresentationException {
//		
//		Representation representation = repClass.getAnnotation(Representation.class);
//		if (representation == null) {
//			throw new RepresentationException("Representation class (" + repClass + ") must be annotated with @Representation.");
//		}
//		String defaultPackage = representation.defaultPackage();
//		String defaultEntity = representation.defaultEntity();
//		String representsValue = represents == null ? null : represents.value();
//		try {
//			return Class.forName(representsValue);
//			
//		} catch (Exception e) {
//			try {
//				return Class.forName(defaultPackage + "." + representsValue);
//				
//			} catch (Exception e1) {
//				try {
//					return Class.forName(defaultEntity);
//					
//				} catch (Exception e2) {
//					try {
//						return Class.forName(defaultPackage + "." + defaultEntity);
//						
//					} catch (Exception e3) {
//						throw new RepresentationException("Cannot resolve source class for " + repClass.getName());
//					}
//				}
//			}
//		}
//	}
	
	public Object getEntity(Object rep) {
		return null;
	}
}

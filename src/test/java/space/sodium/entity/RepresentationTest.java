package space.sodium.entity;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

import org.junit.Test;

import space.sodium.entity.rep.UserRep;

public class RepresentationTest {
	
	@Test
	public void test() throws Exception {
		
		UserRep userRep = new UserRep();
		
		Class<? extends Object> clazz = userRep.getClass();
		
		System.out.println("Class: " + clazz.getName());
		for (Annotation annotation : clazz.getDeclaredAnnotations()) {
			Class<? extends Annotation> type = annotation.annotationType();
			System.out.println("\t\tAnnotation: " + type);
			
			for (Method method : type.getDeclaredMethods()) {
				System.out.println("\t\t\t" + method.getName() + ": '" + method.invoke(annotation, (Object[]) null) + "'");
			}
		}
		System.out.println("");
		for (Field field : clazz.getDeclaredFields()) {
			
			System.out.println("\tField: " + field.getName());
			for (Annotation annotation : field.getDeclaredAnnotations()) {
				Class<? extends Annotation> type = annotation.annotationType();
				System.out.println("\t\tAnnotation: " + type);
				
				for (Method method : type.getDeclaredMethods()) {
					System.out.println("\t\t\t" + method.getName() + ": '" + method.invoke(annotation, (Object[]) null) + "'");
				}
			}
			System.out.println("");
		}
		System.out.println("\n");
	}
}

package br.com.cursojava.todolist.utils;

import java.beans.PropertyDescriptor;
import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;

public class Utils {

  public static void  copyNonNullProperties(Object source, Object target) {
    org.springframework.beans.BeanUtils.copyProperties(source, target, getNullPropertyNames(source));
  }

  public static String[] getNullPropertyNames(Object source) {
    final BeanWrapper src = new BeanWrapperImpl(source);

    PropertyDescriptor[] pds = src.getPropertyDescriptors();

    Set<String> propertyNames = new HashSet<String>();

    for(PropertyDescriptor pd : pds){
      if (src.getPropertyValue(pd.getName()) == null)
        propertyNames.add(pd.getName());
    }

    String[] result = new String[propertyNames.size()];
    return propertyNames.toArray(result);
  }

}

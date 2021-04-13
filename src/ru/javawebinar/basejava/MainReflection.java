package ru.javawebinar.basejava;

import ru.javawebinar.basejava.model.Resume;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class MainReflection {
    public static void main(String[] args) throws IllegalAccessException, NoSuchMethodException,
            InvocationTargetException {
        Resume r = new Resume();
        System.out.println(r);
        Class<? extends Resume> resumeClass = r.getClass();
        Field field = resumeClass.getDeclaredFields()[0];
        field.setAccessible(true);
        System.out.println("Field name: " + field.getName());
        System.out.println("Field value: " + field.get(r));
        field.set(r, "new_uuid");
        System.out.println("Changed field value: " + field.get(r));

        Method method = resumeClass.getMethod("toString");
        System.out.println("Invocation r.toString using Reflection API: ");
        System.out.println(method.invoke(r));
    }
}

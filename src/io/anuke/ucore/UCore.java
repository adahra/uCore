package io.anuke.ucore;

import com.badlogic.gdx.Application.ApplicationType;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.reflect.ClassReflection;
import com.badlogic.gdx.utils.reflect.Field;
import com.badlogic.gdx.utils.reflect.Method;
import com.badlogic.gdx.utils.reflect.ReflectionException;
import io.anuke.ucore.core.Timers;
import io.anuke.ucore.util.Log;
import io.anuke.ucore.util.OS;

public class UCore{

    public static void profile(int iterations, Runnable c1, Runnable c2){
        //warmup
        for(int i = 0; i < iterations; i++){
            c1.run();
            c2.run();
        }

        Timers.mark();
        for(int i = 0; i < iterations; i++){
            c1.run();
        }
        Log.info("Time taken for procedure 1: {0}ms", Timers.elapsed());

        Timers.mark();
        for(int i = 0; i < iterations; i++){
            c2.run();
        }
        Log.info("Time taken for procedure 2: {0}ms", Timers.elapsed());
    }

    public static boolean isAssets(){
        return Gdx.app != null && Gdx.app.getType() != ApplicationType.WebGL
                && OS.getProperty("user.name").equals("anuke")
                && getAbsolute(Gdx.files.local("na").parent()).endsWith("assets");
    }

    public static String getAbsolute(FileHandle file){
        try{
            Method method = ClassReflection.getMethod(file.getClass(), "file");
            Object object = method.invoke(file);
            Method fm = ClassReflection.getMethod(object.getClass(), "getAbsolutePath");
            return (String) fm.invoke(object);
        }catch(ReflectionException e){
            throw new RuntimeException(e);
        }
    }

    public static Object getPrivate(Object object, String name){
        try{
            Field field = ClassReflection.getDeclaredField(object.getClass(), name);
            field.setAccessible(true);
            return field.get(object);
        }catch(ReflectionException e){
            throw new RuntimeException(e);
        }
    }

    public static Object getPrivate(Class<?> type, Object object, String name){
        try{
            Field field = ClassReflection.getDeclaredField(type, name);
            field.setAccessible(true);
            return field.get(object);
        }catch(ReflectionException e){
            throw new RuntimeException(e);
        }
    }

    public static void setPrivate(Object object, String name, Object value){
        try{
            Field field = ClassReflection.getDeclaredField(object.getClass(), name);
            field.setAccessible(true);
            field.set(object, value);
        }catch(ReflectionException e){
            throw new RuntimeException(e);
        }
    }
}

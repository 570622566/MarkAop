package com.mark.aoplibrary.aspect;

import android.util.Log;
import android.widget.Toast;

import com.mark.aoplibrary.MarkAOPHelper;
import com.mark.aoplibrary.Utils;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;

/**
 * <pre>
 *     author : Mark
 *     e-mail : makun.cai@aorise.org
 *     time   : 2018/05/30
 *     desc   : 检查网络动态注入类
 *     version: 1.0
 * </pre>
 */
@Aspect
public class CheckNetAspect {

    @Pointcut("execution(@com.mark.aoplibrary.annotation.CheckNet * *(..))")
    public void methodAnnotated() {
    }

    @Around("methodAnnotated()")
    public void aroundJoinPoint(ProceedingJoinPoint joinPoint) throws Throwable {
        if (Utils.isConnected()) {
            joinPoint.proceed();
        }else {
            Toast.makeText(MarkAOPHelper.getInstance().getApplication(),"网络暂时不可用，请检查网络",Toast.LENGTH_SHORT).show();
        }
    }
}

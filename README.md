# MarkAop
面向切面编程-使代码更加清晰————工具使用aspectj

基于aspectj的AOP，无需使用耗费性能的反射.不过,需要在build.gradle中配置一下aspectj
  
AOP切面框架：

    buildscript {
       repositories {
          maven { url "https://raw.githubusercontent.com/zfl5232577/maven/master" }
      }
      dependencies {
          classpath 'com.mark:markaop-plugin:1.0.0'
      }
    }
    
    allprojects {
      repositories {
          maven { url "https://raw.githubusercontent.com/zfl5232577/maven/master" }
      }
    }
    
Module的build.gradle文件

    apply plugin: 'markaop-plugin'
    
框架初始化:
---
```Java在Appliction的onCreate

	MarkAOPHelper.getInstance().init(context);
  	MarkAOPHelper.getInstance().getOptions().setDebug(BuildConfig.DEBUG);
  	MarkAOPHelper.getInstance().getOptions().setLoginActivity(LoginActivity.class,LoginActivity.REQUEST_CODE);
  
```

登陆成功后一定记得调用：

    SPUtils.getInstance().put("isLogin",true);
    
BaseActivity添加下面函数和变量：

	
	
	public class BaseActivity extends AppCompatActivity {
	    private List<Method> mMethodList = new ArrayList<>();
	    private HashMap<String, Object[]> mMethodArgs = new HashMap<>();

	    @Override
	    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (resultCode != RESULT_OK) {
		    return;
		}
		switch (requestCode) {
		    case LoginActivity.REQUEST_CODE:
			for (Method method : mMethodList) {
			    try {
				method.setAccessible(true);
				if (mMethodArgs.get(method.getName()) != null) {
				    method.invoke(this, mMethodArgs.get(method.getName()));
				}else {
				    method.invoke(this);
				}
			    } catch (IllegalAccessException e) {
				e.printStackTrace();
			    } catch (InvocationTargetException e) {
				e.printStackTrace();
			    }
			}
			mMethodList.clear();
			break;
		}
	    }

	    @Override
	    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
		super.onRequestPermissionsResult(requestCode, permissions, grantResults);
		MPermissionUtils.onRequestPermissionsResult(requestCode, permissions, grantResults);
	    }
	}


| 注解名称         | 作用          | 备注          |
| -------------   |:-------------:| :-------------:|
| @Async          |借助rxjava,异步执行app中的方法|       |
| @CheckLogin     |检查是否登陆，设置了登陆界面直接登陆界面，登陆完成后自己执行上此操作|
| @Logger         |将方法的入参和出参都打印出来,可以用于调试|       |
| @CheckNet       |可以在调用某个方法之前，检查网络连接状况，没有连接不操作并弹出Toast|
| @CheckPermission|适配6.0动态权限，没有权限并且申请权限|
| @SingleClick    |防止按钮被连点，执行重复操作，点击间隔为600ms|支持追踪匿名内部类中的方法    |
| @TimeLog        |用于追踪某个方法花费的时间,可以用于性能调优的评判|支持追踪匿名内部类中的方法       |
  
  

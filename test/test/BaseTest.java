package test;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;

/** * @author chinajash */
public class BaseTest {
	public static void main(String[] args) throws Exception { 
		ScriptEngineManager factory = new ScriptEngineManager();
		//step 1 
		ScriptEngine engine = factory.getEngineByName("JavaScript");
		//Step 2 
		engine.eval("print('Hello, Scripting')");
		//Step 3
		} }
/*运行上面程序，控制台会输出Hello, Scripting上面这个简单的Scripting程序演示了如何在Java里面运行脚本语言，除此之外，我们还可以利用Scripting API实现以下功能
1、暴露Java对象为脚本语言的全局变量
2、在Java中调用脚本语言的方法
3、脚本语言可以实现Java的接口
4、脚本语言可以像Java一样使用JDK平台下的类下面的类演示了以上
4种功能	}
*/

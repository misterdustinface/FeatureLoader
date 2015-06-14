package FeatureLoaderImp;

import org.luaj.vm2.Globals;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.lib.jse.CoerceJavaToLua;
import org.luaj.vm2.lib.jse.JsePlatform;

import FeatureLoader.FeatureFinder;
import FeatureLoader.FeatureLoader;

public class LuaJFeatureLoader extends FeatureLoader {
	
	final private Globals GLOBALS;
	final private String  FEATURE_LOADER_SCRIPT_LUAJ = "LuaJIntegration/FeatureLoader.lua";
	final private String  ORDERING_FILE              = "_order.lua";
	
	public LuaJFeatureLoader() {
		GLOBALS = JsePlatform.standardGlobals();
		loadLuaFeatureLoaderFunctionsToGlobalFunctions();
		exposeThisFeatureLoaderToLua();
	}
	
	public void setApplication(Object app) {
		LuaValue setApp = GLOBALS.get("setApplication");
		setApp.call(CoerceJavaToLua.coerce(app));
	}
	
	public void loadFileAsFeature(String filepath) {
		callFunctionWithStringArgument("loadFileAsFeature", filepath);
	}
	
	private LuaValue callFunctionWithStringArgument(String functionname, String argument) {
		LuaValue function = GLOBALS.get(functionname);
		return function.call(LuaValue.valueOf(argument));
	}
	
	private void loadLuaFeatureLoaderFunctionsToGlobalFunctions() {
		LuaValue chunk = GLOBALS.loadfile(FEATURE_LOADER_SCRIPT_LUAJ);
		chunk.call();
	}
	
	private void exposeThisFeatureLoaderToLua() {
		LuaValue function = GLOBALS.get("setLoader");
		function.call(CoerceJavaToLua.coerce(this));
	}
	
	protected FeatureFinder customFeatureFinder() {
		return new FeatureFinder() {

			protected String getOrderingFilePath(String directoryPath) {
				assert(directoryPath != null);
				return directoryPath + "/" + ORDERING_FILE;
			}

			protected String[] getOrder(String orderingFilePath) {
				LuaValue result = callFunctionWithStringArgument("getOrder", orderingFilePath);
				assert(result.istable());
				
				String[] orderingList = new String[result.length()];
				for (int i = 0; i < result.length(); i++) {
					orderingList[i] = result.get(i + 1).toString();
					System.out.println(orderingList[i]);
				}
				return orderingList;
			}
			
		};
	}
	
}
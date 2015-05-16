package FeatureLoaderImp;

import org.luaj.vm2.Globals;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.lib.jse.CoerceJavaToLua;
import org.luaj.vm2.lib.jse.JsePlatform;

import FeatureLoader.FeatureLoader;

public class LuaJFeatureLoader implements FeatureLoader {
	
	private Globals globals;
	private String featureLoaderEngineScriptFile = "LuaJFeatureLoaderEngine/FeatureLoader.lua";
	
	public LuaJFeatureLoader() {
		globals = JsePlatform.standardGlobals();
		loadLuaFeatureLoaderFunctionsToGlobalFunctions();
	}
	
	public void setApplication(Object app) {
		LuaValue function = globals.get("setApplication");
		function.call(CoerceJavaToLua.coerce(app));
	}
	
	public Object getApplication() {
		return getGlobalUserObject("APPLICATION");
	}
	
	public void loadFeatures(String featuresFolder) {
		callFunctionWithArgument("loadFeatures", featuresFolder);
	}
	
	public void displayLoadedFiles() {
		callFunction("displayLoadedFiles");
	}
	
	private void callFunction(String functionname) {
		LuaValue function = globals.get(functionname);
		function.call();
	}
	
	private void callFunctionWithArgument(String functionname, String argument) {
		LuaValue function = globals.get(functionname);
		function.call(LuaValue.valueOf(argument));
	}
	
	private void loadLuaFeatureLoaderFunctionsToGlobalFunctions() {
		LuaValue chunk = globals.loadfile(featureLoaderEngineScriptFile);
		chunk.call();
	}
	
	private Object getGlobalUserObject(String name) {
		return globals.get(name).touserdata();
	}
	
}
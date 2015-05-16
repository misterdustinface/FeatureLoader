package FeatureLoader;



public interface FeatureLoader {
	
	public void setApplication(Object app);
	public Object getApplication();
	public void loadFeatures(String featuresFolder);
	public void displayLoadedFiles();
	
}
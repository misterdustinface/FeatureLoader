package FeatureLoader;



abstract public class FeatureLoader {
	
	final private FeatureFinder finder;
	
	public FeatureLoader() {
		finder = customFeatureFinder();
	}
	
	public abstract void setApplication(Object app);
	
	public void loadFeatures(String featuresFolder) {
		String[] filepaths = finder.gatherFilesFrom(featuresFolder);
		for (String filepath : filepaths) {
			System.out.println(filepath);
			loadFileAsFeature(filepath);
		}
	}
	
	protected abstract void loadFileAsFeature(String filepath);
	protected abstract FeatureFinder customFeatureFinder();
	
}
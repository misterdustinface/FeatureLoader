package FeatureLoader;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;

abstract public class FeatureFinder {
	
	public String[] gatherFilesFrom(String filepath) {
		ArrayList<String> basket = new ArrayList<String>();
		if (isDirectory(filepath)) {
			addAll(basket, gatherFilesFromDirectory(filepath));
		} else if (isFile(filepath)) {
			add(basket, filepath);
		}
		return basket.toArray(new String[basket.size()]);
	}
	
	protected abstract String getOrderingFilePath(String directoryPath);
	protected abstract String[] getOrder(String orderingFilePath);
	
	private boolean isDirectory(String filepath) {
		File f = new File(filepath);
		return f.isDirectory();
	}
	
	private boolean isFile(String filepath) {
		File f = new File(filepath);
		return f.isFile();
	}
	
	private String[] gatherFilesFromDirectory(String directoryPath) {
		String[] paths;
		if (hasOrderingFile(directoryPath)) {
			paths = getFilePathsFromOrderingFile(directoryPath);
		} else {
			paths = getFilePathsInDirectory(directoryPath);
		}
		return gatherFilesForPaths(paths);
	}
	
	private boolean hasOrderingFile(String directoryPath) {
		File f = new File(getOrderingFilePath(directoryPath));
		return f.exists();
	}
	
	private String[] getFilePathsFromOrderingFile(String directoryPath) {
		String orderingFilePath  = getOrderingFilePath(directoryPath);
		String[] orderingListing = getOrder(orderingFilePath);
		return concatPathToFilenames(directoryPath, orderingListing);
	}
	
	private String[] getFilePathsInDirectory(String directoryPath) {
		File directory = new File(directoryPath);
		File[] files = directory.listFiles();
		String[] filepaths = new String[files.length];
		for (int i = 0; i < files.length; i++) 
			filepaths[i] = files[i].getPath();
		return filepaths;
	}
	
	private String[] concatPathToFilenames(String filepath, String[] filenames) {
		for (int i = 0; i < filenames.length; i++) {
			filenames[i] = filepath + "/" + filenames[i];
		}
		return filenames;
	}
	
	private String[] gatherFilesForPaths(String[] paths) {
		ArrayList<String> basket = new ArrayList<String>();
		for (String path : paths) {
			String[] recursiveResult = gatherFilesFrom(path);
			addAll(basket, recursiveResult);
		}
		return basket.toArray(new String[basket.size()]);
	}
	
	private void addAll(Collection<String> A, String[] B) {
		for (String toAdd : B) {
			A.add(toAdd);
		}
	}
	
	private void add(Collection<String> A, String B) {
		A.add(B);
	}
	
}
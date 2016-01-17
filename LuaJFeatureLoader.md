## LuaJFeatureLoader

### Features
  - All lua files which reside within the designated features folder are Features
  - Features can access a global lua variable named APPLICATION
  - Features can be given a load order with a _order.lua file.  The _order.lua file is not necessary. 

### Ordering Features with a _order.lua file
  - Must return a lua table with the relative file paths of the Features in sequential order.
  - Can refer to a file or a directory

### Integration

#### In Java
  - Create an instance of LuaJFeatureLoader
  - Call setApplication on the instance, passing your application as an argument
  - Then call loadFeatures on the instance, passing a file path (string) as an argument

```Java
  GameLauncher() {
		game = new Game();
		loader = new LuaJFeatureLoader();
		loader.setApplication(game);
		loader.loadFeatures("features");
		game.start();
	}
```

  - loadFeatures can take a directory (string) as the file path; hence, the "features" directory was used.

#### In Lua
  - Create your features as lua scripts
  - These scripts can access a global 'APPLICATION'

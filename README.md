## FeatureLoader
Loads features (recursively) for an application

### Features in LuaJ (LuaJFeatureLoader)
  - All lua files which reside within the designated features folder are Features
  - Features can access a global lua variable named APPLICATION
  - Features can be given a load order with a _order.lua file.  The _order.lua file is not necessary. 

### Ordering LuaJFeatureLoader Features with a _order.lua file
  - Must return a lua table with the relative file paths of the Features in sequential order.
  - Can refer to a file or a directory

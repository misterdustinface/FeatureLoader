require("FeatureLoaderEngine/FeatureFinder")
local loadLuaFileAsFeature
local safeLoadLuaFile
local pcall2

function setApplication(appObject)
    APPLICATION = appObject
end

function loadFeatures(featuresFolderFilePath)
    local files = findFeatureFiles(featuresFolderFilePath)
    for _, filename in ipairs(files) do
        loadLuaFileAsFeature(filename)
    end
end

function displayLoadedFiles()
    DISPLAY_LOADED_FILES = true
end

function loadLuaFileAsFeature(filename)
    if DISPLAY_LOADED_FILES then print(filename) end
    local chunk = pcall2(safeLoadLuaFile, filename)
    if chunk then pcall2(chunk) end
end

function safeLoadLuaFile(filename)
    return assert(loadfile(filename), "Failed to load " .. filename .. " as a lua chunk")
end

function pcall2(...)
    local ok, outputOrError = pcall(...)
    if ok then
        return outputOrError
    else
        print(outputOrError)
    end
end

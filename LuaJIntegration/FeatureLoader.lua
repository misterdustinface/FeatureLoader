local safeLoadLuaFile
local pcall2

function setApplication(appObject)
    APPLICATION = appObject
end

function setLoader(featureloader)
    loadFeatures = function(location)
        featureloader:loadFeatures(location)
    end
end

function getOrder(orderingFilePath)
    local listingTable = dofile(orderingFilePath)
    return listingTable
end

function loadFileAsFeature(filepath)
    local chunk = pcall2(safeLoadLuaFile, filepath)
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
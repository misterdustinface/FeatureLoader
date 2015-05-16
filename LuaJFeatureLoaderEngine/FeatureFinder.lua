local gatherFeatureFiles
local isVisibleDirectory
local isVisibleFile
local mergetables
local gatherFeatureFilesFromDirectory
local concatPathToFile
local concatPathToFiles
local gatherFeatureFilesForPaths
local getFilePathsInDirectory

function gatherFeatureFiles(filepath)
    local files = {}
    if isVisibleDirectory(filepath) then
        mergetables(files, gatherFeatureFilesFromDirectory(filepath))
    elseif isVisibleFile(filepath) then
        table.insert(files, filepath)
    end
    return files
end

function isVisibleDirectory(filepath)
    local javaFile = luajava.newInstance("java.io.File", filepath)
    return javaFile:isDirectory() and not javaFile:isHidden()
end

function isVisibleFile(filepath)
    local javaFile = luajava.newInstance("java.io.File", filepath)
    return javaFile:isFile() and not javaFile:isHidden()
end

-- Because table.insert(A, table.unpack(B)) is fucked up in luaj 
function mergetables(A, B)
    for index = 1, #B do
        table.insert(A, B[index])
    end
end

function gatherFeatureFilesFromDirectory(directoryPath)
    local orderingFilePath = concatPathToFile(directoryPath, "_order.lua")
    local hasOrderingFile, ordering = pcall(dofile, orderingFilePath)
    
    if hasOrderingFile then
        local orderedPaths = concatPathToFiles(directoryPath, ordering)
        return gatherFeatureFilesForPaths(orderedPaths)
    else 
        local filePaths = getFilePathsInDirectory(directoryPath)
        return gatherFeatureFilesForPaths(filePaths)
    end
end

function concatPathToFile(filepath, file)
    return filepath .. "/" .. file
end

function concatPathToFiles(filepath, files)
    for index = 1, #files do
        files[index] = concatPathToFile(filepath, files[index])
    end
    return files
end

function gatherFeatureFilesForPaths(paths)
    local files = {}
    for i = 1, #paths do
        local recursiveResult = gatherFeatureFiles(paths[i])
        mergetables(files, recursiveResult)
    end
    return files
end

function getFilePathsInDirectory(directoryPath)
    local filepaths = {}
    local javaDir = luajava.newInstance("java.io.File", directoryPath)
    local javaFiles = javaDir:listFiles()
    for i = 1, javaFiles.length do
        filepaths[i] = javaFiles[i]:getPath()
    end
    return filepaths
end

findFeatureFiles = gatherFeatureFiles
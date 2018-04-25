package fr.gwombat.cmstest.core.path;

public interface CmsPath {
    /**
     * @return the path without any dynamic variable resolution
     */
    String getPath();

    /**
     * @return the path resolved with dynamic variables
     */
    String getResolvedPath();
}

package fr.gwombat.cmstest.configuration;

/**
 * Created by guillaume.
 *
 * @since 14/04/2018
 */
public interface CmsResultConfiguration {

    default char getPropertySeparator() {
        return '/';
    }

    default String getRootNodePrefix() {
        return getLanguage() + getPropertySeparator() + getRootNodeName() + getPropertySeparator();
    }

    default String getRootNodePath(final String nodeName) {
        return getRootNodePrefix() + nodeName;
    }

    String getRootNodeName();

    String getLanguage();

}

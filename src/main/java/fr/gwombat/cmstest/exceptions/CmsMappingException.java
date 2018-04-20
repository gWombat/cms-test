package fr.gwombat.cmstest.exceptions;

/**
 * Created by guillaume.
 *
 * @since 20/04/2018
 */
public class CmsMappingException extends CmsException {
    public CmsMappingException(String message) {
        super(message);
    }

    public CmsMappingException(Throwable cause) {
        super(cause);
    }
}

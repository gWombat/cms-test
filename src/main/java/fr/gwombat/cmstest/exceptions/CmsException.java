package fr.gwombat.cmstest.exceptions;

/**
 * Created by guillaume.
 *
 * @since 19/04/2018
 */
public class CmsException extends Exception {

    public CmsException(String message) {
        super(message);
    }

    public CmsException(Throwable cause) {
        super(cause);
    }
}

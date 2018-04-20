package fr.gwombat.cmstest.exceptions;

/**
 * Created by guillaume.
 *
 * @since 19/04/2018
 */
public class CmsRuntimeException extends RuntimeException {

    public CmsRuntimeException(String message) {
        super(message);
    }

    public CmsRuntimeException(Throwable cause) {
        super(cause);
    }
}

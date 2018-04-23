package fr.gwombat.cmstest.custom.jackrabbit;

import fr.gwombat.cmstest.core.CmsCallConfigWrapper;

public class JackrabbitCallConfigWrapper extends CmsCallConfigWrapper {

    private boolean callSpecificNodes;
    private boolean callDefaultNodes;

    public boolean isCallSpecificNodes() {
        return callSpecificNodes;
    }

    public void setCallSpecificNodes(boolean callSpecificNodes) {
        this.callSpecificNodes = callSpecificNodes;
    }

    public boolean isCallDefaultNodes() {
        return callDefaultNodes;
    }

    public void setCallDefaultNodes(boolean callDefaultNodes) {
        this.callDefaultNodes = callDefaultNodes;
    }
}

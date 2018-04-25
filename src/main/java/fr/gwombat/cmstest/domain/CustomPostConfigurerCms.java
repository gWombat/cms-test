package fr.gwombat.cmstest.domain;

import fr.gwombat.cmstest.core.configurers.CmsCallPostConfigurer;
import fr.gwombat.cmstest.core.path.CmsPath;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class CustomPostConfigurerCms implements CmsCallPostConfigurer {

    private static final Logger logger = LoggerFactory.getLogger(CustomPostConfigurerCms.class);

    @Override
    public void postConfigure(List<CmsPath> calls) {
        logger.debug("Custom post configurer called!!");
    }
}

package com.kodokux.magento;

import com.intellij.openapi.components.*;
import com.intellij.openapi.project.Project;
import com.intellij.util.xmlb.XmlSerializerUtil;
import org.jetbrains.annotations.Nullable;

@State(
        name = "MagentoIdeaSettings",
        storages = {
                @Storage(id = "default", file = StoragePathMacros.PROJECT_FILE),
                @Storage(id = "dir", file = StoragePathMacros.PROJECT_CONFIG_DIR + "/magento-idea.xml", scheme = StorageScheme.DIRECTORY_BASED)
        }
)

public class Settings implements PersistentStateComponent<Settings> {

    public static String DEFAULT_PHP_PATH = "/opt/local/bin/php";
    public static String DEFAULT_GIT_PATH = "/usr/bin/git";
    public static String MAGERUN_URL = "https://github.com/netz98/n98-magerun/blob/master/n98-magerun.phar?raw=true";


    public String phpPath = DEFAULT_PHP_PATH;
    public String gitPath = DEFAULT_GIT_PATH;
    public String magerunRepositoryURL = MAGERUN_URL;
    protected Project project;
    public String magerunCommand = null;

    public static Settings getInstance(Project project) {
        Settings settings = ServiceManager.getService(project, Settings.class);

        settings.project = project;

        return settings;
    }

    @Nullable
    @Override
    public Settings getState() {
        return this;
    }

    @Override
    public void loadState(Settings settings) {
        XmlSerializerUtil.copyBean(settings, this);
    }
}

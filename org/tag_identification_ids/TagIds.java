package org.tag_identification_ids;

import org.openqa.selenium.By;

public class TagIds
{
    public static final By loginLanguageSelectionBox = By.id("loginLanguage");
    public static final By loginButton               = By.id("login");
    public static final By portalUsernameField       = By.id("CSPortalLoginUserID");
    public static final By portalPasswordField       = By.id("CSPortalLoginPassword");

    // PimStudio
    public static final By pimTab                    = By.id("CSPortalTabButtonTitle_103");
    public static final By pimSetting                = By.id("Settings@0");
    public static final By pimTree                   = By.name("frmTree");
    public static final By pimTreeFrame              = By.name("frame_190");
    public static final By pimSettingNode            = By.id("Settings~Configurations@0");
    public static final By pimSettingClassNode       = By.id("Settings~ConfigurationLinks@0");

    // PimAttribute
    public static final By pimAttributeType          = By.name("CSTypeLabel");

    // AskBox
    public static final By inputBox                  = By.id("userInput");
    public static final By okayButton                = By.id("CSGUI_MODALDIALOG_OKBUTTON");
    public static final By cancleButton              = By.id("CSGUI_MODALDIALOG_CANCELBUTTON");

    // RightClick
    public static final By rightClickDiv             = By.id("CSPopupDiv");
    public static final By rightClickFrame           = By.id("CSPopupFrame");
    public static final By rightClickFrame_CS17      = By.id("CSPopupDivFrame");
}

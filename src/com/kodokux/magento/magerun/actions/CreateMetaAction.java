package com.kodokux.magento.magerun.actions;

import com.intellij.execution.ExecutionException;
import com.intellij.execution.configurations.GeneralCommandLine;
import com.intellij.execution.process.ScriptRunnerUtil;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.PlatformDataKeys;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.MessageType;
import com.intellij.openapi.ui.popup.Balloon;
import com.intellij.openapi.ui.popup.JBPopupFactory;
import com.intellij.openapi.vfs.VirtualFileManager;
import com.intellij.openapi.wm.StatusBar;
import com.intellij.openapi.wm.WindowManager;
import com.intellij.ui.awt.RelativePoint;
import com.kodokux.magento.Settings;

/**
 * Created with IntelliJ IDEA by me!
 * User: johna
 * Date: 2013/08/30
 * Time: 17:58
 */
public class CreateMetaAction extends AnAction {
    private Project project;
    final protected String MAGERUN_COMMAND = "dev:ide:phpstorm:meta";
    private StatusBar statusBar;

    @Override
    public void actionPerformed(AnActionEvent anActionEvent) {
        this.project = PlatformDataKeys.PROJECT.getData(anActionEvent.getDataContext());
        statusBar = WindowManager.getInstance()
                .getStatusBar(PlatformDataKeys.PROJECT.getData(anActionEvent.getDataContext()));
        GeneralCommandLine commandLine = null;
        if (project != null) {
            commandLine = new GeneralCommandLine(getSettings().phpPath, getSettings().magerunCommand, MAGERUN_COMMAND);
            commandLine.setWorkDirectory(project.getBasePath());
        }


        if (commandLine != null) {
            showBalloonInfo("Start Run Command");
            final GeneralCommandLine finalCommandLine = commandLine;
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        System.out.println(finalCommandLine.toString());
                        String output = ScriptRunnerUtil.getProcessOutput(finalCommandLine);
                        System.out.println(output);
                        showBalloonInfo("End Run Command");
                        VirtualFileManager.getInstance().syncRefresh();
                    } catch (ExecutionException e1) {
                        e1.printStackTrace();
                        showBalloonInfo("Error!!!");
                    }
                }
            }).start();
        }
    }

    protected void showBalloonInfo(String message) {
        if (statusBar != null) {
            JBPopupFactory.getInstance()
                    .createHtmlTextBalloonBuilder(message, MessageType.INFO, null)
//                    .setFadeoutTime(7500)
                    .createBalloon()
                    .show(RelativePoint.getCenterOf(statusBar.getComponent()),
                            Balloon.Position.atRight);
        }
    }

    private Settings getSettings() {
        return Settings.getInstance(project);
    }

}

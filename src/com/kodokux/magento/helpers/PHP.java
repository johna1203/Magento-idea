package com.kodokux.magento.helpers;

import com.intellij.execution.ExecutionException;
import com.intellij.execution.configurations.GeneralCommandLine;
import com.intellij.execution.process.ScriptRunnerUtil;
import com.intellij.openapi.project.Project;
import com.kodokux.magento.Settings;

/**
 * Created with IntelliJ IDEA by me!
 * User: johna
 * Date: 2013/08/30
 * Time: 18:08
 */
public class PHP {

    public static String execute(String phpCode, Project project) {
        String pathToPhp = "php";
        Settings settings = Settings.getInstance(project);
        if (settings != null) {
            if (settings.phpPath != null && !settings.phpPath.isEmpty()) {
                pathToPhp = settings.phpPath;
            }

        }
        return executeWithCommandLine(pathToPhp, phpCode);
    }

    public static String execute(String phpCode) {
        return execute(phpCode, null);
    }

    public static String executeWithCommandLine(String pathToPhp, String phpCode) {
        if (pathToPhp != null && !pathToPhp.isEmpty() && phpCode != null && !phpCode.isEmpty()) {

            GeneralCommandLine commandLine = new GeneralCommandLine(pathToPhp, "-r", phpCode);
            try {
                String output = ScriptRunnerUtil.getProcessOutput(commandLine);
                return output;
                //Messages.showMessageDialog(output,"titulo" , Messages.getInformationIcon());
                //ActionManager.createActionPopupMenu and ActionManager.createActionToolbar. To get a Swing component from such an object, simply call the getComponent() method.
            } catch (ExecutionException e1) {
                e1.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            }
        }
        return null;
    }

}

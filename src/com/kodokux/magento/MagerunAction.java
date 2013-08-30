package com.kodokux.magento;

import com.intellij.openapi.actionSystem.ActionManager;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.DefaultActionGroup;
import com.intellij.openapi.actionSystem.impl.ActionManagerImpl;
import com.intellij.openapi.ui.popup.JBPopupFactory;
import com.intellij.openapi.ui.popup.ListPopup;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA by me!
 * User: johna
 * Date: 2013/08/30
 * Time: 17:41
 */
public class MagerunAction extends AnAction {
    @Override
    public void actionPerformed(AnActionEvent anActionEvent) {
        DefaultActionGroup actionGroup = new DefaultActionGroup();
        //SimpleActionGroup actionGroup = new SimpleActionGroup();

        List<AnAction> actions = _getMagentoContextActions();
        if (actions.size() > 0) {
            for (AnAction action : actions) {
                if (action != null)
                    actionGroup.add(action);
            }
            final ListPopup popup =
                    JBPopupFactory.getInstance().createActionGroupPopup(
                            "Magento-Idea Actions",
                            actionGroup,
                            anActionEvent.getDataContext(),
                            JBPopupFactory.ActionSelectionAid.SPEEDSEARCH,
                            false);
            popup.showInBestPositionFor(anActionEvent.getDataContext());
        }


    }

    /**
     * Define MagicentoActions to be listed when using Alt+M (Option+M)
     *
     * @return List<AnAction>
     */
    protected List<AnAction> _getMagentoContextActions() {
        List<AnAction> actions = new ArrayList<AnAction>();
        String[] actionIds = {
                "GenAutoComplete",
        };
        ActionManager actionManager = ActionManagerImpl.getInstance();
        for (String actionId : actionIds) {
            AnAction action = actionManager.getAction(actionId);
            actions.add(action);
        }
        return actions;
    }

}

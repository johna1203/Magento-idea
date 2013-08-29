package com.magicento.models.xml.config;

import com.intellij.psi.xml.XmlTag;
import com.magicento.helpers.IdeHelper;
import com.magicento.helpers.JavaHelper;
import com.magicento.helpers.Magento;
import com.magicento.helpers.Magicento;
import org.apache.commons.lang.WordUtils;

import java.util.ArrayList;
import java.util.Map;

/**
 * @author Enrique Piatti
 */
public class GlobalEventsIdObserversIdMethodXmlTag extends MagentoConfigXmlTag {

    public GlobalEventsIdObserversIdMethodXmlTag(){

        super();
        help = "Method of the class defined in the <class> sibling element to be executed when the event is fired";

    }

    @Override
    public Map<String, String> getPossibleValues()
    {
        possibleValues = new ArrayList<String>();
        //XmlTag event = getNodeFromContextHierarchy("config/global/events/*");
        XmlTag event = getNodeFromContextHierarchy("config/*/events/*");
        if(event != null){
            String methodName = JavaHelper.camelCase(event.getName(), "_");
            possibleValues.add(methodName);
        }
        return super.getPossibleValues();
    }
}

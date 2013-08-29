package com.magicento.models.xml.layout;

/**
 * @author Enrique Piatti
 */
public class LayoutXmlTag extends MagentoLayoutXmlTag {


    @Override
    protected void initChildren() {
        MagentoLayoutXmlTag handle = new HandleXmlTag();
        addChild(handle);
    }

    @Override
    protected void initAttributes() {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    protected void initName() {
        name = "layout";    // important!
    }

    @Override
    protected void initHelp() {
        help = "";
    }

}

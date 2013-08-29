package com.magicento.extensions;

import com.intellij.openapi.project.Project;
import com.intellij.psi.PsiElement;
import com.jetbrains.php.lang.psi.elements.PhpNamedElement;
import com.jetbrains.php.lang.psi.resolve.types.PhpTypeProvider2;
import org.jetbrains.annotations.Nullable;

import java.util.Collection;

public class FactoryTypeProvider implements PhpTypeProvider2 {
    @Override
    public char getKey() {
        return 0;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Nullable
    @Override
    public String getType(PsiElement psiElement) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public Collection<? extends PhpNamedElement> getBySignature(String s, Project project) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

//    @Nullable
//    @Override
//    public PhpType getType(PsiElement e)
//    {
//        if (DumbService.getInstance(e.getProject()).isDumb()) {
//            return null;
//        }
//
//        if( ! IdeHelper.isPhpWithAutocompleteFeature()){
//            return null;
//        }
//
//        MagicentoProjectComponent magicentoProjectComponent = MagicentoProjectComponent.getInstance(e.getProject());
//        if(magicentoProjectComponent == null || magicentoProjectComponent.isDisabled()){
//            return null;
//        }
//
//        // filter out method calls without parameter
//        if(!PlatformPatterns.psiElement(PhpElementTypes.METHOD_REFERENCE).withChild(
//                PlatformPatterns.psiElement(PhpElementTypes.PARAMETER_LIST).withFirstChild(
//                        PlatformPatterns.psiElement(PhpElementTypes.STRING))).accepts(e)) {
//
//            return null;
//        }
//
//        String className = getHelperClassNameFromTemplate(e);
//        if(className == null)
//        {
//            // container calls are only on "get" methods
//            // cant we move it up to PlatformPatterns? withName condition dont looks working
//            // String methodRefName = ((MethodReference) e).getName();
//            if( ! MagentoParser.isFactory(e)){
//                return null;
//            }
//
//            String uri = MagentoParser.getUriFromFactory(e);
//            if (null == uri) {
//                return null;
//            }
//
//            className = magicentoProjectComponent.getClassNameFromFactory(e);
//        }
//
//        if (null == className) {
//            return null;
//        }
//
//        return new PhpType().add(className);
//    }


//    /**
//     * $this->helper('...') inside .phtml files
//     * @return
//     */
//    protected String getHelperClassNameFromTemplate(PsiElement e)
//    {
//        if(Magicento.isInsideTemplateFile(e)){
//            if(PsiPhpHelper.isMethodRefence(e)){
//                String methodName = PsiPhpHelper.getMethodName(e);
//                if(methodName != null && methodName.equals("helper")){
//                    PsiElement parameterList = PsiPhpHelper.findFirstChildOfType(e, PsiPhpHelper.PARAMETER_LIST);
//                    if(parameterList != null)
//                    {
//                        String value = parameterList.getText();
//                        if(value != null){
//                            String uri = value.replaceAll("\"", "").replaceAll("'", "");
//                            if( ! uri.isEmpty()){
//                                MagicentoProjectComponent magicentoProjectComponent = MagicentoProjectComponent.getInstance(e.getProject());
//                                List<MagentoClassInfo> helpers = magicentoProjectComponent.findHelpersOfFactoryUri(uri);
//                                if(helpers != null && helpers.size() > 0){
//                                    return helpers.get(0).name;
//                                }
//                            }
//                        }
//                    }
//                }
//            }
//        }
//        return null;
//    }

}


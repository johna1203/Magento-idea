package com.magicento.extensions;

import com.intellij.openapi.project.Project;
import com.intellij.psi.PsiElement;
import com.jetbrains.php.lang.psi.elements.PhpNamedElement;
import com.jetbrains.php.lang.psi.resolve.types.PhpTypeProvider2;
import org.jetbrains.annotations.Nullable;

import java.util.Collection;

/**
 * @author Enrique Piatti
 */
public class BlockTypeProvider implements PhpTypeProvider2 {
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
//        if( ! magicentoProjectComponent.getMagicentoSettings().automaticThisInTemplate){
//            return null;
//        }
//
//        // e.isVariable && e.getText.equals("$this");
//        // filter out method calls without parameter
//        if(!PlatformPatterns.psiElement(PhpElementTypes.VARIABLE).withName("this").accepts(e)) {
//            return null;
//        }
//
//        if( ! Magicento.isInsideTemplateFile(e)){
//            return null;
//        }
//
//        final VirtualFile file = e.getContainingFile().getOriginalFile().getVirtualFile();
//        Template template = new Template(file);
//        List<String> blocks = template.getBlocksClasses(e.getProject());
//        if(blocks != null && blocks.size() > 0)
//        {
//            return new PhpType().add(blocks.get(0));
//        }
//
//        return null;
//
//    }


}

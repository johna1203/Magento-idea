package com.magicento.actions;

import com.intellij.ide.IdeView;
import com.intellij.lang.html.HTMLLanguage;
import com.intellij.lang.xml.XMLLanguage;
import com.intellij.openapi.actionSystem.impl.ActionManagerImpl;
import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.fileEditor.FileEditorManager;
import com.intellij.openapi.vfs.VfsUtilCore;
import com.intellij.util.PsiNavigateUtil;
import com.magicento.MagicentoProjectComponent;
import com.intellij.codeInsight.TargetElementUtilBase;
import com.intellij.openapi.actionSystem.*;
import com.intellij.openapi.editor.CaretModel;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.editor.SelectionModel;
import com.intellij.openapi.editor.impl.DocumentImpl;
import com.intellij.openapi.fileEditor.OpenFileDescriptor;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.pom.Navigatable;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiFile;
import com.intellij.psi.PsiManager;
import com.intellij.psi.util.PsiUtilCore;
import com.magicento.MagicentoSettings;
import com.magicento.helpers.PsiPhpHelper;

import java.util.List;

/**
 * base class for all the MagicentoActions
 * @author Enrique Piatti
 */
public abstract class MagicentoActionAbstract extends AnAction implements IMagicentoAction
{

    protected String _code;
    protected int _cursorOffset = -1;
    protected AnActionEvent _event;
    protected DataContext _dataContext;
    protected Project _project;
    protected Editor _editor;
//    protected FindModel findModel;
//    protected FindManager findManager;
//    protected AbstractPopup popup;
    protected VirtualFile _virtualFile;
    protected PsiFile _psiFile;
    protected DocumentImpl _document;

//    protected FoldingModelImpl foldingModel;
//    protected SearchBox searchBox;
    protected CaretModel _caretModel;




    // public abstract Boolean isApplicable(AnActionEvent e);

//    public void actionPerformed(AnActionEvent e)
//    {
//        initFromEvent(e);
//    }

//    public void initFromEvent(AnActionEvent e)
//    {
//        _event = e;
//    }

    private void reset()
    {
        _event = null;
        _caretModel = null;
        _code = null;
        _cursorOffset = -1;
        _dataContext = null;
        _document = null;
        _editor = null;
        _project = null;
        _psiFile = null;
        _virtualFile = null;
    }

    //////////////////////////////////////////////////////////
    // SETTERS
    //////////////////////////////////////////////////////////

    public void setCaretModel(CaretModel caretModel) {
        this._caretModel = caretModel;
    }

    public void setCode(String _code) {
        this._code = _code;
    }

    public void setCursorOffset(int _cursorOffset) {
        this._cursorOffset = _cursorOffset;
    }

    public void setEvent(AnActionEvent _event) {
        reset();
        this._event = _event;
    }

    public void setDataContext(DataContext _dataContext) {
        this._dataContext = _dataContext;
    }

    public void setProject(Project _project) {
        this._project = _project;
    }

    public void setEditor(Editor _editor) {
        this._editor = _editor;
    }

    public void setVirtualFile(VirtualFile _virtualFile) {
        this._virtualFile = _virtualFile;
    }

    public void setPsiFile(PsiFile _psiFile) {
        this._psiFile = _psiFile;
    }

    public void setDocument(DocumentImpl _document) {
        this._document = _document;
    }

    //////////////////////////////////////////////////////////
    // GETTERS
    //////////////////////////////////////////////////////////

    public CaretModel getCaretModel() {
        if(_caretModel == null){
            if(getEditor() != null){
                _caretModel = getEditor().getCaretModel();
            }
        }
        return _caretModel;
    }

    /**
     * source code
     * @return
     */
    public String getCode() {
        if(_code == null){
            if(getEditor() != null){
                _code = getEditor().getDocument().getCharsSequence().toString();
            }
        }
        return _code;
    }

    public int getCursorOffset() {
        if(_cursorOffset == -1){
            if(getEditor() != null){
                _cursorOffset = getEditor().getCaretModel().getOffset();
            }
        }
        return _cursorOffset;
    }

    public AnActionEvent getEvent() {
        return _event;
    }

    public DataContext getDataContext() {
        if(_dataContext == null){
            if(getEvent() != null){
                _dataContext = getEvent().getDataContext();
            }
        }
        return _dataContext;
    }

    public Project getProject() {
        if(_project == null){
            if(getDataContext() != null){
                //_project = (Project) getDataContext().getData(DataConstants.PROJECT); // DataKeys.PROJECT.getData(getDataContext());
                _project = PlatformDataKeys.PROJECT.getData(getDataContext());
            }
        }
        return _project;
    }

    public Editor getEditor() {
        if(_editor == null){
            if(getDataContext() != null){
                //_editor = (Editor) getDataContext().getData(DataConstants.EDITOR);  //DataKeys.EDITOR.getData(e.getDataContext());
                _editor = PlatformDataKeys.EDITOR.getData(getDataContext());
            }
        }
        return _editor;
    }

    public VirtualFile getVirtualFile() {
        if(_virtualFile == null){
            if(getDataContext() != null){
                //_virtualFile = (VirtualFile) getDataContext().getData(DataConstants.VIRTUAL_FILE); //DataKeys.VIRTUAL_FILE.getData(_dataContext);
                _virtualFile = PlatformDataKeys.VIRTUAL_FILE.getData(getDataContext());
            }
        }
        return _virtualFile;
    }

    public PsiFile getPsiFile() {
        if(_psiFile == null){
            if(getProject() != null && getVirtualFile() != null){
                _psiFile = PsiManager.getInstance(getProject()).findFile(getVirtualFile());
            }
        }
        // _psiFile = PsiUtilBase.getPsiFileInEditor(editor, project);
        return _psiFile;
    }

    public DocumentImpl getDocument() {
        if(_document == null){
            if(getEditor() != null){
                _document = (DocumentImpl) getEditor().getDocument();
            }
        }
        return _document;
    }


    public IdeView getIdeView() {
        return LangDataKeys.IDE_VIEW.getData(getDataContext());
    }


    public PsiElement getPsiElementAtCursor(){
        PsiFile psiFile = getPsiFile();
        if(psiFile == null)
            return null;
        return psiFile.findElementAt(getCursorOffset());
    }

    public String getSelectedText(){
        if(getEditor() == null)
            return null;
        SelectionModel selectionModel = getEditor().getSelectionModel();
        return selectionModel.getSelectedText();
    }


    protected void gotoClass(String className)
    {
        if(className == null)
            return;

//        GotoClassModel2 model = new GotoClassModel2(getProject());
//        Object[] elements = model.getElementsByName(className, true, className);

        List<PsiElement> elements = PsiPhpHelper.getPsiElementsFromClassName(className, getProject());

        //com.jetbrains.php.lang.psi.elements.impl.PhpClassImpl
        //if(elements.length > 0)
        if(elements.size() > 0)
        {
            PsiElement element = elements.get(0);// (PsiElement) elements[0];
            /*
            PsiFile containingFile = element.getContainingFile();
            //PsiReference psiReference = element.getReference(); // element.getReferences() // (PsiReference) element;
            //PsiFile containingFile = psiReference.resolve().getContainingFile();
            VirtualFile virtualFile = containingFile.getVirtualFile();
            FileEditorManager.getInstance(project).openFile(virtualFile, true);
            */
            PsiElement navElement = element.getNavigationElement();
            navElement = TargetElementUtilBase.getInstance().getGotoDeclarationTarget(element, navElement);
            if (navElement instanceof Navigatable) {
                if (((Navigatable)navElement).canNavigate()) {
                    ((Navigatable)navElement).navigate(true);
                }
            }
            else if (navElement != null) {
                int navOffset = navElement.getTextOffset();
                VirtualFile virtualFile = PsiUtilCore.getVirtualFile(navElement);
                if (virtualFile != null) {
                    new OpenFileDescriptor(getProject(), virtualFile, navOffset).navigate(true);
                }
            }
        }
    }

    public MagicentoProjectComponent getMagicentoComponent()
    {
        Project project = getProject();
        if(project != null){
            //return (MagicentoProjectComponent) project.getComponent("MagicentoProjectComponent");
           return MagicentoProjectComponent.getInstance(project);
        }
        return null;
    }


    public MagicentoSettings getMagicentoSettings()
    {
        Project project = getProject();
        if(project != null){
            //return (MagicentoProjectComponent) project.getComponent("MagicentoProjectComponent");
            return MagicentoSettings.getInstance(project);
        }
        return null;
    }

    protected String getWordAtCursor(CharSequence editorText, int cursorOffset)
    {
        if(editorText.length() == 0) return null;
        if(cursorOffset > 0 && !Character.isJavaIdentifierPart(editorText.charAt(cursorOffset)) &&
                Character.isJavaIdentifierPart(editorText.charAt(cursorOffset - 1))) {
            cursorOffset--;
        }
        if(Character.isJavaIdentifierPart(editorText.charAt(cursorOffset))){
            int start = cursorOffset;
            int end = cursorOffset;
            while(start > 0 && Character.isJavaIdentifierPart(editorText.charAt(start - 1))) {
                start--;
            }
            while(end < editorText.length() && Character.isJavaIdentifierPart(editorText.charAt(end))) {
                end++;
            }
            return editorText.subSequence(start, end).toString();
        }
        return null;
    }


    public void openFile(VirtualFile file)
    {
        if(file != null){
            FileEditorManager.getInstance(getProject()).openFile(file, true);
        }
        // new OpenFileDescriptor(getProject(), file, 0).navigate(true);
    }

    public void openFile(PsiFile file)
    {
//        PsiElement navElement = file.getNavigationElement();
//        if(navElement != null){
//            navElement = TargetElementUtilBase.getInstance().getGotoDeclarationTarget(file, navElement);
//            if (navElement instanceof Navigatable) {
//                if (((Navigatable)navElement).canNavigate()) {
//                    ((Navigatable)navElement).navigate(true);
//                    return;
//                }
//            }
//        }
//
//        openFile(file.getVirtualFile());

        PsiNavigateUtil.navigate(file);
    }

    @Override
    public void actionPerformed(AnActionEvent e)
    {
        setEvent(e);
        executeAction();
    }

    public abstract void executeAction();


    protected void addNewLineAfterCaret()
    {
        ActionManager actionManager = ActionManagerImpl.getInstance();
        final AnAction action = actionManager.getAction(IdeActions.ACTION_EDITOR_START_NEW_LINE);
        AnActionEvent event = new AnActionEvent(null, getDataContext(), IdeActions.ACTION_EDITOR_START_NEW_LINE, getEvent().getPresentation(), ActionManager.getInstance(), 0);
        action.actionPerformed(event);
    }

    protected void addNewLineBeforeCaret()
    {
        ActionManager actionManager = ActionManagerImpl.getInstance();
        AnAction action = actionManager.getAction(IdeActions.ACTION_EDITOR_MOVE_CARET_UP);
        AnActionEvent event = new AnActionEvent(null, getDataContext(), IdeActions.ACTION_EDITOR_COMPLETE_STATEMENT, getEvent().getPresentation(), ActionManager.getInstance(), 0);
        action.actionPerformed(event);

        addNewLineAfterCaret();
    }

    protected void writeStringInCaret(String text)
    {
        writeStringAtPosition(text, getCaretModel().getOffset());
    }

    protected void writeStringAtPosition(String text, final int position)
    {
        final String write = text;
        ApplicationManager.getApplication().runWriteAction(new Runnable() {
            @Override
            public void run() {
                getDocument().insertString(position, write);
            }
        });
    }

    protected boolean isPhp()
    {
        // return getPsiElementAtCursor().getLanguage() instanceof com.jetbrains.php.lang.PhpLanguage;
        return getPsiElementAtCursor().getLanguage().getID().equals("PHP");
    }

    protected boolean isXml()
    {
        // return getPsiElementAtCursor().getLanguage() instanceof XMLLanguage;
        return getPsiElementAtCursor().getLanguage().getID().equals("XML");
    }

    protected boolean isHtml()
    {
        // InjectedLanguageUtil, InjectedLanguageManager
        // return getPsiElementAtCursor().getLanguage() instanceof HTMLLanguage;
        String languageId = getPsiElementAtCursor().getLanguage().getID();
        return languageId.equals("HTML") || languageId.equals("XHTML") || (languageId.equals("XML") && isPhtml());
    }

    protected boolean isPhtml()
    {
        return getVirtualFile().getExtension().equals("phtml");
    }



}




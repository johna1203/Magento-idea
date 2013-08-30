package com.kodokux.magento;

import com.intellij.openapi.components.StoragePathMacros;
import com.intellij.openapi.options.Configurable;
import com.intellij.openapi.options.ConfigurationException;
import com.intellij.openapi.project.Project;
import org.jetbrains.annotations.Nls;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.io.*;
import java.net.URL;

/**
 * User: johna
 * Date: 2013/08/30
 * Time: 15:39
 */
public class SettingsForm implements Configurable {

    private final Project project;
    private JPanel myPanel;
    private JTextField phpPathTextFiled;
    private JTextField gitPathTextFiled;
    private JTextField magerunRepoTextFiled;
    private JButton downloadMagerunButton;

    public SettingsForm(@NotNull final Project project) {
        this.project = project;
    }

    @Nls
    @Override
    public String getDisplayName() {
        return "Magento Idea";
    }

    @Nullable
    @Override
    public String getHelpTopic() {
        return null;
    }

    @Nullable
    @Override
    public JComponent createComponent() {


        downloadMagerunButton.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String gitCommand = getSettings().gitPath;
                File downloadPath = new File(project.getBasePath(), "/.idea/magento-idea");
                if (!downloadPath.exists() && !downloadPath.mkdirs()) {
                    System.out.println("cant create");
                }
                downloadMagerun(getSettings().magerunRepositoryURL, downloadPath.getPath() + "/n98-magerun.phar");
            }
        });

        return (JComponent) myPanel;
    }

    @Override
    public boolean isModified() {
        return !phpPathTextFiled.getText().equals(getSettings().phpPath)
                || !gitPathTextFiled.getText().equals(getSettings().gitPath)
                || !magerunRepoTextFiled.getText().equals(getSettings().magerunRepositoryURL)
                ;
    }

    @Override
    public void apply() throws ConfigurationException {
        getSettings().phpPath = phpPathTextFiled.getText();
        getSettings().gitPath = gitPathTextFiled.getText();
        getSettings().magerunRepositoryURL = magerunRepoTextFiled.getText();
    }

    @Override
    public void reset() {
        updateUIFromSettings();
    }

    private void updateUIFromSettings() {
        phpPathTextFiled.setText(getSettings().phpPath);
        gitPathTextFiled.setText(getSettings().gitPath);
        magerunRepoTextFiled.setText(getSettings().magerunRepositoryURL);
    }

    @Override
    public void disposeUIResources() {

    }

    private Settings getSettings() {
        return Settings.getInstance(project);
    }

    private void downloadMagerun(final String urlString, final String outputPath) {


        new Thread(new Runnable() {
            @Override
            public void run() {
                InputStream in = null;
                OutputStream out = null;
                try {
                    URL url = new URL(urlString);
                    in = url.openStream();
                    out = new FileOutputStream(outputPath);

                    System.out.println(urlString);
                    System.out.println(outputPath);

                    byte[] buf = new byte[1024];
                    int len = 0;

                    while ((len = in.read(buf)) > 0) {
                        out.write(buf, 0, len);
                    }

                    out.flush();

                    getSettings().magerunCommand = outputPath;

                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    // ストリームをクローズする
                    if (out != null) {
                        try {
                            out.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }

                    if (in != null) {
                        try {
                            in.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }).start();

    }


}

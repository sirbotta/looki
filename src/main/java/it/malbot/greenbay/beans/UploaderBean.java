/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package it.malbot.greenbay.beans;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.UploadedFile;

/**
 *
 * @author simone
 */
@ManagedBean
@RequestScoped
public class UploaderBean {

    private UploadedFile file;
    private final String relativeWebPath = "/resources/img/";

    public UploadedFile getFile() {
        return file;
    }

    public void setFile(UploadedFile file) {
        this.file = file;
    }

    //per command button
    public String upload() {
        if (file != null) {
            try {
                FacesMessage fm = new FacesMessage(FacesMessage.SEVERITY_INFO, "SUCCESSO", "Immagine " + file.getFileName()
                    + " caricata correttamente");
            FacesContext.getCurrentInstance().addMessage(null, fm);
            FacesContext.getCurrentInstance().getExternalContext().getFlash().setKeepMessages(true);
                copyFile(file.getFileName(), file.getInputstream());
            } catch (IOException e) {
                e.printStackTrace();
            }
            
            return "boh";

        } else {
            FacesMessage fm = new FacesMessage(FacesMessage.SEVERITY_WARN, "ATTENZIONE", "Nessuna imagine valida. "
                    + "L'asta verrà creata con una immagine di Defoult");
            FacesContext.getCurrentInstance().addMessage(null, fm);
            FacesContext.getCurrentInstance().getExternalContext().getFlash().setKeepMessages(true);
            return "boh";
        }
    }
    
    //per actionlistener
    public void handleFileUpload(FileUploadEvent event) {
        setFile(event.getFile());
        if (file != null) {
            try {
                FacesMessage fm = new FacesMessage(FacesMessage.SEVERITY_INFO, "SUCCESSO", "Immagine " + file.getFileName()
                    + " caricata correttamente");
            FacesContext.getCurrentInstance().addMessage(null, fm);
            FacesContext.getCurrentInstance().getExternalContext().getFlash().setKeepMessages(true);
                copyFile(file.getFileName(), file.getInputstream());
            } catch (IOException e) {
                e.printStackTrace();
            }
            
           

        } else {
            FacesMessage fm = new FacesMessage(FacesMessage.SEVERITY_WARN, "ATTENZIONE", "Nessuna imagine valida. "
                    + "L'asta verrà creata con una immagine di Defoult");
            FacesContext.getCurrentInstance().addMessage(null, fm);
            FacesContext.getCurrentInstance().getExternalContext().getFlash().setKeepMessages(true);
            
        }
        
    }
    
    
    public void copyFile(String fileName, InputStream in) {
        try {

            String destination = FacesContext.getCurrentInstance().getExternalContext().getRealPath(relativeWebPath);
            // write the inputStream to a FileOutputStream
            OutputStream out = new FileOutputStream(new File(destination+"/" + fileName));

            int read = 0;
            byte[] bytes = new byte[1024];

            while ((read = in.read(bytes)) != -1) {
                out.write(bytes, 0, read);
            }

            in.close();
            out.flush();
            out.close();

            System.out.println("New file created at "+destination);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
}

package com.mz.mreal.controller.img;

import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

@Controller
public class ImagesController {
    private final File imagesDir;

    @Autowired
    public ImagesController(@Qualifier("imagesDir") File imagesDir) {this.imagesDir = imagesDir;}


    @RequestMapping(value = "/img/{uploadedFileName}", method = RequestMethod.GET)
    public void getFile(@PathVariable String uploadedFileName, HttpServletResponse response) throws IOException {
        try {
            File toReturn = new File(imagesDir, uploadedFileName);
            if (toReturn.exists()) {
                response.setContentType(MediaType.IMAGE_JPEG_VALUE);
                IOUtils.copy(new FileInputStream(toReturn), response.getOutputStream());
            } else {
                response.sendError(404, "La imagen " + uploadedFileName + " no existe");
            }
        } catch (Exception e) {
            response.sendError(500, "Error al enviar la imagen");
        }
    }
}

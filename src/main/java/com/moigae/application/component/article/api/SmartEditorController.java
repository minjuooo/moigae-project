package com.moigae.application.component.article.api;

import com.moigae.application.component.article.api.request.PostForm;
import com.moigae.application.component.user.dto.CustomUser;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.UUID;

@Controller
@RequestMapping("/smarteditor")
@RequiredArgsConstructor
@Slf4j
public class SmartEditorController {
    @Value("${image.upload.path}")
    private String uploadPath;

    @Value("${resource.handler}")
    private String resourceHandler;
    @GetMapping("/smartEditor")
    public String login(Model model,
                        @AuthenticationPrincipal CustomUser customUser) {
        model.addAttribute("customUser", customUser);
        model.addAttribute("postForm", new PostForm());
        return "smarteditor/smartEditor";
    }

    @PostMapping("/admin/post/imageUpload")
    public void postImage(MultipartFile upload, HttpServletResponse res, HttpServletRequest req){

            OutputStream out = null;
            PrintWriter printWriter = null;

            res.setCharacterEncoding("utf-8");
            res.setContentType("text/html;charset=utf-8");

            try{

                UUID uuid = UUID.randomUUID();
                String extension = FilenameUtils.getExtension(upload.getOriginalFilename());

                byte[] bytes = upload.getBytes();

                // 실제 이미지 저장 경로
                String imgUploadPath = uploadPath + File.separator + uuid + "." + extension;

                // 이미지 저장
                out = new FileOutputStream(imgUploadPath);
                out.write(bytes);
                out.flush();

                // ckEditor 로 전송
                printWriter = res.getWriter();
                String callback = req.getParameter("CKEditorFuncNum");
                String fileUrl = "/admin/post/image/" + uuid + "." + extension;

                printWriter.println("<script type='text/javascript'>"
                        + "window.parent.CKEDITOR.tools.callFunction("
                        + callback+",'"+ fileUrl+"','이미지를 업로드하였습니다.')"
                        +"</script>");

                printWriter.flush();

            } catch (IOException e) {
                e.printStackTrace();
            }finally {
                try {
                    if(out != null) { out.close(); }
                    if(printWriter != null) { printWriter.close(); }
                } catch(IOException e) { e.printStackTrace(); }
            }
    }
}

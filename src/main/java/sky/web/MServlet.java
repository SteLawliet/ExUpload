package sky.web;

import com.alibaba.fastjson.JSON;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import sky.dao.FileDaoImpl;
import sky.dao.FileDao;
import sky.model.FileBean;
import sky.util.MyUtils;

/**
 * @program: ExUpload
 * @description: baseServlet
 * @author: Zhaoziqi
 * @create: 2018-07-30 11:15
 **/
@WebServlet("/MServlet")
public class MServlet extends HttpServlet {
    public static final String PATH = System.getProperty("user.dir")+File.separator;
    public static final String UP_PATH = PATH+"upload";


    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
    }

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");

        String methodName = request.getParameter("method");
        try {

            if (methodName == null) {
                throw new NoSuchMethodException();
            }

            Method method = MServlet.class.getMethod(methodName,
                    HttpServletRequest.class, HttpServletResponse.class);

            method.invoke(this, request, response);



        } catch (NoSuchMethodException e) {

            throw new RuntimeException("the method is null");
        } catch (IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    public String Upload(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String temp = getServletContext().getRealPath("/temp");
        File file1 = new File(temp);
        if (!file1.exists()) {
            file1.mkdirs();
        }

//        String upload = getServletContext().getRealPath("/upload");
        String upload = UP_PATH;
        File file0 = new File(upload);
        if (!file0.exists()) {
            file0.mkdirs();
        }

        DiskFileItemFactory factory =
                new DiskFileItemFactory(1024 * 10, new File(temp));
        DiskFileItemFactory fileItemFactory = new DiskFileItemFactory();
        ServletFileUpload fileUpload = new ServletFileUpload(fileItemFactory);

        fileUpload.setHeaderEncoding("UTF-8");

        List<FileItem> fileItems = null;
        List<FileBean> filelist = new ArrayList<FileBean>();
        try {
            fileItems = fileUpload.parseRequest(request);
        } catch (FileUploadException e) {
            System.err.println("upload exception");
            return null;
        }
        if (fileItems != null) {
            for (FileItem fileItem : fileItems) {

                if (fileItem.isFormField()) {
                    System.err.println(fileItem.getFieldName());
                    System.err.println(fileItem.getString("UTF-8"));
                    continue;
                }

                InputStream in = fileItem.getInputStream();

                File file = new File(upload + "/" + fileItem.getName());

                if (!file.exists()) {
                    file.createNewFile();
                }
                MyUtils.InToOut(in, new FileOutputStream(file));

                String name = file.getName();
                String size = String.valueOf(fileItem.getSize());
                FileBean fileBean = MyUtils.FileItemToBean(fileItem);
                fileBean.setPath(file.getAbsolutePath());


                filelist.add(fileBean);
                fileItem.delete();
            }
        }
        FileDao daoFile = new FileDaoImpl();
        daoFile.addFile(filelist);
        request.setAttribute("filelist", daoFile.FindAll());
        return null;

    }

    public void showFileList(HttpServletRequest request, HttpServletResponse response)
            throws IOException{
        FileDao daoFile = new FileDaoImpl();
        List<FileBean> list = daoFile.FindAll();
        response.setCharacterEncoding("utf8");
        list.forEach(val->val.setPath(""));
        response.getWriter().println(JSON.toJSONString(list));
    }

    public String Download(HttpServletRequest request, HttpServletResponse response)
            throws IOException {

        String upload = getServletContext().getRealPath("/upload");
        File file0 = new File(upload);
        if (!file0.exists()) {
            file0.mkdirs();
        }

        String downFile = request.getParameter("filename");

        File file = new File(upload + "/" + downFile);

        if (!file.exists()) {
            file.createNewFile();
        }

        response.setContentType("application/octet-stream");
        response.setHeader("content-disposition",
                "attachment;filename=" +
                        new String(downFile.getBytes("utf-8"),
                                "ISO-8859-1"));
        FileDao daoFile = new FileDaoImpl();
        FileBean fileBean = daoFile.FindFileByName(downFile);
        InputStream in = FileUtils.openInputStream(new File(fileBean.getPath()));
        MyUtils.InToOut(in, response.getOutputStream());
        return null;
    }

}

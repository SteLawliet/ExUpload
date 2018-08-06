package sky.dao;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.stream.Stream;

import sky.model.FileBean;
import sky.web.MServlet;

/**
 * @program: ExUpload
 * @description:
 * @author: Zhaoziqi
 * @create: 2018-07-31 10:14
 **/
public class FileDaoJson implements FileDao {
    //    public static final List<FileBean> FILE_BEAN_LIST ;
    public static final String history = MServlet.PATH + "history";


    @Override
    public void addFile(FileBean fileBean) {
        List<FileBean> fileBeans = FindAll();
        if (fileBeans.contains(fileBean)) {
            return;
        }
        fileBeans.add(fileBean);
        String s = JSON.toJSONString(fileBeans, true);
        BufferedWriter writer = null;
        try {
            writer = new BufferedWriter(new FileWriter(history));
            writer.write(s);
            writer.flush();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (writer != null) {
                    writer.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    @Override
    public List<FileBean> FindAll() {
        StringBuilder stringBuilder = new StringBuilder();
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new FileReader(history));
            String line = null;

            while (null != (line = reader.readLine())) {
                stringBuilder.append(line);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        List<FileBean> fileBeans = JSONObject.parseArray(stringBuilder.toString(), FileBean.class);
        return fileBeans;
    }


    @Override
    public FileBean FindFileByName(String name) {
        List<FileBean> fileBeans = FindAll();
        FileBean fileBean = null;
        for (FileBean bean : fileBeans) {
            if (bean.getName().equals(name)){
                return bean;
            }
        }
        return null;
    }

    @Override
    public void addFile(List<FileBean> beanList) {

    }
}

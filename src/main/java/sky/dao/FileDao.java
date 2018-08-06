package sky.dao;

import java.util.ArrayList;
import java.util.List;

import sky.model.FileBean;

public interface FileDao {
    void addFile(FileBean fileBean);

    List<FileBean> FindAll();

    FileBean FindFileByName(String name);

    void addFile(List<FileBean> beanList);
}

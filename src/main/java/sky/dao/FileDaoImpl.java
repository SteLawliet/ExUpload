package sky.dao;

import com.mchange.v2.c3p0.ComboPooledDataSource;


import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.sql.DataSource;

import sky.model.FileBean;

/**
 * Created by Stelawliet on 17/12/18.
 */
public class FileDaoImpl implements FileDao {
    private static DataSource ds = null;

    public FileDaoImpl() {
        if (ds == null) {
            ds = new ComboPooledDataSource();
        }
    }

    public static void main(String[] args) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        String s = sdf.format(new Date());
        System.out.println(s);

        FileDaoImpl fileDaoImpl = new FileDaoImpl();


        FileBean fileBean = fileDaoImpl.FindFileByName("1235.jpg");


        List<FileBean> list = fileDaoImpl.FindAll();

        for (FileBean f : list) {

            System.out.println(f);
        }
    }

    @Override
    public void addFile(FileBean fileBean) {

        QueryRunner qR = new QueryRunner(ds);
        String sql = "insert table_file_upload values(null,?,?,?,?,?)";
        FileBean fileBean1 = FindFileByName(fileBean.getName());
        if (fileBean1 != null) {
            return;
        }
        try {
            qR.update(sql,
                    fileBean.getName(),
                    fileBean.getSize(),
                    fileBean.getType(),
                    fileBean.getUpdate(),
                    fileBean.getPath());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<FileBean> FindAll() {
        QueryRunner qR = new QueryRunner(ds);
        String sql = "Select * from table_file_upload where 1=1 ";
        List<FileBean> fileList = null;
        try {
            fileList = qR.query(sql, new BeanListHandler<FileBean>(FileBean.class));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return fileList;
    }

    @Override
    public FileBean FindFileByName(String name) {
        QueryRunner qR = new QueryRunner(ds);
        String sql = "Select * from table_file_upload where name =? ";
        Object[] params = new Object[]{name};
        FileBean fileBean = null;
        try {
            fileBean = qR.query(sql, new BeanHandler<FileBean>(FileBean.class), params);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return fileBean;
    }

    @Override
    public void addFile(List<FileBean> beanList) {

        for (FileBean f : beanList) {
            addFile(f);
        }
    }

}

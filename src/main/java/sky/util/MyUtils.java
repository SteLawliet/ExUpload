package sky.util;

import org.apache.commons.fileupload.FileItem;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

import sky.model.FileBean;


/**
 * Created by Stelawliet on 17/12/18.
 */
public class MyUtils {

    private final static long MaxB = 1024;

    public static void InToOut(InputStream in, OutputStream out) {
        BufferedInputStream bufferedInputStream =
                new BufferedInputStream(in);
        BufferedOutputStream bufferedOutputStream =
                new BufferedOutputStream(out);
        byte[] bytes = new byte[1024];
        int len = 0;
        try {
            while ((len = bufferedInputStream.read(bytes)) != -1) {
                bufferedOutputStream.write(bytes, 0, len);
//                bufferedOutputStream.flush();
            }

//            FileUtils.copyInputStreamToFile();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                in.close();
                bufferedInputStream.close();
                bufferedOutputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static String getByteSize(long size) {


        if (size < MaxB) {
            return String.valueOf(size) + "B";

        } else if (size < (MaxB * MaxB)) {
            String size0 = String.valueOf(size % MaxB).concat("00").substring(0, 2);
            size = size / MaxB;
            return String.valueOf(size) +
                    "." + size0 + "KB";

        } else if (size < (MaxB * MaxB * MaxB)) {
            String size0 = String.valueOf(size % (MaxB * MaxB)).concat("00").substring(0, 2);
            size = size / (MaxB * MaxB);
            return String.valueOf(size) +
                    "." + size0 + "MB";

        } else {
            String size0 = String.valueOf(size % (MaxB * MaxB * MaxB)).concat("00").substring(0, 2);
            size = size / (MaxB * MaxB * MaxB);
            return String.valueOf(size) +
                    "." + size0 + "GB";

        }

    }

    public static FileBean FileItemToBean(FileItem Item) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");

        return new FileBean(Item.getName(), Item.getContentType(),
                sdf.format(new Date()), MyUtils.getByteSize(Item.getSize()));
    }

    public static void main(String[] args) {
        System.out.println((888888888 / MaxB / 1024.0));//1024.00GB
    }
}

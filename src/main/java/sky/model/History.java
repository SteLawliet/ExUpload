package sky.model;

import java.util.List;

/**
 * @program: ExUpload
 * @description:
 * @author: Zhaoziqi
 * @create: 2018-07-31 19:42
 **/
public class History {
    private List<FileBean> fileBeans;

    public History(List<FileBean> fileBeans) {
        this.fileBeans = fileBeans;
    }

    public History() {
    }

    public List<FileBean> getFileBeans() {
        return fileBeans;
    }

    public void setFileBeans(List<FileBean> fileBeans) {
        this.fileBeans = fileBeans;
    }
}

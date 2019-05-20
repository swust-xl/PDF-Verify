package swust.PdfVerify.dao;

import swust.PdfVerify.pojo.po.mysql.tables.pojos.Doc;

/**
 * 
 * pdf操作DAO层接口
 * </p>
 * 
 * @author xuLiang
 * @since 1.0.0
 */
public interface PdfMapper {
    /**
     * 
     * 添加一条记录
     *
     * @param doc
     *            待添加的数据
     * @return 添加完成的数据
     * @author xuLiang
     * @since 1.0.0
     */
    Doc insertDoc(Doc doc);

    /**
     * 
     * 根据DOC_ID查询一条记录
     *
     * @param docId
     *            文档id
     * @return 查询到的记录信息
     * @author xuLiang
     * @since 1.0.0
     */
    Doc queryDoc(Long docId);
}

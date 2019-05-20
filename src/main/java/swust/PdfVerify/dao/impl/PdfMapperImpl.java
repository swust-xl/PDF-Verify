package swust.PdfVerify.dao.impl;

import org.jooq.DSLContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;

import swust.PdfVerify.dao.PdfMapper;
import swust.PdfVerify.pojo.po.mysql.tables.pojos.Doc;
import swust.PdfVerify.pojo.po.mysql.tables.records.DocRecord;

import static swust.PdfVerify.pojo.po.mysql.tables.Doc.DOC;

/**
 * 
 * pdf操作DAO层实现类
 * </p>
 * 
 * @author xuLiang
 * @since 1.0.0
 */
@Repository
public class PdfMapperImpl implements PdfMapper {

    @Autowired
    private DSLContext dsl;

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
    @Override
    public Doc insertDoc(Doc doc) {
        Assert.notNull(doc, "待添加的数据不能为空");
        DocRecord record = dsl.newRecord(DOC, doc);
        int result = record.store();
        return result == 1 ? record.into(Doc.class) : null;
    }

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
    @Override
    public Doc queryDoc(Long docId) {
        Assert.notNull(docId, "待查询的文档id不能为空");
        DocRecord record = dsl.selectFrom(DOC)
                .where(DOC.DOC_ID.eq(docId))
                .fetchOne();
        return record == null ? null : record.into(Doc.class);
    }

}

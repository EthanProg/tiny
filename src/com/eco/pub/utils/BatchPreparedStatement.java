package com.eco.pub.utils;

import java.io.InputStream;
import java.io.Reader;
import java.math.BigDecimal;
import java.sql.Array;
import java.sql.Blob;
import java.sql.Clob;
import java.sql.Connection;
import java.sql.Date;
import java.sql.NClob;
import java.sql.PreparedStatement;
import java.sql.Ref;
import java.sql.RowId;
import java.sql.SQLException;
import java.sql.SQLXML;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.Calendar;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;


/**
 * @author sunfs
 * @title:批量更新实现
 * @description:多线程，非阻塞，每个批量更新实现持有自己的连接和PreparedStatement, 使用代理模式来进行参数传递和方法的程序, 执行时按以下逻辑：
 * 1、传入sql创建实例，可以指定数据源
 * 2、注入参数
 * 3、批量执行
 * 4、接口内部实现连接的打开和关闭。
 * @example:
 *      try {
 *          String insertSql="INSERT INTO DEPT(DEPT_ID,DEPT_NAME,DEPT_LEVEL,DEPT_DESC) VALUES(?,?,?,?)";
 *          BatchPreparedStatement prest = new BatchPreparedStatement(insertSql);
 *          List<String[]> paramList = createBatchParam();
 *          for (Iterator iterator = paramList.iterator(); iterator.hasNext();) {
 *              String[] element = (String[]) iterator.next();
 *              prest.setString(1, element[0]);
 *              prest.setString(2, element[1]);
 *              prest.setString(3, element[2]);
 *              prest.setString(4, element[3]);
 *              prest.addBatch();
 *          }
 *          prest.executeBatch(inTransaction);
 *      }catch (Exception e) {
 *          log.error("DictDomain.batchInsert.error:",e);
 *      }
 * @date:2015-10-26
 * 在mysql下使用需设置rewriteBatchedStatements参数为true
 */
public class BatchPreparedStatement {
    private static Log log = LogFactory.getLog(BatchPreparedStatement.class);
    private PreparedStatement prest = null;
    private Connection conn = null;
    private String sql;

    /**
     * 构造方法
     *
     * @param sql 以预编译的方式指定参数，如：
     *            INSERT INTO DEPT(DEPT_ID,DEPT_NAME,DEPT_LEVEL,DEPT_DESC) VALUES(?,?,?,?)
     * @throws Exception
     */
    public BatchPreparedStatement(String sql) throws Exception {
        this.sql = sql;
        init(sql, "sqlSessionFactory");
    }

    /**
     * 构造方法
     *
     * @param sql                   以预编译的方式指定参数，如：
     *                              INSERT INTO DEPT(DEPT_ID,DEPT_NAME,DEPT_LEVEL,DEPT_DESC) VALUES(?,?,?,?)
     * @param sqlsessionFactoryBean jdbcSupport.xml中配置的数据源
     * @throws Exception
     */
    public BatchPreparedStatement(String sql, String sqlsessionFactoryBean) throws Exception {
        this.sql = sql;
        init(sql, sqlsessionFactoryBean);

    }

    private void init(String sql, String sqlFactoryBean) throws Exception {
        SqlSessionFactory sqlSessionFactory = (SqlSessionFactory) SpringContextUtil
                .getBean(sqlFactoryBean);
        SqlSession sqlSession = sqlSessionFactory.openSession();
        conn = sqlSession.getConnection();
        conn.setAutoCommit(false);
//		log.debug("connection.autocommit:"+conn.getAutoCommit());
//		log.debug("connection.transaction level:"+conn.getTransactionIsolation());
        prest = conn.prepareStatement(sql);

    }

    public void setNull(int parameterIndex, int sqlType) throws SQLException {
        prest.setNull(parameterIndex, sqlType);
    }

    public void setBoolean(int parameterIndex, boolean x) throws SQLException {
        prest.setBoolean(parameterIndex, x);
    }

    public void setByte(int parameterIndex, byte x) throws SQLException {
        prest.setByte(parameterIndex, x);
    }

    public void setMaxFieldSize(int max) throws SQLException {
        prest.setMaxFieldSize(max);
    }

    public void setShort(int parameterIndex, short x) throws SQLException {
        prest.setShort(parameterIndex, x);
    }

    public void setInt(int parameterIndex, int x) throws SQLException {
        prest.setInt(parameterIndex, x);
    }

    public void setMaxRows(int max) throws SQLException {
        prest.setMaxRows(max);
    }

    public void setLong(int parameterIndex, long x) throws SQLException {
        prest.setLong(parameterIndex, x);
    }

    public void setFloat(int parameterIndex, float x) throws SQLException {
        prest.setFloat(parameterIndex, x);
    }

    public void setDouble(int parameterIndex, double x) throws SQLException {
        prest.setDouble(parameterIndex, x);
    }

    public void setQueryTimeout(int seconds) throws SQLException {
        prest.setQueryTimeout(seconds);
    }

    public void setBigDecimal(int parameterIndex, BigDecimal x)
            throws SQLException {
        prest.setBigDecimal(parameterIndex, x);
    }

    public void setString(int parameterIndex, String x) throws SQLException {
        prest.setString(parameterIndex, x);
    }

    public void setBytes(int parameterIndex, byte[] x) throws SQLException {
        prest.setBytes(parameterIndex, x);
    }

    public void setDate(int parameterIndex, Date x) throws SQLException {
        prest.setDate(parameterIndex, x);
    }

    public void setTime(int parameterIndex, Time x) throws SQLException {
        prest.setTime(parameterIndex, x);
    }

    public void setTimestamp(int parameterIndex, Timestamp x)
            throws SQLException {
        prest.setTimestamp(parameterIndex, x);
    }

    public void setAsciiStream(int parameterIndex, InputStream x, int length)
            throws SQLException {
        prest.setAsciiStream(parameterIndex, x, length);
    }

    public void setBinaryStream(int parameterIndex, InputStream x, int length)
            throws SQLException {
        prest.setBinaryStream(parameterIndex, x, length);
    }

    public void setObject(int parameterIndex, Object x, int targetSqlType)
            throws SQLException {
        prest.setObject(parameterIndex, x, targetSqlType);
    }

    public void setObject(int parameterIndex, Object x) throws SQLException {
        prest.setObject(parameterIndex, x);
    }

    public void addBatch() throws SQLException {
        prest.addBatch();
    }

    /**
     * 批量执行，该 方法用于事务模板内部
     *
     * @return
     * @throws Exception
     */
    public int[] executeBatch() throws Exception {
        return executeBatch(true);
    }

    /**
     * 批量执行方法
     *
     * @param inTransaction 判断该方法是否被事务模板包裹
     * @return
     * @throws Exception
     */
    public int[] executeBatch(boolean inTransaction) throws Exception {
        int[] result;
        //关闭连接
        try {
            result = prest.executeBatch();
            //提交
            conn.commit();
            conn.setAutoCommit(true);
        } catch (Exception e1) {
            if (conn != null && !conn.isClosed()) {
                conn.rollback();
            }
            log.error("更新失败：sql=" + sql, e1);
            throw e1;
        } finally {
            try {
                if (prest != null) {
                    prest.close();
                }
                //如果在事务内进行操作的话，以下连接不需要关闭，否则会影响框架事务的连接关闭
                if (!inTransaction) {
                    if (conn != null && !conn.isClosed()) {
                        conn.close();
                    }
                }
            } catch (SQLException e) {
                log.error("Error occured while close PreparedStatement:", e);
                throw e;
            }
        }
        return result;
    }

    public void setCharacterStream(int parameterIndex, Reader reader, int length)
            throws SQLException {
        prest.setCharacterStream(parameterIndex, reader, length);
    }

    public void setRef(int parameterIndex, Ref x) throws SQLException {
        prest.setRef(parameterIndex, x);
    }

    public void setBlob(int parameterIndex, Blob x) throws SQLException {
        prest.setBlob(parameterIndex, x);
    }

    public void setClob(int parameterIndex, Clob x) throws SQLException {
        prest.setClob(parameterIndex, x);
    }

    public void setArray(int parameterIndex, Array x) throws SQLException {
        prest.setArray(parameterIndex, x);
    }

    public void setDate(int parameterIndex, Date x, Calendar cal)
            throws SQLException {
        prest.setDate(parameterIndex, x, cal);
    }

    public void setTime(int parameterIndex, Time x, Calendar cal)
            throws SQLException {
        prest.setTime(parameterIndex, x, cal);
    }

    public void setTimestamp(int parameterIndex, Timestamp x, Calendar cal)
            throws SQLException {
        prest.setTimestamp(parameterIndex, x, cal);
    }

    public void setNull(int parameterIndex, int sqlType, String typeName)
            throws SQLException {
        prest.setNull(parameterIndex, sqlType, typeName);
    }

    public void setRowId(int parameterIndex, RowId x) throws SQLException {
        prest.setRowId(parameterIndex, x);
    }

    public void setNString(int parameterIndex, String value)
            throws SQLException {
        prest.setNString(parameterIndex, value);
    }

    public void setNCharacterStream(int parameterIndex, Reader value,
                                    long length) throws SQLException {
        prest.setNCharacterStream(parameterIndex, value, length);
    }

    public void setNClob(int parameterIndex, NClob value) throws SQLException {
        prest.setNClob(parameterIndex, value);
    }

    public void setClob(int parameterIndex, Reader reader, long length)
            throws SQLException {
        prest.setClob(parameterIndex, reader, length);
    }

    public void setBlob(int parameterIndex, InputStream inputStream, long length)
            throws SQLException {
        prest.setBlob(parameterIndex, inputStream, length);
    }

    public void setNClob(int parameterIndex, Reader reader, long length)
            throws SQLException {
        prest.setNClob(parameterIndex, reader, length);
    }

    public void setSQLXML(int parameterIndex, SQLXML xmlObject)
            throws SQLException {
        prest.setSQLXML(parameterIndex, xmlObject);
    }

    public void setObject(int parameterIndex, Object x, int targetSqlType,
                          int scaleOrLength) throws SQLException {
        prest.setObject(parameterIndex, x, targetSqlType, scaleOrLength);
    }

    public void setPoolable(boolean poolable) throws SQLException {
        prest.setPoolable(poolable);
    }

    public void setAsciiStream(int parameterIndex, InputStream x, long length)
            throws SQLException {
        prest.setAsciiStream(parameterIndex, x, length);
    }

    public void setBinaryStream(int parameterIndex, InputStream x, long length)
            throws SQLException {
        prest.setBinaryStream(parameterIndex, x, length);
    }

    public void setCharacterStream(int parameterIndex, Reader reader,
                                   long length) throws SQLException {
        prest.setCharacterStream(parameterIndex, reader, length);
    }

    public void setAsciiStream(int parameterIndex, InputStream x)
            throws SQLException {
        prest.setAsciiStream(parameterIndex, x);
    }

    public void setBinaryStream(int parameterIndex, InputStream x)
            throws SQLException {
        prest.setBinaryStream(parameterIndex, x);
    }

    public void setCharacterStream(int parameterIndex, Reader reader)
            throws SQLException {
        prest.setCharacterStream(parameterIndex, reader);
    }

    public void setNCharacterStream(int parameterIndex, Reader value)
            throws SQLException {
        prest.setNCharacterStream(parameterIndex, value);
    }

    public void setClob(int parameterIndex, Reader reader) throws SQLException {
        prest.setClob(parameterIndex, reader);
    }

    public void setBlob(int parameterIndex, InputStream inputStream)
            throws SQLException {
        prest.setBlob(parameterIndex, inputStream);
    }

    public void setNClob(int parameterIndex, Reader reader) throws SQLException {
        prest.setNClob(parameterIndex, reader);
    }
}

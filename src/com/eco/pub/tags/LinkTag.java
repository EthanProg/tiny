package com.eco.pub.tags;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
*
* 类描述：终端路径CSS文件引入,mrtlink标签实现类
*
* @author Ethan
* @date 2016年3月3日
* 
* 修改描述：
* @modifier
*/
public class LinkTag extends TagSupport {

    private static final long serialVersionUID = 1L; 

    private final static Log logger = LogFactory.getLog(ScriptTag.class);
    
    // 需要加载的文件路径
 	private String path;

 	// 是否返回压缩后的文件
 	private boolean compress;
	

    public boolean isCompress() {
		return compress;
	}

	public void setCompress(boolean compress) {
		this.compress = compress;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	/**
	 * 
	 * 方法描述: 替换文件路径
	 * 
	 * @author Ethan 2016年3月5日
	 *
	 * 修改描述：
	 * @modifier Ethan 2016年3月5日
	 */
    public int doStartTag() throws JspException {
        try {
            if (path == null || "".equals(path)) {
                pageContext.getOut().print("请加入mrtlink参数！");
            } else {
                String[] files = path.split(",");
                for(int i = 0; i != files.length; ++i){
                    pageContext.getOut().println(
                            "<link rel=\"stylesheet\" type=\"text/css\" href=\""+
                             SkinUtils.getCSS((HttpServletRequest) pageContext.getRequest(), files[i]) 
                             + "\"/>");
                }
            }
        } catch (RuntimeException e) {
            e.printStackTrace();
        } catch (Exception e) {
            String message = "使用lambo:mrtlink标签引入css文件出错！";
            logger.error(message, e);
            try {
                pageContext.getOut().print(message + e.getMessage());
            } catch (IOException ioe) {
                logger.error(ioe);
            }
        }
        return SKIP_BODY;
    }
}

package com.eco.pub.utils;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.multipart.FilePart;
import org.apache.commons.httpclient.methods.multipart.MultipartRequestEntity;
import org.apache.commons.httpclient.methods.multipart.Part;
import org.apache.commons.httpclient.methods.multipart.StringPart;
import org.apache.commons.httpclient.util.EncodingUtil;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.multipart.MultipartFile;

import com.google.gson.Gson;

/**
 *
 * 类描述：上传文件到文档中心工具类接口
 *        通过将文件上传到应用服务器，将此文件写入临时中转目录file_transfer_station
 *        而后将此文件上传到文档中心
 *        最终返回文档中心的文件访问地址
 *        目前仅支持图片上传，文件上传接口待扩展
 *        ***************************************************************
 *        图片访问显示接口
 *        原始尺寸图片访问服务url：
 *        http://xxx/DocCenterService/image?photo_id=xxx
 *        180x180尺寸的图片url：
 *        http://xxx/DocCenterService/image?photo_id=xxx&photo_size=180x180
 *        参数说明：
 *        /image：图片访问的servlet服务
 *        photo_id：图片id
 *        photo_size：图片尺寸，如果url没有此参数则返回图片上传原始尺寸图片
 *        若只写入一个尺寸，例如photo_size=180，则会以此尺寸来缩放图片
 *        若写入两个尺寸，则会截取图片
 *        ***************************************************************
 *        文件可以直接根据url来下载访问
 *        ***************************************************************
 *        注意： 当前测试图片大小低于80k, 文档中心上传成功，但获取到的图片质量有问题
 *        因此前端上传图片时，需要限制图片大小不能低于80k
 * @author Ethan
 * @date 2016年5月6日
 * 
 * 修改描述：
 * @modifier
 */
public class FileUtils {
    
    private static Log log = LogFactory.getLog(FileUtils.class);
    
    /**
     * 
     * 方法描述: 上传单独文件到文档中心
     * 
     * @param files MultipartFile 
     * @param req HttpServletRequest
     * @return String 返回图片的访问地址
     * @author Ethan 2016年5月6日
     *
     * 修改描述：
     * @modifier
     */
    public static String uploadImage(MultipartFile files,HttpServletRequest req) {
        MultipartFile[] multipartFile = { files };
        return uploadImageBatch(multipartFile, req)[0];
    }
    
    /**
     * 
     * 方法描述: 批量上传图片到文档中心
     * 
     * @param files MultipartFile Array
     * @param req HttpServletRequest
     * @return String[] 返回图片的访问地址
     * @author Ethan 2016年5月6日
     *
     * 修改描述：
     * @modifier
     */
    public static String[] uploadImageBatch(MultipartFile[] files,HttpServletRequest req) {
        
        int fileLength = files.length;
        
        String[] resStrings = new String[fileLength];
        
        if (fileLength > 0) {
            
            // 应用服务器删除文件目录
            String[] delStrings = new String[fileLength];
            
            // 获取应用根目录下的file_transfer_station，作为图片的临时中转目录
            String rootPath = req.getSession().getServletContext().getRealPath("file_transfer_station");
            
            rootPath = rootPath + File.separator;
            
            File dir = new File(rootPath);
            
            if (!dir.exists()){
                if (!dir.mkdirs()) {
                    log.error("FileUtils---uploadImageBatch---文件目录file_transfer_station创建失败！");
                    return resStrings;
                }
            }
            
            MultipartFile multipartFile;
            
            File serverFile;
            
            String serverPath;
            
            byte[] bytes;
            
            BufferedOutputStream stream = null;
            
            try {
                for (int i = 0; i < fileLength; i++) {
                    
                    multipartFile = files[i];
                    
                    bytes = multipartFile.getBytes();
                    
                    serverFile = new File(rootPath + PubTool.uuid() + ".jpg");
                    
                    stream = new BufferedOutputStream(new FileOutputStream(serverFile));
                    
                    stream.write(bytes);
                    
                    serverPath = serverFile.getAbsolutePath();
                    
                    resStrings[i] = uploadFileToV6DocCenter(serverPath,true);
                    
                    delStrings[i] = serverPath;
                }
            } catch (RuntimeException ex) {
                throw new RuntimeException(ex);
            } catch (Exception ex) {
                try {
                    throw new Exception(ex);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } finally {
                if (stream != null) {
                    try {
                        stream.flush();
                        stream.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    for (int i = 0; i < fileLength; i++) {
                        delFile(delStrings[i]);
                    }
                }
            }
        }
        
        return resStrings;
    }
    
    /**
     * 
     * 方法描述: 删除文件
     * 
     * @param filePath 文件路径
     * @author Ethan 2016年5月6日
     *
     * 修改描述：
     * @modifier
     */
    public static void delFile(String filePath) {
        if (null != filePath && !"".equals(filePath)) {
            File myDelFile = new File(filePath);
            
            if (!myDelFile.delete()) {
                log.error("FileUtils---uploadImageBatch---文件[" + filePath + "]删除失败！");
            }
        }
    }
    
    /**
     * 
     * 方法描述: 上传文件到文档中心
     * 
     * @param serverPath 文件所在应用服务器的地址
     * @param isImage 上传是否为图片，默认为文件
     * @return String 上传后文件的访问路径
     * @author Ethan 2016年5月6日
     *
     * 修改描述：
     * @modifier
     */
    private static String uploadFileToV6DocCenter(String serverPath, boolean isImage){
        
        // 文档中心地址
        String v6UpDownRoad = PropertiesUtil.getString("sso.v6DocCenter", "/plantform.properties");
        
        // 调用提供的上传的servlet
        String serletUrl = v6UpDownRoad + "/DocCenterService/upload";
        
        // 上传后文件的访问路径前缀
        String downUrl = v6UpDownRoad + (isImage ? "/DocCenterService/image?photo_id=" : "/DocCenterService/edoc?ed_id=");
        
        // 上传后文件的访问路径
        String visitUrl = "";
        
        PostMethod filePost = null;
        
        try {
            
            File targetFile = new File(serverPath);
            
            // 解决中文乱码问题
            filePost = new PostMethod(serletUrl) {
                public String getRequestCharSet() {
                    return "UTF-8";
                }
            };
            
            // 通过以下方法可以模拟页面参数提交
            CharSetFilePart filePart = new CharSetFilePart("uploadfile", targetFile);
            // 组织参数
            // 固定参数superadmin
            // 传递文件需要四个参数
            // 传递图片加入额外的1个参数default_place, type参数为image
            int parmLength = isImage ? 5 : 4;
            Part[] parts = new Part[parmLength];
            parts[0] = new StringPart("uid", "superadmin");
            parts[1] = new StringPart("folder_id", "5557555");
            parts[2] = new StringPart("type", isImage ? "image" : "rc");
            if (isImage) {
                parts[3] = new StringPart("default_place", "weibo"); 
                parts[4] = filePart;
            } else {
                parts[3] = filePart;
            }
            
            filePost.setRequestEntity(new MultipartRequestEntity(parts, filePost.getParams()));
            HttpClient client = new HttpClient();
            client.getHttpConnectionManager().getParams().setConnectionTimeout(5000);
            
            if (client.executeMethod(filePost) == HttpStatus.SC_OK) {
                if (log.isDebugEnabled()) {
                    log.debug("FileUtils---uploadFileToV6DocCenter---文件上传成功"); 
                }
                // 处理返回的字符串，截取ed_id号或者photo_id号
                // 文件上传成功后返回的字符串{"store_id":442,"code":"0000","ed_id":1327}
                // 图片上传成功后返回的字符串{"code":"0000","photo_id":1327}
                String rString = filePost.getResponseBodyAsString();    
                
                if (log.isDebugEnabled()) {
                    log.debug("FileUtils---uploadFileToV6DocCenter---返回的字符串为：" + rString);
                }
                // 截取文件的id号
                // 文件的key为ed_id, 图片的为photo_id
                Gson gson = new Gson();
                Map resMap = gson.fromJson(rString, HashMap.class);
                
                String ed_id = resMap.get(isImage ? "photo_id" : "ed_id").toString();
                
                visitUrl = downUrl + ed_id;
            } else {
                log.error("FileUtils---uploadFileToV6DocCenter---文件上传失败");
            }
            
        } catch (RuntimeException ex) {
            throw new RuntimeException(ex);
        } catch (Exception ex) {
            try {
                throw new Exception(ex);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } finally {
            if (filePost != null) {
                filePost.releaseConnection();
            }
        }
        
        return visitUrl;
    }
    
    static class CharSetFilePart extends FilePart {
        public CharSetFilePart(String filename, File file) throws FileNotFoundException {
            super(filename, file);
        }
        protected void sendDispositionHeader(OutputStream out) throws IOException {
            super.sendDispositionHeader(out);
            String filename = getSource().getFileName();
            if (filename != null) {
                out.write(EncodingUtil.getAsciiBytes(FILE_NAME));
                out.write(QUOTE_BYTES);
                out.write(EncodingUtil.getBytes(filename, "utf-8"));
                out.write(QUOTE_BYTES);
            }
        }
    }
}

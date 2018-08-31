package com.shy.tools.utils;

import com.jcraft.jsch.ChannelExec;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
import org.apache.commons.io.IOUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.security.NoSuchAlgorithmException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * <p>
 * 文件管理工具类
 * </p>
 * ClassName: FileUtil <br/>
 * Author: xiayanxin <br/>
 * Date: 2018/7/16 <br/>
 */
public class FileUtil {

	private static final String PRIVACY_FILE_PATH = PropertiesUtil
			.getProperty("privacy.file.dir");
	private static final String FILE_PATH = PropertiesUtil
			.getProperty("public.file.path");
    private static Map<String, Integer> _map;
    
    private static final String SSH_HOST = PropertiesUtil
			.getProperty("ssh.host");
    private static final int SSH_PORT = Integer.parseInt(PropertiesUtil
			.getProperty("ssh.port"));
    private static final String SSH_USERNAME = PropertiesUtil.getProperty("ssh.username");
    private static final String SSH_PASSWORD =PropertiesUtil
			.getProperty("ssh.password");

	/**
	 * <p>
	 * 文件上传
	 * </p>
	 * Author: xiayanxin <br/>
	 * Date: 2018/7/16 <br/>
	 *
	 * @param file
	 *            文件流
	 * @param menu
	 *            存储业务文件夹
	 * @return 上传后的路径
	 * @throws IOException
	 * @throws NoSuchAlgorithmException
	 */
	public static String uploadFile(MultipartFile file, String menu)
			throws IOException, NoSuchAlgorithmException {

		// 根据系统的实际情况选择目录分隔符
		String fileName01 = file.getOriginalFilename();
		String fileName = getFileName(fileName01);
		String basePath = FILE_PATH;
		String commonBase = "/files/"+menu
				+ "/"+GeneralUtils.date2String(new Date(), "yyyyMMdd") + "/";
		File baseFile = new File(basePath);
		basePath = baseFile.getParent();
		basePath += commonBase;
		File folder = new File(basePath);
		// 如果文件夹不存在则创建
		if (!folder.exists() && !folder.isDirectory()) {
			folder.mkdirs();
		}
		String path = basePath + fileName;

		// 压缩文件路径
		String returnPath = commonBase + fileName;
		File localFile = new File(path);
		if (!localFile.exists()) {
			file.transferTo(localFile);
		}

		return returnPath;

	}

	/**
	 * <p>
	 * 下载文件
	 * </p>
	 * Author: xiayanxin <br/>
	 * Date: 2018/7/16 <br/>
	 *
	 * @param response
	 *            当前response
	 * @param filePath
	 *            文件物理路径
	 */
	public static void downloadFile(HttpServletResponse response,
			String filePath) {

		File downloadFile = new File(filePath);

		InputStream in = null;
		OutputStream os = null;
		try {
			in = new FileInputStream(downloadFile);
			response.setCharacterEncoding("utf-8");

			response.setContentType("multipart/form-data");
			response.setHeader("Content-Disposition", "attachment;fileName="
					+ new String(downloadFile.getName().getBytes("utf-8"),
							"iso8859-1"));
			// response.setContentLengthLong(downloadFile.length());
			os = response.getOutputStream();
			byte[] b = new byte[6 * 1024];
			int length;
			while ((length = in.read(b)) > 0) {
				os.write(b, 0, length);
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			// 关闭io流
			IOUtils.closeQuietly(in);
			IOUtils.closeQuietly(os);

		}
	}

	/**
	 * <p>
	 * 删除文件
	 * </p>
	 * Author: xiayanxin <br/>
	 * Date: 2018/7/16 <br/>
	 *
	 * @param filePath
	 *            文件物理路径
	 * @return 删除结果，true删除成功，false删除失败
	 */
	public static boolean deletefile(String filePath) {
		boolean result = true;
		File file = new File(filePath);

		if (!file.isDirectory()) {
			result = file.delete();
		} else if (file.isDirectory()) {
			String[] fileList = file.list();
			if (null != fileList && fileList.length != 0) {
				for (int i = 0; i < fileList.length; i++) {
					File delFile = new File(filePath + "/" + fileList[i]);
					if (!delFile.isDirectory()) {
						result = delFile.delete();
					} else if (delFile.isDirectory()) {
						deletefile(filePath + "/" + fileList[i]);
					}
				}
				result = file.delete();
			}
		}
		return result;
	}

	/**
	 * <p>
	 * 使用MD5生成唯一文件名
	 * </p>
	 ** Author: xiayanxin <br/>
	 * Date: 2018/7/16 <br/>
	 *
	 * @param fileStr
	 *            原始文件名
	 * @return 唯一文件名
	 * @throws UnsupportedEncodingException
	 * @throws NoSuchAlgorithmException
	 */
	public static String getFileName(String fileStr)
			throws UnsupportedEncodingException, NoSuchAlgorithmException {

		int lastIndexOfDot = fileStr.lastIndexOf('.');
		int fileNameLength = fileStr.length();
		// 获取文件后缀名
		final String extension = fileStr.substring(lastIndexOfDot + 1,
				fileNameLength);
		// uuid生成文件名
		return UUID.randomUUID() + "." + extension;
	}

	/**
	 * <p>
	 * 将String字符串写入txt文件
	 * </p>
	 * Author: xiayanxin <br/>
	 * Date: 2018/7/16 <br/>
	 *
	 * @param fileContent
	 *            保存内容
	 * @param filePath
	 *            保存txt文件
	 * @return 保存txt文件
	 */
	public static String uploadFile(String filePath, String fileContent)
			throws Exception {
		File file = new File(PRIVACY_FILE_PATH + filePath);
		File fileParent = file.getParentFile();
		if (!fileParent.exists()) {
			fileParent.mkdirs();
		}
		file.createNewFile();
		PrintStream printStream = new PrintStream(new FileOutputStream(file));
		printStream.print(fileContent);
		if (null != printStream) {
			IOUtils.closeQuietly(printStream);
		}
		return "files/privacy/upload/"+filePath;
	}

	/**
	 * <p>
	 * 读取txt
	 * </p>
	 * Author: xiayanxin <br/>
	 * Date: 2018/7/16 <br/>
	 *
	 * @Param filePath 文件路径
	 * @Return 文件内容
	 */
	public static String readTxt(String filePath) throws Exception {
		StringBuilder result = null;
		File file = new File("/usr/local/nginx/html/"+filePath);
		result = new StringBuilder();
		BufferedReader br = new BufferedReader(new FileReader(file));
		String s = null;
		while ((s = br.readLine()) != null) {
			result.append(s);
		}
		return result.toString();
	}
	
    
    /**
     * 获取文件格式
     * @param file
     * @return  1：图片 2：视频 3:文档 9:其他
     */
    public static int getFileType(MultipartFile file){
        String fileName = file.getOriginalFilename();
        
        int lastIndexOfDot = fileName.lastIndexOf('.');
        int fileNameLength = fileName.length();
        // 获取文件后缀名
        final String extension = fileName.substring(lastIndexOfDot + 1,
                fileNameLength);
        Integer num = getFileTypeMap().get(extension.toLowerCase());
        if( num == null){
            return 9;
        }else{
            return num;
        }
    }
    
    
    public static Map<String,Integer> getFileTypeMap(){
        if(_map == null){
            _map = new HashMap<String, Integer>();
            String []images =new String[]{"png","jpg","jpeg","bmp"};
            for(String img:images){
                _map.put(img, 1);
            }
            String [] videos = new String[]{"mp4","avi","mpeg","mpg","mov","wmv","flv","rmvb"};
            for(String video:videos){
                _map.put(video, 2);
            }
            String []docs =  new String[]{"pdf","doc","docx"};
            for(String doc:docs){
                _map.put(doc, 3);
            }
        }
        return _map;
    }
    
    
	
	/**
	 * 根据视频生成图片路径
	 * @param videoUrl
	 */
	public static void saveThumbnail(String videoUrl){
		String basePath = FILE_PATH;
		File baseFile = new File(basePath);
		basePath = baseFile.getParent();
		videoUrl = basePath+videoUrl;
		
		try {
			int lastIndexOfDot = videoUrl.lastIndexOf('.');
	        // 获取文件前缀名
	        final String prefix = videoUrl.substring(0,
	        		lastIndexOfDot);
	        String picUrl = prefix +".png";

			String command = "ffmpeg -i "+videoUrl+" -ss 00:00:01 -vframes 1 -y "+picUrl;
			exeCommand(SSH_HOST,SSH_PORT,SSH_USERNAME,SSH_PASSWORD,command);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	/**
	 * 执行服务器上生成图片的命令
	 * @param host
	 * @param port
	 * @param user
	 * @param password
	 * @param command
	 * @return
	 * @throws JSchException
	 * @throws IOException
	 */
    public static String exeCommand(String host, int port, String user, String password, String command) throws Exception {
        
    	user = AESUtil.decrypt(user);
    	password = AESUtil.decrypt(password);
        JSch jsch = new JSch();
        Session session = jsch.getSession(user, host, port);
        session.setConfig("StrictHostKeyChecking", "no");
        session.setPassword(password);
        session.connect();
        ChannelExec channelExec = (ChannelExec) session.openChannel("exec");
        InputStream in = channelExec.getInputStream();
        channelExec.setCommand(command);
        channelExec.connect();
        String out = IOUtils.toString(in, "UTF-8");
        channelExec.disconnect();
        session.disconnect();
        
        return out;
    }

}

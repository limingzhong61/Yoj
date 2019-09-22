//package com.yoj.used;
//
//import java.io.File;
//import java.io.FileInputStream;
//import java.io.IOException;
//import java.io.InputStream;
//import java.net.SocketException;
//
//import org.apache.commons.net.ftp.FTPClient;
//import org.apache.commons.net.ftp.FTPReply;
//
///**
// * 
// * @author zhou_dong
// * 
// *         服务器上传文件 2014-03-07 17:03
// */
//public class MyFTP {
//
//	public static void main(String[] args) {
////		"E:\Desktop\learn.txt"
//		File imagefile = new File("E:\\Desktop\\learn.txt");
//		String imagefileFileName = "learn.jpg";
//		// 创建ftp客户端
//		FTPClient ftpClient = new FTPClient();
//		ftpClient.setControlEncoding("GBK");
//		String hostname = "192.168.163.132";
//		int port = 21;
//		String username = "nicolas";
//		String EncryptTest = "nicolas";
//		try {
//			// 链接ftp服务器
//			ftpClient.connect(hostname, port);
//			// 登录ftp
//			ftpClient.login(username, EncryptTest);
//			int reply = ftpClient.getReplyCode();
//			System.out.println(reply);
//			// 如果reply返回230就算成功了，如果返回530密码用户名错误或当前用户无权限下面有详细的解释。
//			if (!FTPReply.isPositiveCompletion(reply)) {
//				ftpClient.disconnect();
//				return;
//			}
//			//改变目录
//			boolean changeWorkingDirectory = ftpClient.changeWorkingDirectory("/tmp");
//			System.out.println(changeWorkingDirectory);
//			
//			ftpClient.setFileType(FTPClient.BINARY_FILE_TYPE);
//			
//
//			boolean makeDirectory = ftpClient.makeDirectory("path");// 在root目录下创建文件夹
//			System.out.println("makeDirectory:"+ makeDirectory);
//			String remoteFileName = System.currentTimeMillis() + "_" + imagefileFileName;
//			InputStream input = new FileInputStream(imagefile);
//			boolean storeFile = ftpClient.storeFile(remoteFileName, input);// 文件你若是不指定就会上传到root目录下
//			System.out.println(storeFile);
//			input.close();
//			ftpClient.logout();
//
//		} catch (SocketException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (IOException e) {
//			e.printStackTrace();
//		} finally {
//			if (ftpClient.isConnected()) {
//				try {
//					ftpClient.disconnect();
//				} catch (IOException ioe) {
//					ioe.printStackTrace();
//				}
//			}
//
//		}
//	}
//
//}
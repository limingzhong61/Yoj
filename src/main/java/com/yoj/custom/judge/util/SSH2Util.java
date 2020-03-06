package com.yoj.custom.judge.util;


import com.jcraft.jsch.*;
import com.yoj.custom.properties.JudgeProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;
import java.io.*;

/**
 * java远程上传文件
 *
 * @author lenovo
 */
@Slf4j
public class SSH2Util {

    @Autowired
    JudgeProperties judgeProperties;

    private Session session;

    @PostConstruct
    private void initialSession() {
        String host = judgeProperties.getWindows().getIp();

        String user = judgeProperties.getWindows().getUserName();

        String password = judgeProperties.getWindows().getPassword();

        int port = 22;
        if (session == null) {
            JSch jsch = new JSch();
            try {
                session = jsch.getSession(user, host, port);
            } catch (JSchException e) {
                e.printStackTrace();
            }
            session.setUserInfo(new UserInfo() {

                public String getPassphrase() {
                    return null;
                }

                public String getPassword() {
                    return null;
                }

                public boolean promptPassword(String arg0) {
                    return false;
                }

                public boolean promptPassphrase(String arg0) {
                    return false;
                }

                public boolean promptYesNo(String arg0) {
                    return true;
                }

                public void showMessage(String arg0) {
                }

            });
            session.setPassword(password);
            try {
                session.connect();
            } catch (JSchException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 关闭连接
     *
     * @throws Exception
     */
    public void close() throws Exception {
        if (session != null && session.isConnected()) {
            session.disconnect();
            session = null;
        }
    }

    /**
     * 上传文件
     *
     * @param localPath  本地路径，若为空，表示当前路径
     * @param localFile  本地文件名，若为空或是“*”，表示目录下全部文件
     * @param remotePath 远程路径，若为空，表示当前路径，若服务器上无此目录，则会自动创建
     *                   注意：只能创建最后一级目录
     * @return is or not create file success
     */
    public boolean putFile(String localPath, String localFile, String remotePath) {
        this.initialSession();
        Channel channelSftp = null;
        try {
            channelSftp = session.openChannel("sftp");
            channelSftp.connect();

            ChannelSftp c = (ChannelSftp) channelSftp;
            String remoteFile = null;
            if (remotePath != null && remotePath.trim().length() > 0) {
                // 先删掉再创建
                try {
                    this.runCommand("rm -r " + remotePath);
                } catch (Exception e) {
                    log.info("can't remove dir,because doesn't exist");
                }
                this.runCommand("mkdir " + remotePath);
//                c.mkdir(remotePath);
                remoteFile = remotePath + "/.";
            } else {
                remoteFile = ".";
            }
            String file = null;
            if (localFile == null || localFile.trim().length() == 0) {
                file = "*";
            } else {
                file = localFile;
            }
            if (localPath != null && localPath.trim().length() > 0) {
                if (localPath.endsWith("/")) {
                    file = localPath + file;
                } else {
                    file = localPath + "/" + file;
                }
            }
            c.put(file, remoteFile);

            channelSftp.disconnect();

        } catch (JSchException | SftpException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    // command 命令
    public String runCommand(String command) {
        // CommonUtil.printLogging("[" + command + "] begin", host, user);
        StringBuffer sb = null;
        InputStream in = null;
        InputStream err = null;
        BufferedReader inReader = null;
        BufferedReader errReader = null;
        int time = 0;
        String s = null;
        boolean run = false;
        try {

            sb = new StringBuffer();
            Channel channel = session.openChannel("exec");
            ((ChannelExec) channel).setCommand(command);
            channel.setInputStream(null);
            ((ChannelExec) channel).setErrStream(null);
            err = ((ChannelExec) channel).getErrStream();
            in = channel.getInputStream();
            channel.connect();
            inReader = new BufferedReader(new InputStreamReader(in, "UTF-8"));
            errReader = new BufferedReader(new InputStreamReader(err, "UTF-8"));

            while (true) {
                s = errReader.readLine();
                if (s != null) {
                    sb.append("error:" + s).append("\n");
                } else {
                    run = true;
                    break;
                }
            }
            while (true) {
                s = inReader.readLine();
                if (s != null) {
                    sb.append("info:" + s).append("\n");
                } else {
                    run = true;
                    break;
                }
            }

            while (true) {
                if (channel.isClosed() || run) {
                    // CommonUtil.printLogging("[" + command + "] finish: " +
                    // channel.getExitStatus(), host, user);
                    break;
                }
                try {
                    Thread.sleep(1000);
                } catch (Exception ee) {
                }
                if (time > 180) {
                    // CommonUtil.printLogging("[" + command + "] finish2: " +
                    // channel.getExitStatus(), host, user);
                    break;
                }
                time++;
            }

            inReader.close();
            errReader.close();
            channel.disconnect();
            //don't close session, because it always is used;
//            session.disconnect();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSchException e) {
            e.printStackTrace();
        }
        return sb.toString();
    }
}

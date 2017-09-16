package com.example.luxiang.sshdatacollection;

/**
 * Created by luxiang on 2017/9/16.
 */

import android.graphics.Color;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

import ch.ethz.ssh2.StreamGobbler;
import ch.ethz.ssh2.Connection;
import ch.ethz.ssh2.Session;

import java.io.IOException;

public class Log {


    public String sshConnect(String filename) {
        String result = "";
        if (filename.equals("")) {
            result = "Please input file name!";
            return result;
        } else {
            result = "Loging...";
            String suffix = ".dat";
            String hostname = "192.168.1.112";
            String username = "luxiang";
            String password = "root";

            try {
                // create a connection instance and connect to it
                Connection ssh = new Connection(hostname);

                ssh.connect();
                boolean authorized = ssh.authenticateWithPassword(username,
                        password);
                if (authorized == false)
                    throw new IOException(
                            "Could not authenticate connection, please try again.");

                // if authorized, create the session
                Session session = ssh.openSession();
                session.execCommand("cd /home/luxiang/linux-80211n-csitool-supplementary/CSI_data && sudo ../netlink/log_to_file " + filename + suffix);

                System.out.println("Here is some information about the remote host:");

            /*
             * This basic example does not handle stderr, which is sometimes dangerous
             * (please read the FAQ).
             */
                //接收目标服务器上的控制台返回结果,输出结果。
                InputStream stdout = new StreamGobbler(session.getStdout());

                BufferedReader br = new BufferedReader(new InputStreamReader(stdout));

                while (true) {
                    String line = br.readLine();
                    if (line == null)
                        break;
                    System.out.println(line);
//                result = line;
                }

            /* Show exit status, if available (otherwise "null") */
                //得到脚本运行成功与否的标志 ：0－成功 非0－失败
                System.out.println("ExitCode: " + session.getExitStatus());
                // terminate the session
                session.close();

                // terminate the connection
                ssh.close();
            } catch (IOException e) {
                e.printStackTrace(System.err);
                System.out.println(e.getMessage());
                //System.exit(2);
            }
            return result;
        }
    }
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rmiserver;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 *
 * @author hb
 */
public class Config {
    private String hostRmi;
    private String nomeRMI;
    private int portRMI;

    
    
    public Config(String filename) {
        InputStream in = null;
        Properties properties = new Properties();
                   try {
                in = new FileInputStream(filename);
                properties.load(in);
                nomeRMI = properties.getProperty("RMINAME");
                hostRmi = properties.getProperty("RMIHOST");
                portRMI = Integer.parseInt(properties.getProperty("RMIPORT"));
                
                //rmiport = 7000;

            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (in != null) {
                    try {
                        in.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        
    }

    public String getHostRmi() {
        return hostRmi;
    }

    public String getNomeRMI() {
        return nomeRMI;
    }

    public int getPortRMI() {
        return portRMI;
    }
    
  
    
    
}

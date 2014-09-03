package nettytest;

import java.io.Serializable;

/**
 * User:  Sed.Lee(李朝)
 * Date: 14-9-3
 * Time: 下午4:40
 */
public class User implements Serializable{


    private static final long serialVersionUID = -3049673845542023781L;

    private String username;

    private String realname;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getRealname() {
        return realname;
    }

    public void setRealname(String realname) {
        this.realname = realname;
    }
}

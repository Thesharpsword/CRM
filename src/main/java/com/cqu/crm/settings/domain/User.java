package com.cqu.crm.settings.domain;

public class User {
    /*
     1 关于时间
        字符串表现日期及时间有两种方式
          1 年月日：yyyy-MM-dd 10位字符串
          2 年月日时分秒：yyyy-MM-dd HH-mm-ss 19位字符串
     2 关于登录
        1 验证账号密码
            需要通过前端用户所提交的数据查询出用户可能的所有信息，即
            User user = select * from tbl_user where loginAct = ? and loginPwd = ?
        2 若查询出的user对象为null，则说明账号密码错误
          若查询出的user对象不为null，则继续验证
        3 验证失效时间 expireTime
        4 验证Ip地址 allowIps
        5 验证锁定状态 lockState

     */
    private String id;  // 编号 主键
    private String loginAct;    // 登录账号
    private String name;    // 用户真实姓名
    private String loginPwd;    // 登录密码
    private String email;   // 邮箱
    private String expireTime;  // 失效时间
    private String lockState;   // 锁定状态 0：锁定 1：启用
    private String deptno;  // 部门编号
    private String allowIps;    // 允许访问的Ip地址
    private String createTime;  // 创建时间
    private String createBy;    // 创建人
    private String editTime;    // 修改时间
    private String editBy;  // 修改人

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLoginAct() {
        return loginAct;
    }

    public void setLoginAct(String loginAct) {
        this.loginAct = loginAct;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLoginPwd() {
        return loginPwd;
    }

    public void setLoginPwd(String loginPwd) {
        this.loginPwd = loginPwd;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getExpireTime() {
        return expireTime;
    }

    public void setExpireTime(String expireTime) {
        this.expireTime = expireTime;
    }

    public String getLockState() {
        return lockState;
    }

    public void setLockState(String lockState) {
        this.lockState = lockState;
    }

    public String getDeptno() {
        return deptno;
    }

    public void setDeptno(String deptno) {
        this.deptno = deptno;
    }

    public String getAllowIps() {
        return allowIps;
    }

    public void setAllowIps(String allowIps) {
        this.allowIps = allowIps;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getCreateBy() {
        return createBy;
    }

    public void setCreateBy(String createBy) {
        this.createBy = createBy;
    }

    public String getEditTime() {
        return editTime;
    }

    public void setEditTime(String editTime) {
        this.editTime = editTime;
    }

    public String getEditBy() {
        return editBy;
    }

    public void setEditBy(String editBy) {
        this.editBy = editBy;
    }

    @Override
    public String toString() {
        return "User{" +
                "id='" + id + '\'' +
                ", loginAct='" + loginAct + '\'' +
                ", name='" + name + '\'' +
                ", loginPwd='" + loginPwd + '\'' +
                ", email='" + email + '\'' +
                ", expireTime='" + expireTime + '\'' +
                ", lockState='" + lockState + '\'' +
                ", deptno='" + deptno + '\'' +
                ", allowIps='" + allowIps + '\'' +
                ", createTime='" + createTime + '\'' +
                ", createBy='" + createBy + '\'' +
                ", editTime='" + editTime + '\'' +
                ", editBy='" + editBy + '\'' +
                '}';
    }
}

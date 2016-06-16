package mybatis.auto.model;

public class Project {
    private Integer id;

    private String name;

    private Integer enterpriseId;

    private Integer departmentId;

    private Integer areaId;

    private Integer jxwId;

    private Byte typeId;

    private Byte status;

    private String contact;

    private String contactphone;

    private String contactLeader;

    private String contactJxw;

    private Integer contactId;

    private Integer starttime;

    private Integer endtime;

    private Integer createtime;

    private Float totalinvest;

    private Float pastinvest;

    private Boolean importance;

    private String remark;

    private Integer userId;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public Integer getEnterpriseId() {
        return enterpriseId;
    }

    public void setEnterpriseId(Integer enterpriseId) {
        this.enterpriseId = enterpriseId;
    }

    public Integer getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(Integer departmentId) {
        this.departmentId = departmentId;
    }

    public Integer getAreaId() {
        return areaId;
    }

    public void setAreaId(Integer areaId) {
        this.areaId = areaId;
    }

    public Integer getJxwId() {
        return jxwId;
    }

    public void setJxwId(Integer jxwId) {
        this.jxwId = jxwId;
    }

    public Byte getTypeId() {
        return typeId;
    }

    public void setTypeId(Byte typeId) {
        this.typeId = typeId;
    }

    public Byte getStatus() {
        return status;
    }

    public void setStatus(Byte status) {
        this.status = status;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact == null ? null : contact.trim();
    }

    public String getContactphone() {
        return contactphone;
    }

    public void setContactphone(String contactphone) {
        this.contactphone = contactphone == null ? null : contactphone.trim();
    }

    public String getContactLeader() {
        return contactLeader;
    }

    public void setContactLeader(String contactLeader) {
        this.contactLeader = contactLeader == null ? null : contactLeader.trim();
    }

    public String getContactJxw() {
        return contactJxw;
    }

    public void setContactJxw(String contactJxw) {
        this.contactJxw = contactJxw == null ? null : contactJxw.trim();
    }

    public Integer getContactId() {
        return contactId;
    }

    public void setContactId(Integer contactId) {
        this.contactId = contactId;
    }

    public Integer getStarttime() {
        return starttime;
    }

    public void setStarttime(Integer starttime) {
        this.starttime = starttime;
    }

    public Integer getEndtime() {
        return endtime;
    }

    public void setEndtime(Integer endtime) {
        this.endtime = endtime;
    }

    public Integer getCreatetime() {
        return createtime;
    }

    public void setCreatetime(Integer createtime) {
        this.createtime = createtime;
    }

    public Float getTotalinvest() {
        return totalinvest;
    }

    public void setTotalinvest(Float totalinvest) {
        this.totalinvest = totalinvest;
    }

    public Float getPastinvest() {
        return pastinvest;
    }

    public void setPastinvest(Float pastinvest) {
        this.pastinvest = pastinvest;
    }

    public Boolean getImportance() {
        return importance;
    }

    public void setImportance(Boolean importance) {
        this.importance = importance;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }
}
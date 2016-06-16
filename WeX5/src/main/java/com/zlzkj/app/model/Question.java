package com.zlzkj.app.model;

public class Question {
    private Integer id;

    private String questionName;

    private Integer status;

    private Integer addTime;

    private String choiceNamea;

    private String choiceNameb;

    private String choiceNamec;

    private String choiceNamed;

    private Integer questionType;

    private Integer choiceValue;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getQuestionName() {
        return questionName;
    }

    public void setQuestionName(String questionName) {
        this.questionName = questionName == null ? null : questionName.trim();
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getAddTime() {
        return addTime;
    }

    public void setAddTime(Integer addTime) {
        this.addTime = addTime;
    }

    public String getChoiceNamea() {
        return choiceNamea;
    }

    public void setChoiceNamea(String choiceNamea) {
        this.choiceNamea = choiceNamea == null ? null : choiceNamea.trim();
    }

    public String getChoiceNameb() {
        return choiceNameb;
    }

    public void setChoiceNameb(String choiceNameb) {
        this.choiceNameb = choiceNameb == null ? null : choiceNameb.trim();
    }

    public String getChoiceNamec() {
        return choiceNamec;
    }

    public void setChoiceNamec(String choiceNamec) {
        this.choiceNamec = choiceNamec == null ? null : choiceNamec.trim();
    }

    public String getChoiceNamed() {
        return choiceNamed;
    }

    public void setChoiceNamed(String choiceNamed) {
        this.choiceNamed = choiceNamed == null ? null : choiceNamed.trim();
    }

    public Integer getQuestionType() {
        return questionType;
    }

    public void setQuestionType(Integer questionType) {
        this.questionType = questionType;
    }

    public Integer getChoiceValue() {
        return choiceValue;
    }

    public void setChoiceValue(Integer choiceValue) {
        this.choiceValue = choiceValue;
    }
}
package com.example.servingwebcontent.domain;

public class InterviewForm {
    public String pinCode;
    public boolean shaking;
    public boolean musclePain;
    public boolean headache;
    public boolean soreThroat;
    public boolean lossTasteAndSmell;
    public boolean closeContact;
    private String testType;

    public String getPinCode() {
        return pinCode;
    }

    public void setPinCode(String pinCode) {
        this.pinCode = pinCode;
    }
//
    public boolean getShaking() {
        return shaking;
    }

    public void setShaking(boolean shaking) {
        this.shaking = shaking;
    }

    public boolean getMusclePain() {
        return musclePain;
    }

    public void setMusclePain(boolean musclePain) {
        this.musclePain = musclePain;
    }

    public boolean getHeadache() {
        return headache;
    }

    public void setHeadache(boolean headache) {
        this.headache = headache;
    }

    public boolean getSoreThroat() {
        return soreThroat;
    }

    public void setSoreThroat(boolean soreThroat) {
        this.soreThroat = soreThroat;
    }

    public boolean getLossTasteAndSmell() {
        return lossTasteAndSmell;
    }

    public void setLossTasteAndSmell(boolean lossTasteAndSmell) {
        this.lossTasteAndSmell = lossTasteAndSmell;
    }

    public boolean getCloseContact() {
        return closeContact;
    }

    public void setCloseContact(boolean closeContact) {
        this.closeContact = closeContact;
    }

    public String getTestType() {
        return testType;
    }

    public void setTestType(String testType) {
        this.testType = testType;
    }

    @Override
    public String toString() {
        return "InterviewForm{" +
                "pinCode='" + pinCode + '\'' +
                ", shaking=" + shaking +
                ", musclePain=" + musclePain +
                ", headache=" + headache +
                ", soreThroat=" + soreThroat +
                ", lossTasteAndSmell=" + lossTasteAndSmell +
                ", closeContact=" + closeContact +
                ", testType='" + testType + '\'' +
                '}';
    }
}

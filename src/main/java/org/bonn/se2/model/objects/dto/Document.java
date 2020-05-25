/*
 * @version 0.1a
 * @author Coll@Aldernativ
 * @Programmer
 *
 *
 */

package org.bonn.se2.model.objects.dto;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Objects;

public class Document {

    private Integer documentID;
    private Integer userID;

    private String title;
    private String desc;
    private LocalDate date;
    private byte[] file;

    public Document(){

    }

    public Document(Integer documentID, Integer userID, String title, String desc, LocalDate date, byte[] file) {
        this.documentID = documentID;
        this.userID = userID;
        this.title = title;
        this.desc = desc;
        this.date = date;
        this.file = file;
    }

    public Integer getDocumentID() {
        return documentID;
    }

    public void setDocumentID(Integer documentID) {
        this.documentID = documentID;
    }

    public Integer getUserID() {
        return userID;
    }

    public void setUserID(Integer userID) {
        this.userID = userID;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public byte[] getFile() {
        return file;
    }

    public void setFile(byte[] file) {
        this.file = file;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Document document = (Document) o;
        return Objects.equals(getDocumentID(), document.getDocumentID()) &&
                Objects.equals(getUserID(), document.getUserID()) &&
                Objects.equals(getTitle(), document.getTitle()) &&
                Objects.equals(getDesc(), document.getDesc()) &&
                Objects.equals(getDate(), document.getDate()) &&
                Arrays.equals(getFile(), document.getFile());
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(getDocumentID(), getUserID(), getTitle(), getDesc(), getDate());
        result = 31 * result + Arrays.hashCode(getFile());
        return result;
    }

    @Override
    public String toString() {
        return "Document{" +
                "documentID=" + documentID +
                ", userID=" + userID +
                ", title='" + title + '\'' +
                ", desc='" + desc + '\'' +
                ", date=" + date +
                '}';
    }
}

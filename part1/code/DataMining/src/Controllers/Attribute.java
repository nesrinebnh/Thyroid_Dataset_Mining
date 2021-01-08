package Controllers;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Attribute {

	private StringProperty attr1, attr2, attr3, attr4, attr5, target;
	private StringProperty name, min, max, mode, mediane, mean;
	private String id;

	public Attribute(){}

	public Attribute(String attr1, String attr2,String attr3, String attr4, String attr5, String target){
		this.attr1 = new SimpleStringProperty(attr1);
		this.attr2 = new SimpleStringProperty(attr2);
		this.attr3 = new SimpleStringProperty(attr3);
		this.attr4 = new SimpleStringProperty(attr4);
		this.attr5 = new SimpleStringProperty(attr5);
		this.target = new SimpleStringProperty(target);
	}

	public Attribute(String id,String name, String min,String max, String mode, String mediane, String mean){
		this.name = new SimpleStringProperty(name);
		this.min = new SimpleStringProperty(min);
		this.max = new SimpleStringProperty(max);
		this.mode = new SimpleStringProperty(mode);
		this.mediane = new SimpleStringProperty(mediane);
		this.mean = new SimpleStringProperty(mean);
	}

	public String getattr1() {
        return attr1.get();
    }
    public void setattr1(String CIN) {
        this.attr1.set(CIN);
    }
    public StringProperty attr1Property() {
        return attr1;
    }

    public String getattr2() {
        return attr2.get();
    }
    public void setattr2(String CIN) {
        this.attr2.set(CIN);
    }
    public StringProperty attr2Property() {
        return attr2;
    }

    public String getattr3() {
        return attr3.get();
    }
    public void setattr3(String CIN) {
        this.attr3.set(CIN);
    }
    public StringProperty attr3Property() {
        return attr3;
    }

    public String getattr4() {
        return attr4.get();
    }
    public void setattr4(String CIN) {
        this.attr4.set(CIN);
    }
    public StringProperty attr4Property() {
        return attr4;
    }

    public String getattr5() {
        return attr5.get();
    }
    public void setattr5(String CIN) {
        this.attr5.set(CIN);
    }
    public StringProperty attr5Property() {
        return attr5;
    }

    public String gettarget() {
        return target.get();
    }
    public void settarget(String CIN) {
        this.target.set(CIN);
    }
    public StringProperty targetProperty() {
        return target;
    }

    public String getname() {
        return name.get();
    }
    public void setname(String CIN) {
        this.name.set(CIN);
    }
    public StringProperty nameProperty() {
        return name;
    }

    public String getmin() {
        return min.get();
    }
    public void setmin(String CIN) {
        this.min.set(CIN);
    }
    public StringProperty minProperty() {
        return min;
    }

    public String getmax() {
        return max.get();
    }
    public void setmax(String CIN) {
        this.max.set(CIN);
    }
    public StringProperty maxProperty() {
        return max;
    }

    public String getmode() {
        return mode.get();
    }
    public void setmode(String CIN) {
        this.mode.set(CIN);
    }
    public StringProperty modeProperty() {
        return mode;
    }

    public String getmediane() {
        return mediane.get();
    }
    public void setmediane(String CIN) {
        this.mediane.set(CIN);
    }
    public StringProperty medianeProperty() {
        return mediane;
    }

    public String getmean() {
        return mean.get();
    }
    public void setmean(String CIN) {
        this.mean.set(CIN);
    }
    public StringProperty meanProperty() {
        return mean;
    }

    public String getId() {
		return id;
	}
}

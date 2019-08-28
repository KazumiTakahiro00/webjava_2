package jp.co.systena.tigerscave.rpg_fighting_app.model;

public abstract class Profession {
  public abstract void Fight();
  protected String profession;  //職業
  protected String name;        //名前
  protected String method;      //攻撃手段

  public void setName(String name) {
    this.name = name;
  }
  public String getName() {
    return this.name;
  }

  public void setProfession(String profession) {
    this.profession = profession;
  }
  public String getProfession() {
    return this.profession;
  }

  public void setMethod(String method) {
    this.method = method;
  }
  public String getMethod() {
    return this.method;
  }

}

